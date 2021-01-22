package org.propagate.client.sse;

import org.propagate.client.sse.util.BackoffPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class SSEventConsumerImpl implements SSEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SSEventConsumerImpl.class);
    protected final ExecutorService executorService;
    protected final HttpClient client;
    protected final URI uri;
    protected final Consumer<SSEvent> consumer;
    protected final BackoffPolicy backoffPolicy;

    public SSEventConsumerImpl(ExecutorService executorService, HttpClient client, URI uri, Consumer<SSEvent> consumer, BackoffPolicy backoffPolicy) {
        this.executorService = executorService;
        this.client = client;
        this.uri = uri;
        this.consumer = consumer;
        this.backoffPolicy = backoffPolicy;
    }

    public SSEventConsumerImpl(URI uri, Consumer<SSEvent> consumer, BackoffPolicy backoffPolicy) {
        this(Executors.newCachedThreadPool(defaultThreadFactory()),
                HttpClient.newBuilder().build(),
                uri,
                consumer,
                backoffPolicy);
    }

    public SSEventConsumerImpl(URI uri, Consumer<SSEvent> consumer) {
        this(uri, consumer, BackoffPolicy.fixedDelay(Duration.ofMillis(3003)));
    }

    @Override
    public void start() {
        executorService.submit(new ConnectionRunnable(uri, client, backoffPolicy, consumer));
    }


    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }

    private static class ConnectionRunnable implements Runnable {
        private final URI uri;
        private final HttpClient client;
        private final BackoffPolicy backoffPolicy;
        private final Consumer<SSEvent> consumer;

        public ConnectionRunnable(URI uri, HttpClient client, BackoffPolicy backoffPolicy, Consumer<SSEvent> consumer) {
            this.uri = uri;
            this.client = client;
            this.backoffPolicy = backoffPolicy;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    final HttpResponse<InputStream> response = client.send(getRequest(), HttpResponse.BodyHandlers.ofInputStream());
                    if (response.statusCode() != 200) {
                        logger.info("Streaming error status={}, will retry in {} ms", response.statusCode(), backoffPolicy.nextDelayMillis());
                        Thread.sleep(backoffPolicy.nextDelayMillis());
                    } else if (response.statusCode() == 200) {
                        final BufferedReader br = new BufferedReader(new InputStreamReader(response.body()));
                        consumer.accept(getEvent(br));
                    }
                } catch (Exception ex) {
                    logger.error("Exception streaming connection, will retry in {} ms", backoffPolicy.nextDelayMillis(), ex);
                    try {
                        Thread.sleep(backoffPolicy.nextDelayMillis());
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted during backoff", e);
                    }
                }
            }
        }

        private HttpRequest getRequest() {
            return HttpRequest.newBuilder(uri).build();
        }

        private SSEvent getEvent(BufferedReader br) throws IOException {
            String id = null, event = null, data = null;
            String line = "";
            do {
                line = br.readLine();
                if (line.startsWith("id:")) id = line;
                else if (line.startsWith("event:")) event = line;
                else if (line.startsWith("data:")) data = line;
            } while (!line.startsWith("data:"));

            return new SSEvent(id != null ? id.substring(4) : null,
                    event != null ? event.substring(6) : null,
                    data != null ? data.substring(5) : null);
        }
    }

    private static class SSEThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        SSEThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "sse-pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static ThreadFactory defaultThreadFactory() {
        return new SSEThreadFactory();
    }
}

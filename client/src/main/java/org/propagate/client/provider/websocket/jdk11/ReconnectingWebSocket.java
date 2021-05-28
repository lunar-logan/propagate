package org.propagate.client.provider.websocket.jdk11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ReconnectingWebSocket implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ReconnectingWebSocket.class);
    private WebSocket webSocket = null;
    private final Supplier<WebSocket> webSocketSupplier;
    private final ScheduledExecutorService scheduler;

    public ReconnectingWebSocket(Supplier<WebSocket> webSocketSupplier, ScheduledExecutorService scheduler) {
        this.webSocketSupplier = webSocketSupplier;
        this.scheduler = scheduler;
    }

    public ReconnectingWebSocket(Supplier<WebSocket> webSocketSupplier) {
        this(webSocketSupplier, Executors.newSingleThreadScheduledExecutor());
    }

    @Override
    public void run() {
        createWebsocket();
        startPingTask();
    }

    private void createWebsocket() {
        abortWebsocket();
        for (;;) {
            try {
                webSocket = webSocketSupplier.get();
                logger.info("Created new websocket...");
                break;
            } catch (CompletionException ex) {
                logger.error("Failed to connect to websocket", ex);
            }
            logger.info("Retrying in {} seconds", 3);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void abortWebsocket() {
        if (webSocket == null)
            return;
        try {
            webSocket.abort();
        } catch (Exception ex) { // no-op
            ex.printStackTrace();
        }
    }

    private class PingTask implements Runnable {
        private final byte[] PING_MSG = "heartbeat".getBytes(StandardCharsets.UTF_8);

        @Override
        public void run() {
            logger.info("Pinging websocket server....");
            final ByteBuffer pingMessage = ByteBuffer.wrap(PING_MSG);
            webSocket.sendPing(pingMessage)
                    .thenAccept(ws -> logger.info("Successfully pinged {}", ws.toString()))
                    .exceptionally(ex -> {
                        logger.error("Failed to ping the websocket server", ex);
                        createWebsocket();
                        return null;
                    }).join();
        }
    }

    private void startPingTask() {
        scheduler.scheduleAtFixedRate(new PingTask(), 0, 5000, TimeUnit.MILLISECONDS);
    }
}

package org.propagate.reactive.websocket;

import org.propagate.common.domain.util.Either;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.*;

@Deprecated
public class SimpleEventBusImpl implements EventBus {
    private final BlockingDeque<Event<Object>> q = new LinkedBlockingDeque<>();
    private final Map<EventFetcher<?>, BlockingDeque<Event<?>>> sinks = Collections.synchronizedMap(new WeakHashMap<>());
    private final ExecutorService executorService;

    public SimpleEventBusImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.executorService.execute(new DispatchTask());
    }

    public SimpleEventBusImpl() {
        this(Executors.newCachedThreadPool());
    }

    private final class DispatchTask implements Runnable {
        @Override
        public void run() {
            Event<?> ev;
            try {
                while (true) {
                    do {
                        ev = q.poll(1, TimeUnit.MINUTES);
                    } while (ev == null);
                    Event<?> effectiveFinalEv = Event.builder()
                            .data(ev.getData())
                            .build();
                    System.out.println("Published: " +effectiveFinalEv);
                    sinks.forEach((ignore, privateQueue) -> privateQueue.offer(effectiveFinalEv));
                    System.out.println(sinks);
                }

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private final class EventFetcher<T> implements Callable<Event<T>> {
        private final BlockingDeque<Event<?>> privateQueue;

        public EventFetcher() {
            privateQueue = new LinkedBlockingDeque<>();
            sinks.put(this, privateQueue);
        }

        @Override
        @SuppressWarnings("unckecked")
        public Event<T> call() throws Exception {
            Event<?> ev;
            do {
                ev = privateQueue.poll(10, TimeUnit.SECONDS);
                System.out.println("Fetcher: " + ev);
            } while (ev == null);
            return (Event<T>) ev;
        }
    }

    @Override
    public <T> Flux<Event<T>> subscribe(String channel) {
        final EventFetcher<T> fetcher = new EventFetcher<>();
        return Flux.generate(eventSynchronousSink->{
            try {
                eventSynchronousSink.next(fetcher.call());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public <T> Mono<Either<Boolean>> publish(String channel, Event<T> event) {
        return Mono.fromSupplier(() -> {
            q.offer((Event<Object>) event);
            return Either.right(true);
        });
    }

    public static void main(String[] args) {
        EventBus evBus = new SimpleEventBusImpl();
        evBus.subscribe("")
                .doOnNext(ev -> {
                    System.out.println("Received: " +ev);
                })
                .subscribeOn(Schedulers.boundedElastic())
        .subscribe();


        System.out.println("Subscribed!");
        evBus.publish("", Event.builder().data("Hi").build())
        .subscribe();
    }
}

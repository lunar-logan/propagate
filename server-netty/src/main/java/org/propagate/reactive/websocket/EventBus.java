package org.propagate.reactive.websocket;

import org.propagate.common.domain.util.Either;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * DO NOT USE THIS
 */
@Deprecated
public interface EventBus {
    <T> Flux<Event<T>> subscribe(String channel);

    <T> Mono<Either<Boolean>> publish(String channel, Event<T> event);
}

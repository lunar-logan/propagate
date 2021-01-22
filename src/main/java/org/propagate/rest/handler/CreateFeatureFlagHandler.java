package org.propagate.rest.handler;

import lombok.AllArgsConstructor;
import org.propagate.domain.FeatureFlag;
import org.propagate.service.FeatureFlagService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateFeatureFlagHandler implements HandlerFunction<ServerResponse> {
    private final FeatureFlagService service;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(FeatureFlag.class)
                .log()
                .flatMapMany(service::createOrUpdate)
                .log()
                .collectList()
                .log()
                .flatMap(ffs -> ServerResponse.ok().body(BodyInserters.fromValue(ffs)))
                .log();
    }
}

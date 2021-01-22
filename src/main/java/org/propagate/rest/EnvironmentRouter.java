package org.propagate.rest;

import lombok.AllArgsConstructor;
import org.propagate.service.FeatureFlagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class EnvironmentRouter {
    private final FeatureFlagService service;

    @Bean
    public RouterFunction<ServerResponse> environmentRouterFunction() {
        return route()
                .POST("/api/v1/env",
                        req -> req.bodyToMono(String.class)
                                .flatMap(service::createEnvironment)
                                .flatMap(env -> ServerResponse.ok().bodyValue(env)))
                .build();
    }
}

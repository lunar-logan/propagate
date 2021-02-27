package org.propagate.reactive.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.domain.Environment;
import org.propagate.reactive.common.exception.ExceptionUtils;
import org.propagate.reactive.featureflag.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/environments")
@AllArgsConstructor
@Slf4j
public class EnvironmentController {
    private final FeatureFlagService service;

    @PostMapping
    public Mono<ResponseEntity<Environment>> createEnvironment(@RequestBody @Valid Environment env) {
        return service.createEnvironment(env.getId())
                .map(ResponseEntity::ok);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<?>> handler(Exception ex) {
        return Mono.fromSupplier(() -> ExceptionUtils.toString(ex))
                .log()
                .map(sw -> ResponseEntity.status(ExceptionUtils.getHttpStatusCode(ex)).body(Map.of("exception", sw)));
    }
}

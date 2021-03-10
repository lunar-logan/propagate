package org.propagate.reactive.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.reactive.common.exception.ExceptionUtils;
import org.propagate.reactive.featureflag.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ff")
@AllArgsConstructor
@Slf4j
public class FeatureFlagController {
    private final FeatureFlagService service;

    @PostMapping
    public Mono<ResponseEntity<FeatureFlag>> createOrUpdateFeatureFlag(@RequestBody FeatureFlag flag) {
        return Mono.justOrEmpty(flag)
                .log()
                .flatMap(service::createOrUpdate)
                .log()
                .map(ResponseEntity::ok);
    }

    @GetMapping("{key}")
    public Mono<ResponseEntity<FeatureFlag>> getFeatureFlagByKey(@PathVariable String key) {
        return service.getFeatureFlagByKey(key)
                .log()
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<List<FeatureFlag>>> getAllFeatureFlags() {
        return service.getAllFeatureFlags()
                .collectList()
                .log()
                .map(ResponseEntity::ok);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<?>> handler(Exception ex) {
        return Mono.fromSupplier(() -> ExceptionUtils.toString(ex))
                .log()
                .map(sw -> ResponseEntity.status(ExceptionUtils.getHttpStatusCode(ex)).body(Map.of("exception", sw)));
    }
}

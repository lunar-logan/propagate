package org.propagate.reactive.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.reactive.common.exception.ExceptionUtils;
import org.propagate.reactive.featureflag.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/api/v1/ff")
@AllArgsConstructor
@Validated
@Slf4j
public class FeatureFlagController {
    private final FeatureFlagService service;
    private final ScheduledExecutorService scheduledExecutorService = null;//Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @PostMapping
    public Mono<ResponseEntity<List<FeatureFlag>>> createFeatureFlags(@RequestBody @Valid FeatureFlag flag) {
        return service.createOrUpdate(flag)
                .collectList()
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

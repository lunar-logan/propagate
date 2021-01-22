package org.propagate.rest;

import lombok.AllArgsConstructor;
import org.propagate.domain.FeatureFlag;
import org.propagate.service.FeatureFlagService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ff")
@AllArgsConstructor
public class FeatureFlagController {
    private final FeatureFlagService service;

    @PostMapping
    public Mono<ResponseEntity<List<FeatureFlag>>> createFeatureFlags(@RequestBody @Valid FeatureFlag flag) {
        return service.createOrUpdate(flag)
                .collectList()
                .log()
                .map(ResponseEntity::ok);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<FeatureFlag>> emitFeatureFlags() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(it -> service.getAllFeatureFlags()
                        .collectList()
                        .log());
    }
}

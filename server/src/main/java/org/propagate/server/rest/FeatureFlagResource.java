package org.propagate.server.rest;

import io.atlassian.fugue.Option;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;
import org.propagate.core.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/featureflag")
@Slf4j
@AllArgsConstructor
public class FeatureFlagResource extends AbstractFeatureFlagResource {
    private final FeatureFlagService service;

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagRestEntity> getFeatureFlagById(@PathVariable String id) {
        return Option.option(id)
                .flatMap(ffId -> Option.fromOptional(service.getFeatureFlagById(ffId)))
                .map(this::toFeatureFlagRestEntity)
                .fold(
                        () -> ResponseEntity.notFound().build(),
                        ResponseEntity::ok
                );
    }

    @GetMapping
    public ResponseEntity<List<FeatureFlagRestEntity>> getAllFeatureFlags() {
        return Option.option(service.getAllFeatureFlags())
                .map(Collection::stream)
                .map(stream -> stream.map(this::toFeatureFlagRestEntity))
                .map(stream -> stream.collect(Collectors.toList()))
                .fold(
                        () -> ResponseEntity.notFound().build(),
                        ResponseEntity::ok
                );
    }

    @PostMapping
    public ResponseEntity<FeatureFlagRestEntity> createFeatureFlag(@RequestBody @Valid FeatureFlagRestEntity restEntity) {
        return Option.option(restEntity)
                .map(this::toFeatureFlag)
                .map(service::createFeatureFlag)
                .map(this::toFeatureFlagRestEntity)
                .fold(
                        () -> ResponseEntity.badRequest().build(),
                        entity -> ResponseEntity.created(URI.create("/api/v1/featureflag/" + entity.getId())).body(entity)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeatureFlagById(@PathVariable String id) {
        service.deleteFeatureFlagById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/debug/all")
    public ResponseEntity<?> deleteAllFeatureFlagsDebug() {
        service.deleteAllFeatureFlags();
        return ResponseEntity.ok().build();
    }
}

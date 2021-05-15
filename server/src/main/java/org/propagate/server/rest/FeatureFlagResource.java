package org.propagate.server.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;
import org.propagate.server.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/featureflag")
@Slf4j
@AllArgsConstructor
public class FeatureFlagResource extends AbstractFeatureFlagResource {
    private final FeatureFlagService service;

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagRestEntity> getFeatureFlagById(@PathVariable String id) {
        return service.getFeatureFlagById(id)
                .map(this::toFeatureFlagRestEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeatureFlagRestEntity>> getAllFeatureFlagsDebug() {
        final List<FeatureFlagRestEntity> allFeatureFlags = service.getAllFeatureFlagsDebug().stream()
                .map(this::toFeatureFlagRestEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allFeatureFlags);
    }

    @PostMapping
    public ResponseEntity<FeatureFlagRestEntity> createFeatureFlag(@RequestBody @Valid FeatureFlagRestEntity restEntity) {
        return Optional.ofNullable(service.createFeatureFlag(restEntity))
                .map(this::toFeatureFlagRestEntity)
                .map(entity -> ResponseEntity.created(URI.create("/api/v1/featureflag/" + entity.getId())).body(entity))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeatureFlagById(@PathVariable String id) {
        service.deleteFeatureFlagById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/debug/all")
    public ResponseEntity<?> deleteAllFeatureFlagsDebug() {
        service.deleteAllFeatureFlagsDebug();
        return ResponseEntity.ok().build();
    }
}

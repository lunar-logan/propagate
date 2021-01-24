package org.propagate.server.rest;

import lombok.AllArgsConstructor;
import org.propagate.common.domain.Environment;
import org.propagate.server.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/environments")
@AllArgsConstructor
public class EnvironmentController {
    private final FeatureFlagService service;

    @PostMapping
    public ResponseEntity<Environment> createEnvironment(@RequestBody @Valid Environment env) {
        return service.createEnvironment(env.getId())
                .map(ResponseEntity::ok)
                .blockingGet();
    }
}

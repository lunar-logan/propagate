package org.propagate.reactive.featureflag.service;

import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface FeatureFlagService {
    Mono<Environment> createEnvironment(@NotEmpty String name);

    Mono<Void> deleteEnvironment(@NotEmpty String name);

    Flux<Environment> getAllEnvironments();

    Flux<FeatureFlag> createOrUpdate(@NotNull @Valid FeatureFlag featureFlag);

    Flux<FeatureFlag> getAllFeatureFlagsByKey(@NotEmpty String key);

    Flux<FeatureFlag> getAllFeatureFlags();

    Flux<FeatureFlag> archiveFeatureFlag(@NotEmpty String key);
}

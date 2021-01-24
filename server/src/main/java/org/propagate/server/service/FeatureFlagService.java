package org.propagate.server.service;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface FeatureFlagService {
    Maybe<Environment> createEnvironment(@NotEmpty String name);

    Maybe<Void> deleteEnvironment(@NotEmpty String name);

    Observable<Environment> getAllEnvironments();

    Observable<FeatureFlag> createOrUpdate(@NotNull @Valid FeatureFlag featureFlag);

    Observable<FeatureFlag> getAllFeatureFlagsByKey(@NotEmpty String key);

    Observable<FeatureFlag> getAllFeatureFlags();

    Observable<FeatureFlag> archiveFeatureFlag(@NotEmpty String key);
}

package org.propagate.server.service;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface FeatureFlagService {
    FeatureFlag createFeatureFlag(@NotNull final FeatureFlagRestEntity featureFlagRestEntity);

    Optional<FeatureFlag> getFeatureFlagById(@NotBlank String featureFlagId);

    List<FeatureFlag> getAllFeatureFlagsDebug();

    List<FeatureFlag> getAllFeatureFlags(int page, int size);

    void deleteFeatureFlagById(@NotBlank String id);

    void deleteAllFeatureFlagByIds(@NotEmpty Iterable<String> ids);

    void deleteAllFeatureFlagsDebug();
}

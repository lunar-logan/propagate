package org.propagate.core.service;

import org.propagate.common.domain.FeatureFlag;

import java.util.List;
import java.util.Optional;

public interface FeatureFlagService {
    FeatureFlag createFeatureFlag(final FeatureFlag featureFlag);

    Optional<FeatureFlag> getFeatureFlagById(String featureFlagId);

    List<FeatureFlag> getAllFeatureFlagsDebug();

    List<FeatureFlag> getAllFeatureFlags(int page, int size);

    void deleteFeatureFlagById(String id);

    void deleteAllFeatureFlagByIds(Iterable<String> ids);

    void deleteAllFeatureFlags();
}

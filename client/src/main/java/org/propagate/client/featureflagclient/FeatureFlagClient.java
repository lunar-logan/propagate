package org.propagate.client.featureflagclient;

import org.propagate.common.domain.util.Either;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.util.List;

public interface FeatureFlagClient {
    Either<List<FeatureFlagRestEntity>> getAllFeatureFlags();
}

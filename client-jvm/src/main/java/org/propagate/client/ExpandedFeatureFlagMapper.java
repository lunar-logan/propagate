package org.propagate.client;

import org.propagate.common.domain.FeatureFlag;

public interface ExpandedFeatureFlagMapper {
    ExpandedFeatureFlag map(FeatureFlag flag);
}

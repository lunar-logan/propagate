package org.propagate.client.mapper;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.common.domain.FeatureFlag;

public interface ExpandedFeatureFlagMapper {
    ExpandedFeatureFlag map(FeatureFlag flag);
}

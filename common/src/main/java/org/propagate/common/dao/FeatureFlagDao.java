package org.propagate.common.dao;

import org.propagate.common.domain.FeatureFlag;

import java.util.List;
import java.util.Optional;

public interface FeatureFlagDao extends CrudOperations<FeatureFlag, String>{
    Optional<FeatureFlag> findByKey(String key);
    List<FeatureFlag> findByName(String name);
}

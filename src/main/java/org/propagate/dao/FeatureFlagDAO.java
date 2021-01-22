package org.propagate.dao;

import org.propagate.domain.FeatureFlag;
import org.propagate.domain.ID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FeatureFlagDAO extends ReactiveMongoRepository<FeatureFlag, ID> {
}

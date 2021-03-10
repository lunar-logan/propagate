package org.propagate.reactive.featureflag.dao;

import org.propagate.reactive.featureflag.entity.FeatureFlagEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FeatureFlagDAO extends ReactiveMongoRepository<FeatureFlagEntity, String> {
}

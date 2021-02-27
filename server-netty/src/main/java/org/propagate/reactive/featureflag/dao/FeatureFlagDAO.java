package org.propagate.reactive.featureflag.dao;

import org.propagate.reactive.featureflag.FeatureFlagEntity;
import org.propagate.reactive.featureflag.IDEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FeatureFlagDAO extends ReactiveMongoRepository<FeatureFlagEntity, IDEntity> {
}

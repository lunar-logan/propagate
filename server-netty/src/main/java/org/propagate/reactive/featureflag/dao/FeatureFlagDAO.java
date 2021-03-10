package org.propagate.reactive.featureflag.dao;

import org.propagate.reactive.featureflag.entity.FeatureFlagEntity;
import org.propagate.reactive.featureflag.IDEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FeatureFlagDAO extends ReactiveMongoRepository<FeatureFlagEntity, IDEntity> {
    Flux<FeatureFlagEntity> findAllByEnvironment(String environment);
}

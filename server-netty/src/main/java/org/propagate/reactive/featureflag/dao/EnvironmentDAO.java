package org.propagate.reactive.featureflag.dao;

import org.propagate.reactive.featureflag.entity.EnvironmentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EnvironmentDAO extends ReactiveMongoRepository<EnvironmentEntity, String> {
}

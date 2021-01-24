package org.propagate.server.dao;

import org.propagate.server.entity.EnvironmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnvironmentDAO extends MongoRepository<EnvironmentEntity, String> {
}

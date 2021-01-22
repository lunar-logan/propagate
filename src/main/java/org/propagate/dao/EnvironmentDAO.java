package org.propagate.dao;

import org.propagate.domain.Environment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EnvironmentDAO extends ReactiveMongoRepository<Environment, String> {
}

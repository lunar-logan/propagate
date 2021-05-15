package org.propagate.persistence.mongo.dao;

import org.propagate.persistence.mongo.entity.NamespaceMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NamespaceMongoDao extends MongoRepository<NamespaceMongoEntity, String> {
}

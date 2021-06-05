package org.propagate.persistence.mongo.dao;

import org.propagate.persistence.mongo.entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoDao extends MongoRepository<UserMongoEntity, String> {
    Optional<UserMongoEntity> findByEmail(String email);
}

package org.propagate.persistence.mongo.dao;

import org.propagate.persistence.mongo.entity.FeatureFlagMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeatureFlagMongoDao extends MongoRepository<FeatureFlagMongoEntity, String> {
    Optional<FeatureFlagMongoEntity> findByKey(String key);
}

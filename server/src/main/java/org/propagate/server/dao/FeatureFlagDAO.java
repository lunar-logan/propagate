package org.propagate.server.dao;

import org.propagate.server.entity.FeatureFlagEntity;
import org.propagate.server.entity.IDEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureFlagDAO extends MongoRepository<FeatureFlagEntity, IDEntity> {
}

package org.propagate.persistence.mongo.dao;

import lombok.AllArgsConstructor;
import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.persistence.mongo.entity.FeatureFlagMongoEntity;
import org.propagate.persistence.mongo.helper.ConversionHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FeatureFlagDaoImpl implements FeatureFlagDao {
    private final FeatureFlagMongoDao featureFlagMongoDao;

    @Override
    public FeatureFlag save(FeatureFlag featureFlag) {
        final FeatureFlagMongoEntity featureFlagMongoEntity = featureFlagMongoDao.save(ConversionHelper.convert(featureFlag));
        return ConversionHelper.convert(featureFlagMongoEntity);
    }

    @Override
    public Optional<FeatureFlag> findById(String id) {
        return featureFlagMongoDao.findById(id).map(ConversionHelper::convert);
    }

    @Override
    public List<FeatureFlag> findAll() {
        return featureFlagMongoDao.findAll().stream()
                .map(ConversionHelper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeatureFlag> findAll(int page, int size) {
        return featureFlagMongoDao.findAll(PageRequest.of(page, size))
                .map(ConversionHelper::convert)
                .toList();
    }

    @Override
    public void delete(FeatureFlag entity) {
        featureFlagMongoDao.deleteById(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        featureFlagMongoDao.deleteById(id);
    }

    @Override
    public Optional<FeatureFlag> findByKey(String key) {
        return featureFlagMongoDao.findByKey(key).map(ConversionHelper::convert);
    }

    @Override
    public List<FeatureFlag> findByName(String name) {
        throw new UnsupportedOperationException("Find by name is unsupported");
    }
}

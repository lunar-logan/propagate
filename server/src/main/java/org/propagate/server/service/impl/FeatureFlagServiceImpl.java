package org.propagate.server.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;
import org.propagate.server.service.ConversionHelper;
import org.propagate.server.service.FeatureFlagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final FeatureFlagDao dao;

    @Override
    @Transactional
    public FeatureFlag createFeatureFlag(@NotNull FeatureFlagRestEntity featureFlagRestEntity) {
        final FeatureFlag featureFlag = ConversionHelper.convert(featureFlagRestEntity);
        log.info("Converted: {}", featureFlag);
        return dao.save(featureFlag);
    }

    @Override
    public Optional<FeatureFlag> getFeatureFlagById(@NotBlank String featureFlagId) {
        return dao.findById(featureFlagId);
    }

    @Override
    public List<FeatureFlag> getAllFeatureFlagsDebug() {
        return dao.findAll();
    }

    @Override
    public List<FeatureFlag> getAllFeatureFlags(int page, int size) {
        return dao.findAll(page, size);
    }

    @Override
    @Transactional
    public void deleteFeatureFlagById(@NotBlank String id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllFeatureFlagByIds(@NotEmpty Iterable<String> ids) {
        if(ids != null) {
            for (String id : ids) {
                deleteFeatureFlagById(id);
            }
        }
    }

    @Override
    @Transactional
    public void deleteAllFeatureFlagsDebug() {
        for (FeatureFlag featureFlag : dao.findAll()) {
            dao.delete(featureFlag);
        }
    }
}

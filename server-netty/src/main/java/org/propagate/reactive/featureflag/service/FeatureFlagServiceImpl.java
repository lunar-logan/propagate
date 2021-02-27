package org.propagate.reactive.featureflag.service;

import lombok.AllArgsConstructor;
import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.reactive.featureflag.EnvironmentEntity;
import org.propagate.reactive.featureflag.FeatureFlagEntity;
import org.propagate.reactive.featureflag.IDEntity;
import org.propagate.reactive.featureflag.dao.EnvironmentDAO;
import org.propagate.reactive.featureflag.dao.FeatureFlagDAO;
import org.propagate.reactive.featureflag.internal.ConversionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final FeatureFlagDAO featureFlagDAO;
    private final EnvironmentDAO environmentDAO;

    @Override
    @Transactional
    public Mono<Environment> createEnvironment(@NotEmpty String name) {
        return Mono.fromCallable(() -> EnvironmentEntity.builder()
                .id(name)
                .name(name)
                .build())
                .flatMap(environmentDAO::save)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Mono<Void> deleteEnvironment(@NotEmpty String name) {
        return Mono.fromCallable(() -> {
            environmentDAO.deleteById(name);
            return null;
        });
    }

    @Override
    public Flux<Environment> getAllEnvironments() {
        return environmentDAO.findAll()
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Flux<FeatureFlag> createOrUpdate(@NotNull FeatureFlag featureFlag) {
        return getAllEnvironments()
                .map(env -> featureFlag.getId().toBuilder().env(env.getId()).build())
                .map(id -> featureFlag.toBuilder().id(id).build())
                .map(ConversionUtils::toEntity)
                .collectList()
                .flatMapMany(featureFlagDAO::saveAll)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Flux<FeatureFlag> getAllFeatureFlagsByKey(@NotEmpty String key) {
        return getAllEnvironments()
                .map(env -> IDEntity.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .collectList()
                .flatMapMany(featureFlagDAO::findAllById)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Flux<FeatureFlag> getAllFeatureFlags() {
        return featureFlagDAO.findAll()
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Flux<FeatureFlag> archiveFeatureFlag(String key) {
        return getAllEnvironments()
                .map(env -> IDEntity.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .collectList()
                .flatMapMany(featureFlagDAO::findAllById)
                .map(ff -> ff.toBuilder().archived(true).build())
                .collectList()
                .flatMapMany(featureFlagDAO::saveAll)
                .map(ConversionUtils::toDomainModel);
    }
}
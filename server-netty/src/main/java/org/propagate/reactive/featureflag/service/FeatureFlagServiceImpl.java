package org.propagate.reactive.featureflag.service;

import lombok.AllArgsConstructor;
import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.reactive.featureflag.dao.EnvironmentDAO;
import org.propagate.reactive.featureflag.dao.FeatureFlagDAO;
import org.propagate.reactive.featureflag.entity.EnvironmentEntity;
import org.propagate.reactive.featureflag.internal.ConversionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    public Mono<Void> deleteEnvironment(@NotEmpty String name) {
//        return environmentDAO.findById(name)
//
//                // Delete all the feature flags mapped with this environment
//                .flatMap(env -> featureFlagDAO.findAllByEnvironment(env.getId()).collectList())
//                .flatMap(featureFlagDAO::deleteAll)
//
//                // Finally delete the environment
//                .then(environmentDAO.deleteById(name));
        return Mono.error(new UnsupportedOperationException());
    }

    @Override
    public Flux<Environment> getAllEnvironments() {
        return environmentDAO.findAll()
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Mono<Environment> getEnvironment(String name) {
        return environmentDAO.findById(name)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Mono<FeatureFlag> createOrUpdate(@NotNull FeatureFlag featureFlag) {
        // Feature flag has to be created for every environment
        return Mono.fromSupplier(() -> ConversionUtils.toEntity(featureFlag))
                .flatMap(featureFlagDAO::save)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Mono<FeatureFlag> getFeatureFlagByKey(String key) {
        return Mono.justOrEmpty(key)
                .flatMap(ffKey -> featureFlagDAO.findById(key))
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Flux<FeatureFlag> getAllFeatureFlags() {
        return featureFlagDAO.findAll()
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Mono<FeatureFlag> archiveFeatureFlag(String key) {
        return Mono.justOrEmpty(key)
                .flatMap(featureFlagDAO::findById)
                .doOnNext(entity -> entity.setArchived(true))
                .flatMap(featureFlagDAO::save)
                .map(ConversionUtils::toDomainModel);
    }
}

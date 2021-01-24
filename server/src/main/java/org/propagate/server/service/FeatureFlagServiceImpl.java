package org.propagate.server.service;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.server.dao.EnvironmentDAO;
import org.propagate.server.dao.FeatureFlagDAO;
import org.propagate.server.entity.EnvironmentEntity;
import org.propagate.server.entity.IDEntity;
import org.propagate.server.util.ConversionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final FeatureFlagDAO featureFlagDAO;
    private final EnvironmentDAO environmentDAO;

    @Override
    @Transactional
    public Maybe<Environment> createEnvironment(@NotEmpty String name) {
        return Maybe.fromCallable(() -> EnvironmentEntity.builder()
                .id(name)
                .name(name)
                .build())
                .map(environmentDAO::save)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Maybe<Void> deleteEnvironment(@NotEmpty String name) {
        return Maybe.fromCallable(() -> {
            environmentDAO.deleteById(name);
            return null;
        });
    }

    @Override
    public Observable<Environment> getAllEnvironments() {
        return Observable.fromIterable(environmentDAO.findAll())
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Observable<FeatureFlag> createOrUpdate(@NotNull FeatureFlag featureFlag) {
        return getAllEnvironments()
                .map(env -> featureFlag.getId().toBuilder().env(env.getId()).build())
                .map(id -> featureFlag.toBuilder().id(id).build())
                .map(ConversionUtils::toEntity)
                .toList()
                .flattenAsObservable(featureFlagDAO::saveAll)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Observable<FeatureFlag> getAllFeatureFlagsByKey(@NotEmpty String key) {
        return getAllEnvironments()
                .map(env -> IDEntity.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .toList()
                .flattenAsObservable(featureFlagDAO::findAllById)
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    public Observable<FeatureFlag> getAllFeatureFlags() {
        return Observable.fromIterable(featureFlagDAO.findAll())
                .map(ConversionUtils::toDomainModel);
    }

    @Override
    @Transactional
    public Observable<FeatureFlag> archiveFeatureFlag(String key) {
        return getAllEnvironments()
                .map(env -> IDEntity.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .toList()
                .flattenAsObservable(featureFlagDAO::findAllById)
                .map(ff -> ff.toBuilder().archived(true).build())
                .toList()
                .flattenAsObservable(featureFlagDAO::saveAll)
                .map(ConversionUtils::toDomainModel);
    }
}

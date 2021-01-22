package org.propagate.service;

import lombok.AllArgsConstructor;
import org.propagate.dao.EnvironmentDAO;
import org.propagate.dao.FeatureFlagDAO;
import org.propagate.domain.Environment;
import org.propagate.domain.FeatureFlag;
import org.propagate.domain.ID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final FeatureFlagDAO featureFlagDAO;
    private final EnvironmentDAO environmentDAO;

    @Override
    public Mono<Environment> createEnvironment(@NotEmpty String name) {
        return environmentDAO.save(Environment.builder()
                .id(name)
                .name(name)
                .build());
    }

    @Override
    public Mono<Void> deleteEnvironment(@NotEmpty String name) {
        return environmentDAO.deleteById(name);
    }

    @Override
    public Flux<Environment> getAllEnvironments() {
        return environmentDAO.findAll();
    }

    @Override
    @Transactional
    public Flux<FeatureFlag> createOrUpdate(@NotNull @Valid FeatureFlag featureFlag) {
        return getAllEnvironments()
                .map(env -> featureFlag.getId().toBuilder().env(env.getId()).build())
                .map(id -> featureFlag.toBuilder().id(id).build())
                .collectList()
                .flatMapMany(featureFlagDAO::saveAll);
    }

    @Override
    public Flux<FeatureFlag> getAllFeatureFlagsByKey(@NotEmpty String key) {
        return getAllEnvironments()
                .map(env -> ID.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .collectList()
                .flatMapMany(featureFlagDAO::findAllById);
    }

    @Override
    public Flux<FeatureFlag> getAllFeatureFlags() {
        return featureFlagDAO.findAll();
    }

    @Override
    public Flux<FeatureFlag> archiveFeatureFlag(String key) {
        return getAllEnvironments()
                .map(env -> ID.builder()
                        .key(key)
                        .env(env.getId())
                        .build())
                .collectList()
                .flatMapMany(featureFlagDAO::findAllById)
                .map(ff -> ff.toBuilder().archived(true).build())
                .collectList()
                .flatMapMany(featureFlagDAO::saveAll);
    }
}

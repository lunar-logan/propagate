package org.propagate.server.util;

import org.propagate.common.domain.Environment;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.FeatureFlagType;
import org.propagate.common.domain.ID;
import org.propagate.common.domain.rollout.ConditionalRollout;
import org.propagate.common.domain.rollout.PercentageRollout;
import org.propagate.server.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public final class ConversionUtils {
    public static EnvironmentEntity toEntity(Environment env) {
        return EnvironmentEntity.builder()
                .id(env.getId())
                .name(env.getName())
                .build();
    }

    public static Environment toDomainModel(EnvironmentEntity entity) {
        return Environment.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static FeatureFlagEntity toEntity(FeatureFlag flag) {
        return FeatureFlagEntity.builder()
                .id(toEntity(flag.getId()))
                .name(flag.getName())
                .description(flag.getDescription())
                .type(flag.getType().name())
                .variations(flag.getVariations())
                .defaultRollout(flag.getDefaultRollout())
                .conditionalRollout(emptyIfNull(flag.getConditionalRollout()).stream()
                        .map(ConversionUtils::toEntity)
                        .collect(Collectors.toList()))
                .percentRollout(emptyIfNull(flag.getPercentRollout()).stream()
                        .map(ConversionUtils::toEntity)
                        .collect(Collectors.toList()))
                .archived(flag.isArchived())
                .build();
    }

    public static FeatureFlag toDomainModel(FeatureFlagEntity flag) {
        return FeatureFlag.builder()
                .id(toDomainModel(flag.getId()))
                .name(flag.getName())
                .description(flag.getDescription())
                .type(FeatureFlagType.valueOf(flag.getType().toUpperCase()))
                .variations(flag.getVariations())
                .defaultRollout(flag.getDefaultRollout())
                .conditionalRollout(emptyIfNull(flag.getConditionalRollout()).stream()
                        .map(ConversionUtils::toDomainModel)
                        .collect(Collectors.toList()))
                .percentRollout(emptyIfNull(flag.getPercentRollout()).stream()
                        .map(ConversionUtils::toDomainModel)
                        .collect(Collectors.toList()))
                .archived(flag.isArchived())
                .build();
    }

    private static PercentRolloutEntity toEntity(PercentageRollout percentageRollout) {
        return PercentRolloutEntity.builder()
                .variation(percentageRollout.getVariation())
                .percent(percentageRollout.getPercent())
                .build();
    }

    private static PercentageRollout toDomainModel(PercentRolloutEntity percentageRollout) {
        return PercentageRollout.builder()
                .variation(percentageRollout.getVariation())
                .percent(percentageRollout.getPercent())
                .build();
    }

    private static <T> List<T> emptyIfNull(List<T> list) {
        if (list == null) {
            return List.of();
        }
        return list;
    }

    private static ConditionalRolloutEntity toEntity(ConditionalRollout rollout) {
        return ConditionalRolloutEntity.builder()
                .expression(rollout.getExpression())
                .variation(rollout.getVariation())
                .build();
    }

    private static ConditionalRollout toDomainModel(ConditionalRolloutEntity rollout){
        return ConditionalRollout.builder()
                .expression(rollout.getExpression())
                .variation(rollout.getVariation())
                .build();
    }

    private static IDEntity toEntity(ID id) {
        return IDEntity.builder()
                .key(id.getKey())
                .env(id.getEnv())
                .build();
    }

    private static ID toDomainModel(IDEntity id) {
        return ID.builder()
                .key(id.getKey())
                .env(id.getEnv())
                .build();
    }
}

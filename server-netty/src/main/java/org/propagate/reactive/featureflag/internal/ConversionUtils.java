package org.propagate.reactive.featureflag.internal;

import org.propagate.common.domain.*;
import org.propagate.common.domain.rollout.ConditionalRollout;
import org.propagate.common.domain.rollout.PercentageRollout;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.reactive.featureflag.entity.*;

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
                .id(flag.getKey())
                .name(flag.getName())
                .description(flag.getDescription())
                .type(flag.getType().name())
                .variations(flag.getVariations())
                .defaultRollout(flag.getDefaultRollout())
                .rolloutRules(flag.getRolloutRules()
                        .stream()
                        .map(ConversionUtils::toEntity)
                        .collect(Collectors.toList()))
                .targeting(flag.isTargeting())
                .archived(flag.isArchived())
                .build();
    }

    private static RolloutEntity toEntity(Rollout rollout) {
        return RolloutEntity.builder()
                .environment(rollout.getEnvironment())
                .rules(rollout.getRules().stream()
                        .map(ConversionUtils::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private static RolloutRuleEntity toEntity(RolloutRule rolloutRule) {
        if (rolloutRule instanceof ConditionalRollout) {
            return ConditionalRolloutEntity.builder()
                    .expression(((ConditionalRollout) rolloutRule).getExpression())
                    .variation(rolloutRule.getVariation())
                    .type("conditional")
                    .build();
        }
        return PercentRolloutEntity.builder()
                .percent(((PercentageRollout) rolloutRule).getPercent())
                .variation(rolloutRule.getVariation())
                .type("percent")
                .build();
    }

    public static FeatureFlag toDomainModel(FeatureFlagEntity flag) {
        return FeatureFlag.builder()
                .key(flag.getId())
                .name(flag.getName())
                .description(flag.getDescription())
                .type(FeatureFlagType.valueOf(flag.getType().toUpperCase()))
                .variations(flag.getVariations())
                .defaultRollout(flag.getDefaultRollout())
                .rolloutRules(flag.getRolloutRules().stream()
                        .map(ConversionUtils::toDomainModel)
                        .collect(Collectors.toList()))
                .targeting(flag.isTargeting())
                .archived(flag.isArchived())
                .build();
    }

    private static Rollout toDomainModel(RolloutEntity rolloutEntity) {
        return Rollout.builder()
                .environment(rolloutEntity.getEnvironment())
                .rules(rolloutEntity.getRules().stream()
                        .map(ConversionUtils::toDomainModel)
                        .collect(Collectors.toList()))
                .build();
    }

    private static RolloutRule toDomainModel(RolloutRuleEntity rolloutRuleEntity) {
        if (rolloutRuleEntity instanceof ConditionalRolloutEntity) {
            return ConditionalRollout.builder()
                    .expression(((ConditionalRolloutEntity) rolloutRuleEntity).getExpression())
                    .variation(rolloutRuleEntity.getVariation())
                    .type(rolloutRuleEntity.getType())
                    .build();
        }
        return PercentageRollout.builder()
                .variation(rolloutRuleEntity.getVariation())
                .percent(((PercentRolloutEntity) rolloutRuleEntity).getPercent())
                .type(rolloutRuleEntity.getType())
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

    private static ConditionalRollout toDomainModel(ConditionalRolloutEntity rollout) {
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

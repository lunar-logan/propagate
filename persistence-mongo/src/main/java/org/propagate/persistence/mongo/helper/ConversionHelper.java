package org.propagate.persistence.mongo.helper;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.FeatureFlagType;
import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.Variation;
import org.propagate.common.domain.rollout.ConditionalDistribution;
import org.propagate.common.domain.rollout.PercentDistribution;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.common.domain.rollout.RolloutRuleType;
import org.propagate.persistence.mongo.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public final class ConversionHelper {
    private ConversionHelper() {
    }

    public static FeatureFlagMongoEntity convert(FeatureFlag featureFlag) {
        return FeatureFlagMongoEntity.builder()
                .id(featureFlag.getId())
                .key(featureFlag.getKey())
                .name(featureFlag.getName())
                .description(featureFlag.getDescription())
                .type(featureFlag.getType().name())
                .variations(featureFlag.getVariations().stream().map(Variation::getVariation).collect(Collectors.toSet()))
                .rolloutRules(featureFlag.getRolloutRules() != null ? featureFlag.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toSet()) : null)
                .archived(featureFlag.isArchived())
                .targeting(featureFlag.isTargeting())
                .created(featureFlag.getCreated())
                .lastUpdated(featureFlag.getLastUpdated())
                .build();
    }

    public static FeatureFlag convert(FeatureFlagMongoEntity featureFlag) {
        return FeatureFlag.builder()
                .id(featureFlag.getId())
                .key(featureFlag.getKey())
                .name(featureFlag.getName())
                .description(featureFlag.getDescription())
                .type(FeatureFlagType.valueOf(featureFlag.getType()))
                .variations(featureFlag.getVariations().stream().map(value -> new Variation(null, value)).collect(Collectors.toList()))
                .rolloutRules(featureFlag.getRolloutRules() != null ? featureFlag.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toList()) : null)
                .archived(featureFlag.isArchived())
                .targeting(featureFlag.isTargeting())
                .created(featureFlag.getCreated())
                .lastUpdated(featureFlag.getLastUpdated())
                .build();
    }


    public static RolloutRulesMongoEntity convert(RolloutRules rolloutRules) {
        return RolloutRulesMongoEntity.builder()
                .rolloutRules(rolloutRules.getRolloutRules() != null ? rolloutRules.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toSet()) : null)
                .namespace(rolloutRules.getNamespace())
                .defaultVariationTargetingOff(rolloutRules.getDefaultVariationTargetingOff().getVariation())
                .defaultVariationTargetingOn(rolloutRules.getDefaultVariationTargetingOn().getVariation())
                .created(rolloutRules.getCreated())
                .lastUpdated(rolloutRules.getLastUpdated())
                .build();
    }

    public static RolloutRules convert(RolloutRulesMongoEntity rolloutRules) {
        return RolloutRules.builder()
                .rolloutRules(rolloutRules.getRolloutRules() != null ? rolloutRules.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toList()) : null)
                .namespace(rolloutRules.getNamespace())
                .defaultVariationTargetingOff(new Variation(null, rolloutRules.getDefaultVariationTargetingOff()))
                .defaultVariationTargetingOn(new Variation(null, rolloutRules.getDefaultVariationTargetingOn()))
                .created(rolloutRules.getCreated())
                .lastUpdated(rolloutRules.getLastUpdated())
                .build();
    }

    public static RolloutRuleMongoEntity convert(RolloutRule rolloutRule) {
        return RolloutRuleMongoEntity.builder()
                .ruleType(rolloutRule.getRuleType().name())
                .conditionalDistribution(toConditionalDistributionMongoEntities(rolloutRule.getConditionalDistribution()))
                .percentDistribution(toPercentDistributionMongoEntities(rolloutRule.getPercentDistribution()))
                .created(rolloutRule.getCreated())
                .lastUpdated(rolloutRule.getLastUpdated())
                .build();
    }

    private static List<PercentDistributionMongoEntity> toPercentDistributionMongoEntities(List<PercentDistribution> percentDistribution) {
        return percentDistribution.stream()
                .map(ConversionHelper::convert)
                .collect(Collectors.toList());
    }

    private static List<PercentDistribution> toPercentDistributionList(List<PercentDistributionMongoEntity> percentDistributionMongoEntities) {
        if(percentDistributionMongoEntities == null) {
            return null;
        }
        return percentDistributionMongoEntities
                .stream().map(ConversionHelper::convert).collect(Collectors.toList());
    }

    private static PercentDistributionMongoEntity convert(PercentDistribution percentDistribution) {
        return new PercentDistributionMongoEntity(percentDistribution.getPercent(), percentDistribution.getVariation().getVariation());
    }

    private static PercentDistribution convert(PercentDistributionMongoEntity percentDistribution) {
        return new PercentDistribution(percentDistribution.getPercent(), new Variation(null, percentDistribution.getVariation()));
    }

    private static List<ConditionalDistributionMongoEntity> toConditionalDistributionMongoEntities(List<ConditionalDistribution> conditionalDistribution) {
        return conditionalDistribution.stream()
                .map(ConversionHelper::convert)
                .collect(Collectors.toList());
    }

    private static List<ConditionalDistribution> toConditionalDistributionList(List<ConditionalDistributionMongoEntity> conditionalDistributionMongoEntities) {
        if(conditionalDistributionMongoEntities == null) {
            return null;
        }
        return conditionalDistributionMongoEntities
                .stream().map(ConversionHelper::convert).collect(Collectors.toList());
    }

    private static ConditionalDistributionMongoEntity convert(ConditionalDistribution conditionalDistribution) {
        return new ConditionalDistributionMongoEntity(conditionalDistribution.getCondition(), conditionalDistribution.getVariation().getVariation());
    }

    private static ConditionalDistribution convert(ConditionalDistributionMongoEntity conditionalDistributionMongoEntity) {
        return new ConditionalDistribution(conditionalDistributionMongoEntity.getCondition(), new Variation(null, conditionalDistributionMongoEntity.getVariation()));
    }

    public static RolloutRule convert(RolloutRuleMongoEntity rolloutRule) {
        return RolloutRule.builder()
                .ruleType(RolloutRuleType.valueOf(rolloutRule.getRuleType()))
                .conditionalDistribution(toConditionalDistributionList(rolloutRule.getConditionalDistribution()))
                .percentDistribution(toPercentDistributionList(rolloutRule.getPercentDistribution()))
                .created(rolloutRule.getCreated())
                .lastUpdated(rolloutRule.getLastUpdated())
                .build();
    }
}

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
import java.util.Optional;
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
                .setId(featureFlag.getId())
                .setKey(featureFlag.getKey())
                .setName(featureFlag.getName())
                .setDescription(featureFlag.getDescription())
                .setType(FeatureFlagType.valueOf(featureFlag.getType()))
                .setVariations(featureFlag.getVariations().stream().map(value -> new Variation(null, value)).collect(Collectors.toList()))
                .setRolloutRules(featureFlag.getRolloutRules() != null ? featureFlag.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toList()) : null)
                .setArchived(featureFlag.isArchived())
                .setTargeting(featureFlag.isTargeting())
                .setCreated(featureFlag.getCreated())
                .setLastUpdated(featureFlag.getLastUpdated())
                .build();
    }


    public static RolloutRulesMongoEntity convert(RolloutRules rolloutRules) {
        return RolloutRulesMongoEntity.builder()
                .rolloutRules(rolloutRules.getRolloutRules() != null ? rolloutRules.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toSet()) : null)
                .namespace(rolloutRules.getNamespace())
                .defaultVariationTargetingOff(rolloutRules.getDefaultVariationTargetingOff().getVariation())
                .defaultVariationTargetingOn(rolloutRules.getDefaultVariationTargetingOn().getVariation())
                .created(rolloutRules.getCreated().orElse(null))
                .lastUpdated(rolloutRules.getLastUpdated().orElse(null))
                .build();
    }

    public static RolloutRules convert(RolloutRulesMongoEntity rolloutRules) {
        return RolloutRules.builder()
                .setRolloutRules(rolloutRules.getRolloutRules() != null ? rolloutRules.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toList()) : null)
                .setNamespace(rolloutRules.getNamespace())
                .setDefaultVariationTargetingOff(new Variation(null, rolloutRules.getDefaultVariationTargetingOff()))
                .setDefaultVariationTargetingOn(new Variation(null, rolloutRules.getDefaultVariationTargetingOn()))
                .setCreated(rolloutRules.getCreated())
                .setLastUpdated(rolloutRules.getLastUpdated())
                .build();
    }

    public static RolloutRuleMongoEntity convert(RolloutRule rolloutRule) {
        return RolloutRuleMongoEntity.builder()
                .ruleType(rolloutRule.getRuleType().name())
                .conditionalDistribution(toConditionalDistributionMongoEntities(rolloutRule.getConditionalDistribution()))
                .percentDistribution(toPercentDistributionMongoEntities(rolloutRule.getPercentDistribution()))
                .created(rolloutRule.getCreated().orElse(null))
                .lastUpdated(rolloutRule.getLastUpdated().orElse(null))
                .build();
    }

    private static List<PercentDistributionMongoEntity> toPercentDistributionMongoEntities(Optional<List<PercentDistribution>> percentDistribution) {
        return percentDistribution
                .map(list -> list.stream().map(ConversionHelper::convert).collect(Collectors.toList()))
                .orElse(null);
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

    private static List<ConditionalDistributionMongoEntity> toConditionalDistributionMongoEntities(Optional<List<ConditionalDistribution>> conditionalDistribution) {
        return conditionalDistribution
                .map(dist -> dist.stream().map(ConversionHelper::convert).collect(Collectors.toList()))
                .orElse(null);
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
                .setRuleType(RolloutRuleType.valueOf(rolloutRule.getRuleType()))
                .setConditionalDistribution(toConditionalDistributionList(rolloutRule.getConditionalDistribution()))
                .setPercentDistribution(toPercentDistributionList(rolloutRule.getPercentDistribution()))
                .setCreated(rolloutRule.getCreated())
                .setLastUpdated(rolloutRule.getLastUpdated())
                .build();
    }
}

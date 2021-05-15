package org.propagate.server.service;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.FeatureFlagType;
import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.Variation;
import org.propagate.common.domain.rollout.ConditionalDistribution;
import org.propagate.common.domain.rollout.PercentDistribution;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.common.domain.rollout.RolloutRuleType;
import org.propagate.common.rest.entity.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ConversionHelper {
    private ConversionHelper() {
    }

    private static final Function<String, Variation> variationInitializer = val -> new Variation(null, val);

    public static FeatureFlag convert(FeatureFlagRestEntity featureFlagRestEntity) {
        return FeatureFlag.builder()
                .setId(featureFlagRestEntity.getId())
                .setKey(featureFlagRestEntity.getKey())
                .setName(featureFlagRestEntity.getName())
                .setDescription(featureFlagRestEntity.getDescription())
                .setType(FeatureFlagType.valueOf(featureFlagRestEntity.getType()))
                .setVariations(featureFlagRestEntity.getVariations().stream().map(variationInitializer).collect(Collectors.toList()))
                .setRolloutRules(featureFlagRestEntity.getRolloutRules() != null ? featureFlagRestEntity.getRolloutRules().stream().map(ConversionHelper::convert).collect(Collectors.toList()) : null)
                .setArchived(featureFlagRestEntity.isArchived())
                .setTargeting(featureFlagRestEntity.isTargeting())
                .setCreated(featureFlagRestEntity.getCreated())
                .setLastUpdated(featureFlagRestEntity.getLastUpdated())
                .build();
    }

    public static RolloutRules convert(RolloutRulesRestEntity rolloutRulesRestEntity) {
        return RolloutRules.builder()
                .setNamespace(rolloutRulesRestEntity.getNamespace())
                .setDefaultVariationTargetingOff(variationInitializer.apply(rolloutRulesRestEntity.getDefaultVariationTargetingOff()))
                .setDefaultVariationTargetingOn(variationInitializer.apply(rolloutRulesRestEntity.getDefaultVariationTargetingOn()))
                .setRolloutRules(convertRolloutRuleModelList(rolloutRulesRestEntity.getRolloutRules()))
                .build();
    }

    private static List<RolloutRule> convertRolloutRuleModelList(List<RolloutRuleRestEntity> rolloutRules) {
        if (rolloutRules == null) {
            return null;
        }

        return rolloutRules.stream()
                .map(ConversionHelper::convert)
                .collect(Collectors.toList());
    }

    public static RolloutRule convert(RolloutRuleRestEntity rolloutRuleRestEntity) {
        return RolloutRule.builder()
                .setRuleType(RolloutRuleType.valueOf(rolloutRuleRestEntity.getType()))
                .setConditionalDistribution(toConditionalDistributions(rolloutRuleRestEntity.getConditionalDistribution()))
                .setPercentDistribution(toPercentDistributions(rolloutRuleRestEntity.getPercentDistribution()))
                .setCreated(rolloutRuleRestEntity.getCreated())
                .setLastUpdated(rolloutRuleRestEntity.getLastUpdated())
                .build();
    }

    private static List<PercentDistribution> toPercentDistributions(List<PercentDistributionRestEntity> percentDistribution) {
        if(percentDistribution == null) {
            return null;
        }

        return percentDistribution.stream()
                .map(ConversionHelper::toPercentDistribution)
                .collect(Collectors.toList());
    }

    private static PercentDistribution toPercentDistribution(PercentDistributionRestEntity percentDistributionRestEntity) {
        return new PercentDistribution(percentDistributionRestEntity.getPercent(), new Variation(null, percentDistributionRestEntity.getVariation()));
    }

    private static List<ConditionalDistribution> toConditionalDistributions(List<ConditionalDistributionRestEntity> conditionalDistribution) {
        if(conditionalDistribution==null) {
            return null;
        }
        return conditionalDistribution.stream()
                .map(ConversionHelper::toConditionalDistribution)
                .collect(Collectors.toList());
    }

    private static ConditionalDistribution toConditionalDistribution(ConditionalDistributionRestEntity conditionalDistributionRestEntity) {
        return new ConditionalDistribution(conditionalDistributionRestEntity.getCondition(), new Variation(null, conditionalDistributionRestEntity.getVariation()));
    }
}

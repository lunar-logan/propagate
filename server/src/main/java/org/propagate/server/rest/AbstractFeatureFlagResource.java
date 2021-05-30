package org.propagate.server.rest;

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

public abstract class AbstractFeatureFlagResource {
    private static final Function<String, Variation> variationInitializer = val -> new Variation(null, val);

    protected FeatureFlagRestEntity toFeatureFlagRestEntity(FeatureFlag featureFlag) {
        return FeatureFlagRestEntity.builder()
                .id(featureFlag.getId())
                .key(featureFlag.getKey())
                .name(featureFlag.getName())
                .description(featureFlag.getDescription())
                .type(featureFlag.getType().name())
                .variations(featureFlag.getVariations().stream().map(Variation::getVariation).collect(Collectors.toList()))
                .rolloutRules(featureFlag.getRolloutRules()!=null? featureFlag.getRolloutRules().stream().map(this::toRolloutRulesRestEntity).collect(Collectors.toList()): null)
                .archived(featureFlag.isArchived())
                .targeting(featureFlag.isTargeting())
                .created(featureFlag.getCreated())
                .lastUpdated(featureFlag.getLastUpdated())
                .build();

    }

    protected RolloutRulesRestEntity toRolloutRulesRestEntity(RolloutRules rolloutRules) {
        return RolloutRulesRestEntity.builder()
                .namespace(rolloutRules.getNamespace())
                .defaultVariationTargetingOff(rolloutRules.getDefaultVariationTargetingOff().getVariation())
                .defaultVariationTargetingOn(rolloutRules.getDefaultVariationTargetingOn().getVariation())
                .rolloutRules(rolloutRules.getRolloutRules() == null? null: rolloutRules.getRolloutRules().stream().map(this::toRolloutRuleRestEntity).collect(Collectors.toList()))
                .build();
    }

    protected RolloutRuleRestEntity toRolloutRuleRestEntity(RolloutRule rolloutRule) {
        return RolloutRuleRestEntity.builder()
                .type(rolloutRule.getRuleType().name())
                .conditionalDistribution(toConditionalDistributionRestEntities(rolloutRule.getConditionalDistribution()))
                .percentDistribution(toPercentDistributionRestEntities(rolloutRule.getPercentDistribution()))
                .created(rolloutRule.getCreated())
                .lastUpdated(rolloutRule.getLastUpdated())
                .build();
    }

    protected List<PercentDistributionRestEntity> toPercentDistributionRestEntities(List<PercentDistribution> percentDistribution) {
        if(percentDistribution == null) {
            return null;
        }

        return percentDistribution.stream()
                .map(this::toPercentDistributionRestEntity)
                .collect(Collectors.toList());
    }

    protected PercentDistributionRestEntity toPercentDistributionRestEntity(PercentDistribution percentDistribution) {
        return new PercentDistributionRestEntity(percentDistribution.getPercent(), percentDistribution.getVariation().getVariation());
    }

    protected List<ConditionalDistributionRestEntity> toConditionalDistributionRestEntities(List<ConditionalDistribution> conditionalDistribution) {
        if(conditionalDistribution==null) {
            return null;
        }
        return conditionalDistribution.stream()
                .map(this::toConditionalDistributionRestEntity)
                .collect(Collectors.toList());
    }

    protected ConditionalDistributionRestEntity toConditionalDistributionRestEntity(ConditionalDistribution conditionalDistribution) {
        return new ConditionalDistributionRestEntity(conditionalDistribution.getCondition(), conditionalDistribution.getVariation().getVariation());
    }

    protected FeatureFlag toFeatureFlag(FeatureFlagRestEntity featureFlagRestEntity) {
        return FeatureFlag.builder()
                .id(featureFlagRestEntity.getId())
                .key(featureFlagRestEntity.getKey())
                .name(featureFlagRestEntity.getName())
                .description(featureFlagRestEntity.getDescription())
                .type(FeatureFlagType.valueOf(featureFlagRestEntity.getType()))
                .variations(featureFlagRestEntity.getVariations().stream().map(variationInitializer).collect(Collectors.toList()))
                .rolloutRules(featureFlagRestEntity.getRolloutRules() != null ? featureFlagRestEntity.getRolloutRules().stream().map(this::toRolloutRules).collect(Collectors.toList()) : null)
                .archived(featureFlagRestEntity.isArchived())
                .targeting(featureFlagRestEntity.isTargeting())
                .created(featureFlagRestEntity.getCreated())
                .lastUpdated(featureFlagRestEntity.getLastUpdated())
                .build();
    }

    protected RolloutRules toRolloutRules(RolloutRulesRestEntity rolloutRulesRestEntity) {
        return RolloutRules.builder()
                .namespace(rolloutRulesRestEntity.getNamespace())
                .defaultVariationTargetingOff(variationInitializer.apply(rolloutRulesRestEntity.getDefaultVariationTargetingOff()))
                .defaultVariationTargetingOn(variationInitializer.apply(rolloutRulesRestEntity.getDefaultVariationTargetingOn()))
                .rolloutRules(toRolloutRuleModelList(rolloutRulesRestEntity.getRolloutRules()))
                .build();
    }

    protected List<RolloutRule> toRolloutRuleModelList(List<RolloutRuleRestEntity> rolloutRules) {
        if (rolloutRules == null) {
            return null;
        }

        return rolloutRules.stream()
                .map(this::toRolloutRules)
                .collect(Collectors.toList());
    }

    protected RolloutRule toRolloutRules(RolloutRuleRestEntity rolloutRuleRestEntity) {
        return RolloutRule.builder()
                .ruleType(RolloutRuleType.valueOf(rolloutRuleRestEntity.getType()))
                .conditionalDistribution(toConditionalDistributions(rolloutRuleRestEntity.getConditionalDistribution()))
                .percentDistribution(toPercentDistributions(rolloutRuleRestEntity.getPercentDistribution()))
                .created(rolloutRuleRestEntity.getCreated())
                .lastUpdated(rolloutRuleRestEntity.getLastUpdated())
                .build();
    }

    protected List<PercentDistribution> toPercentDistributions(List<PercentDistributionRestEntity> percentDistribution) {
        if(percentDistribution == null) {
            return null;
        }

        return percentDistribution.stream()
                .map(this::toPercentDistribution)
                .collect(Collectors.toList());
    }

    protected PercentDistribution toPercentDistribution(PercentDistributionRestEntity percentDistributionRestEntity) {
        return new PercentDistribution(percentDistributionRestEntity.getPercent(), new Variation(null, percentDistributionRestEntity.getVariation()));
    }

    protected List<ConditionalDistribution> toConditionalDistributions(List<ConditionalDistributionRestEntity> conditionalDistribution) {
        if(conditionalDistribution==null) {
            return null;
        }
        return conditionalDistribution.stream()
                .map(this::toConditionalDistribution)
                .collect(Collectors.toList());
    }

    protected ConditionalDistribution toConditionalDistribution(ConditionalDistributionRestEntity conditionalDistributionRestEntity) {
        return new ConditionalDistribution(conditionalDistributionRestEntity.getCondition(), new Variation(null, conditionalDistributionRestEntity.getVariation()));
    }
}

package org.propagate.server.rest;

import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.Variation;
import org.propagate.common.domain.rollout.ConditionalDistribution;
import org.propagate.common.domain.rollout.PercentDistribution;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.common.rest.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFeatureFlagResource {
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
                .conditionalDistribution(toConditionalDistributionRestEntities(rolloutRule.getConditionalDistribution().orElse(null)))
                .percentDistribution(toPercentDistributionRestEntities(rolloutRule.getPercentDistribution().orElse(null)))
                .created(rolloutRule.getCreated().orElse(null))
                .lastUpdated(rolloutRule.getLastUpdated().orElse(null))
                .build();
    }

    private static List<PercentDistributionRestEntity> toPercentDistributionRestEntities(List<PercentDistribution> percentDistribution) {
        if(percentDistribution == null) {
            return null;
        }

        return percentDistribution.stream()
                .map(AbstractFeatureFlagResource::toPercentDistributionRestEntity)
                .collect(Collectors.toList());
    }

    private static PercentDistributionRestEntity toPercentDistributionRestEntity(PercentDistribution percentDistribution) {
        return new PercentDistributionRestEntity(percentDistribution.getPercent(), percentDistribution.getVariation().getVariation());
    }

    private static List<ConditionalDistributionRestEntity> toConditionalDistributionRestEntities(List<ConditionalDistribution> conditionalDistribution) {
        if(conditionalDistribution==null) {
            return null;
        }
        return conditionalDistribution.stream()
                .map(AbstractFeatureFlagResource::toConditionalDistributionRestEntity)
                .collect(Collectors.toList());
    }

    private static ConditionalDistributionRestEntity toConditionalDistributionRestEntity(ConditionalDistribution conditionalDistribution) {
        return new ConditionalDistributionRestEntity(conditionalDistribution.getCondition(), conditionalDistribution.getVariation().getVariation());
    }
}

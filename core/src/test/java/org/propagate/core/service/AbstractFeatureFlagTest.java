package org.propagate.core.service;

import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.Variation;
import org.propagate.common.domain.rollout.ConditionalDistribution;
import org.propagate.common.domain.rollout.PercentDistribution;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.common.domain.rollout.RolloutRuleType;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractFeatureFlagTest {
    protected Variation variation(String value) {
        return new Variation(null, value);
    }

    protected Variation variation(Long id, String value) {
        return new Variation(id, value);
    }

    protected RolloutRule percentRule(Long id, Collection<PercentDistribution> rules) {
        return RolloutRule.builder()
                .id(id)
                .ruleType(RolloutRuleType.PERCENT)
                .percentDistribution(new ArrayList<>(rules))
                .build();
    }

    protected RolloutRule percentRule(Collection<PercentDistribution> rules) {
        return percentRule(null, rules);
    }

    protected RolloutRule conditionalRule(Long id, Collection<ConditionalDistribution> rules) {
        return RolloutRule.builder()
                .id(id)
                .ruleType(RolloutRuleType.CONDITIONAL)
                .conditionalDistribution(new ArrayList<>(rules))
                .build();
    }

    protected RolloutRule conditionalRule(Collection<ConditionalDistribution> rules) {
        return conditionalRule(null, rules);
    }

    protected RolloutRules rollout(Long id, String namespace, Variation targetOff, Variation targetOn, Collection<RolloutRule> rules) {
        return RolloutRules.builder()
                .id(id)
                .namespace(namespace)
                .defaultVariationTargetingOn(targetOn)
                .defaultVariationTargetingOff(targetOff)
                .rolloutRules(new ArrayList<>(rules))
                .build();
    }

    protected RolloutRules rollout(String namespace, Variation targetOff, Variation targetOn, Collection<RolloutRule> rules) {
        return rollout(null, namespace, targetOff, targetOn, rules);
    }

    protected PercentDistribution percent(int percent, String variation) {
        return new PercentDistribution(percent, variation(variation));
    }

    protected ConditionalDistribution condition(String condition, String variation) {
        return new ConditionalDistribution(condition, variation(variation));
    }
}


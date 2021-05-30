package org.propagate.core.service.impl;

import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.Variation;
import org.propagate.common.domain.rollout.ConditionalDistribution;
import org.propagate.common.domain.rollout.PercentDistribution;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.core.service.FeatureFlagService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.propagate.common.domain.rollout.RolloutRuleType.CONDITIONAL;
import static org.propagate.common.domain.rollout.RolloutRuleType.PERCENT;

public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final FeatureFlagDao dao;

    public FeatureFlagServiceImpl(FeatureFlagDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Override
    public FeatureFlag createFeatureFlag(FeatureFlag featureFlag) {
        validateFeatureFlagCreation(featureFlag);
        return dao.save(featureFlag);
    }

    private void validateFeatureFlagCreation(FeatureFlag featureFlag) {
        final Set<String> variations = featureFlag.getVariations().stream().map(Variation::getVariation).collect(Collectors.toSet());
        for (RolloutRules aRolloutRules : featureFlag.getRolloutRules()) {
            if(!variations.contains(aRolloutRules.getDefaultVariationTargetingOff().getVariation())) {
                throw new IllegalArgumentException("Invalid default variation when targeting off");
            }
            if(!variations.contains(aRolloutRules.getDefaultVariationTargetingOn().getVariation())) {
                throw new IllegalArgumentException("Invalid default variation when targeting on");
            }
            validateRolloutRules(variations, aRolloutRules);
        }
    }

    private void validateRolloutRules(Set<String> variations, RolloutRules rules) {
        if (rules.getRolloutRules() != null && !rules.getRolloutRules().isEmpty()) {
            validateRolloutRuleListPerRollout(variations, rules.getRolloutRules());
        }
    }

    private void validateRolloutRuleListPerRollout(Set<String> variations, List<RolloutRule> rolloutRules) {
        for (RolloutRule rule : rolloutRules) {
            if (rule.getRuleType() == PERCENT) {
                validatePercentRolloutRule(variations, rule);
            } else if (rule.getRuleType() == CONDITIONAL) {
                validateConditionalRolloutRule(variations, rule);
            }
        }
    }

    private void validateConditionalRolloutRule(Set<String> variations, RolloutRule rule) {
        if (rule.getConditionalDistribution() == null || rule.getConditionalDistribution().isEmpty()) {
            throw new IllegalArgumentException("Condition distribution cannot be null or empty");
        }

        for (ConditionalDistribution conditionalDistribution : rule.getConditionalDistribution()) {
            if (!variations.contains(conditionalDistribution.getVariation().getVariation())) {
                throw new IllegalArgumentException("Undefined variation '" + conditionalDistribution.getVariation().getVariation() + "'");
            }
        }
    }

    private void validatePercentRolloutRule(Set<String> variations, RolloutRule rule) {
        if (rule.getPercentDistribution() == null || rule.getPercentDistribution().isEmpty()) {
            throw new IllegalArgumentException("Percent distribution cannot be null or empty");
        }

        int totalPercent = 0;
        for (PercentDistribution percentDistribution : rule.getPercentDistribution()) {
            if (!variations.contains(percentDistribution.getVariation().getVariation())) {
                throw new IllegalArgumentException("Undefined variation '" + percentDistribution.getVariation().getVariation() + "'");
            }
            totalPercent += percentDistribution.getPercent();
        }

        if (totalPercent != 100) {
            throw new IllegalArgumentException("Percent distribution does not sums to 100");
        }
    }

    @Override
    public Optional<FeatureFlag> getFeatureFlagById(String featureFlagId) {
        return dao.findById(featureFlagId);
    }

    @Override
    public List<FeatureFlag> getAllFeatureFlags() {
        return dao.findAll();
    }

    @Override
    public List<FeatureFlag> getAllFeatureFlags(int page, int size) {
        return dao.findAll(page, size);
    }

    @Override
    public void deleteFeatureFlagById(String id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteAllFeatureFlagByIds(Iterable<String> ids) {
        if (ids != null) {
            for (String id : ids) {
                deleteFeatureFlagById(id);
            }
        }
    }

    @Override
    public void deleteAllFeatureFlags() {
        for (FeatureFlag featureFlag : dao.findAll()) {
            dao.delete(featureFlag);
        }
    }
}

package org.propagate.client.mapper;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.client.model.PercentRollout;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.Rollout;
import org.propagate.common.domain.rollout.ConditionalRollout;
import org.propagate.common.domain.rollout.PercentageRollout;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.query.Query;
import org.propagate.query.QueryFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ExpandedFeatureFlagMapperImpl implements ExpandedFeatureFlagMapper {
    private final QueryFactory queryFactory;

    public ExpandedFeatureFlagMapperImpl(QueryFactory queryFactory) {
        this.queryFactory = Objects.requireNonNull(queryFactory);
    }

    @Override
    public ExpandedFeatureFlag map(FeatureFlag flag) {
        return new ExpandedFeatureFlag(
                flag.getKey(),
                flag.getName(),
                flag.getDescription(),
                flag.getVariations(),
                flag.getType(),
                collectConditionalRolloutRules(flag.getRolloutRules()),
                collectPercentRolloutRules(flag.getRolloutRules()),
                flag.getDefaultRolloutTargetingOn(),
                flag.getDefaultRolloutTargetingOff(),
                flag.isTargeting(),
                flag.isArchived()
        );
    }

    private Map<String, Map<Query, String>> collectConditionalRolloutRules(List<Rollout> rolloutRules) {
        if (rolloutRules == null || rolloutRules.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Map<Query, String>> conditionalRolloutRules = new HashMap<>();
        for (Rollout rollout : rolloutRules) {
            if (rollout.getRules() != null) {
                final Map<Query, String> m = new HashMap<>();
                for (RolloutRule rule : rollout.getRules()) {
                    if (rule instanceof ConditionalRollout) {
                        m.put(queryFactory.parse(((ConditionalRollout) rule).getExpression()), rule.getVariation());
                    }
                }
                conditionalRolloutRules.put(rollout.getEnvironment(), m);
            }
        }
        return conditionalRolloutRules;
    }

    private Map<String, List<PercentRollout>> collectPercentRolloutRules(List<Rollout> rolloutRules) {
        if (rolloutRules == null || rolloutRules.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, List<PercentRollout>> percentRolloutRules = new HashMap<>();
        for (Rollout rollout : rolloutRules) {
            if (rollout.getRules() != null) {
                final List<PercentRollout> m = new ArrayList<>();
                for (RolloutRule rule : rollout.getRules()) {
                    if (rule instanceof PercentageRollout) {
                        m.add(new PercentRollout(rule.getVariation(), ((PercentageRollout) rule).getPercent()));
                    }
                }
                percentRolloutRules.put(rollout.getEnvironment(), m);
            }
        }
        return percentRolloutRules;
    }

    private Map<Query, String> mapConditionalRollout(List<ConditionalRollout> conditionalRollouts) {
        Map<Query, String> m = new LinkedHashMap<>();
        if (conditionalRollouts != null) {
            for (ConditionalRollout conditionalRollout : conditionalRollouts) {
                m.put(queryFactory.parse(conditionalRollout.getExpression()), conditionalRollout.getVariation());
            }
        }
        return m;
    }

    private List<PercentRollout> mapPercentageRollout(List<PercentageRollout> rollouts) {
        if (rollouts != null) {
            return rollouts.stream()
                    .map(rollout -> new PercentRollout(rollout.getVariation(), rollout.getPercent()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}

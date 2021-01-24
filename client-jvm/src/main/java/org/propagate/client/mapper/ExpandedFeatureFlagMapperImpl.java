package org.propagate.client.mapper;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.client.model.PercentRollout;
import org.propagate.common.domain.ConditionalRollout;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.PercentageRollout;
import org.propagate.query.Query;
import org.propagate.query.QueryFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpandedFeatureFlagMapperImpl implements ExpandedFeatureFlagMapper {
    private final QueryFactory queryFactory;

    public ExpandedFeatureFlagMapperImpl(QueryFactory queryFactory) {
        this.queryFactory = Objects.requireNonNull(queryFactory);
    }

    @Override
    public ExpandedFeatureFlag map(FeatureFlag flag) {
        return new ExpandedFeatureFlag(
                flag.getId().getKey(),
                flag.getId().getEnv(),
                flag.getName(),
                flag.getDescription(),
                flag.getVariations(),
                flag.getType(),
                mapConditionalRollout(flag.getConditionalRollout()),
                mapPercentageRollout(flag.getPercentRollout()),
                flag.getDefaultRollout(),
                flag.isTargeting(),
                flag.isArchived()
        );
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

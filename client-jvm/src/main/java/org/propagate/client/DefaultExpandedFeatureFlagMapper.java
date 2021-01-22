package org.propagate.client;

import org.propagate.common.domain.Condition;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.query.Query;
import org.propagate.query.QueryFactory;

import java.util.*;

public class DefaultExpandedFeatureFlagMapper implements ExpandedFeatureFlagMapper {
    private final QueryFactory queryFactory;

    public DefaultExpandedFeatureFlagMapper(QueryFactory queryFactory) {
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
                flag.getPercentRollout(),
                flag.getDefaultRollout(),
                flag.isArchived()
        );
    }

    private Map<Query, String> mapConditionalRollout(List<Condition> conditions) {
        Map<Query, String> m = new LinkedHashMap<>();
        if (conditions != null) {
            for (Condition condition : conditions) {
                m.put(queryFactory.parse(condition.getExpression()), condition.getVariation());
            }
        }
        return m;
    }
}

package org.propagate.client.eval.rule;

import org.propagate.client.eval.EvaluationContext;
import org.propagate.common.rest.entity.ConditionalDistributionRestEntity;
import org.propagate.common.rest.entity.RolloutRuleRestEntity;
import org.propagate.query.Query;
import org.propagate.query.QueryFactory;

import java.util.Optional;

public class ConditionalRuleEvaluatorImpl implements RuleEvaluator {
    private final QueryFactory queryFactory;

    public ConditionalRuleEvaluatorImpl(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<String> eval(RolloutRuleRestEntity rule, EvaluationContext evaluationContext) {
        return rule.getConditionalDistribution()
                .stream()
                .filter(condition -> evalCondition(condition, evaluationContext))
                .findFirst()
                .map(ConditionalDistributionRestEntity::getVariation);
    }

    private boolean evalCondition(ConditionalDistributionRestEntity condition, EvaluationContext evaluationContext) {
        final Query query = queryFactory.parse(condition.getCondition());
        return query.eval(evaluationContext.getCtx());
    }
}

package org.propagate.client.eval.rule;

import org.propagate.common.domain.rollout.RolloutRuleType;
import org.propagate.common.rest.entity.RolloutRuleRestEntity;
import org.propagate.query.DefaultQueryFactoryImpl;

import java.util.HashMap;
import java.util.Map;

public  class RuleEvaluatorFactory {
    private static final Map<RolloutRuleType, RuleEvaluator> RULE_EVALUATOR_MAP = new HashMap<>();
    static {
        RULE_EVALUATOR_MAP.put(RolloutRuleType.PERCENT, new PercentRuleEvaluatorImpl());
        RULE_EVALUATOR_MAP.put(RolloutRuleType.CONDITIONAL, new ConditionalRuleEvaluatorImpl(new DefaultQueryFactoryImpl()));
    }
    public static RuleEvaluator getInstance(RolloutRuleRestEntity rule) {
        RolloutRuleType ruleType = RolloutRuleType.fromName(rule.getType());
        return RULE_EVALUATOR_MAP.get(ruleType);
    }
}

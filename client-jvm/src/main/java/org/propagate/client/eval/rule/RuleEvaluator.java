package org.propagate.client.eval.rule;

import org.propagate.client.eval.EvaluationContext;
import org.propagate.common.rest.entity.RolloutRuleRestEntity;

import java.util.Optional;

public interface RuleEvaluator {
    Optional<String> eval(RolloutRuleRestEntity rule, EvaluationContext evaluationContext);
}

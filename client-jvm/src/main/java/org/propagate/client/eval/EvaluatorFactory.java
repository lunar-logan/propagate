package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;

import java.util.Map;

public class EvaluatorFactory {
    private EvaluatorFactory() {
    }

    public static Evaluator percentRolloutEvaluator(ExpandedFeatureFlag exff, String environment, Map<String, Object> ctx) {
        return new PercentRolloutEvaluator(exff, new EvaluationContext(environment, ctx));
    }

    public static Evaluator conditionalRolloutEvaluator(ExpandedFeatureFlag exff, String environment, Map<String, Object> ctx) {
        return new ConditionalRolloutEvaluator(exff, new EvaluationContext(environment, ctx));
    }
}

package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;

import java.util.Map;

public class EvaluatorFactory {
    private EvaluatorFactory() {
    }

    public static Evaluator percentRolloutEvaluator(ExpandedFeatureFlag exff, Map<String, Object> ctx) {
        return new PercentRolloutEvaluator(exff, ctx);
    }

    public static Evaluator conditionalRolloutEvaluator(ExpandedFeatureFlag exff, Map<String, Object> ctx) {
        return new ConditionalRolloutEvaluator(exff, ctx);
    }
}

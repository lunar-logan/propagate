package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.common.domain.util.Either;

import java.util.Map;

public class ConditionalRolloutEvaluator implements Evaluator {
    private final ExpandedFeatureFlag expandedFeatureFlag;
    private final Map<String, Object> ctx;

    public ConditionalRolloutEvaluator(ExpandedFeatureFlag expandedFeatureFlag, Map<String, Object> ctx) {
        this.expandedFeatureFlag = expandedFeatureFlag;
        this.ctx = ctx;
    }

    @Override
    public Either<String> eval() {
        if (expandedFeatureFlag.getConditionalRollout() == null) {
            return Either.left(new NullPointerException("conditional rollout is null"));
        }
        try {
            String result = expandedFeatureFlag.getConditionalRollout().entrySet()
                    .stream()
                    .filter(e -> e.getKey().eval(ctx))
                    .findAny()
                    .map(Map.Entry::getValue)
                    .orElseThrow();
            return Either.right(result);
        } catch (Exception ex) {
            return Either.left(ex);
        }
    }
}

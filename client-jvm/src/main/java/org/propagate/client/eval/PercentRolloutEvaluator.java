package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.client.model.PercentRollout;
import org.propagate.common.domain.util.Either;

import java.util.*;

public class PercentRolloutEvaluator implements Evaluator {
    private final ExpandedFeatureFlag expandedFeatureFlag;
    private final Map<String, Object> ctx;

    public PercentRolloutEvaluator(ExpandedFeatureFlag expandedFeatureFlag, Map<String, Object> ctx) {
        this.expandedFeatureFlag = Objects.requireNonNull(expandedFeatureFlag);
        this.ctx = Objects.requireNonNull(ctx);
    }

    @Override
    public Either<String> eval() {
        List<PercentRollout> percentRollout = expandedFeatureFlag.getPercentRollout();
        if (percentRollout == null) {
            return Either.left(new NullPointerException("percentage rollout is null"));
        }

        List<String> elements = new ArrayList<>();
        for (PercentRollout rollout : percentRollout) {
            for (int i = 0; i < rollout.getPercent(); i++) {
                elements.add(rollout.getVariation());
            }
        }
        Collections.shuffle(elements);
        return Either.right(elements.get(Math.abs(ctx.hashCode()) % 100));
    }
}

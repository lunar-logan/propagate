package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.client.model.PercentRollout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PercentRolloutEvaluator implements Evaluator {
    private final ExpandedFeatureFlag expandedFeatureFlag;
    private final EvaluationContext ctx;

    public PercentRolloutEvaluator(ExpandedFeatureFlag expandedFeatureFlag, EvaluationContext ctx) {
        this.expandedFeatureFlag = expandedFeatureFlag;
        this.ctx = ctx;
    }

    @Override
    public Optional<String> eval() {
        if (expandedFeatureFlag.getPercentRolloutRules() == null) {
            throw new IllegalArgumentException("Percentage rollout is null");
        }
        List<PercentRollout> percentRollout = expandedFeatureFlag.getPercentRolloutRules().get(ctx.getEnvironment());
        if (percentRollout == null) {
            throw new IllegalArgumentException("Unknown environment \"" + ctx.getEnvironment() + "\"");
        }

        final List<String> elements = new ArrayList<>();
        for (PercentRollout rollout : percentRollout) {
            for (int i = 0; i < rollout.getPercent(); i++) {
                elements.add(rollout.getVariation());
            }
        }
        Collections.shuffle(elements);
        return Optional.of(elements.get(Math.abs(ctx.hashCode()) % 100));
    }
}

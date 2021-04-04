package org.propagate.client.eval;

import org.propagate.client.model.ExpandedFeatureFlag;
import org.propagate.query.Query;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ConditionalRolloutEvaluator implements Evaluator {
    private final ExpandedFeatureFlag expandedFeatureFlag;
    private final EvaluationContext ctx;

    public ConditionalRolloutEvaluator(ExpandedFeatureFlag expandedFeatureFlag, EvaluationContext ctx) {
        this.expandedFeatureFlag = Objects.requireNonNull(expandedFeatureFlag);
        this.ctx = Objects.requireNonNull(ctx);
    }

    @Override
    public Optional<String> eval() {
        if (expandedFeatureFlag.getConditionalRolloutRules() == null) {
            throw new IllegalArgumentException("Conditional rollout rules are null");
        }

        Map<Query, String> queryMap = expandedFeatureFlag.getConditionalRolloutRules().get(ctx.getEnvironment());
        if (queryMap == null) {
            throw new IllegalArgumentException("No conditional rules found for environment \"" + ctx.getEnvironment() + "\"");
        }

        final String result = queryMap
                .entrySet()
                .stream()
                .filter(e -> e.getKey().eval(ctx.getCtx()))
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(null);
        return Optional.ofNullable(result);
    }
}

package org.propagate.client.eval.rule;

import org.propagate.client.eval.EvaluationContext;
import org.propagate.common.rest.entity.PercentDistributionRestEntity;
import org.propagate.common.rest.entity.RolloutRuleRestEntity;

import java.util.*;

public class PercentRuleEvaluatorImpl implements RuleEvaluator {
    private static final Random r = new Random(1L);

    @Override
    public Optional<String> eval(RolloutRuleRestEntity rule, EvaluationContext evaluationContext) {
        final List<String> candidates = new ArrayList<>();
        for (PercentDistributionRestEntity percentDistribution : rule.getPercentDistribution()) {
            Optional.ofNullable(percentDistribution.getVariation())
                    .ifPresent(variation -> {
                        for (int i = 0; i < percentDistribution.getPercent(); i++) {
                            candidates.add(variation);
                        }
                    });

        }
        Collections.shuffle(candidates, r);
        if (!candidates.isEmpty()) {
            return Optional.of(candidates.get(Objects.hash(evaluationContext.getCtx()) % candidates.size()));
        }
        return Optional.empty();
    }
}

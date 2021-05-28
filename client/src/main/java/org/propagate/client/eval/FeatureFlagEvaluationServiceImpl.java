package org.propagate.client.eval;

import org.propagate.client.eval.rule.RuleEvaluator;
import org.propagate.client.eval.rule.RuleEvaluatorFactory;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;
import org.propagate.common.rest.entity.RolloutRuleRestEntity;
import org.propagate.common.rest.entity.RolloutRulesRestEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FeatureFlagEvaluationServiceImpl implements FeatureFlagEvaluationService {
    @Override
    public String eval(FeatureFlagRestEntity featureFlag, EvaluationContext evaluationContext, Supplier<String> fallback) {
        validateEvaluationRequest(featureFlag, evaluationContext);

        final RolloutRulesRestEntity rolloutRules = getRolloutRulesByEnv(featureFlag.getRolloutRules(), evaluationContext.getEnvironment()).orElseThrow(() -> new IllegalArgumentException("Unknown environment: \"" + evaluationContext.getEnvironment() + "\""));
        if (!featureFlag.isTargeting() && rolloutRules.getDefaultVariationTargetingOff() != null) { // targeting: OFF
            return rolloutRules.getDefaultVariationTargetingOff();
        }

        if (featureFlag.isTargeting() && rolloutRules.getRolloutRules() != null) {
            for (RolloutRuleRestEntity rolloutRule : rolloutRules.getRolloutRules()) {
                RuleEvaluator evaluator = RuleEvaluatorFactory.getInstance(rolloutRule);
                Optional<String> eval = evaluator.eval(rolloutRule, evaluationContext);
                if (eval.isPresent()) {
                    return eval.get();
                }
            }
        }

        if (featureFlag.isTargeting() && rolloutRules.getDefaultVariationTargetingOn() != null) { // targeting: ON
            return rolloutRules.getDefaultVariationTargetingOn();
        }

        return fallback != null ? fallback.get() : null;
    }

    private void validateEvaluationRequest(FeatureFlagRestEntity featureFlag, EvaluationContext ctx) {
        if (ctx.getEnvironment() == null) {
            throw new NullPointerException("Environment is null");
        }

        if (featureFlag.isArchived()) {
            throw new IllegalArgumentException("Evaluation of an archived feature flag");
        }
    }

    private Optional<RolloutRulesRestEntity> getRolloutRulesByEnv(List<RolloutRulesRestEntity> rolloutRules, String environment) {
        for (RolloutRulesRestEntity rolloutRule : rolloutRules) {
            if (rolloutRule.getNamespace().equals(environment)) {
                return Optional.of(rolloutRule);
            }
        }
        return Optional.empty();
    }
}

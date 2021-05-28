package org.propagate.client.eval;

import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.util.function.Supplier;

public interface FeatureFlagEvaluationService {
    String eval(FeatureFlagRestEntity featureFlag, EvaluationContext evaluationContext, Supplier<String> fallback);
}

package org.propagate.client.eval;

import java.util.Map;

public class EvaluationContext {
    private final String environment;
    private final Map<String, Object> ctx;

    public EvaluationContext(String environment, Map<String, Object> ctx) {
        this.environment = environment;
        this.ctx = ctx;
    }

    public String getEnvironment() {
        return environment;
    }

    public Map<String, Object> getCtx() {
        return ctx;
    }

    @Override
    public String toString() {
        return "EvaluationContext{" +
                "environment='" + environment + '\'' +
                ", ctx=" + ctx +
                '}';
    }
}

package org.propagate.common.domain;

import java.io.Serializable;

public class Condition implements Serializable {
    private String variation;

    private String expression;

    public Condition() {
    }

    public Condition(String variation, String expression) {
        this.variation = variation;
        this.expression = expression;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "variation='" + variation + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}

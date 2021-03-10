package org.propagate.common.domain.rollout;

import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
public class ConditionalRollout extends RolloutRule implements Serializable {
    private String expression;

    public ConditionalRollout(String variation, String expression) {
        super(variation);
        this.expression = expression;
    }

    public ConditionalRollout() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public static void main(String[] args) {
        ConditionalRollout.builder();
    }

    @Override
    public String toString() {
        return "ConditionalRollout{" +
                "expression='" + expression + '\'' +
                ", variation='" + variation + '\'' +
                '}';
    }
}

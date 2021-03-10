package org.propagate.common.domain.rollout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ConditionalRollout extends RolloutRule implements Serializable {
    private String expression;

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

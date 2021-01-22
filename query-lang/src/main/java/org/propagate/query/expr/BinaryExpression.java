package org.propagate.query.expr;

import org.propagate.query.Expression;

import java.util.Objects;

public abstract class BinaryExpression implements Expression {
    protected final String variable;
    protected final Object value;

    public BinaryExpression(String variable, Object value) {
        this.variable = Objects.requireNonNull(variable);
        this.value = value;
    }
}

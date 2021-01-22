package org.propagate.query.expr;

import org.propagate.query.Expression;

import java.util.List;
import java.util.Objects;

public abstract class MultiExpression implements Expression {
    protected final List<Expression> expressions;

    public MultiExpression(List<Expression> expressions) {
        this.expressions = Objects.requireNonNull(expressions);
    }
}

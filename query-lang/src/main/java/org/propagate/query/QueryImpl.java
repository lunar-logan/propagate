package org.propagate.query;

import java.util.Map;
import java.util.Objects;

public class QueryImpl implements Query {
    private final Expression expression;

    public QueryImpl(Expression expression) {
        this.expression = Objects.requireNonNull(expression);
    }

    @Override
    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean eval(Map<String, Object> bindings) {
        return (boolean) expression.eval(bindings);
    }
}

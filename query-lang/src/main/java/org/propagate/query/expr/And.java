package org.propagate.query.expr;

import org.propagate.query.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class And extends MultiExpression {
    public And(List<Expression> expressions) {
        super(expressions);
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        return expressions.stream().allMatch(exp -> (boolean) exp.eval(bindings));
    }

    @Override
    public String toString() {
        return expressions.stream()
                .map(Expression::toString)
                .collect(Collectors.joining(" AND ", "(", ")"));
    }
}

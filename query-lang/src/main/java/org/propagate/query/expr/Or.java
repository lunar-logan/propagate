package org.propagate.query.expr;

import org.propagate.query.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Or extends MultiExpression {
    public Or(List<Expression> expressions) {
        super(expressions);
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        return expressions.stream().anyMatch(exp -> (boolean) exp.eval(bindings));
    }

    @Override
    public String toString() {
        return expressions.stream()
                .map(Expression::toString)
                .collect(Collectors.joining(" OR ", "(", ")"));
    }
}

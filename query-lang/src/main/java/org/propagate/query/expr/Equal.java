package org.propagate.query.expr;

import java.util.Map;
import java.util.Optional;

public class Equal extends BinaryExpression {
    public Equal(String variable, Object value) {
        super(variable, value);
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        if (bindings != null) {
            return Optional.ofNullable(bindings.get(variable))
                    .map(actualVal -> actualVal.equals(value))
                    .orElse(false);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + variable + " = " + value + ")";
    }
}

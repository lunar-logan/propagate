package org.propagate.query;

import java.util.Map;

public interface Expression {
    Object eval(Map<String, Object> bindings);
}

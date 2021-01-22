package org.propagate.query;

import java.util.Map;
// a=b AND (c=d OR e!=f)
public interface Query {
    Expression getExpression();
    boolean eval(Map<String, Object> bindings);
}

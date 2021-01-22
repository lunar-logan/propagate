package org.propagate.query;

public interface QueryFactory {
    Query parse(String query);
}

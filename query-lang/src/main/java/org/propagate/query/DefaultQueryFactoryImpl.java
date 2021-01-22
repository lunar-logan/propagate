package org.propagate.query;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.propagate.query.parser.PropagateQueryExpressionLexer;
import org.propagate.query.parser.PropagateQueryExpressionParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultQueryFactoryImpl implements QueryFactory  {

    // todo: should probably limit the size here
    private final Map<String, Query> cache = new ConcurrentHashMap<>();

    @Override
    public Query parse(String query) {
        return cache.computeIfAbsent(query, s -> new QueryImpl(parseExpression(query)));
    }

    private Expression parseExpression(String expression) {
        final CommonTokenStream tokenStream = new CommonTokenStream(new PropagateQueryExpressionLexer(CharStreams.fromString(expression)));
        final PropagateQueryExpressionParser parser = new PropagateQueryExpressionParser(tokenStream);
        return parser.prog().accept(new PropagateQueryVisitorImpl());
    }
}

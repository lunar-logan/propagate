package org.propagate.query;

import org.propagate.query.expr.And;
import org.propagate.query.expr.Equal;
import org.propagate.query.expr.NotEqual;
import org.propagate.query.expr.Or;
import org.propagate.query.parser.PropagateQueryExpressionBaseVisitor;
import org.propagate.query.parser.PropagateQueryExpressionParser;

import java.util.List;

public class PropagateQueryVisitorImpl extends PropagateQueryExpressionBaseVisitor<Expression> {
    @Override
    public Expression visitAssign(PropagateQueryExpressionParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        String value = ctx.TEXT().getText().substring(1, ctx.TEXT().getText().length() - 1);
        return switch (ctx.op.getType()) {
            case PropagateQueryExpressionParser.EQ -> new Equal(id, value);
            case PropagateQueryExpressionParser.NEQ -> new NotEqual(id, value);
            default -> throw new IllegalArgumentException("Unknown operator [" + ctx.op.getText() + "]");
        };
    }

    @Override
    public Expression visitAndOr(PropagateQueryExpressionParser.AndOrContext ctx) {
        final Expression left = visit(ctx.expr(0));
        final Expression right = visit(ctx.expr(1));
        return switch (ctx.op.getType()) {
            case PropagateQueryExpressionParser.AND -> new And(List.of(left, right));
            case PropagateQueryExpressionParser.OR -> new Or(List.of(left, right));
            default -> throw new IllegalArgumentException("Unknown operator [" + ctx.op.getText() + "]");
        };
    }

    @Override
    public Expression visitParens(PropagateQueryExpressionParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}

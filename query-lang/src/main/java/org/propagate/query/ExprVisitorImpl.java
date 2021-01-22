package org.propagate.query;

import org.propagate.query.gen.ExprBaseVisitor;
import org.propagate.query.gen.ExprParser;

import java.util.HashMap;
import java.util.Map;

public class ExprVisitorImpl extends ExprBaseVisitor<Integer> {
    /**
     * "memory" for our calculator; variable/value pairs go here
     */
    final Map<String, Integer> memory = new HashMap<>();

    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Integer val = visit(ctx.expr());
        memory.put(id, val);
        return val;
    }

    @Override
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        Integer result = visit(ctx.expr());
        System.out.println(result);
        return 0;
    }

    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0);
    }

    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        Integer x = visit(ctx.expr(0));
        Integer y = visit(ctx.expr(1));
        return switch (ctx.op.getType()) {
            case ExprParser.MUL -> x * y;
            case ExprParser.DIV -> x / y;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        Integer x = visit(ctx.expr(0));
        Integer y = visit(ctx.expr(1));
        return switch (ctx.op.getType()) {
            case ExprParser.ADD -> x + y;
            case ExprParser.SUB -> x - y;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public Integer visitParens(ExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}

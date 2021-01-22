// Generated from /Users/anurag/git/propagate/query-lang/src/main/antlr/PropagateQueryExpression.g4 by ANTLR 4.9
package org.propagate.query.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PropagateQueryExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PropagateQueryExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PropagateQueryExpressionParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(PropagateQueryExpressionParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andOr}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOr(PropagateQueryExpressionParser.AndOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(PropagateQueryExpressionParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(PropagateQueryExpressionParser.AssignContext ctx);
}
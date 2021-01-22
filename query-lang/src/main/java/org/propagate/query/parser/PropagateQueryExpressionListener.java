// Generated from /Users/anurag/git/propagate/query-lang/src/main/antlr/PropagateQueryExpression.g4 by ANTLR 4.9
package org.propagate.query.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PropagateQueryExpressionParser}.
 */
public interface PropagateQueryExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PropagateQueryExpressionParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(PropagateQueryExpressionParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link PropagateQueryExpressionParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(PropagateQueryExpressionParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andOr}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndOr(PropagateQueryExpressionParser.AndOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andOr}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndOr(PropagateQueryExpressionParser.AndOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(PropagateQueryExpressionParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(PropagateQueryExpressionParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign(PropagateQueryExpressionParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign}
	 * labeled alternative in {@link PropagateQueryExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign(PropagateQueryExpressionParser.AssignContext ctx);
}
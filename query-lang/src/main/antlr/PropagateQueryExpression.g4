grammar PropagateQueryExpression;
import PropagateQueryLexerRules;

prog: expr;

expr: ID op=('=' | '!=') TEXT       # assign
    | expr op=('AND' | 'OR') expr   # andOr
    | '(' expr ')'                  # parens
    ;

EQ: '=';
NEQ: '!=';
AND: 'AND';
OR: 'OR';

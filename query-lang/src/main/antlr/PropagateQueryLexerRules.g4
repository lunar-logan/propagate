lexer grammar PropagateQueryLexerRules;
ID: [a-zA-Z]+[a-zA-Z0-9_]* ;
NEWLINE:'\r'? '\n' ; // return newlines to parser (end-statement signal)
TEXT : '"'.*?'"' ;
WS : [ \t]+ -> skip ;

lexer grammar MxLexer;

@headerBlock {
package chaos.compiler.frontend.parser;
}

// 1 Operator

// 1.1 Standard Operator
Add: '+' ;
Sub: '-' ;
Mul: '*' ;
Div: '/' ;
Mod: '%' ;

// 1.2 Relation Operator
Greater: '>' ;
Less: '<' ;
GreaterEqual: '>=' ;
LessEqual: '<=' ;
NotEqual: '!=' ;
Equal: '==' ;

// 1.3 Logic Operator
LogicAnd: '&&' ;
LogicOr: '||' ;
LogicNot: '!' ;

// 1.4 Bit Operator
ShiftLeft: '<<' ;
ShiftRight: '>>' ;
BitAnd: '&' ;
BitOr: '|' ;
BitXor: '^' ;
BitNot: '~' ;

// 1.5 Assign Operator
Assign: '=' ;

// 1.6 Increment & Decrement Operator
Increment: '++' ;
Decrement: '--' ;

// 1.7 Member Operator
Member: '.' ;

// 1.8 Index Operator
LeftBracket: '[';
RightBracket: ']';

// 1.9 Priority Operator
LeftParen: '(';
RightParen: ')';

// 1.10 Seperator Operator
SemiColon: ';';
Comma: ',';
LeftBrace: '{';
RightBrace: '}';

// 1.11 Specific Operator
Quote: '"';
LambdaStart: '[&]';
LambdaArrow: '->' ;

// 2.Keyword
IntType: 'int' ;
BoolType: 'bool' ;
StringType: 'string' ;
VoidType: 'void' ;
NullConstant: 'null' ;
TrueConstant: 'true' ;
FalseConstant: 'false' ;
If: 'if' ;
Else: 'else' ;
For: 'for' ;
While: 'while' ;
Break: 'break' ;
Continue: 'continue' ;
Return: 'return' ;
New: 'new' ;
Class: 'class' ;
This: 'this' ;

// 3 Blank
WhitespaceEater: (' ' | '\t' | '\r' | '\n')+ -> skip ;

// 4 Comment
LineCommentEater: '//' ~[\r\n]* -> skip ;
BlockCommentEater: '/*' .*? '*/' -> skip ;

// 5 Identifier
Identifier: [a-zA-Z] [a-zA-Z_0-9]* ;

// 6 Constant

// 6.1 Bool Constant -> 2.Keyword

// 6.2 Integer Constant
IntegerConstant: '0' | [1-9][0-9]* ;

// 6.3 String Constant
StringConstant: Quote (ESC|.)*? Quote;
fragment ESC:'\\n' | '\\\\' | '\\"'; //Escape character

// 6.4 Null Constant -> 2.Keyword
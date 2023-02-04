parser grammar MxParser;

@headerBlock {
package chaos.compiler.frontend.parser;
}
options {
tokenVocab = MxLexer;
}

// 0 Program

mxProgram: (classDef | funcDef | varDefStmt)* EOF ;

// 7 Variable

// 7.1 BuiltinType

builtinType: BoolType | IntType | StringType | Identifier;

// 7.2 ArrayType

varDefType: (VoidType | builtinType) (LeftBracket RightBracket)* ;

arraySizeDef: LeftBracket expr? RightBracket ;

varDef: varDefType varDefSingle (Comma varDefSingle)* ;

varDefSingle: Identifier (Assign expr)? ;

// 8 Class

classDef: Class Identifier LeftBrace (varDefStmt | classConstructorDef | funcDef)* RightBrace SemiColon ;

classConstructorDef: Identifier LeftParen RightParen suite ;

// 9 Function

funcDef: varDefType Identifier LeftParen parameterList? RightParen suite ;

parameterList: varDefType Identifier (Comma varDefType Identifier)* ;

funcCallArgs: LeftParen (expr (Comma expr)*)? RightParen ;

// 10 expr

// 10.1   Set

atom: Identifier | IntegerConstant | StringConstant | FalseConstant | TrueConstant | NullConstant | This ;
prefix : (Increment | Decrement);
postfix : (Increment | Decrement);
unary : (BitNot | LogicNot | Add | Sub);
shift : (ShiftLeft | ShiftRight);
mulLevel : (Mul | Div | Mod);
addLevel : (Add | Sub);
compare : (Greater | GreaterEqual | Less | LessEqual);
equal : (Equal | NotEqual);
bitLogic : (BitAnd | BitXor | BitOr | LogicAnd | LogicOr) ;

expr
    :   atom                                                                                                            #atomExp
    |   LeftParen expr RightParen                                                                                       #parenExp
    |   New (builtinType | VoidType) arraySizeDef* (LeftParen RightParen)?                                              #newExp
    |   expr LeftBracket expr RightBracket                                                                              #indexExp
    |   expr Member Identifier                                                                                          #memberExp
    |   expr funcCallArgs                                                                                               #funcCallExp
    |   LambdaStart (LeftParen parameterList? RightParen)?LambdaArrow suite funcCallArgs                                #lambdaExp
    |   expr postfix                                                                                                    #postfixExp
    |   <assoc=right> prefix expr                                                                                       #prefixExp
    |   <assoc=right> unary expr                                                                                        #unaryExp
    |   expr mulLevel expr                                                                                              #binaryExp
    |   expr addLevel expr                                                                                              #binaryExp
    |   expr shift expr                                                                                                 #binaryExp
    |   expr compare expr                                                                                               #binaryExp
    |   expr equal expr                                                                                                 #binaryExp
    |   expr bitLogic  expr                                                                                             #binaryExp
    |   <assoc=right> expr Assign expr                                                                                  #assignExp
    ;

// 11 Statement

suite: LeftBrace statement* RightBrace ;
varDefStmt: varDef SemiColon ;
ifStmt: If LeftParen expr RightParen statement (Else statement)?;
whileStmt: While LeftParen expr RightParen statement;
forInit: (varDef | expr);
forStmt: For LeftParen forInit? SemiColon forCondition = expr? SemiColon forIncr = expr? RightParen statement ;
returnStmt: Return expr? SemiColon;
controlStmt: (Break | Continue) SemiColon;
pureStmt: expr? SemiColon;

statement: suite | ifStmt | whileStmt | forStmt | controlStmt | returnStmt | varDefStmt | pureStmt ;

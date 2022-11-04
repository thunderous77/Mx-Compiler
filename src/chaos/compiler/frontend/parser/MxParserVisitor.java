// Generated from D:/Sam/program/Compiler-2022/src/chaos/compiler/frontend/grammar\MxParser.g4 by ANTLR 4.10.1

package chaos.compiler.frontend.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#mxProgram}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMxProgram(MxParser.MxProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#builtinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltinType(MxParser.BuiltinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDefType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefType(MxParser.VarDefTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#arraySizeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArraySizeDef(MxParser.ArraySizeDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDefSingle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefSingle(MxParser.VarDefSingleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classConstructorDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstructorDef(MxParser.ClassConstructorDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcCallArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallArgs(MxParser.FuncCallArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(MxParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix(MxParser.PrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(MxParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(MxParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#shift}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift(MxParser.ShiftContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#mulLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulLevel(MxParser.MulLevelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#addLevel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddLevel(MxParser.AddLevelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(MxParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#equal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(MxParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#bitLogic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitLogic(MxParser.BitLogicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExp(MxParser.AtomExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExp(MxParser.PrefixExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp(MxParser.BinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExp(MxParser.AssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExp(MxParser.MemberExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp(MxParser.UnaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExp(MxParser.NewExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExp(MxParser.LambdaExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExp(MxParser.ParenExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExp(MxParser.FuncCallExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postfixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExp(MxParser.PostfixExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExp(MxParser.IndexExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(MxParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#controlStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStmt(MxParser.ControlStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#pureStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureStmt(MxParser.PureStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxParser.StatementContext ctx);
}
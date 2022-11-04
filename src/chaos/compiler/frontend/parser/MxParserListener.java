// Generated from D:/Sam/program/Compiler-2022/src/chaos/compiler/frontend/grammar\MxParser.g4 by ANTLR 4.10.1

package chaos.compiler.frontend.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#mxProgram}.
	 * @param ctx the parse tree
	 */
	void enterMxProgram(MxParser.MxProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#mxProgram}.
	 * @param ctx the parse tree
	 */
	void exitMxProgram(MxParser.MxProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#builtinType}.
	 * @param ctx the parse tree
	 */
	void enterBuiltinType(MxParser.BuiltinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#builtinType}.
	 * @param ctx the parse tree
	 */
	void exitBuiltinType(MxParser.BuiltinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDefType}.
	 * @param ctx the parse tree
	 */
	void enterVarDefType(MxParser.VarDefTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDefType}.
	 * @param ctx the parse tree
	 */
	void exitVarDefType(MxParser.VarDefTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#arraySizeDef}.
	 * @param ctx the parse tree
	 */
	void enterArraySizeDef(MxParser.ArraySizeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#arraySizeDef}.
	 * @param ctx the parse tree
	 */
	void exitArraySizeDef(MxParser.ArraySizeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(MxParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDefSingle}.
	 * @param ctx the parse tree
	 */
	void enterVarDefSingle(MxParser.VarDefSingleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDefSingle}.
	 * @param ctx the parse tree
	 */
	void exitVarDefSingle(MxParser.VarDefSingleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classConstructorDef}.
	 * @param ctx the parse tree
	 */
	void enterClassConstructorDef(MxParser.ClassConstructorDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classConstructorDef}.
	 * @param ctx the parse tree
	 */
	void exitClassConstructorDef(MxParser.ClassConstructorDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcCallArgs}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallArgs(MxParser.FuncCallArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcCallArgs}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallArgs(MxParser.FuncCallArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MxParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MxParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#prefix}.
	 * @param ctx the parse tree
	 */
	void enterPrefix(MxParser.PrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#prefix}.
	 * @param ctx the parse tree
	 */
	void exitPrefix(MxParser.PrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(MxParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(MxParser.PostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(MxParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(MxParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#shift}.
	 * @param ctx the parse tree
	 */
	void enterShift(MxParser.ShiftContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#shift}.
	 * @param ctx the parse tree
	 */
	void exitShift(MxParser.ShiftContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#mulLevel}.
	 * @param ctx the parse tree
	 */
	void enterMulLevel(MxParser.MulLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#mulLevel}.
	 * @param ctx the parse tree
	 */
	void exitMulLevel(MxParser.MulLevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#addLevel}.
	 * @param ctx the parse tree
	 */
	void enterAddLevel(MxParser.AddLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#addLevel}.
	 * @param ctx the parse tree
	 */
	void exitAddLevel(MxParser.AddLevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompare(MxParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompare(MxParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#equal}.
	 * @param ctx the parse tree
	 */
	void enterEqual(MxParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#equal}.
	 * @param ctx the parse tree
	 */
	void exitEqual(MxParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#bitLogic}.
	 * @param ctx the parse tree
	 */
	void enterBitLogic(MxParser.BitLogicContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#bitLogic}.
	 * @param ctx the parse tree
	 */
	void exitBitLogic(MxParser.BitLogicContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExp(MxParser.AtomExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExp(MxParser.AtomExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExp(MxParser.PrefixExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExp(MxParser.PrefixExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp(MxParser.BinaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp(MxParser.BinaryExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExp(MxParser.AssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExp(MxParser.AssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExp(MxParser.MemberExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExp(MxParser.MemberExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExp(MxParser.UnaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExp(MxParser.UnaryExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewExp(MxParser.NewExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewExp(MxParser.NewExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExp(MxParser.LambdaExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExp(MxParser.LambdaExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExp(MxParser.ParenExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExp(MxParser.ParenExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCallExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExp(MxParser.FuncCallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCallExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExp(MxParser.FuncCallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code postfixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExp(MxParser.PostfixExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code postfixExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExp(MxParser.PostfixExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndexExp(MxParser.IndexExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExp}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndexExp(MxParser.IndexExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MxParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MxParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(MxParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(MxParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#controlStmt}.
	 * @param ctx the parse tree
	 */
	void enterControlStmt(MxParser.ControlStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#controlStmt}.
	 * @param ctx the parse tree
	 */
	void exitControlStmt(MxParser.ControlStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#pureStmt}.
	 * @param ctx the parse tree
	 */
	void enterPureStmt(MxParser.PureStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#pureStmt}.
	 * @param ctx the parse tree
	 */
	void exitPureStmt(MxParser.PureStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxParser.StatementContext ctx);
}
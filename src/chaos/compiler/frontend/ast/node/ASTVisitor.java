package chaos.compiler.frontend.ast.node;

import chaos.compiler.frontend.ast.node.*;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.*;

public interface ASTVisitor {

    void visit(RootNode node);

    void visit(AssignExprNode node);

    void visit(AtomExprNode node);

    void visit(BinaryExprNode node);

    void visit(FuncCallExprNode node);

    void visit(IndexExprNode node);

    void visit(LambdaExprNode node);

    void visit(MemberExprNode node);

    void visit(NewExprNode node);

    void visit(PostfixExprNode node);

    void visit(PrefixExprNode node);

    void visit(UnaryExprNode node);

    void visit(ControlStmtNode node);

    void visit(ForStmtNode node);

    void visit(IfStmtNode node);

    void visit(PureStmtNode node);

    void visit(ReturnStmtNode node);

    void visit(SuiteStmtNode node);

    void visit(VarDefSingleNode node);

    void visit(VarDefStmtNode node);

    void visit(WhileStmtNode node);

    void visit(FuncDefNode node);

    void visit(ClassDefNode node);

}
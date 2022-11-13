package chaos.compiler.frontend.semantic;

import java.util.Objects;

import chaos.compiler.frontend.utility.ArrayBuiltinFunc;
import chaos.compiler.frontend.utility.StringBuiltinFunc;
import chaos.compiler.frontend.utility.type.FuncType;
import chaos.utility.Error;
import chaos.compiler.frontend.ast.node.*;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.*;
import chaos.compiler.frontend.utility.Commander;
import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.registry.VarRegistry;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.frontend.utility.type.VarType;

public class SemanticChecker implements ASTVisitor {

    private final Commander commander = new Commander();

    @Override
    public void visit(RootNode node) {
        commander.push(node.scope);
        node.sonNodes.forEach(sonnode -> sonnode.accept(this));
        commander.pop();
    }

    @Override
    public void visit(AssignExprNode node) {
        assert node.lNode != null;
        assert node.rNode != null;

        node.lNode.accept(this);
        node.rNode.accept(this);
        TypeMatcher.match(node);
        node.type = node.rNode.type.copy();
    }

    @Override
    public void visit(AtomExprNode node) {
        if (node.context.IntegerConstant() != null)
            node.type = new VarType(BaseType.BuiltinType.INT);
        else if (node.context.StringConstant() != null)
            node.type = new VarType(BaseType.BuiltinType.STRING);
        else if (node.context.TrueConstant() != null || node.context.FalseConstant() != null)
            node.type = new VarType(BaseType.BuiltinType.BOOL);
        else if (node.context.NullConstant() != null)
            node.type = new VarType(BaseType.BuiltinType.NULL);
        else if (node.context.This() != null) {
            ClassRegistry currentClass = commander.currentClass();
            if (currentClass == null)
                throw new Error(node.pos, "\"this\" is not a class body");
            node.type = new VarType(currentClass.name);
        } else if (node.context.Identifier() != null) {
            VarRegistry varRegistry = commander.queryVar(node.context.Identifier().getText());
            FuncRegistry funcRegistry = commander.queryFunc(node.context.Identifier().getText());
            if (varRegistry != null)
                node.type = varRegistry.type.copy();
            else if (funcRegistry != null)
                node.type = funcRegistry.type.copy();
            else throw new Error(node.pos, "nameError: \"" + node.context.Identifier().getText() + "\" is undefined");
        }
    }

    @Override
    public void visit(BinaryExprNode node) {
        assert node.lNode != null;
        assert node.rNode != null;
        node.lNode.accept(this);
        node.rNode.accept(this);
        TypeMatcher.match(node);
        if (Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS_EQUAL) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER_EQUAL) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.EQUAL) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.NOT_EQUAL))
            node.type = new VarType(BaseType.BuiltinType.BOOL);
        else node.type = node.rNode.type.copy();
    }

    @Override
    public void visit(FuncCallExprNode node) {
        node.funcCallNode.accept(this);
        node.argNodes.forEach(sonnode -> sonnode.accept(this));

        if (!(node.funcCallNode.type instanceof FuncType)) {
            if (!(node.funcCallNode instanceof AtomExprNode))
                throw new Error(node.pos, "\"" + ((AtomExprNode) node.funcCallNode).context.Identifier().getText() + "\" is not a function");
            if (((AtomExprNode) node.funcCallNode).context.Identifier() == null)
                throw new Error(node.pos, "\"" + ((AtomExprNode) node.funcCallNode).context.Identifier().getText() + "\" is not a function");
            FuncRegistry funcRegistry = commander.queryFunc(((AtomExprNode) node.funcCallNode).context.Identifier().getText());
            if (funcRegistry == null)
                throw new Error(node.pos, "\"" + ((AtomExprNode) node.funcCallNode).context.Identifier().getText() + "\" is not a function");
            else node.funcCallNode.type = funcRegistry.type.copy();
        }

        int result = ((FuncType) node.funcCallNode.type).funcCallMatch(node.argNodes);
        if (result == -1)
            throw new Error(node.pos, "wrong number of the input arguments for the function");
        else if (result == -2)
            throw new Error(node.pos, "wrong builtinType of the input arguments for the function");

        node.type = ((FuncType) node.funcCallNode.type).retType.copy();
    }

    @Override
    public void visit(IndexExprNode node) {
        if (node.arrayNode != null) node.arrayNode.accept(this);
        if (node.indexNode != null) node.indexNode.accept(this);
        assert node.arrayNode != null;
        TypeMatcher.match(node);
        node.type = node.arrayNode.type.copy();
        ((VarType) node.type).dimensize--;
    }

    @Override
    public void visit(LambdaExprNode node) {
        commander.push(node.funcRegistry.scope);
        for (VarRegistry varRegistry : node.funcRegistry.funcArgs) {
            if (varRegistry.type.builtinType == BaseType.BuiltinType.CLASS && commander.queryClass(varRegistry.type.name) == null)
                throw new Error(node.pos, "\"" + varRegistry.type.name + "\" is undefined");
            commander.register(varRegistry);
        }
        assert node.suiteNode != null;
        visit(node.suiteNode);
        node.ArgsNode.forEach(sonnode -> sonnode.accept(this));
        int result = node.funcRegistry.type.funcCallMatch(node.ArgsNode);
        if (result == -1)
            throw new Error(node.pos, "wrong number of the input arguments for the function");
        else if (result == -2)
            throw new Error(node.pos, "wrong builtinType of the input arguments for the function");
        if (node.funcRegistry.scope.retTypeList.isEmpty()) // no return
            node.type = new VarType(BaseType.BuiltinType.VOID);
        else {
            for (VarType retType : node.funcRegistry.scope.retTypeList) {
                if (node.type == null) {
                    node.type = retType;
                    node.funcRegistry.type.retType = retType;
                } else if (!node.type.match(retType))
                    throw new Error(node.pos, "wrong return builtinType");
            }
        }
        commander.pop();

    }

    @Override
    public void visit(MemberExprNode node) {
        if (node.superNode != null) node.superNode.accept(this);
        assert node.superNode != null;
        if (node.superNode.type.match(BaseType.BuiltinType.STRING)) {
            if (StringBuiltinFunc.scope.funcMap.containsKey(node.memberName)) {
                node.type = StringBuiltinFunc.scope.queryFunc(node.memberName).type.copy();
                return;
            }
        }
        if (node.superNode.type.isArray() && ArrayBuiltinFunc.scope.funcMap.containsKey(node.memberName)) {
            node.type = ArrayBuiltinFunc.scope.queryFunc(node.memberName).type.copy();
            return;
        }
        TypeMatcher.match(node);
        String className = ((VarType) node.superNode.type).name;
        ClassRegistry classRegistry = commander.queryClass(className);
        if (classRegistry == null) throw new Error(node.pos, "\'" + className + "\" is undefined");
        if (classRegistry.scope.funcMap.containsKey(node.memberName))
            node.type = classRegistry.scope.queryFunc(node.memberName).type.copy();
        else if (classRegistry.scope.varMap.containsKey(node.memberName))
            node.type = classRegistry.scope.queryVar(node.memberName).type.copy();
        else throw new Error(node.pos, "\'" + className + "\" is undefined");
    }

    @Override
    public void visit(NewExprNode node) {
        node.eachDimSizeNode.forEach(sonnode -> sonnode.accept(this));
        TypeMatcher.match(node);
    }

    @Override
    public void visit(PostfixExprNode node) {
        assert node.selfNode != null;
        node.selfNode.accept(this);
        TypeMatcher.match(node);
        node.type = node.selfNode.type.copy();
    }

    @Override
    public void visit(PrefixExprNode node) {
        assert node.selfNode != null;
        node.selfNode.accept(this);
        TypeMatcher.match(node);
        node.type = node.selfNode.type.copy();
    }

    @Override
    public void visit(UnaryExprNode node) {
        assert node.selfNode != null;
        node.selfNode.accept(this);
        TypeMatcher.match(node);
        node.type = node.selfNode.type.copy();
    }

    @Override
    public void visit(ControlStmtNode node) {
        if (!commander.isInLoop()) {
            if (Objects.equals(node.opcode, ControlStmtNode.controlOp.BREAK))
                throw new Error(node.pos, "\"break\" is not in a loop");
            else
                throw new Error(node.pos, "\"continue\" is not in a loop");
        }
    }

    @Override
    public void visit(ForStmtNode node) {
        commander.push(node.scope);
        if (node.initNode != null) node.initNode.accept(this);
        node.initVarDefSingleNode.forEach(sonnode -> sonnode.accept(this));
        if (node.conditionNode != null) {
            node.conditionNode.accept(this);
            TypeMatcher.match(node);
        }
        if (node.incrNode != null) node.incrNode.accept(this);
        assert node.bodyStmtNode != null;
        node.bodyStmtNode.accept(this);
        commander.pop();
    }

    @Override
    public void visit(IfStmtNode node) {
        node.conditionNode.accept(this);
        TypeMatcher.match(node);
        commander.push(node.ifScope);
        node.ifStmtNode.accept(this);
        commander.pop();
        if (node.elseStmtNode != null) {
            commander.push(node.elseScope);
            node.elseStmtNode.accept(this);
            commander.pop();
        }
    }

    @Override
    public void visit(PureStmtNode node) {
        if (node.exprNode != null) node.exprNode.accept(this);
    }

    @Override
    public void visit(ReturnStmtNode node) {
        if (!commander.isInFunc()) throw new Error(node.pos, "\"return\" is not in a function");
        if (node.retNode != null) {
            node.retNode.accept(this);
            commander.stackReturn((VarType) node.retNode.type);
        } else commander.stackReturn(new VarType(BaseType.BuiltinType.VOID));
    }

    @Override
    public void visit(SuiteStmtNode node) {
        commander.push(node.scope);
        node.stmtNodes.forEach(sonnode -> sonnode.accept(this));
        commander.pop();
    }

    @Override
    public void visit(VarDefSingleNode node) {
        // from right to left, init first, register after
        if (node.initNode != null) node.initNode.accept(this);
        if (node.varRegistry.type.builtinType == BaseType.BuiltinType.CLASS && commander.queryClass(node.varRegistry.type.name) == null)
            throw new Error(node.pos, "\"" + node.varRegistry.type.name + "\" is undefined");
        if (node.initNode != null) TypeMatcher.match(node);
        commander.register(node.varRegistry);
    }

    @Override
    public void visit(VarDefStmtNode node) {
        node.varDefSingleNodes.forEach(sonnode -> sonnode.accept(this));
    }

    @Override
    public void visit(WhileStmtNode node) {
        commander.push(node.scope);

        node.conditionNode.accept(this);

        TypeMatcher.match(node);

        if (node.bodyStmtNode != null) node.bodyStmtNode.accept(this);

        commander.pop();
    }

    @Override
    public void visit(FuncDefNode node) {
        commander.push(node.funcRegistry.scope);

        // query class builtinType
        for (VarRegistry registry : node.funcRegistry.funcArgs) {
            if (registry.type.builtinType == BaseType.BuiltinType.CLASS &&
                    commander.queryClass(registry.type.name) == null) {
                throw new Error(node.pos, "\"" + registry.type.name + "\" is undefined");
            }
            commander.register(registry);
        }

        assert node.suiteNode != null;
        visit(node.suiteNode);

        if (node.funcRegistry.scope.retTypeList.isEmpty()) { //no return statement
            if (!node.isValidMainFunc() && !node.funcRegistry.type.retType.match(BaseType.BuiltinType.VOID)) {
                throw new Error(node.pos, "no return statement in the function");
            }
        } else {
            // check every return statement
            for (VarType catchedRetType : node.funcRegistry.scope.retTypeList) {
                // retType != null
                assert node.funcRegistry.type.retType != null;
                if (!node.funcRegistry.type.retType.match(catchedRetType)) {
                    throw new Error(node.pos, "function returns a wrong builtinType");
                }
            }
        }

        commander.pop();
    }

    @Override
    public void visit(ClassDefNode node) {
        commander.push(node.classRegistry);
        assert node.constructorDefNode != null;
        visit(node.constructorDefNode);
        node.varDefStmtNodes.forEach(sonnode -> sonnode.accept(this));
        node.funcDefNodes.forEach(sonnode -> sonnode.accept(this));
        commander.pop();
    }

}
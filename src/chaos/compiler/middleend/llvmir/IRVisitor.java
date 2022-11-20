package chaos.compiler.middleend.llvmir;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.*;
import chaos.compiler.frontend.ast.node.stmt.*;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.VarDefSingleNode;
import chaos.compiler.frontend.utility.Commander;
import chaos.compiler.frontend.utility.StringBuiltinFunc;
import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.registry.VarRegistry;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
import chaos.compiler.middleend.llvmir.type.IRPointerType;
import chaos.compiler.middleend.llvmir.type.IRStructType;

import java.util.ArrayList;

public class IRVisitor implements ASTVisitor {

    public final IRModule module = new IRModule();
    private final Commander commander = new Commander();
    private final IRTranslator translator = new IRTranslator();

    public IRVisitor(RootNode rootNode) {
        this.visit(rootNode);
    }

    @Override
    public void visit(RootNode node) {
        commander.push(node.scope);
        translator.setGlobalScope(node.scope);
        builtinFuncDef(node);

        commander.pop();
    }

    @Override
    public void visit(ClassDefNode node) {
        commander.push(node.classRegistry.scope);

        commander.pop();
    }

    @Override
    public void visit(FuncDefNode node) {
        commander.push(node.funcRegistry.scope);

        commander.pop();
    }

    @Override
    public void visit(VarDefSingleNode node) {

    }

    @Override
    public void visit(SuiteStmtNode node) {
        commander.push(node.scope);
        node.stmtNodes.forEach(sonnode -> {
            sonnode.accept(this);
        });
    }

    @Override
    public void visit(VarDefStmtNode node) {
        node.varDefSingleNodes.forEach(sonnode -> {
            sonnode.accept(this);
        });
    }

    @Override
    public void visit(IfStmtNode node) {

    }

    @Override
    public void visit(WhileStmtNode node) {

    }

    @Override
    public void visit(ForStmtNode node) {

    }

    @Override
    public void visit(ReturnStmtNode node) {

    }

    @Override
    public void visit(ControlStmtNode node) {

    }

    @Override
    public void visit(PureStmtNode node) {

    }

    @Override
    public void visit(AssignExprNode node) {

    }

    @Override
    public void visit(BinaryExprNode node) {

    }

    @Override
    public void visit(FuncCallExprNode node) {

    }

    @Override
    public void visit(IndexExprNode node) {

    }

    @Override
    public void visit(MemberExprNode node) {

    }

    @Override
    public void visit(NewExprNode node) {

    }

    @Override
    public void visit(PostfixExprNode node) {

    }

    @Override
    public void visit(PrefixExprNode node) {

    }

    @Override
    public void visit(UnaryExprNode node) {

    }

    @Override
    public void visit(LambdaExprNode node) {

    }

    @Override
    public void visit(AtomExprNode node) {

    }

    private void createInitFunc() {
        FuncRegistry initRegistry = new FuncRegistry("_glb_init", BaseType.BuiltinType.VOID);
        initRegistry.isBuiltin = false;

    }

    private void builtinFuncDef(RootNode node) {
        // malloc & string bottom function
        module.setBottomFunction();

        //global builtin function
        for (FuncRegistry builtinFuncRegistry : node.scope.builtinFuncList) {
            IRFunction buitlinFunc = new IRFunction(builtinFuncRegistry.name, translator.transFuncType(builtinFuncRegistry.type, null), module);
            module.builtinFuncList.add(buitlinFunc);
            builtinFuncRegistry.value = buitlinFunc;
        }

        // string builtin function
        for (FuncRegistry builtinFuncRegistry : StringBuiltinFunc.scope.funcList) {
            IRFunction buitlinFunc = new IRFunction("_str_" + builtinFuncRegistry.name, translator.transFuncType(builtinFuncRegistry.type, IRPointerType.StringType), module);
            module.builtinFuncList.add(buitlinFunc);
            builtinFuncRegistry.value = buitlinFunc;
        }
    }

    public void classDef(RootNode node) {
        ArrayList<ClassRegistry> classRegList = new ArrayList<>();
        // add class
        for (BaseNode sonNode : node.sonNodes) {
            IRStructType defClass = new IRStructType(((ClassDefNode) sonNode).classRegistry.name);
            ((ClassDefNode) sonNode).classRegistry.value = defClass.struct;
            module.classList.add(defClass.struct);
            classRegList.add(((ClassDefNode) sonNode).classRegistry);
        }

        for (ClassRegistry classRegistry : classRegList) {
            IRStructType defClass = (IRStructType) classRegistry.value.type;
            // add member variable
            for (VarRegistry memberVar : classRegistry.memberVars)
                defClass.varTypeList.add(translator.transAllocaType(memberVar.type));
            // add member function
            for (FuncRegistry memberFunc : classRegistry.memberFuncs) {
                IRFunction defMemeberFunc = new IRFunction(defClass.structName + "." + memberFunc.name, translator.transFuncType(memberFunc.type, new IRPointerType(defClass)), module);
                module.funcList.add(defMemeberFunc);
                memberFunc.value = defMemeberFunc;
            }
        }
    }

}

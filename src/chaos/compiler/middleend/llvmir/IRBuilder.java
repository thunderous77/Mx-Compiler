package chaos.compiler.middleend.llvmir;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.*;
import chaos.compiler.frontend.ast.node.stmt.*;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.VarDefSingleNode;
import chaos.compiler.frontend.utility.Commander;
import chaos.compiler.frontend.utility.StringBuiltinFunc;
import chaos.compiler.frontend.utility.registry.*;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.frontend.utility.type.FuncType;
import chaos.compiler.frontend.utility.type.VarType;
import chaos.compiler.middleend.llvmir.constant.*;
import chaos.compiler.middleend.llvmir.hierarchy.*;
import chaos.compiler.middleend.llvmir.instruction.*;
import chaos.compiler.middleend.llvmir.type.*;
import chaos.utility.Error;
import chaos.utility.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class IRBuilder implements ASTVisitor {
    public final IRModule module = new IRModule();
    private final Commander commander = new Commander();
    private final IRCurrent current = new IRCurrent();
    private final IRTranslator translator = new IRTranslator();
    private boolean isGlobalVar = true;
    private IRFunction globalVarInitFunc = new IRFunction("global_var_init", new IRFunctionType(new IRVoidType(), null), module);

    public IRBuilder(RootNode rootNode) {
        this.visit(rootNode);
    }

    @Override
    public void visit(RootNode node) {
        // add global variable function at head
        module.funcList.add(globalVarInitFunc);

        commander.push(node.scope);
        translator.setGlobalScope(node.scope);

        // initialize
        builtinFuncDef(node);

        // class
        classDeclare(node);

        // function
        for (BaseNode sonNode : node.sonNodes)
            if (sonNode instanceof FuncDefNode) funcDeclare((FuncDefNode) sonNode);

        // global variable
        current.function = globalVarInitFunc;
        current.block = globalVarInitFunc.entryBlock;
        for (BaseNode sonNode : node.sonNodes) {
            if (sonNode instanceof VarDefStmtNode) sonNode.accept(this);
        }
        isGlobalVar = false;
        new IRBrInst(globalVarInitFunc.exitBlock, current.block);
        current.block = globalVarInitFunc.exitBlock;
        new IRRetInst(globalVarInitFunc.exitBlock);

        // common nodes
        for (BaseNode sonNode : node.sonNodes)
            if (!(sonNode instanceof VarDefStmtNode)) sonNode.accept(this);

        // terminate all blocks
        for (IRFunction function : module.funcList) {
            for (IRBlock block : function.blockList)
                if (!block.isTerminated()) new IRBrInst(function.exitBlock, block);
        }

        commander.pop();
    }

    @Override
    public void visit(ClassDefNode node) {
        commander.push(node.classRegistry.scope);

        current.classRegistry = node.classRegistry;

        node.varDefStmtNodes.forEach(sonNode -> sonNode.accept(this));

        assert node.constructorDefNode != null;
        node.constructorDefNode.accept(this);

        node.funcDefNodes.forEach(sonNode -> sonNode.accept(this));

        current.classRegistry = null;

        commander.pop();
    }

    @Override
    public void visit(FuncDefNode node) {
        commander.push(node.funcRegistry.scope);

        current.function = (IRFunction) node.funcRegistry.value;
        current.block = current.function.entryBlock;

        if (!node.funcRegistry.type.retType.match(BaseType.BuiltinType.VOID)) {
            current.function.retValuePtr = memAlloca("func.ret", translator.transAllocaType(node.funcRegistry.type.retType));
            // main function
            if (Objects.equals(node.funcRegistry.name, "main")) {
                // start with running global variable initiate function
                new IRCallInst(globalVarInitFunc, current.block);
                // store 0 as default return value
                memStore(current.function.retValuePtr, new IRIntergerConstant(0));
            }

            // initiate return statement here
            // in ReturnStmtNode we only store the return value
            new IRRetInst(memLoad(current.function.retValuePtr, current.function.exitBlock), current.function.exitBlock);
        } else new IRRetInst(current.function.exitBlock);

        for (int i = 0; i < current.function.getArgNum(); i++) {
            VarRegistry argRegistry;
            if (current.classRegistry == null)
                argRegistry = node.funcRegistry.funcArgsList.get(i);
            else {
                if (i == 0) {
                    // add "this" pointer as the first argument of member function
                    // pay attention that getArgNum() will also add 1
                    current.function.addOperand(new IRValue("this", new IRPointerType(current.classRegistry.value.type)));
                    continue;
                } else argRegistry = node.funcRegistry.funcArgsList.get(i - 1);
            }
            IRValue arg = new IRValue(argRegistry.name, translator.transVarType(argRegistry.type));
            IRValue allocaPtr = memAlloca(argRegistry.name, translator.transAllocaType(argRegistry.type));
            argRegistry.value = allocaPtr;
            current.function.addOperand(arg);
            memStore(allocaPtr, arg);
        }

        node.suiteNode.accept(this);

        commander.pop();
    }

    @Override
    public void visit(VarDefSingleNode node) {
        IRValue allocaPtr;

        // global variable
        if (isGlobalVar) {
            if (current.classRegistry != null)
                allocaPtr = new IRGlobalVariable(current.classRegistry.name + "." + node.varRegistry.name, translator.transAllocaType(node.varRegistry.type));
            else {
                allocaPtr = new IRGlobalVariable(node.varRegistry.name, translator.transAllocaType(node.varRegistry.type));
                module.globalVarList.add((IRGlobalVariable) allocaPtr);
            }
        } else allocaPtr = memAlloca(node.varRegistry.name, translator.transAllocaType(node.varRegistry.type));

        node.value = allocaPtr;
        node.varRegistry.value = allocaPtr;

        if (node.initNode != null) {
            node.initNode.accept(this);
            memStore(allocaPtr, node.initNode.value);
            if (allocaPtr instanceof IRGlobalVariable && (node.initNode.value instanceof IRIntergerConstant || node.initNode.value instanceof IRBoolConstant))
                ((IRGlobalVariable) allocaPtr).initValue = node.initNode.value;
        } else if (node.varRegistry.type.match(BaseType.BuiltinType.CLASS) || node.varRegistry.type.isArray())
            memStore(allocaPtr, new IRNullPointerConstant());
        else if (allocaPtr instanceof IRGlobalVariable) {
            if (((IRGlobalVariable) allocaPtr).pointedType().match(IRIntergerType.INT32))
                ((IRGlobalVariable) allocaPtr).initValue = new IRIntergerConstant(0);
            else if (((IRGlobalVariable) allocaPtr).pointedType().match(new IRBoolType()))
                ((IRGlobalVariable) allocaPtr).initValue = new IRBoolConstant(false);
        }
    }

    @Override
    public void visit(SuiteStmtNode node) {
        commander.push(node.scope);
        node.stmtNodes.forEach(sonnode -> sonnode.accept(this));
        commander.pop();
    }

    @Override
    public void visit(VarDefStmtNode node) {
        node.varDefSingleNodes.forEach(sonnode -> sonnode.accept(this));
    }

    @Override
    public void visit(IfStmtNode node) {
        IRBlock ifTrueBlock = new IRBlock("if.true", current.function);
        IRBlock exitBlock = new IRBlock("if.exit", current.function);

//        if (node.elseStmtNode == null) {
//            // condition
//            node.conditionNode.accept(this);
//            new BrInst(node.conditionNode.value, ifTrueBlock, exitBlock, current.block);
//
//            // true branch
//            current.block = ifTrueBlock;
//            commander.push(node.ifScope);
//            node.ifStmtNode.accept(this);
//            new BrInst(exitBlock, current.block);
//            commander.pop();
//        } else {
        IRBlock ifFalseBlock = new IRBlock("if.false", current.function);

        node.conditionNode.accept(this);
        new IRBrInst(node.conditionNode.value, ifTrueBlock, ifFalseBlock, current.block);

        // false branch
        current.block = ifFalseBlock;
        if (node.elseStmtNode != null) {
            commander.push(node.elseScope);
            node.elseStmtNode.accept(this);
            commander.pop();
        }
        new IRBrInst(exitBlock, current.block);

        // true branch
        current.block = ifTrueBlock;
        commander.push(node.ifScope);
        node.ifStmtNode.accept(this);
        new IRBrInst(exitBlock, current.block);
        commander.pop();
//        }

        current.block = exitBlock;
    }

    @Override
    public void visit(WhileStmtNode node) {
        IRBlock conditionBlock = new IRBlock("while.cond", current.function);
        IRBlock bodyBlock = new IRBlock("while.body", current.function);
        IRBlock exitBlock = new IRBlock("while.exit", current.function);

        new IRBrInst(conditionBlock, current.block);
        commander.push(node.scope);
        current.block = conditionBlock;
        node.conditionNode.accept(this);
        new IRBrInst(node.conditionNode.value, bodyBlock, exitBlock, current.block);

        current.block = bodyBlock;
        current.setLoopTarget(conditionBlock, exitBlock);
        node.bodyStmtNode.accept(this);
        new IRBrInst(conditionBlock, current.block);

        current.block = exitBlock;
        current.popLoopTarget();
        commander.pop();
    }

    @Override
    public void visit(ForStmtNode node) {
        IRBlock conditionBlock = new IRBlock("for.cond", current.function);
        IRBlock increaseBlock = new IRBlock("for.incr", current.function);
        IRBlock bodyBlock = new IRBlock("for.body", current.function);
        IRBlock exitBlock = new IRBlock("for.exit", current.function);

        commander.push(node.scope);

        // init
        if (node.initNode != null)
            node.initNode.accept(this);
        node.initVarDefSingleNode.forEach(sonNode -> sonNode.accept(this));
        new IRBrInst(conditionBlock, current.block);

        // condition block
        current.block = conditionBlock;
        if (node.conditionNode != null) {
            node.conditionNode.accept(this);
            new IRBrInst(node.conditionNode.value, bodyBlock, exitBlock, current.block);
        } else new IRBrInst(bodyBlock, current.block);

        // increase block
        current.block = increaseBlock;
        if (node.incrNode != null)
            node.incrNode.accept(this);
        new IRBrInst(conditionBlock, current.block);

        // body block
        current.block = bodyBlock;
        current.setLoopTarget(increaseBlock, exitBlock);
        node.bodyStmtNode.accept(this);
        new IRBrInst(increaseBlock, current.block);
        current.popLoopTarget();

        current.block = exitBlock;

        commander.pop();
    }

    @Override
    public void visit(ReturnStmtNode node) {
        if (node.retNode != null && !node.retNode.type.match(BaseType.BuiltinType.VOID)) {
            node.retNode.accept(this);
            if (node.retNode.value instanceof IRCallInst) ((IRCallInst) node.retNode.value).isTailCall = true;
            memStore(current.function.retValuePtr, node.retNode.value);
        }
        new IRBrInst(current.function.exitBlock, current.block);
    }

    @Override
    public void visit(ControlStmtNode node) {
        current.generateBrInst(node.opcode);
    }

    @Override
    public void visit(PureStmtNode node) {
        if (node.exprNode != null) node.exprNode.accept(this);
    }

    @Override
    public void visit(AssignExprNode node) {
        IRValue leftPtr;
        if (node.lNode instanceof AtomExprNode && node.lNode.type instanceof VarType && current.classRegistry == null)
            // condition: lNode is global variable (is variable in AtomExprNode except class member)
            // small optimizer: only need memory address
            leftPtr = queryGlobalVarStorePtr((AtomExprNode) node.lNode);
        else {
            node.lNode.accept(this);
            leftPtr = node.lNode.value.storePtr;
        }
        node.rNode.accept(this);
        memStore(leftPtr, node.rNode.value);
        node.value = node.rNode.value;
        node.value.storePtr = leftPtr;
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.lNode.accept(this);
        IRBlock entryBlock = current.block;

        if (node.opcode == BinaryExprNode.binaryOp.LOGIC_OR) {
            // a || b : if (!a) calculate b
            IRBlock logicOrContinueBlock = new IRBlock("logic_or_continue", current.function);
            IRBlock logicOrExitBlock = new IRBlock("logic_or_exit", current.function);
            new IRBrInst(node.lNode.value, logicOrExitBlock, logicOrContinueBlock, current.block);
            current.block = logicOrContinueBlock;
            node.rNode.accept(this);
            new IRBrInst(logicOrExitBlock, current.block);
            node.value = new IRPhiInst(new IRBoolType(), logicOrExitBlock, node.lNode.value, entryBlock, node.rNode.value, logicOrContinueBlock);
            current.block = logicOrExitBlock;
        } else if (node.opcode == BinaryExprNode.binaryOp.LOGIC_AND) {
            // a && b : if (a) calculate b
            IRBlock logicAndContinueBlock = new IRBlock("logic_and_continue", current.function);
            IRBlock logicAndExitBlock = new IRBlock("logic_and_exit", current.function);
            new IRBrInst(node.lNode.value, logicAndContinueBlock, logicAndExitBlock, current.block);
            current.block = logicAndContinueBlock;
            node.rNode.accept(this);
            new IRBrInst(logicAndExitBlock, current.block);
            node.value = new IRPhiInst(new IRBoolType(), logicAndExitBlock, node.lNode.value, entryBlock, node.rNode.value, logicAndContinueBlock);
            current.block = logicAndExitBlock;
        } else {
            node.rNode.accept(this);
            if (node.lNode.type.match(BaseType.BuiltinType.STRING))
                node.value = new IRCallInst(module.getStringFunc(node.opcode), current.block, node.lNode.value, node.rNode.value);
            else if (node.isCompare())
                node.value = new IRIcmpInst(IRTranslator.transBinaryOp(node.opcode), node.lNode.value, node.rNode.value, current.block);
            else if (node.isArith())
                node.value = new IRBinaryInst(IRTranslator.transBinaryOp(node.opcode), IRIntergerType.INT32, node.lNode.value, node.rNode.value, current.block);
        }
    }

    @Override
    public void visit(FuncCallExprNode node) {
        node.funcCallNode.accept(this);

        ArrayList<IRValue> argValueList = new ArrayList<>();

        // array.size() -> skip
        if (!(node.funcCallNode.value.type instanceof IRFunctionType)) {
            node.value = node.funcCallNode.value;
            return;
        }

        // member function -> add "this" pointer as the first argument
        if (((IRFunctionType) node.funcCallNode.value.type).methodFrom != null) {
            if (node.funcCallNode instanceof MemberExprNode)
                // out of the class definition (e.x. A.a())
                argValueList.add(((MemberExprNode) node.funcCallNode).superNode.value);
            else {
                // in the class definition (e.x. a())
                assert current.classRegistry != null;
                argValueList.add(current.getThis());
            }
        }

        for (int i = 0; i < node.argNodes.size(); i++) {
            node.argNodes.get(i).accept(this);
            argValueList.add(node.argNodes.get(i).value);
        }

        node.value = new IRCallInst((IRFunction) node.funcCallNode.value, current.block, argValueList);
    }

    @Override
    public void visit(IndexExprNode node) {
        node.arrayNode.accept(this);
        node.indexNode.accept(this);
        node.value = memLoad(new IRGEPInst(node.arrayNode.value.name + ".element", node.arrayNode.value, node.arrayNode.value.type, current.block, node.indexNode.value), current.block);
    }

    @Override
    public void visit(MemberExprNode node) {
        node.superNode.accept(this);

        if (node.superNode.type.isArray()) {
            // array.size()
            IRValue storePtr = new IRBitcastInst(node.superNode.value, new IRPointerType(IRIntergerType.INT32), current.block);
            node.value = memLoad(new IRGEPInst(storePtr, new IRPointerType(IRIntergerType.INT32), current.block, new IRIntergerConstant(-1)), current.block);
        } else if (node.superNode.type.match(BaseType.BuiltinType.STRING))
            // string.builtinFunction
            node.value = StringBuiltinFunc.scope.queryFunc(node.memberName).value;
        else {
            // class.member / member function
            String className = ((VarType) node.superNode.type).name;
            ClassRegistry classRegistry = commander.queryClass(className);

            if (node.type instanceof VarType) {
                VarRegistry varRegistry = classRegistry.scope.queryVar(node.memberName);
                IRValue varPtr = new IRGEPInst(node.memberName, node.superNode.value, new IRPointerType(translator.transAllocaType(varRegistry.type)), current.block, new IRIntergerConstant(0), new IRIntergerConstant(classRegistry.getMemberIndex(varRegistry.name)));
                node.value = memLoad(varPtr, current.block);
            } else if (node.type instanceof FuncType)
                node.value = classRegistry.scope.queryFunc(node.memberName).value;
        }
    }

    @Override
    public void visit(NewExprNode node) {
        assert node.type instanceof VarType;
        if (((VarType) node.type).dimensize == 0) {
            if (node.type.match(BaseType.BuiltinType.CLASS)) {
                ClassRegistry classRegistry = commander.queryClass(((VarType) node.type).name);
                // malloc a place and bitcast the i8* to "this" pointer (node.value)
                node.value = new IRBitcastInst(new IRCallInst(module.getMallocFunc(), current.block, new IRIntergerConstant((classRegistry.value.type).size()), current.block).noalias(), new IRPointerType(classRegistry.value.type), current.block);
                // all the member function need a "this" pointer as the first argument
                // call construction function
                new IRCallInst((IRFunction) classRegistry.scope.queryFunc(classRegistry.name).value, current.block, node.value);
            }
        } else {
            ArrayList<IRValue> dimensionSizeList = new ArrayList<>();
            for (int i = 0; i < node.eachDimSizeNode.size(); ++i) {
                node.eachDimSizeNode.get(i).accept(this);
                dimensionSizeList.add(node.eachDimSizeNode.get(i).value);
            }
            if (dimensionSizeList.size() > 0)
                node.value = arrayMalloc(dimensionSizeList, 0, ((IRPointerType) translator.transAllocaType(node.type)).pointedType);
            else node.value = new IRNullPointerConstant();
        }
    }

    @Override
    public void visit(PostfixExprNode node) {
        node.selfNode.accept(this);
        IRValue result = null, storePtr = node.selfNode.value.storePtr;

        switch (node.opcode) {
            case INCRE:
                result = new IRBinaryInst("add", IRIntergerType.INT32, node.selfNode.value, new IRIntergerConstant(1), current.block);
                break;
            case DECRE:
                result = new IRBinaryInst("sub", IRIntergerType.INT32, node.selfNode.value, new IRIntergerConstant(1), current.block);
                break;
        }

        memStore(storePtr, result);
        node.value = node.selfNode.value;
    }

    @Override
    public void visit(PrefixExprNode node) {
        node.selfNode.accept(this);
        IRValue result = null, storePtr = node.selfNode.value.storePtr;

        switch (node.opcode) {
            case INCRE:
                result = new IRBinaryInst("add", IRIntergerType.INT32, node.selfNode.value, new IRIntergerConstant(1), current.block);
                break;
            case DECRE:
                result = new IRBinaryInst("sub", IRIntergerType.INT32, node.selfNode.value, new IRIntergerConstant(1), current.block);
                break;
        }

        memStore(storePtr, result);
        result.storePtr = storePtr;
        node.value = result;
    }

    @Override
    public void visit(UnaryExprNode node) {
        node.selfNode.accept(this);
        switch (node.opcode) {
            case ADD:
                node.value = node.selfNode.value;
                break;
            case SUB:
                node.value = new IRBinaryInst("sub", IRIntergerType.INT32, new IRIntergerConstant(0), node.selfNode.value, current.block);
                break;
            case LOGIC_NOT:
                node.value = new IRBinaryInst("xor", new IRBoolType(), node.selfNode.value, new IRBoolConstant(true), current.block);
                break;
            case BIT_NOT:
                node.value = new IRBinaryInst("xor", IRIntergerType.INT32, node.selfNode.value, new IRIntergerConstant(-1), current.block);
                break;
        }
    }

    @Override
    public void visit(LambdaExprNode node) {
        throw new Error(node.pos, "[Codegen Error] Lambda sentence exists in codegen testcase");
    }

    @Override
    public void visit(AtomExprNode node) {
        if (node.context.StringConstant() != null)
            node.value = new IRGEPInst(module.getStrConstant(node.getString()), IRPointerType.StringType, current.block, new IRIntergerConstant(0), new IRIntergerConstant(0));
        else if (node.context.IntegerConstant() != null)
            node.value = new IRIntergerConstant(Integer.parseInt(node.context.IntegerConstant().toString()));
        else if (node.context.FalseConstant() != null)
            node.value = new IRBoolConstant(false);
        else if (node.context.TrueConstant() != null)
            node.value = new IRBoolConstant(true);
        else if (node.context.This() != null)
            node.value = current.getThis();
        else if (node.context.NullConstant() != null)
            node.value = new IRNullPointerConstant();
        else if (node.context.Identifier() != null) {
            if (node.type instanceof VarType) {
                IRValue storePtr;
                Pair<VarRegistry, Boolean> ret = commander.queryVarforIR(node.context.Identifier().getText());
                VarRegistry varRegistry = ret.first();
                int memberIndex = -1;
                if (ret.second() && current.classRegistry != null)
                    memberIndex = current.classRegistry.getMemberIndex(varRegistry.name);
                if (memberIndex < 0)
                    storePtr = varRegistry.value;
                else
                    storePtr = new IRGEPInst(varRegistry.name, current.getThis(), new IRPointerType(translator.transAllocaType(varRegistry.type)), current.block, new IRIntergerConstant(0), new IRIntergerConstant(memberIndex));
                node.value = memLoad(storePtr, current.block);
            } else if (node.type instanceof FuncType)
                node.value = commander.queryFunc(node.context.Identifier().getText()).value;
        }
    }

    // especially for global variables' assignment as they don't need to load, just need storePtr
    private IRValue queryGlobalVarStorePtr(AtomExprNode node) {
        if (node.context.Identifier() != null && node.type instanceof VarType)
            return commander.queryVar(node.context.Identifier().getText()).value;
        else return null;
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

    public void classDeclare(RootNode node) {
        ArrayList<ClassRegistry> classRegList = new ArrayList<>();
        // add class
        for (BaseNode sonNode : node.sonNodes) {
            if (sonNode instanceof ClassDefNode) {
                IRStructType defClass = new IRStructType(((ClassDefNode) sonNode).classRegistry.name);
                ((ClassDefNode) sonNode).classRegistry.value = defClass.struct;
                module.classList.add(defClass.struct);
                classRegList.add(((ClassDefNode) sonNode).classRegistry);
            }
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

    private void funcDeclare(FuncDefNode node) {
        IRFunction declareFunc = new IRFunction(node.funcRegistry.name, translator.transFuncType(node.funcRegistry.type, null), module);
        module.funcList.add(declareFunc);
        node.funcRegistry.value = declareFunc;
    }

    private IRValue memLoad(IRValue pointer, IRBlock parentBlock) {
        assert pointer.type instanceof IRPointerType;
        IRValue ret = new IRLoadInst(pointer, parentBlock);
        if (((IRPointerType) pointer.type).pointedType instanceof IRMemBoolType)
            ret = new IRTruncInst(ret, new IRBoolType(), parentBlock);
        ret.storePtr = pointer;
        return ret;
    }

    private void memStore(IRValue destPtr, IRValue storeData) {
        if (storeData.type instanceof IRBoolType)
            storeData = new IRZextInst(storeData, new IRMemBoolType(), current.block);
        assert destPtr.type instanceof IRPointerType;
        assert ((IRPointerType) destPtr.type).pointedType.match(storeData.type);
        new IRStoreInst(destPtr, storeData, current.block);
    }

    private IRValue memAlloca(String allocaName, IRBaseType pointedType) {
        return new IRAllocaInst(allocaName, pointedType, current.function.entryBlock);
    }

    private IRValue arrayMalloc(ArrayList<IRValue> dimensionSizeList, int currentDim, IRBaseType elementType) {
        IRValue dataSize = new IRBinaryInst("mul", IRIntergerType.INT32, dimensionSizeList.get(currentDim), new IRIntergerConstant(elementType.size()), current.block);
        IRValue mallocSize = new IRBinaryInst("add", IRIntergerType.INT32, dataSize, new IRIntergerConstant(4), current.block);
        IRValue mallocPtr = new IRCallInst(module.getMallocFunc(), current.block, mallocSize).noalias();

        IRValue sizePtr = new IRBitcastInst(mallocPtr, new IRPointerType(IRIntergerType.INT32), current.block);
        memStore(sizePtr, dimensionSizeList.get(currentDim));

        IRValue arrayHeadPtr = new IRBitcastInst(new IRGEPInst(sizePtr, new IRPointerType(IRIntergerType.INT32), current.block, new IRIntergerConstant(1)), new IRPointerType(elementType), current.block);
        if (currentDim < dimensionSizeList.size() - 1) {
            // simulate while statement in llvm
            IRBlock conditionBlock = new IRBlock("new.cond", current.function);
            IRBlock bodyBlock = new IRBlock("new.body", current.function);
            IRBlock exitBlock = new IRBlock("new.exit", current.function);

            IRPhiInst currentDimPtr = new IRPhiInst(arrayHeadPtr.type, null);
            currentDimPtr.addOperand(arrayHeadPtr);
            currentDimPtr.addOperand(current.block);

            IRGEPInst endDimPtr = new IRGEPInst(arrayHeadPtr, arrayHeadPtr.type, current.block, dimensionSizeList.get(currentDim));
            IRGEPInst incrPtr = new IRGEPInst(currentDimPtr, currentDimPtr.type, null, new IRIntergerConstant(1));

            new IRBrInst(conditionBlock, current.block);

            current.block = conditionBlock;
            currentDimPtr.setParentBlock(conditionBlock);
            IRValue condition = new IRIcmpInst("ne", currentDimPtr, endDimPtr, current.block);
            new IRBrInst(condition, bodyBlock, exitBlock, current.block);

            current.block = bodyBlock;
            IRValue nextDimPtr = arrayMalloc(dimensionSizeList, currentDim + 1, ((IRPointerType) elementType).pointedType);
            memStore(currentDimPtr, nextDimPtr);
            incrPtr.setParentBlock(current.block);
            currentDimPtr.addOperand(incrPtr);
            currentDimPtr.addOperand(current.block);
            new IRBrInst(conditionBlock, current.block);

            current.block = exitBlock;
        }
        return arrayHeadPtr;
    }

}

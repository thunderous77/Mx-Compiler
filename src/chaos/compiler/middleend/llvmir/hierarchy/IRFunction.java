package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

import java.util.HashSet;
import java.util.LinkedList;

public class IRFunction extends IRGlobalValue {

    public IRModule parentModule;
    public IRBlock entryBlock, exitBlock;
    public IRValue retValuePtr;
    public LinkedList<IRBlock> blockList = new LinkedList<>();

    public HashSet<Loop> topLevelLoops = new HashSet<>();

    public IRFunction(String name, IRBaseType functionType, IRModule parentModule) {
        super(name, functionType);
        entryBlock = new IRBlock("entry", this);
        exitBlock = new IRBlock("exit", this);
        this.parentModule = parentModule;
    }

    public IRFunction(String name, IRBaseType retType, IRBaseType... argsType) {
        super(name, new IRFunctionType(retType, null));
        for (IRBaseType argType : argsType) ((IRFunctionType) this.type).argTypes.add(argType);
    }

    public Boolean isVoid() {
        return ((IRFunctionType) this.type).match(IRVoidType.VOID);
    }

    public IRValue getArg(int index) {
        return this.getOperand(index);
    }

    public int getArgNum() {
        return ((IRFunctionType) this.type).argTypes.size();
    }

    public IRBaseType getArgType(int index) {
        return ((IRFunctionType) this.type).argTypes.get(index);
    }

    public int operandSize() {
        return this.getOperandSize();
    }

}

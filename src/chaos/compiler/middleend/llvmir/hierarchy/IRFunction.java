package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.constant.GlobalValue;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRFunction extends GlobalValue {

    public IRModule parentModule;
    public IRBlock entryBlock, exitBlock;
    public Value retValue;
    public LinkedList<IRBlock> blockList = new LinkedList<>();

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

    public Value getArg(int index) {
        return this.getOperand(index);
    }

    public int operandSize() {
        return this.getOperandSize();
    }

}

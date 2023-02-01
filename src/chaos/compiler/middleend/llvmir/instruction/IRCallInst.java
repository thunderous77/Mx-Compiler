package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

import java.util.ArrayList;

public class IRCallInst extends IRBaseInst {

    boolean noaliasFlag = false;

    public IRCallInst(IRFunction callFunc, IRBlock parentBlock, ArrayList<IRValue> callArgs) {
        super(callFunc.name + ".call", ((IRFunctionType) callFunc.type).retType, parentBlock);
        this.addOperand(callFunc);
        for (int i = 0; i < callArgs.size(); ++i)
            this.addOperand(callArgs.get(i));
    }

    public IRCallInst(IRFunction callFunc, IRBlock parentBlock, IRValue... callArgs) {
        super(callFunc.name + ".call", ((IRFunctionType) callFunc.type).retType, parentBlock);
        this.addOperand(callFunc);
        for (IRValue arg : callArgs) this.addOperand(arg);
    }

    public IRFunction callFunc() {
        return (IRFunction) this.getOperand(0);
    }

    public IRValue getArg(int index) {
        return this.getOperand(index + 1);
    }

    @Override
    public boolean isValueSelf() {
        return !callFunc().isVoid();
    }

    public IRCallInst noalias() {
        noaliasFlag = true;
        return this;
    }

    @Override
    public String print() {
        // %call = call i32 @foo(i32 1)
        StringBuilder ret = new StringBuilder((this.type.match(new IRVoidType())) ? "" : this.identifier() + " = ");
        ret.append("call ");
        if (noaliasFlag) ret.append("noalias ");
        ret.append(callFunc().typedIdentifier()).append("(");
        for (int i = 0; i < this.callFunc().getArgNum(); i++) {
            ret.append(this.callFunc().getArgType(i)).append(" ").append(this.getArg(i).identifier());
            if (i != this.callFunc().getArgNum() - 1) ret.append(", ");
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public IRBaseInst copy() {
        ArrayList<IRValue> args = new ArrayList<>();
        for (int i = 0; i < this.callFunc().getArgNum(); ++i)
            args.add(getArg(i));
        return new IRCallInst(callFunc(), null, args);
    }

    @Override
    public boolean mayHaveSideEffects() {return true;}

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}

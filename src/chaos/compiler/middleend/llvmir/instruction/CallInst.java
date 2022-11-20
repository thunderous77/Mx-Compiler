package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

import java.util.ArrayList;

public class CallInst extends BaseInst {

    public CallInst(IRFunction callFunc, IRBlock parentBlock, ArrayList<Value> callArgs) {
        super(callFunc.name + ".call", ((IRFunctionType) callFunc.type).retType, parentBlock);
        this.addOperand(callFunc);
        for (int i = 0; i < callArgs.size(); ++i)
            this.addOperand(callArgs.get(i));
    }

    public CallInst(IRFunction callFunc, IRBlock parentBlock, Value... callArgs) {
        super(callFunc.name + ".call", ((IRFunctionType) callFunc.type).retType, parentBlock);
        this.addOperand(callFunc);
        for (Value arg : callArgs) this.addOperand(arg);
    }

    public IRFunction callFunc() {
        return (IRFunction) this.getOperand(0);
    }

    public Value getArg(int index) {
        return this.getOperand(index + 1);
    }

    @Override
    public boolean isValueSelf() {
        return !callFunc().isVoid();
    }

    @Override
    public String format() {
        // %call = call i32 @foo(i32 1)
        StringBuilder ret = new StringBuilder((this.type.match(new IRVoidType())) ? "" : this.identifier() + " = ");
        ret.append("call ");
        ret.append(callFunc().typedIdentifier()).append("(");
        for (int i = 0; i < this.callFunc().getArgNum(); i++) {
            ret.append(this.callFunc().getArgType(i)).append(" ").append(this.getArg(i).identifier());
            if (i != this.callFunc().getArgNum() - 1) ret.append(", ");
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public BaseInst copy() {
        ArrayList<Value> args = new ArrayList<>();
        for (int i = 0; i < this.callFunc().getArgNum(); ++i)
            args.add(getArg(i));
        return new CallInst(callFunc(), null, args);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}

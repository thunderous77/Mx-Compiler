package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.Variable;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;

public class CallInst extends BaseInst {

    public CallInst(Value func) {
        super(((IRFunctionType) func.type).retType);
        this.addUse(func);
    }

    public CallInst(Value func, Value... args) {
        this(func);
        for (Value v : args) {
            this.addUse(v);
        }
    }

}

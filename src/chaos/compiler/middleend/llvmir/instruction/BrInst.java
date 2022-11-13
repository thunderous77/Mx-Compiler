package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

public class BrInst extends BaseInst {

    public BrInst(Value dest) {
        super(new IRVoidType());
        addUse(dest);
    }

    public BrInst(Value condition, Value ifTure, Value ifFalse) {
        super(new IRVoidType());
        addUse(condition);
        addUse(ifTure);
        addUse(ifFalse);
    }

}

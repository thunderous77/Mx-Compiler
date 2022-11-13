package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class BitcastInst extends BaseInst{

    public BitcastInst(IRBaseType resultType, Value val) {
        super(resultType);
        addUse(val);
    }

}

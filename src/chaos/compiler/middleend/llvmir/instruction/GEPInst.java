package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class GEPInst extends BaseInst{

    public GEPInst(IRBaseType retType, Value pointer, Value... index) {
        super(retType);
        addUse(pointer);
        for (Value idx: index) {
            addUse(idx);
        }
    }

}

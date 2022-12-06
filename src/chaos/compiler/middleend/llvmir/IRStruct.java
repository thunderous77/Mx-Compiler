package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRStructType;

public class IRStruct extends IRValue {

    public IRStruct(String name, IRBaseType type) {
        super(name, type);
    }

    public IRStructType type() {
        return (IRStructType) this.type;
    }

}

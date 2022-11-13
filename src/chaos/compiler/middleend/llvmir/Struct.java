package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRStructType;

public class Struct extends Value {

    public Struct(String name, IRBaseType type) {
        super(name, type);
    }

    public IRStructType struct() {
        return (IRStructType) this.type;
    }

}

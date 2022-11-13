package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.IRVisitor;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class NullPointerConstant extends BaseConstant {

    public NullPointerConstant(IRBaseType type) {
        super("const", IRPointerType.NULL);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NullPointerConstant;
    }

    @Override
    public String toString() {
        return "null";
    }

}

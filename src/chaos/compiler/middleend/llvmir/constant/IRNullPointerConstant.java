package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRNullPointerConstant extends IRBaseConstant {

    public IRNullPointerConstant() {
        super("const", IRPointerType.NULL);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IRNullPointerConstant;
    }

    @Override
    public String toString() {
        return "null";
    }

}

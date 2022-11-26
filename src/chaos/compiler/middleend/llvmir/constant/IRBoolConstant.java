package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.type.IRBoolType;

public class IRBoolConstant extends IRBaseConstant {

    public boolean constData;

    public IRBoolConstant(boolean constData) {
        super("const", new IRBoolType());
        this.constData = constData;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IRBoolConstant && constData == ((IRBoolConstant) o).constData;
    }

    @Override
    public String identifier() {
        return constData ? "true" : "false";
    }

}

package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.type.IRBoolType;
import chaos.compiler.middleend.llvmir.type.IRIntergerType;

public class BoolConstant extends BaseConstant {

    public boolean constData;

    public BoolConstant(boolean constData) {
        super("const", new IRBoolType());
        this.constData = constData;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoolConstant && constData == ((BoolConstant) o).constData;
    }

    @Override
    public String identifier() {
        return constData ? "true" : "false";
    }

}

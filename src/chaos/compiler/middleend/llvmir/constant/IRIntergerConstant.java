package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.type.IRIntergerType;

public class IRIntergerConstant extends IRBaseConstant {

    public int constData;

    public IRIntergerConstant(int data, int width) {
        super("const", new IRIntergerType(width));
        this.constData = data;
    }

    public IRIntergerConstant(int data) {
        this(data, 32);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IRIntergerConstant && constData == ((IRIntergerConstant) o).constData;
    }

    @Override
    public String identifier() {
        return String.valueOf(constData);
    }

}

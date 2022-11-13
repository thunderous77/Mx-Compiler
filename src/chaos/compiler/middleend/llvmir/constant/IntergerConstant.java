package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.IRVisitor;
import chaos.compiler.middleend.llvmir.type.IRIntergerType;

public class IntergerConstant extends BaseConstant {

    public int constData;

    public IntergerConstant(int data, int width) {
        super("const", new IRIntergerType(width));
        this.constData = data;
    }

    public IntergerConstant(int data) {
        this(data, 32);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntergerConstant && constData == ((IntergerConstant) o).constData;
    }

    @Override
    public String identifier() {
        return String.valueOf(constData);
    }

}

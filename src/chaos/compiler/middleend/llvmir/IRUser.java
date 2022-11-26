package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.type.IRBaseType;

import java.util.LinkedList;

abstract public class IRUser extends IRValue {

    public final LinkedList<IRUse> operandList = new LinkedList<>();

    public IRUser(String name, IRBaseType type) {
        super(name, type);
    }

    public void addOperand(IRValue value) {
        IRUse.getUseLink(this, value);
    }

    public IRValue getOperand(int index) {
        return operandList.get(index).value;
    }

    public void resetOperand(int index, IRValue value) {
        operandList.get(index).removeUse();
        IRUse.getUseLink(this, value);
    }

    public int getOperandSize() {
        return operandList.size();
    }

}

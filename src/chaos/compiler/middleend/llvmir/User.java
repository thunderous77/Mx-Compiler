package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.type.IRBaseType;

import java.util.LinkedList;

abstract public class User extends Value {

    public final LinkedList<Use> operandList = new LinkedList<>();

    public User(String name,IRBaseType type) {
        super(name,type);
    }

    public void addOperand(Value value) {
        Use.getUseLink(this, value);
    }

    public Value getOperand(int index) {
        return operandList.get(index).value;
    }

    public int getOperandSize() {
        return operandList.size();
    }

}

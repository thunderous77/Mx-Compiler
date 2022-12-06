package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.utility.Error;

public class IRPhiInst extends IRBaseInst {

    public IRPhiInst(IRBaseType yieldType, IRBlock parentBlock, IRValue... operands) {
        super("phi", yieldType, parentBlock);
        for (IRValue operand : operands) this.addOperand(operand);
        if (operands.length % 2 != 0) throw new Error("[IR Error] Invalid Phi instruction operands number");
    }

    @Override
    public String print() {
        // %indvar = phi i32 [ 0, %LoopHeader ], [ %nextindvar, %Loop ]
        String ret = this.identifier() + " = phi " + this.type + " ";
        for (int i = 0; i < this.getOperandSize(); i += 2) {
            ret += "[" + this.getOperand(i).identifier() + ", " + this.getOperand(i + 1).identifier() + "]";
            if (i < this.getOperandSize() - 2) ret += ", ";
        }
        return ret;
    }

    @Override
    public IRBaseInst copy() {
        IRPhiInst ret = new IRPhiInst(type, null);
        for (int i = 0; i < this.getOperandSize(); ++i)
            ret.addOperand(this.getOperand(i));
        return ret;
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}

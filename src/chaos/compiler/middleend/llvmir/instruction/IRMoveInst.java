package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

public class IRMoveInst extends IRBaseInst {

    public IRMoveInst(IRValue dest, IRValue source, IRBlock parentBlock) {
        super("move",new IRVoidType(),parentBlock);
        this.addOperand(dest);
        this.addOperand(source);
        dest.moveInstSet.add(this);
    }

    public IRValue dest() {
        return this.getOperand(0);
    }

    public IRValue source() {
        return this.getOperand(1);
    }

    @Override
    public String print() {
        return "move " + this.dest().identifier() + ", " + this.source().identifier();}

    @Override
    public IRBaseInst copy() {
        return new IRMoveInst(dest(),source(),null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}

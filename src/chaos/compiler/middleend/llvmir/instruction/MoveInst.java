package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

public class MoveInst extends BaseInst{

    public MoveInst(Value dest, Value source, IRBlock parentBlock) {
        super("move",new IRVoidType(),parentBlock);
        this.addOperand(dest);
        this.addOperand(source);
        dest.moveDefs.add(this);
    }

    public Value dest() {
        return this.getOperand(0);
    }

    public Value source() {
        return this.getOperand(1);
    }

    @Override
    public String format() {
        return "move " + this.dest().identifier() + ", " + this.source().identifier();}

    @Override
    public BaseInst copy() {
        return new MoveInst(dest(),source(),null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}

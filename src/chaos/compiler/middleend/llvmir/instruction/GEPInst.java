package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class GEPInst extends BaseInst {

    private String elementName = null;

    // index number 1 or 2 (different from llvm to simplify)
    public GEPInst(String elementName, Value headPointer, IRBaseType yieldType, IRBlock parentBlock, Value... index) {
        super(addrRename(elementName), yieldType, parentBlock);
        assert headPointer.type instanceof IRPointerType;
        this.addOperand(headPointer);
        for (Value idx : index) this.addOperand(idx);
        this.elementName = elementName;
    }

    public GEPInst(Value headPointer, IRBaseType yieldType, IRBlock parentBlock, Value... index) {
        super("getelementptr", yieldType, parentBlock);
        assert headPointer.type instanceof IRPointerType;
        this.addOperand(headPointer);
        for (Value idx : index) this.addOperand(idx);
    }

    public int indexNum() {
        return this.getOperandSize() - 1;
    }

    public Value getIndex(int idx) {
        return this.getOperand(idx + 1);
    }

    public Value headPointer() {
        return this.getOperand(0);
    }

    public Value ptrMoveIndex() {
        return this.getIndex(0);
    }

    public Value memberIndex() {
        return this.getIndex(1);
    }

    @Override
    public String format() {
        // %t4 = getelementptr inbounds [10 x [20 x i32]], [10 x [20 x i32]]* %t3, i32 0, i32 5
        StringBuilder ret = new StringBuilder(this.identifier() + " = getelementptr inbounds " + ((IRPointerType) this.headPointer().type).pointedType + ", " + this.headPointer().typedIdentifier());
        for (int i = 0; i < this.indexNum(); ++i)
            ret.append(", ").append(this.getIndex(i).typedIdentifier());
        return ret.toString();
    }

    @Override
    public BaseInst copy() {
        if (elementName == null) {
            if (indexNum() == 1)
                return new GEPInst(headPointer(), type, null, getIndex(0));
            else {
                assert indexNum() == 2;
                return new GEPInst(headPointer(), type, null, getIndex(0), getIndex(1));
            }
        } else {
            if (indexNum() == 1)
                return new GEPInst(elementName, headPointer(), type, null, getIndex(0));
            else {
                assert indexNum() == 2;
                return new GEPInst(elementName, headPointer(), type, null, getIndex(0), getIndex(1));
            }
        }
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}

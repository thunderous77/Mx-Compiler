package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRGEPInst extends IRBaseInst {

    private String elementName = null;

    // index number 1 or 2 (different from llvm to simplify)
    public IRGEPInst(String elementName, IRValue headPointer, IRBaseType yieldType, IRBlock parentBlock, IRValue... index) {
        super(addrRename(elementName), yieldType, parentBlock);
        assert headPointer.type instanceof IRPointerType;
        this.addOperand(headPointer);
        for (IRValue idx : index) this.addOperand(idx);
        this.elementName = elementName;
    }

    public IRGEPInst(IRValue headPointer, IRBaseType yieldType, IRBlock parentBlock, IRValue... index) {
        super("getelementptr", yieldType, parentBlock);
        assert headPointer.type instanceof IRPointerType;
        this.addOperand(headPointer);
        for (IRValue idx : index) this.addOperand(idx);
    }

    public int indexNum() {
        return this.getOperandSize() - 1;
    }

    public IRValue getIndex(int idx) {
        return this.getOperand(idx + 1);
    }

    public IRValue headPointer() {
        return this.getOperand(0);
    }

    public IRValue ptrMoveIndex() {
        return this.getIndex(0);
    }

    public IRValue memberIndex() {
        return this.getIndex(1);
    }

    @Override
    public String print() {
        // %t4 = getelementptr inbounds [10 x [20 x i32]], [10 x [20 x i32]]* %t3, i32 0, i32 5
        StringBuilder ret = new StringBuilder(this.identifier() + " = getelementptr inbounds " + ((IRPointerType) this.headPointer().type).pointedType + ", " + this.headPointer().typedIdentifier());
        for (int i = 0; i < this.indexNum(); ++i)
            ret.append(", ").append(this.getIndex(i).typedIdentifier());
        return ret.toString();
    }

    @Override
    public IRBaseInst copy() {
        if (elementName == null) {
            if (indexNum() == 1)
                return new IRGEPInst(headPointer(), type, null, getIndex(0));
            else {
                assert indexNum() == 2;
                return new IRGEPInst(headPointer(), type, null, getIndex(0), getIndex(1));
            }
        } else {
            if (indexNum() == 1)
                return new IRGEPInst(elementName, headPointer(), type, null, getIndex(0));
            else {
                assert indexNum() == 2;
                return new IRGEPInst(elementName, headPointer(), type, null, getIndex(0), getIndex(1));
            }
        }
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}

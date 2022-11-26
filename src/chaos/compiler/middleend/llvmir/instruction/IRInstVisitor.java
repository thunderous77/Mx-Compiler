package chaos.compiler.middleend.llvmir.instruction;


public interface IRInstVisitor {

    void visit(IRAllocaInst inst);

    void visit(IRBinaryInst inst);

    void visit(IRBitcastInst inst);

    void visit(IRBrInst inst);

    void visit(IRCallInst inst);

    void visit(IRGEPInst inst);

    void visit(IRIcmpInst inst);

    void visit(IRLoadInst inst);

    void visit(IRPhiInst inst);

    void visit(IRRetInst inst);

    void visit(IRStoreInst inst);

    void visit(IRTruncInst inst);

    void visit(IRZextInst inst);

    void visit(IRMoveInst inst);

}

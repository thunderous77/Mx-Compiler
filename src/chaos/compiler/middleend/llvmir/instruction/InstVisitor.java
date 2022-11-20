package chaos.compiler.middleend.llvmir.instruction;


public interface InstVisitor {

    void visit(AllocaInst inst);

    void visit(BinaryInst inst);

    void visit(BitcastInst inst);

    void visit(BrInst inst);

    void visit(CallInst inst);

    void visit(GEPInst inst);

    void visit(IcmpInst inst);

    void visit(LoadInst inst);

    void visit(PhiInst inst);

    void visit(RetInst inst);

    void visit(StoreInst inst);

    void visit(TruncInst inst);

    void visit(ZextInst inst);

    void visit(MoveInst inst);

}

package chaos.compiler.backend.asm.inst;

public interface AsmInstVisitor {

    void visit(AsmALUInst inst);

    void visit(AsmBrInst inst);

    void visit(AsmCallInst inst);

    void visit(AsmJmpInst inst);

    void visit(AsmLaInst inst);

    void visit(AsmLiInst inst);

    void visit(AsmLoadInst inst);

    void visit(AsmLuiInst inst);

    void visit(AsmMvInst inst);

    void visit(AsmRetInst inst);

    void visit(AsmStoreInst inst);

}

package chaos.compiler.backend.asm;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;
import chaos.compiler.backend.asm.inst.*;
import chaos.compiler.backend.asm.operand.*;

import java.util.ArrayList;
import java.util.HashMap;

public class RegAllocator implements AsmInstVisitor {

    public AsmModule module;
    private int offset;
    private AsmBlock currentBlock;
    private AsmFunction currentFunction;
    private HashMap<AsmRegister, Integer> stackAllocMap = new HashMap<>();

    public void execute(AsmModule module) {
        this.module = module;
        module.functionList.forEach(this::executeFunction);
    }

    public void executeFunction(AsmFunction function) {
        currentFunction = function;
        offset = currentFunction.spOffset;
        function.blockList.forEach(this::executeBlock);
        currentFunction.offsetImmList.forEach(imm -> imm.value += offset);
        function.entryBlock.addInstAtHead(new AsmALUInst("add", module.getReg("sp"),module.getReg("sp"), new AsmImmediate(-offset), null));
        function.exitBlock.addInstAtHead(new AsmALUInst("add", module.getReg("sp"),module.getReg("sp"), new AsmImmediate(offset), null));
    }

    public void executeBlock(AsmBlock block) {
        ArrayList<AsmBaseInst> rawInstList = block.instList;
        block.instList = new ArrayList<>();
        currentBlock = block;
        rawInstList.forEach(inst ->{inst.accept(this);});
        currentBlock = null;
    }

    private AsmImmediate allocOnStack(AsmVirtualReg reg) {
        Integer alloc = stackAllocMap.get(reg);
        if (alloc == null) {
            alloc = (offset += 4);
            stackAllocMap.put(reg, alloc);
        }
        AsmImmediate imm = new AsmImmediate(-alloc);
        currentFunction.offsetImmList.add(imm);
        return imm;
    }

    private AsmBaseOperand load(AsmBaseOperand operand, String regName) {
        if (!(operand instanceof AsmVirtualReg reg)) return operand;
        AsmPhysicalReg physicalReg;
        if (reg.color != 8) {
            physicalReg = module.getReg(regName);
            new AsmLoadInst(4,physicalReg, module.getReg("sp"), allocOnStack(reg), currentBlock);
        } else {
            physicalReg = module.getReg("sp");
            physicalReg.setOffset(allocOnStack((reg)));
        }
        return physicalReg;
    }

    private AsmPhysicalReg store(AsmRegister operand, String regName) {
        if (!(operand instanceof AsmVirtualReg reg)) return (AsmPhysicalReg) operand;
        AsmPhysicalReg physicalReg;
        if (reg.color != 8) {
            physicalReg = module.getReg(regName);
            new AsmStoreInst(4,physicalReg, module.getReg("sp"), allocOnStack(reg), currentBlock);
        } else {
            physicalReg = module.getReg("sp");
            physicalReg.setOffset(allocOnStack((reg)));
        }
        return physicalReg;
    }

    @Override
    public void visit(AsmALUInst inst) {
        inst.rs1 = (AsmRegister) load(inst.rs1, "s0");
        inst.rs2 = (AsmRegister) load(inst.rs2, "s1");
        inst.rd = store(inst.rd, "s0");
    }

    @Override
    public void visit(AsmBrInst inst) {
        inst.rs1 = (AsmRegister) load(inst.rs1, "s0");
        inst.rs2 = (AsmRegister) load(inst.rs2, "s1");
    }

    @Override
    public void visit(AsmCallInst inst) {}

    @Override
    public void visit(AsmJmpInst inst) {}

    @Override
    public void visit(AsmLaInst inst) {
        inst.rd = (AsmRegister) load(inst.rd, "s0");
    }

    @Override
    public void visit(AsmLiInst inst) {
        inst.rd = (AsmRegister) load(inst.rd, "s0");
    }

    public void visit(AsmLoadInst inst) {
        inst.rs1 = (AsmRegister) load(inst.rs1, "s0");
        inst.rd = (AsmRegister) load(inst.rd, "s1");
    }

    @Override
    public void visit(AsmLuiInst inst) {
        inst.rd = (AsmRegister) load(inst.rd, "s0");
    }

    @Override
    public void visit(AsmMvInst inst) {
        inst.rs1 = (AsmRegister) load(inst.rs1, "s0");
        inst.rd = (AsmRegister) load(inst.rd, "s1");
    }

    @Override
    public void visit(AsmRetInst inst) {}

    @Override
    public void visit(AsmStoreInst inst) {
        inst.rs1 = (AsmRegister) load(inst.rs1, "s0");
        inst.rs2 = (AsmRegister) load(inst.rs2, "s1");
    }

}

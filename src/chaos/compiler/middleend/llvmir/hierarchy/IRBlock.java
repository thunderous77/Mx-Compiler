package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.analyzer.DomTreeBuilder;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.instruction.IRBrInst;
import chaos.compiler.middleend.llvmir.instruction.IRPhiInst;
import chaos.compiler.middleend.llvmir.instruction.IRRetInst;
import chaos.compiler.middleend.llvmir.type.IRLabelType;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRBlock extends IRValue {

    public LinkedList<IRBaseInst> instList = new LinkedList<>();
    public ArrayList<IRPhiInst> phiInstList = new ArrayList<>();
    public ArrayList<IRBlock> preBlockList = new ArrayList<>(), nextBlockList = new ArrayList<>();
    public IRFunction parentFunction;

    public int loopDepth = 0;
    public Loop belongLoop = null;
    public DomTreeBuilder.Node dtNode = new DomTreeBuilder.Node(this);

    public IRBlock(String name, IRFunction parentFunction) {
        super(name, new IRLabelType());
        if (parentFunction != null) parentFunction.blockList.add(this);
    }

    public void addInst(IRBaseInst inst) {
        if (inst instanceof IRPhiInst)
            phiInstList.add((IRPhiInst) inst);
        // avoid add inst after IRRetInst or IRBrInst
        else if (!isTerminated())
            instList.add(inst);
    }

    public void addPhiInst(IRPhiInst inst) {
        inst.parentBlock = this;
        phiInstList.add(inst);
    }

    public void addInstAtFirst(IRBaseInst inst) {
        inst.parentBlock = this;
        instList.addFirst(inst);
    }

    public void addInstAtLast(IRBaseInst inst) {
        inst.parentBlock = this;
        instList.addLast(inst);
    }

    public void tAddBeforeTerminator(IRBaseInst inst) {
        inst.parentBlock = this;
        if (instList.isEmpty()) return;
        instList.add(instList.size() - 1, inst);
    }

    public void linkBlock(IRBlock nextBlock) {
        this.nextBlockList.add(nextBlock);
        nextBlock.preBlockList.add(this);
    }

    public boolean isTerminated() {
        return instList.size() != 0 && ((instList.get(instList.size() - 1) instanceof IRRetInst) || (instList.get(instList.size() - 1) instanceof IRBrInst));
    }

    public IRBaseInst terminator() {
        return instList.getLast();
    }

    public void redirectPreBlock(IRBlock oldPre, IRBlock newPre) {
        this.preBlockList.remove(oldPre);
        this.preBlockList.add(newPre);
        for (IRPhiInst phi : this.phiInstList) {
            for (int i = 1; i < phi.getOperandSize(); i += 2) {
                if (phi.getOperand(i) == oldPre)
                    phi.resetOperand(i, newPre);
            }
        }
    }

    public void redirectSucBlock(IRBlock oldSuc, IRBlock newSuc) {
        this.nextBlockList.remove(oldSuc);
        this.nextBlockList.add(newSuc);
        for (IRBaseInst inst : this.instList) {
            if (inst instanceof IRBrInst) {
                if (((IRBrInst) inst).isJump()) {
                    if (((IRBrInst) inst).destBlock() == oldSuc) ((IRBrInst) inst).resetDestBlock(newSuc);
                } else {
                    if (((IRBrInst) inst).ifTrueBlock() == oldSuc) ((IRBrInst) inst).resetIfTrueBlock(newSuc);
                    if (((IRBrInst) inst).ifFalseBlock() == oldSuc) ((IRBrInst) inst).resetIfFalseBlock(newSuc);
                }
            }
        }
    }

}

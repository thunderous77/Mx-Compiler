package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.instruction.IRPhiInst;
import chaos.compiler.middleend.llvmir.type.IRLabelType;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRBlock extends IRValue {

    public LinkedList<IRBaseInst> instList = new LinkedList<>();
    public ArrayList<IRPhiInst> phiInstList = new ArrayList<>();
    public Boolean isTerminated = false;
    public ArrayList<IRBlock> preBlockList = new ArrayList<>(), nextBlockList = new ArrayList<>();

    public IRBlock(String name, IRFunction parentFunction) {
        super(name, new IRLabelType());
        if (parentFunction != null) parentFunction.blockList.add(this);
    }

    public void addInst(IRBaseInst inst) {
        if (isTerminated) return;
        if (inst instanceof IRPhiInst)
            phiInstList.add((IRPhiInst) inst);
        else instList.add(inst);
        if (inst.isTerminator()) isTerminated = true;
    }

    public void addInstAtFirst(IRBaseInst inst) {
        inst.parentBlock = this;
        instList.addFirst(inst);
    }

    public void addInstAtLast(IRBaseInst inst) {
        inst.parentBlock = this;
        instList.addLast(inst);
    }

    public void addLink(IRBlock nextBlock) {
        this.nextBlockList.add(nextBlock);
        nextBlock.preBlockList.add(this);
    }


}

package chaos.compiler.middleend.llvmir.hierarchy;

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

    public boolean isTerminated() {
        return instList.size() != 0 && ((instList.get(instList.size() - 1) instanceof IRRetInst) || (instList.get(instList.size() - 1) instanceof IRBrInst));
    }


}

package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.instruction.BaseInst;
import chaos.compiler.middleend.llvmir.instruction.PhiInst;
import chaos.compiler.middleend.llvmir.type.IRLabelType;

import java.util.ArrayList;
import java.util.LinkedList;

public class IRBlock extends Value {

    public LinkedList<BaseInst> instList = new LinkedList<>();
    public ArrayList<PhiInst> phiInstList = new ArrayList<>();
    public IRFunction parentFunction;
    public Boolean isTerminated = false;
    public ArrayList<IRBlock> preBlockList = new ArrayList<>(), nextBlockList = new ArrayList<>();

    public IRBlock(String name, IRFunction parentFunction) {
        super(name, new IRLabelType());
        this.parentFunction = parentFunction;
        if (parentFunction != null) parentFunction.blockList.add(this);
    }

    public void addInst(BaseInst inst) {
        if (isTerminated) return;
        if (inst instanceof PhiInst)
            phiInstList.add((PhiInst) inst);
        else instList.add(inst);
        if (inst.isTerminator()) isTerminated = true;
    }


    public void addInstAtHead(BaseInst inst) {
        instList.addFirst(inst);
    }

    public void addLink(IRBlock nextBlock) {
        this.nextBlockList.add(nextBlock);
        nextBlock.preBlockList.add(this);
    }


}

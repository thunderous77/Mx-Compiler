package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.analyzer.AliasAnalyzer;
import chaos.compiler.middleend.llvmir.IRUse;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRBaseConstant;
import chaos.compiler.middleend.llvmir.instruction.*;

import java.util.HashSet;

public class Loop {
    public IRBlock headerBlock;
    public HashSet<IRBlock> tailers = new HashSet<>();
    public HashSet<IRBlock> blocks = new HashSet<>();

    public Loop fatherLoop;
    public HashSet<Loop> sonLoopSet = new HashSet<>();

    public Loop(IRBlock headerBlock) {
        this.headerBlock = headerBlock;
    }

    public void addBlock(IRBlock block) {
        block.belongLoop = this;
        this.blocks.add(block);
    }

    public void addNestedLoop(Loop sonLoop) {
        this.sonLoopSet.add(sonLoop);
        sonLoop.fatherLoop = this;
    }

    // check whether value is invariant in this loop
    public boolean isInvariant(IRValue value) {
        if (value instanceof IRBaseConstant) return true;

        if (value instanceof IRBaseInst) {
            return !blocks.contains(((IRBaseInst) value).parentBlock);
        }

        for (IRMoveInst move : value.moveInstSet) {
            if (blocks.contains(move.parentBlock)) return false;
        }

        return true;
    }

    public boolean notModified(IRLoadInst inst, AliasAnalyzer analyzer) {
        for (IRBlock block : this.blocks)
            for (IRBaseInst inst1 : block.instList) {
                if (inst1 instanceof IRStoreInst && analyzer.mayAlias(inst.loadPtr(), ((IRStoreInst) inst1).storePtr()))
                    return false;
                if (inst1 instanceof IRCallInst) return false;
            }
        return true;
    }

    public boolean isInstInvariant(IRBaseInst inst, AliasAnalyzer analyzer) {
         if ((inst.mayHaveSideEffects() && !(inst instanceof IRLoadInst)) || !inst.isValueSelf()) return false;

        for (IRUse use : inst.operandList) {
            if (!this.isInvariant(use.value)) return false;
        }

        if (inst instanceof IRLoadInst) {
            return notModified((IRLoadInst) inst, analyzer);
        }

        return true;
    }
}

package chaos.compiler.middleend.analyzer;

import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.instruction.IRBrInst;

public class CFGBuilder {

    private void init(IRFunction function) {
        for (IRBlock block : function.blockList) {
            block.preBlockList.clear();
            block.nextBlockList.clear();
        };
    }

    public void runOnFunc(IRFunction function) {
        init(function);

        for (IRBlock block : function.blockList) {
            var terminator = block.terminator();
            if (terminator instanceof IRBrInst) {
                if (((IRBrInst) terminator).isJump()) {
                    block.linkBlock(((IRBrInst) terminator).destBlock());
                } else {
                    block.linkBlock(((IRBrInst) terminator).ifTrueBlock());
                    block.linkBlock(((IRBrInst) terminator).ifFalseBlock());
                }
            }
        }
    }

}

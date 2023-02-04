package chaos.compiler.backend.optimization;


import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.*;

import java.util.LinkedList;
import java.util.Queue;

public class TailCall {

    private boolean checkSimpleRetBlock(AsmBlock retBlock, AsmFunction function) {
        if (!(retBlock.instList.getLast() instanceof AsmRetInst)) return false;
        for (AsmBlock block : function.blockList)
            for (AsmBaseInst inst : block.instList)
                if (inst instanceof AsmBrInst && ((AsmBrInst) inst).target == retBlock) return false;
        return true;
    }

    public void runOnFunc(AsmFunction function) {
        Queue<AsmBlock>exitQueue = new LinkedList<>();
        exitQueue.offer(function.exitBlock);

        // propagation many times
        int depthLimit = 25;

        for (int i = 1; i <= depthLimit; i++) {
            for (AsmBlock block : function.blockList) {
                if (block.instList.getLast() instanceof AsmJmpInst) {
                    assert !exitQueue.contains(block);
                    var dest = ((AsmJmpInst) block.instList.getLast()).target;
                    if (exitQueue.contains(dest)) {
                        block.instList.removeLast();
                        for (AsmBaseInst inst : dest.instList) {
                            var copy = inst.copy();
                            block.addInst(copy);
                            // copy can not be jmp
                            if (copy instanceof AsmBrInst && !block.nextBlockList.contains(((AsmBrInst) copy).target)) {
                                block.nextBlockList.add(((AsmBrInst) copy).target);
                                ((AsmBrInst) copy).target.preBlockList.add(block);
                            }
                        }
                        dest.preBlockList.remove(block);
                        block.nextBlockList.remove(dest);
                    }
                }
            }

            for (AsmBlock exit : exitQueue)
                if (exit.preBlockList.isEmpty() && exit != function.entryBlock) function.blockList.remove(exit);

            exitQueue.clear();
            for (AsmBlock block : function.blockList) {
                if (checkSimpleRetBlock(block, function)) exitQueue.offer(block);
            }
        }

        for (AsmBlock block : function.blockList) {
            var it = block.instList.listIterator();

            while (it.hasNext()) {
                var inst = it.next();
                // special judge: terminator must be ret
                if (inst instanceof AsmCallInst && ((AsmCallInst) inst).isTailCall && block.instList.getLast() instanceof AsmRetInst) {
                    it.remove();
                    assert block.instList.getLast() instanceof AsmRetInst;
                    block.instList.removeLast();
                    new AsmTailInst(((AsmCallInst) inst).callFunc, block);
                    break;
                }
            }
        }
    }

}

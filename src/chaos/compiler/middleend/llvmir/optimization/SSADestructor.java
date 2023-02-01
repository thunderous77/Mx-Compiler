package chaos.compiler.middleend.llvmir.optimization;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.instruction.IRBrInst;
import chaos.compiler.middleend.llvmir.instruction.IRMoveInst;
import chaos.compiler.middleend.llvmir.instruction.IRPhiInst;

import java.util.ArrayList;
import java.util.HashMap;

public class SSADestructor {

    private final HashMap<IRBlock, CopyGraph> copyGraphMap = new HashMap<>();

    // critical path: fromBlock -> toBlock
    // notice: ConcurrentModificationException
    private void criticalEdgeSplit(IRFunction function) {
        ArrayList<IRBlock> midBlockLists = new ArrayList<>();
        HashMap<IRBlock, IRBlock> redirectPreLists = new HashMap<>();

        for (IRBlock fromBlock : function.blockList) {
            var successors = fromBlock.nextBlockList;
            if (successors.size() <= 1) continue;

            for (IRBlock toBlock : successors) {
                if (toBlock.preBlockList.size() <= 1) continue;
                // fromBlock -> midBlock -> toBlock
                IRBlock midBlock = new IRBlock("mid", null);

                midBlock.parentFunction = function; // add manually

                midBlockLists.add(midBlock);
                redirectPreLists.put(toBlock, midBlock);
                new IRBrInst(toBlock, midBlock); // jump to toBlock

                midBlock.preBlockList.add(fromBlock);
                midBlock.nextBlockList.add(toBlock);
                toBlock.redirectSucBlock(fromBlock, midBlock);
            }

            // from block suc fix
            redirectPreLists.forEach((to, mid) -> fromBlock.redirectPreBlock(to, mid));
            redirectPreLists.clear();
        }
        function.blockList.addAll(midBlockLists);
    }

    // resolve phi and generate para copies
    private void buildCopyGraph(IRFunction function) {
        function.blockList.forEach(block -> copyGraphMap.put(block, new CopyGraph()));
        for (IRBlock block : function.blockList) {
            for (IRPhiInst phi : block.phiInstList) {
                for (int i = 0; i < phi.getOperandSize(); i += 2) {
                    // insert in the graph of preBlockList
                    copyGraphMap.get((IRBlock) phi.getOperand(i + 1)).
                            insert(new CopyGraph.CopyEdge(phi, phi.getOperand(i)));
                }
            }
        }
    }

    // copy -> move inst
    // notice: ConcurrentModificationException
    // first eliminate all free nodes (whose in_degree in the graph == 0)
    private void eliminateFreeNode(IRBlock block, CopyGraph copyGraph) {
        boolean hasFreeNode = true;
        while (hasFreeNode) {
            hasFreeNode = false;
            for (var it = copyGraph.edges.iterator(); it.hasNext(); ) {
                CopyGraph.CopyEdge nowCopy = it.next();
                if (copyGraph.isFree(nowCopy.dest)) {
                    var move = new IRMoveInst(nowCopy.dest, nowCopy.source, null);// terminated
                    block.tAddBeforeTerminator(move);
                    copyGraph.remove(nowCopy, it);
                    hasFreeNode = true;
                }
            }
        }
    }
    // turn copy to move inst
    // if there is a loop, use a mid dest to break the loop
    public void copyToMove(IRBlock block, CopyGraph copyGraph) {
        boolean loopBreak = true;

        while (loopBreak) {
            loopBreak = false;
            eliminateFreeNode(block, copyGraph);
            for (var it = copyGraph.edges.iterator(); it.hasNext(); ) {
                CopyGraph.CopyEdge nowCopy = it.next();
                if (nowCopy.isLoop()) continue;
                copyGraph.remove(nowCopy, it);
                IRValue midDest = new IRValue("anonymous", nowCopy.dest.type);
                copyGraph.insert(new CopyGraph.CopyEdge(nowCopy.dest, midDest));

                var move = new IRMoveInst(midDest, nowCopy.source, block);
                block.tAddBeforeTerminator(move);

                loopBreak = true; 
                break;
            }
        }
    }

    public void runOnFunc(IRFunction function) {
        criticalEdgeSplit(function);
        buildCopyGraph(function);
        copyGraphMap.forEach(this::copyToMove);
    }
    
}

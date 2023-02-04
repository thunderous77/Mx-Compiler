package chaos.compiler.backend.regalloca;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.*;

public class LivenessAnalyzer {

    private final Queue<AsmBlock> workQueue = new LinkedList<>();
    private final HashSet<AsmBlock> finishSet = new HashSet<>();

    public void runOnFunc(AsmFunction function) {
        init(function);
        function.blockList.forEach(this::collectUsesAndDefs);





        workQueue.offer(function.exitBlock);
        while (!workQueue.isEmpty()) livenessAnalyze(workQueue.poll());
    }

    private void init(AsmFunction function) {
        for (AsmBlock block : function.blockList) {
            block.in.clear();
            block.out.clear();
        }
    }

    private final Map<AsmBlock, HashSet<AsmRegister>> blockUsesMap = new HashMap<>(),
            blockDefsMap = new HashMap<>();

    // first collect all uses and defs in a block
    private void collectUsesAndDefs(AsmBlock block) {
        HashSet<AsmRegister> blockUses = new HashSet<>(), blockDefs = new HashSet<>();
        for (AsmBaseInst inst : block.instList) {
            inst.uses().forEach(use -> {
                if (!blockDefs.contains(use)) blockUses.add(use);
            });
            blockDefs.addAll(inst.defs());
        }
        blockUsesMap.put(block, blockUses);
        blockDefsMap.put(block, blockDefs);
    }

    private void livenessAnalyze(AsmBlock block) {

        if (finishSet.contains(block)) return;
        finishSet.add(block);

        // out = suc in
        HashSet<AsmRegister> newLiveOut = new HashSet<>();
        block.nextBlockList.forEach(suc -> newLiveOut.addAll(suc.in));

        // in = use & (out - def)
        HashSet<AsmRegister> newLiveIn = new HashSet<>(newLiveOut);
        newLiveIn.removeAll(blockDefsMap.get(block));
        newLiveIn.addAll(blockUsesMap.get(block));

        // fixed point
        if (!newLiveIn.equals(block.in) || !newLiveOut.equals(block.out)) {
            block.in.addAll(newLiveIn);
            block.out.addAll(newLiveOut);
            block.preBlockList.forEach(finishSet::remove);
        }

        // BFS
        workQueue.addAll(block.preBlockList);
    }

}

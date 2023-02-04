package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.inst.AsmBrInst;
import chaos.compiler.backend.asm.inst.AsmJmpInst;
import chaos.utility.UnionSet;

import java.util.HashSet;


// this optimiaztion intends to remove unnecessary block:
// b1:
//      j  b2
// -> directly use b1 to replace b2


public class MergeBlock {

    private final UnionSet<AsmBlock> unionSet = new UnionSet<>();

    private AsmBlock getOnlyDest(AsmBlock block) {
        HashSet<AsmBlock> dests = new HashSet<>();
        for (AsmBaseInst inst : block.instList) {
            if (!(inst instanceof AsmBrInst || inst instanceof AsmJmpInst)) return null;
            if (inst instanceof AsmBrInst) dests.add(((AsmBrInst) inst).target);
            else dests.add(((AsmJmpInst) inst).target);
        }
        if (dests.size() > 1) return null;
        return dests.iterator().next();
    }

    public void runOnFunc(AsmFunction function) {
        boolean changed = true;

        while (changed) {
            changed = false;
            unionSet.clear();
            // Log.mark("round");

            for (AsmBlock block : function.blockList) {
                var dest = getOnlyDest(block);
                if (dest != null) {
                    // Log.report("remove", block);
                    var replace = unionSet.getAlias(dest);
                    unionSet.setAlias(block, replace);
                }
            }

            var it = function.blockList.iterator();

            while (it.hasNext()) {
                var block = it.next();
                if (unionSet.contains(block)) {
                    var alias = unionSet.getAlias(block);
                    for (AsmBlock pre : block.preBlockList) {
                        for (AsmBaseInst inst : pre.instList) {
                            if (inst instanceof AsmBrInst && ((AsmBrInst) inst).target == block)
                                ((AsmBrInst) inst).target = alias;
                            if (inst instanceof AsmJmpInst && ((AsmJmpInst) inst).target == block)
                                ((AsmJmpInst) inst).target = alias;
                        }
                    }

                    alias.preBlockList.addAll(block.preBlockList);
                    // nexts must be the same

                    it.remove();
                    changed = true;

                    // if entry is eliminated, the alias should swap with the first
                    if (block == function.entryBlock) {
                        function.entryBlock = alias;
                        var index = function.blockList.indexOf(alias);
                        function.blockList.set(index, function.blockList.get(0));
                        function.blockList.set(0, alias);
                    }
                }
            }
        }
    }

}

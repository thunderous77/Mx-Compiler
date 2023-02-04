package chaos.compiler.middleend.analyzer;

import chaos.compiler.middleend.llvmir.IRUse;
import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;

import java.util.HashMap;

public class GPMarker {

    private HashMap<IRGlobalValue, Integer> useCount = new HashMap<>();

    public void runOnModule(IRModule module) {
        module.funcList.forEach(func -> new LoopAnalyzer().runOnFunc(func));

        for (IRGlobalValue glo : module.globalVarList) {
            for (IRUse use : glo.useList) {
                assert use.user instanceof IRBaseInst;
                useCount.put(glo, (int) Math.pow(10, ((IRBaseInst) use.user).parentBlock.loopDepth));
            }
        }

        // string constant not marked

        IRGlobalValue marked = null;

        for (var entry : useCount.entrySet()) {
            if (marked == null) marked = entry.getKey();
            else {
                if (entry.getValue() > useCount.get(marked)) marked = entry.getKey();
            }
        }

        if (marked != null) marked.gpRegMark = true;
    }

}

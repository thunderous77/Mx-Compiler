package chaos.compiler.middleend.analyzer;

import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.Loop;
import chaos.utility.Pair;

import java.util.*;

public class LoopAnalyzer {

    private Map<IRBlock, Loop> headToLoopMap = new HashMap<>();
    private ArrayList<Pair<IRBlock, IRBlock>> backEdge = new ArrayList<>();

    private void init(IRFunction function) {
        new CFGBuilder().runOnFunc(function);
        new DomTreeBuilder(false).runOnFunc(function);
        function.topLevelLoops.clear();
        function.blockList.forEach(block -> {
            block.loopDepth = 0;
            block.belongLoop = null;
        });
    }

    private void collectBackEdge(IRFunction function) {
        for (IRBlock block : function.blockList) {
            for (IRBlock suc : block.nextBlockList) {
                if (block.dtNode.doms.contains(suc.dtNode)) {
                    backEdge.add(new Pair<>(suc, block));
                    break;
                }
            }
        }
    }

    private void buildNaturalLoop(IRBlock edgeHead, IRBlock edgeTail) {
        headToLoopMap.putIfAbsent(edgeHead, new Loop(edgeHead));
        headToLoopMap.get(edgeHead).tailers.add(edgeTail);
        headToLoopMap.get(edgeHead).addBlock(edgeHead);
        headToLoopMap.get(edgeHead).addBlock(edgeTail);

        Queue<IRBlock> workQueue = new LinkedList<>();
        workQueue.offer(edgeTail);

        while (!workQueue.isEmpty()) {
            IRBlock nowBlock = workQueue.poll();
            for (IRBlock pre : nowBlock.preBlockList)
                if (!headToLoopMap.get(edgeHead).blocks.contains(pre)) {
                    headToLoopMap.get(edgeHead).addBlock(pre);
                    workQueue.offer(pre);
                }
        }
    }

    private Stack<Loop> loopStack = new Stack<>();
    private HashSet<IRBlock> visited = new HashSet<>();

    private void buildLoopNestTree(IRFunction function, IRBlock block) {
        if (visited.contains(block)) return;
        visited.add(block);

        // Log.info("build", block.identifier());

        // filter the inner loop
        Loop outerLoop = null;
        if (!loopStack.empty()) {
            outerLoop = loopStack.peek();
            while (!outerLoop.blocks.contains(block)) {
                loopStack.pop();
                if (loopStack.empty()) {
                    outerLoop = null;
                    break;
                }
                outerLoop = loopStack.peek();
            }
        }

        if (headToLoopMap.containsKey(block)) {
            var nowLoop = headToLoopMap.get(block);
            if (outerLoop != null) {
                outerLoop.addNestedLoop(nowLoop);
            }
            else {
                function.topLevelLoops.add(nowLoop);
            }
            loopStack.push(nowLoop);
        }

        block.loopDepth = loopStack.size();

        for (IRBlock suc : block.nextBlockList) buildLoopNestTree(function, suc);
    }

    public void runOnFunc(IRFunction function) {
        init(function);

        collectBackEdge(function);

        backEdge.forEach(edge -> buildNaturalLoop(edge.first(), edge.second()));
        buildLoopNestTree(function, function.entryBlock);
    }

}

package chaos.compiler.middleend.llvmir;

import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;

import java.util.Stack;

public class IRCommander {
    // current
    public IRBlock currentBlock = null;
    public IRFunction currentFunc = null;
    public ClassRegistry classRegistry = null;

    private final Stack<IRBlock> continueBlockStack = new Stack<>();
    private final Stack<IRBlock> breakBlockStack = new Stack<>();

    public void terminateAllBlock() {
        for (IRBlock block : this.currentFunc.blockList)


}

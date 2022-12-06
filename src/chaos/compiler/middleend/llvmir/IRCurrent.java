package chaos.compiler.middleend.llvmir;

import chaos.compiler.frontend.ast.node.stmt.ControlStmtNode;
import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.instruction.IRBrInst;
import chaos.utility.Error;

import java.util.Stack;

public class IRCurrent {
    // current
    public IRBlock block = null;
    public IRFunction function = null;
    public ClassRegistry classRegistry = null;

    private final Stack<IRBlock> continueBlockStack = new Stack<>();
    private final Stack<IRBlock> breakBlockStack = new Stack<>();

    public void setLoopTarget(IRBlock continueTargetBlock, IRBlock breakTargetBlock) {
        continueBlockStack.push(continueTargetBlock);
        breakBlockStack.push(breakTargetBlock);
    }

    public void popLoopTarget() {
        continueBlockStack.pop();
        breakBlockStack.pop();
    }

    public void generateBrInst(ControlStmtNode.controlOp ctrlWord) {
        switch (ctrlWord) {
            case CONTINUE:
                new IRBrInst(continueBlockStack.peek(), block);
                break;
            case BREAK:
                new IRBrInst(breakBlockStack.peek(), block);
                break;
            default:
                throw new Error("[IR Error] Invalid control word \"" + ctrlWord + "\"");
        }
    }

    public IRValue getThis() {
        if (function != null && classRegistry != null) {
            return function.getOperand(0);
        } else throw new Error("[IR Error] find \"this\" not in a class function.");
    }

}

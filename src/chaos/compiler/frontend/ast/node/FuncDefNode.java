package chaos.compiler.frontend.ast.node;

import java.util.Objects;

import chaos.compiler.frontend.ast.node.stmt.SuiteStmtNode;
import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.utility.Position;

public class FuncDefNode extends BaseNode {
    public FuncRegistry funcRegistry;

    public SuiteStmtNode suiteNode;

    public FuncDefNode(Position pos, FuncRegistry funcRegistry, SuiteStmtNode suiteNode) {
        super(pos);
        this.funcRegistry = funcRegistry;
        this.suiteNode = suiteNode;
    }

    public boolean isValidMainFunc() {
        return Objects.equals(funcRegistry.name, "main") && funcRegistry.type.retType.match(BaseType.BuiltinType.INT) && funcRegistry.funcArgsList.size() == 0;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

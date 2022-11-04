package chaos.compiler.frontend.ast.node;

import java.util.ArrayList;

import chaos.compiler.frontend.ast.node.stmt.VarDefStmtNode;
import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.utility.Position;

public class ClassDefNode extends BaseNode {
    public ClassRegistry classRegistry;

    public FuncDefNode constructorDefNode;
    public ArrayList<VarDefStmtNode> varDefStmtNodes;
    public ArrayList<FuncDefNode> funcDefNodes;

    public ClassDefNode(Position pos) {
        super(pos);
        this.constructorDefNode = null;
        this.varDefStmtNodes = new ArrayList<>();
        this.funcDefNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

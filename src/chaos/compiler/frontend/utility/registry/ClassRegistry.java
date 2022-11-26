package chaos.compiler.frontend.utility.registry;

import java.util.ArrayList;

import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.utility.scope.ClassScope;

public class ClassRegistry extends BaseRegistry {

    public ClassScope scope;
    public ArrayList<VarRegistry> memberVars;
    public ArrayList<FuncRegistry> memberFuncs;

    public ClassRegistry(MxParser.ClassDefContext context) {
        super(context.Identifier().toString(),context);
        this.scope = new ClassScope();
        memberFuncs = new ArrayList<>();
        memberVars = new ArrayList<>();
    }

    // IR GEPInst
    public int getMemberIndex(String name) {
        for (int i = 0; i < memberVars.size(); i++)
            if (memberVars.get(i).name.equals(name)) return i;
        return -1;
    }

}
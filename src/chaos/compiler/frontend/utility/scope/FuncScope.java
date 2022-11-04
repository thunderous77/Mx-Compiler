package chaos.compiler.frontend.utility.scope;

import java.util.ArrayList;

import chaos.compiler.frontend.utility.registry.VarRegistry;
import chaos.compiler.frontend.utility.type.VarType;

public class FuncScope extends NormalScope {

    public ArrayList<VarType> retTypeList = new ArrayList<>();

}
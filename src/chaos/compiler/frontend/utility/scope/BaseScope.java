package chaos.compiler.frontend.utility.scope;

import java.util.HashMap;

import chaos.compiler.frontend.utility.registry.*;

public abstract class BaseScope {

    public HashMap<String,VarRegistry> varMap = new HashMap<>();

    public abstract ClassRegistry queryClass(String name);

    public abstract FuncRegistry queryFunc(String name);

    public abstract VarRegistry queryVar(String name);

    public abstract void register(BaseRegistry registry);

}
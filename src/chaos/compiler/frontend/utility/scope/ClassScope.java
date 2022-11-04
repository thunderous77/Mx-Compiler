package chaos.compiler.frontend.utility.scope;

import java.util.ArrayList;
import java.util.HashMap;

import chaos.compiler.frontend.utility.registry.*;
import chaos.utility.Error;

public class ClassScope extends BaseScope {

    public HashMap<String, FuncRegistry> funcMap;
    public ArrayList<FuncRegistry> funcList;

    public ClassScope() {
        this.varMap = new HashMap<>();
        this.funcMap = new HashMap<>();
        this.funcList = new ArrayList<>();
    }

    @Override
    public ClassRegistry queryClass(String name) {
        return null;
    }

    @Override
    public FuncRegistry queryFunc(String name) {
        return funcMap.get(name);
    }

    @Override
    public VarRegistry queryVar(String name) {
        return varMap.get(name);
    }

    @Override
    public void register(BaseRegistry registry) {
        String name = registry.name;
        if (registry instanceof FuncRegistry) {
            if (funcMap.containsKey(name))
                throw new Error(registry.position, "\"" + name + "\" is redefined");
            funcMap.put(name, (FuncRegistry) registry);
            if (((FuncRegistry) registry).isBuiltin) {
                funcList.add((FuncRegistry) registry);
            }
        } else if (registry instanceof VarRegistry) {
            if (varMap.containsKey(name))
                throw new Error(registry.position, "\"" + name + "\" is redefined");
            varMap.put(name, (VarRegistry) registry);
        }
    }

}
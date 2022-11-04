package chaos.compiler.frontend.utility.scope;

import java.util.ArrayList;
import java.util.HashMap;

import chaos.compiler.frontend.utility.registry.*;
import chaos.utility.Error;

public class GlobalScope extends BaseScope {

    public HashMap<String, ClassRegistry> classMap;
    public HashMap<String, FuncRegistry> funcMap;
    public ArrayList<FuncRegistry> builtinFuncList;

    public GlobalScope() {
        this.funcMap = new HashMap<>();
        this.classMap = new HashMap<>();
        this.varMap = new HashMap<>();
        this.builtinFuncList = new ArrayList<>();
    }

    @Override
    public ClassRegistry queryClass(String name) {
        return classMap.get(name);
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
        if (classMap.containsKey(name))
            throw new Error(registry.position, "\""+name+"\" is redefined");
        if (registry instanceof ClassRegistry) {
            if (funcMap.containsKey(name) || varMap.containsKey(name))
                throw new Error(registry.position, "\""+name+"\" is redefined");
            classMap.put(name, (ClassRegistry) registry);
        } else if (registry instanceof FuncRegistry) {
            if (funcMap.containsKey(name))
                throw new Error(registry.position, "\""+name+"\" is redefined");
            funcMap.put(name, (FuncRegistry) registry);
            if (((FuncRegistry) registry).isBuiltin) {
                builtinFuncList.add((FuncRegistry) registry);
            }
        } else if (registry instanceof VarRegistry) {
            if (varMap.containsKey(name))
                throw new Error(registry.position, "\""+name+"\" is redefined");
            varMap.put(name, (VarRegistry) registry);
        }
    }

}
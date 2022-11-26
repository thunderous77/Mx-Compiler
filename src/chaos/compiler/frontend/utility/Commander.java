package chaos.compiler.frontend.utility;

import java.util.Stack;

import chaos.compiler.frontend.utility.registry.*;
import chaos.compiler.frontend.utility.scope.BaseScope;
import chaos.compiler.frontend.utility.scope.FuncScope;
import chaos.compiler.frontend.utility.scope.LoopScope;
import chaos.compiler.frontend.utility.scope.ClassScope;
import chaos.compiler.frontend.utility.type.VarType;
import chaos.utility.Pair;

public class Commander {
    public Stack<BaseScope> scopeStack;
    public ClassRegistry currentClass = null;

    public Commander() {
        this.scopeStack = new Stack<>();
    }

    public VarRegistry queryVar(String name) {
        VarRegistry ret = null;
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            ret = scopeStack.get(i).queryVar(name);
            if (ret != null) return ret;
        }
        return ret;
    }

    public ClassRegistry queryClass(String name) {
        return scopeStack.get(0).queryClass(name);
    }

    public FuncRegistry queryFunc(String name) {
        FuncRegistry ret = null;
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            ret = scopeStack.get(i).queryFunc(name);
            if (ret != null) return ret;
        }
        return ret;
    }

    public void stackReturn(VarType retType) {
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            if (scopeStack.get(i) instanceof FuncScope) {
                ((FuncScope) scopeStack.get(i)).retTypeList.add(retType);
                return;
            }
        }
    }

    public boolean isInLoop() {
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            if (scopeStack.get(i) instanceof LoopScope)
                return true;
        }
        return false;
    }

    public boolean isInFunc() {
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            if (scopeStack.get(i) instanceof FuncScope)
                return true;
        }
        return false;
    }

    public ClassRegistry currentClass() {
        return currentClass;
    }

    public void register(BaseRegistry registry) {
        if (scopeStack.peek() instanceof ClassScope) return;
        scopeStack.peek().register(registry);
    }

    public void push(BaseScope scope) {
        assert scope != null;
        this.scopeStack.push(scope);
    }

    public void push(ClassRegistry registry) {
        assert registry != null;
        this.scopeStack.push(registry.scope);
        this.currentClass = registry;
    }

    public void pop() {
        if (this.scopeStack.peek() instanceof ClassScope)
            this.currentClass = null;
        this.scopeStack.pop();
    }

    public Pair<VarRegistry, Boolean> queryVarforIR(String name) {
        VarRegistry retVar = null;
        Boolean retBool = false;
        for (int i = scopeStack.size() - 1; i >= 0; --i) {
            retVar = scopeStack.get(i).queryVar(name);
            if (retVar != null && retVar.value != null) {
                if (scopeStack.get(i) instanceof ClassScope) retBool = true;
                break;
            }
        }
        return new Pair<>(retVar, retBool);
    }

}
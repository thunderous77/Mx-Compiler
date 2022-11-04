package chaos.compiler.frontend.utility.registry;

import java.util.ArrayList;

import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.utility.scope.FuncScope;
import chaos.compiler.frontend.utility.type.*;

public class FuncRegistry extends BaseRegistry {

    public FuncType type;
    public FuncScope scope;
    public ArrayList<VarRegistry> funcArgs;
    public boolean isBuiltin;

    //default function
    public FuncRegistry(String name) {
        super(name);
        this.scope = new FuncScope();
        this.type = new FuncType();
        this.type.retType = new VarType(BaseType.BuiltinType.VOID);
        funcArgs = new ArrayList<>();
        isBuiltin = false;
    }

    //builtin function
    public FuncRegistry(String name, BaseType.BuiltinType retType, VarRegistry... args) {
        super(name);
        this.scope = new FuncScope();
        this.type = new FuncType();
        this.type.retType = new VarType(retType);
        funcArgs = new ArrayList<>();
        isBuiltin = true;

        for (int i = 0; i < args.length; ++i) {
            funcArgs.add(args[i]);
            type.funcArgsType.add(args[i].type);
        }
    }

    //lambda
    public FuncRegistry(MxParser.LambdaExpContext context) {
        super("", context);
        this.scope = new FuncScope();
        this.type = new FuncType();
        funcArgs = new ArrayList<>();
        isBuiltin = false;

        MxParser.ParameterListContext parameterListContext = context.parameterList();
        if (parameterListContext != null) {
            for (int i = 0; i < parameterListContext.varDefType().size(); ++i) {
                VarRegistry varRegistry = new VarRegistry(parameterListContext.Identifier(i).toString(), parameterListContext.varDefType(i));
                funcArgs.add(varRegistry);
                type.funcArgsType.add(varRegistry.type);
            }
        }
    }

    //normal
    public FuncRegistry(MxParser.FuncDefContext context) {
        super(context.Identifier().toString(), context);
        this.scope = new FuncScope();
        this.type = new FuncType();
        funcArgs = new ArrayList<>();
        type.retType = new VarType(context.varDefType(), true);
        isBuiltin = false;

        MxParser.ParameterListContext parameterListContext = context.parameterList();
        if (parameterListContext != null) {
            for (int i = 0; i < parameterListContext.varDefType().size(); ++i) {
                VarRegistry varRegistry = new VarRegistry(parameterListContext.Identifier(i).toString(), parameterListContext.varDefType(i));
                funcArgs.add(varRegistry);
                type.funcArgsType.add(varRegistry.type);
            }
        }
    }

    //constructor
    public FuncRegistry(MxParser.ClassConstructorDefContext context) {
        super(context.Identifier().toString(), context);
        this.scope = new FuncScope();
        this.type = new FuncType();
        funcArgs = new ArrayList<>();
        type.retType = new VarType(BaseType.BuiltinType.VOID);
        isBuiltin = false;
    }

}
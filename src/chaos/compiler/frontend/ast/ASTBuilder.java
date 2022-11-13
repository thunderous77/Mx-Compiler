package chaos.compiler.frontend.ast;

import chaos.compiler.frontend.ast.node.*;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.*;
import chaos.compiler.frontend.utility.registry.ClassRegistry;
import chaos.compiler.frontend.utility.type.VarType;
import chaos.utility.Error;
import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.parser.MxParserBaseVisitor;
import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.registry.VarRegistry;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.utility.Position;

import java.util.Objects;

public class ASTBuilder extends MxParserBaseVisitor<BaseNode> {

    @Override
    public BaseNode visitMxProgram(MxParser.MxProgramContext ctx) {
        RootNode ret = new RootNode(new Position(ctx));
        ret.scope.register(new FuncRegistry("print", BaseType.BuiltinType.VOID, new VarRegistry("str", BaseType.BuiltinType.STRING)));
        ret.scope.register(new FuncRegistry("println", BaseType.BuiltinType.VOID, new VarRegistry("str", BaseType.BuiltinType.STRING)));
        ret.scope.register(new FuncRegistry("printInt", BaseType.BuiltinType.VOID, new VarRegistry("n", BaseType.BuiltinType.INT)));
        ret.scope.register(new FuncRegistry("printlnInt", BaseType.BuiltinType.VOID, new VarRegistry("n", BaseType.BuiltinType.INT)));
        ret.scope.register(new FuncRegistry("getString", BaseType.BuiltinType.STRING));
        ret.scope.register(new FuncRegistry("getInt", BaseType.BuiltinType.INT));
        ret.scope.register(new FuncRegistry("toString", BaseType.BuiltinType.STRING, new VarRegistry("i", BaseType.BuiltinType.INT)));
        boolean hasMainFunc = false;
        for (int i = 0; i < ctx.children.size(); i++) {
            if (ctx.children.get(i) instanceof MxParser.ClassDefContext) {
                ClassDefNode classDefNode = (ClassDefNode) visit(ctx.children.get(i));
                ret.sonNodes.add(classDefNode);
                ret.scope.register(classDefNode.classRegistry); // register class first
            } else if (ctx.children.get(i) instanceof MxParser.FuncDefContext) {
                FuncDefNode funcDefNode = (FuncDefNode) visit(ctx.children.get(i));
                ret.sonNodes.add(funcDefNode);
                ret.scope.register(funcDefNode.funcRegistry); // register global-func first
                if (funcDefNode.isValidMainFunc()) hasMainFunc = true;
            } else if (ctx.children.get(i) instanceof MxParser.VarDefStmtContext) {
                VarDefStmtNode varDefStmtNode = (VarDefStmtNode) visit(ctx.children.get(i));
                ret.sonNodes.add(varDefStmtNode);
            }
        }
        if (!hasMainFunc) // report the end of code is reasonable.
            throw new Error(new Position(ctx.getStop()), "main function is missing");
        return ret;
    }

    @Override
    public BaseNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode ret = new ClassDefNode(new Position(ctx));
        ret.classRegistry = new ClassRegistry(ctx);
        ctx.classConstructorDef().forEach(sonctx -> {
            FuncDefNode constructorDefNode = (FuncDefNode) visit(sonctx);
            if (!Objects.equals(ret.classRegistry.name, constructorDefNode.funcRegistry.name)) {
                throw new Error(new Position(ctx), "the construction function's structName does not match with the class structName");
            }
            ret.classRegistry.memberFuncs.add(constructorDefNode.funcRegistry);
            ret.classRegistry.scope.register(constructorDefNode.funcRegistry);
            ret.constructorDefNode = constructorDefNode;
        });
        if (ctx.classConstructorDef().size() == 0) {
            FuncRegistry defaultConstructor = new FuncRegistry(ctx.Identifier().getText());
            SuiteStmtNode suiteNode = new SuiteStmtNode(new Position(ctx));
            ReturnStmtNode retNode = new ReturnStmtNode(new Position(ctx));
            suiteNode.stmtNodes.add(retNode);
            ret.classRegistry.memberFuncs.add(defaultConstructor);
            ret.classRegistry.scope.register(defaultConstructor);
            ret.constructorDefNode = new FuncDefNode(new Position(ctx), defaultConstructor, suiteNode);
        }
        ctx.varDefStmt().forEach(sonctx -> {
            VarDefStmtNode varDefStmtNode = (VarDefStmtNode) visit(sonctx);
            varDefStmtNode.varDefSingleNodes.forEach(sonnode -> {
                ret.classRegistry.memberVars.add(sonnode.varRegistry);
                ret.classRegistry.scope.register(sonnode.varRegistry);
            });
            ret.varDefStmtNodes.add(varDefStmtNode);
        });
        ctx.funcDef().forEach(sonctx -> {
            FuncDefNode funcDefNode = (FuncDefNode) visit(sonctx);
            ret.classRegistry.memberFuncs.add(funcDefNode.funcRegistry);
            ret.classRegistry.scope.register(funcDefNode.funcRegistry);
            ret.funcDefNodes.add(funcDefNode);
        });
        return ret;
    }

    @Override
    public BaseNode visitClassConstructorDef(MxParser.ClassConstructorDefContext ctx) {
        return new FuncDefNode(new Position(ctx), new FuncRegistry(ctx), (SuiteStmtNode) visit(ctx.suite()));
    }

    @Override
    public BaseNode visitFuncDef(MxParser.FuncDefContext ctx) {
        return new FuncDefNode(new Position(ctx), new FuncRegistry(ctx), (SuiteStmtNode) visit(ctx.suite()));
    }

    @Override
    public BaseNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        VarDefStmtNode ret = new VarDefStmtNode(new Position(ctx));
        for (int i = 0; i < ctx.varDef().varDefSingle().size(); i++) {
            VarDefSingleNode varDefSingleNode = (VarDefSingleNode) visit(ctx.varDef().varDefSingle(i));
            varDefSingleNode.varRegistry.type = new VarType(ctx.varDef().varDefType());
            ret.varDefSingleNodes.add(varDefSingleNode);
        }
        return ret;
    }

    @Override
    public BaseNode visitVarDefSingle(MxParser.VarDefSingleContext ctx) {
        VarDefSingleNode ret = new VarDefSingleNode(new Position(ctx));
        ret.varRegistry = new VarRegistry(ctx.Identifier().toString(), ctx);
        if (ctx.Assign() != null) ret.initNode = (ExprBaseNode) visit(ctx.expr());
        return ret;
    }

    @Override
    public BaseNode visitSuite(MxParser.SuiteContext ctx) {
        SuiteStmtNode ret = new SuiteStmtNode(new Position(ctx));
        ctx.statement().forEach(sonctx -> ret.stmtNodes.add((StmtBaseNode) visit(sonctx)));
        return ret;
    }

    @Override
    public BaseNode visitForStmt(MxParser.ForStmtContext ctx) {
        ForStmtNode ret = new ForStmtNode(new Position(ctx));
        if (ctx.forInit() != null) {
            if (ctx.forInit().varDef() != null) {
                for (int i = 0; i < ctx.forInit().varDef().varDefSingle().size(); i++) {
                    VarDefSingleNode varDefSingleNode = (VarDefSingleNode)
                            visit(ctx.forInit().varDef().varDefSingle(i));
                    varDefSingleNode.varRegistry.type = new VarType(ctx.forInit().varDef().varDefType());
                    ret.initVarDefSingleNode.add(varDefSingleNode);
                }
            } else if (ctx.forInit().expr() != null) {
                if (ctx.forInit().expr() != null)
                    ret.initNode = (ExprBaseNode) visit(ctx.forInit().expr());
            }
        }
        if (ctx.forCondition != null) {
            ret.conditionNode = (ExprBaseNode) visit(ctx.forCondition);
        }
        if (ctx.forIncr != null) {
            ret.incrNode = (ExprBaseNode) visit(ctx.forIncr);
        }
        ret.bodyStmtNode = (StmtBaseNode) visit(ctx.statement());
        return ret;
    }

    @Override
    public BaseNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        return new WhileStmtNode(new Position(ctx),
                (ExprBaseNode) visit(ctx.expr()),
                (StmtBaseNode) visit(ctx.statement()));
    }

    @Override
    public BaseNode visitIfStmt(MxParser.IfStmtContext ctx) {
        IfStmtNode ret = new IfStmtNode(new Position(ctx),
                (ExprBaseNode) visit(ctx.expr()),
                (StmtBaseNode) visit(ctx.statement(0)));
        if (ctx.statement().size() == 2) {
            ret.elseStmtNode = (StmtBaseNode) visit(ctx.statement(1));
        }
        return ret;
    }

    @Override
    public BaseNode visitControlStmt(MxParser.ControlStmtContext ctx) {
        String controlWord = ctx.getText();
        controlWord = controlWord.substring(0, controlWord.length() - 1); // get rid of ';'
        return new ControlStmtNode(new Position(ctx), controlWord);
    }

    @Override
    public BaseNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        ReturnStmtNode ret = new ReturnStmtNode(new Position(ctx));
        if (ctx.expr() != null) ret.retNode = (ExprBaseNode) visit(ctx.expr());
        return ret;
    }

    @Override
    public BaseNode visitPureStmt(MxParser.PureStmtContext ctx) {
        PureStmtNode ret = new PureStmtNode(new Position(ctx));
        if (ctx.expr() != null) ret.exprNode = (ExprBaseNode) visit(ctx.expr());
        return ret;
    }

    @Override
    public BaseNode visitBinaryExp(MxParser.BinaryExpContext ctx) {
        BinaryExprNode ret = new BinaryExprNode(new Position(ctx), (ExprBaseNode) visit(ctx.expr().get(0)), (ExprBaseNode) visit(ctx.expr().get(1)));
        if (ctx.mulLevel() != null) {
            ret.setType(ctx.mulLevel().getText());
        } else if (ctx.addLevel() != null) {
            ret.setType(ctx.addLevel().getText());
        } else if (ctx.bitLogic() != null) {
            ret.setType(ctx.bitLogic().getText());
        } else if (ctx.shift() != null) {
            ret.setType(ctx.shift().getText());
        } else if (ctx.compare() != null) {
            ret.setType(ctx.compare().getText());
        } else if (ctx.equal() != null) {
            ret.setType(ctx.equal().getText());
        } else if (ctx.bitLogic() != null) {
            ret.setType(ctx.bitLogic().getText());
        }
        return ret;
    }

    @Override
    public BaseNode visitAssignExp(MxParser.AssignExpContext ctx) {
        return new AssignExprNode(new Position(ctx),
                (ExprBaseNode) visit(ctx.expr(0)), (ExprBaseNode) visit(ctx.expr(1)));
    }

    @Override
    public BaseNode visitFuncCallExp(MxParser.FuncCallExpContext ctx) {
        FuncCallExprNode ret = new FuncCallExprNode(new Position(ctx), (ExprBaseNode) visit(ctx.expr()));
        if (ctx.funcCallArgs().expr() != null) {
            ctx.funcCallArgs().expr().forEach(sonctx -> {
                ret.argNodes.add((ExprBaseNode) visit(sonctx));
            });
        }
        return ret;
    }

    @Override
    public BaseNode visitIndexExp(MxParser.IndexExpContext ctx) {
        return new IndexExprNode(new Position(ctx),
                (ExprBaseNode) visit(ctx.expr(0)),
                (ExprBaseNode) visit(ctx.expr(1))
        );
    }

    @Override
    public BaseNode visitLambdaExp(MxParser.LambdaExpContext ctx) {
        LambdaExprNode ret = new LambdaExprNode(new Position(ctx), (SuiteStmtNode) visit(ctx.suite()));

        ret.funcRegistry = new FuncRegistry(ctx);

        if (ctx.funcCallArgs().expr() != null) {
            ctx.funcCallArgs().expr().forEach(sonctx -> {
                ret.ArgsNode.add((ExprBaseNode) visit(sonctx));
            });
        }
        return ret;
    }

    @Override
    public BaseNode visitMemberExp(MxParser.MemberExpContext ctx) {
        return new MemberExprNode(new Position(ctx), (ExprBaseNode) visit(ctx.expr()), ctx.Identifier().getText());
    }

    @Override
    public BaseNode visitPostfixExp(MxParser.PostfixExpContext ctx) {
        return new PostfixExprNode(new Position(ctx), ctx.postfix().getText(), (ExprBaseNode) visit(ctx.expr()));
    }

    @Override
    public BaseNode visitUnaryExp(MxParser.UnaryExpContext ctx) {
        return new UnaryExprNode(new Position(ctx), ctx.unary().getText(), (ExprBaseNode) visit(ctx.expr()));
    }

    @Override
    public BaseNode visitPrefixExp(MxParser.PrefixExpContext ctx) {
        return new PrefixExprNode(new Position(ctx), ctx.prefix().getText(), (ExprBaseNode) visit(ctx.expr()));
    }

    @Override
    public BaseNode visitNewExp(MxParser.NewExpContext ctx) {
        NewExprNode ret = new NewExprNode(new Position(ctx), new VarType(ctx));
        ctx.arraySizeDef().forEach(sonctx -> {
            if (sonctx.expr() != null) {
                ret.eachDimSizeNode.add((ExprBaseNode) visit(sonctx.expr()));
            }
        });
        return ret;
    }

    @Override
    public BaseNode visitParenExp(MxParser.ParenExpContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public BaseNode visitAtomExp(MxParser.AtomExpContext ctx) {
        return new AtomExprNode(new Position(ctx), ctx.atom());
    }

}

# Compiler Design

## Semantic

### chaos.utility.Position.java

* 增加构造函数

~~~java
public Position(Token token) {}
~~~

### MxParser.g4

* 数组类型声明时，支持void读入，在SematicChecker.java再抛出异常

  ~~~g4
  varDefType: (VoidType | builtinType) (LeftBracket RightBracket)* ;
  ~~~

### Node.java

* accept函数：让ASTVisitor/SematicChecker访问node自己

  ~~~java
  public void accept(ASTVisitor vis) { vis.visit(this); }
  ~~~

### VarType.java

* VarType关于VarDefTypeContext实现了两种构造函数

  ~~~
  public VarType(MxParser.VarDefTypeContext context, boolean isFuncRetType);
  ~~~

  ~~~
  public VarType(MxParser.VarDefTypeContext context);
  ~~~

  第一种中isFuncRetType专门针对函数返回的类型，避免返回值为类型void时的异常抛出

  第二种则是针对普通数据类型的构造函数

### SemanticChecker.java

* 所有类型与函数的未定义行为在`public void visit(AtomExprNode node)`中 `if (node.context.Identifier() != null)`检测


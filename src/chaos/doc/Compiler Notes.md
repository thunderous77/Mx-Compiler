# Compiler Notes

## Semantic

### g4

#### MxLexer.g4

* 语法

  ~~~
  开头大写
  ID1:[a-z]+;//重复至少一次,（方括号表示匹配其中一个字符）
  ID2:[a-z]*;//重复任意多次（可以为0次） 
  ID3:~[a-z];//除了a-z都匹配，取反符号后面必须是单个字符或[]组成的单个字符的集合 (\n取反要加[]，即~[\n]) （若后接()，且()内都为单个字符也合法 ）
  	即可以的形式有 ~a,~'a',~[ab],~('a'|'b'),~'\n'
  ID4:('a'|'bb')*;//匹配'a'或'bb'，重复任意多次
  ID5 'ab'? 'cd';// 'ab'?表示匹配'ab'或者忽略它
  STRING:'"' .*? '"';//'.'可以匹配任意字符,若是不加问号默认为贪婪匹配，加了问号表示改为非贪婪匹配(懒惰匹配)
  	即 若是使用贪婪匹配，则 "sss" "sss" 会被匹配成一个STRING
  	而若是使用懒惰匹配，"sss" "sss"会被匹配成两个STRING
  WS: ->skip; //->skip表示在lexer解析完成后，把该类型的token全部删去
  ~~~

#### MxParser.g4

* 语法

  ~~~
  开头小写
  expr: expr op=('*'|'/') expr		#MulDiv
  	| expr op=('+'|'-') expr		#AddSub
  	| INT						    #int
  	| ID						    #id
  	| '(' expr ')'				    #parens
  	;
  //多个备选分支
  //#后面表示某一分支的标签(请让标签和规则名都独一无二)
  //expr op=('*'|'/') expr 表示在这个语句里，把('*'|'/')命名为op
  ttt	:INT_CONSTANT
  	|<assoc=right>ttt '+' ttt//右结合(antlr4默认为左结合)
  	;
  ~~~
  
* 引用MxLexer.g4

  ~~~java
  options {tokenVocab = MxLexer;}
  ~~~



### ANTLR4

#### 生成java文件

* XXXLexer.java和XXXparser.java为根据XXX.g4文件中的词法/句法生成的lexer/parser

* XXXListener.java提供了listener的基类(虚类)，XXXBaseListener.java提供了listener的默认实现类(从XXXListener.java中派生而来)

* XXXVisitor.java提供了Visitor的基类(虚类)，XXXBaseVisitor.java提供了Visitor的默认实现类(从XXXVisitor.java中派生而来)

#### 语法分析树

* 对于语法分析树中的每一个非叶节点(rule)，都会生成一个单独的RuleNode的子类

* 对于语法分析树中的每一个叶节点(token)，都会生成一个TerminalNode类

* RuleNode和TerminalNode都是ParseTree的子类

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030205825216.png" style="zoom:30%">

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030205920175.png" style="zoom:30%">

#### Listener

* antlr为每个语法文件生成一个ParseTreeListener的子类，该类中，每一个非叶节点(rule)都有一个单独的enter方法和exit方法
* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030210538623.png" style="zoom:30%">

#### Visitor

* 每一个非叶节点(rule)都有一个单独的visitXXX方法

  ~~~java
  @Override public T visitMxProgram(MxParser.MxProgramContext ctx) { return visitChildren(ctx); }
  ~~~

  > 特别的，叶节点(token)也有一个visit方法，名为visitTerminal()

  > antlr运行库提供MyVisitor类，通过调用visit()方法开始对语法分析树进行一次DFS

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030210754246.png" style="zoom:30%">



### CST/AST

* CST--MxParserXXXContext, AST--XXXNode,CST->AST--ASTBuilder.java

* 可以/推荐实现的功能

  > 删除无用的信息（分号，括号等）

  > 简单处理节点信息（判断是否可以作为左值）
  
  > 删去CST中多余的节点，并且将语法树转换成自己熟悉的语法结构

* ASTBuilder.java

  > 从XXXBasevisitor派生出ASTBuilder，让返回类型为BaseNode

  > 重写visitor中所有的visitXXX函数，使得visitor调用visit()函数时自动dfs遍历时可以返回XXXNode类型，得到一棵AST自身Node的树(即最后返回的RootNode节点)，这个RootNode节点就是AST的语法分析树

  > AST的各个Node中存着孩子节点的关系和一些其他有用信息

  > 这棵树上的所有节点可以通过visit函数让visitor来访问自己，从而实现SematicChecker
  

## Codegen

### My LLVM IR

* [Type](https://blog.csdn.net/weixin_42654107/article/details/122862209#:~:text=llvm%3A%3A,ArrayType%20ArrayType%E6%98%AF1%E7%A7%8D%E5%B0%86%E5%85%83%E7%B4%A0%E5%9C%A8%E5%86%85%E5%AD%98%E4%B8%AD%E9%A1%BA%E5%BA%8F%E6%8E%92%E5%88%97%E7%9A%84%E7%B1%BB%E5%9E%8B%EF%BC%8C%E6%9C%892%E4%B8%AA%E5%B1%9E%E6%80%A7%EF%BC%9Asize%E5%92%8C%E5%85%83%E7%B4%A0%E7%B1%BB%E5%9E%8B%E3%80%82)

  ![image-20221109195753530](C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109195753530.png)

  * 删去`VectorType`（未用到）
  * 新增 `LabelType`  `VoidType`
  
* [Constant](https://blog.csdn.net/weixin_42654107/article/details/123419630?spm=1001.2014.3001.5502)

  ![image-20221109205145125](C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109205145125.png)

  * 实现`AggregateZeroConstant`  `IntergerConstant`  `NullPointerConstant`   `StrConstant`

* [Value](https://blog.csdn.net/weixin_42654107/article/details/122998347?spm=1001.2014.3001.5502)  [User](https://blog.csdn.net/weixin_42654107/article/details/123056494?spm=1001.2014.3001.5502)  [Use](https://blog.csdn.net/weixin_42654107/article/details/123086570?spm=1001.2014.3001.5502)

  <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109173451304.png" style="zoom:50%">

  > 一个Value可以有多个Use，每个Use对应一个User
  
  > 一个User可以有多个Use，每个Use对应一个Value
  
  > **Use类的核心就是如何让Value和User高效地双向关联**
  
* hierarchy

  <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109185847766.png" style="zoom:70%">

  实现`Module`  `Function`  `Block`   `Variable`(新增)

* [Instruction](https://blog.csdn.net/qq_37206105/article/details/115274241)

  `BinaryInst` Semantic中的运算二元关系

  

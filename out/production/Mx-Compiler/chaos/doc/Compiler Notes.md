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

* 对于语法分析树中的每一个非叶结点(rule)，都会生成一个单独的RuleNode的子类

* 对于语法分析树中的每一个叶结点(token)，都会生成一个TerminalNode类

* RuleNode和TerminalNode都是ParseTree的子类

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030205825216.png" style="zoom:30%">

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030205920175.png" style="zoom:30%">

#### Listener

* antlr为每个语法文件生成一个ParseTreeListener的子类，该类中，每一个非叶结点(rule)都有一个单独的enter方法和exit方法
* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030210538623.png" style="zoom:30%">

#### Visitor

* 每一个非叶结点(rule)都有一个单独的visitXXX方法

  ~~~java
  @Override public T visitMxProgram(MxParser.MxProgramContext ctx) { return visitChildren(ctx); }
  ~~~

  > 特别的，叶结点(token)也有一个visit方法，名为visitTerminal()

  > antlr运行库提供MyVisitor类，通过调用visit()方法开始对语法分析树进行一次DFS

* <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221030210754246.png" style="zoom:30%">



### CST/AST

* CST--MxParserXXXContext, AST--XXXNode,CST->AST--ASTBuilder.java

* 可以/推荐实现的功能

  > 删除无用的信息（分号，括号等）

  > 简单处理结点信息（判断是否可以作为左值）
  
  > 删去CST中多余的结点，并且将语法树转换成自己熟悉的语法结构

* ASTBuilder.java

  > 从XXXBasevisitor派生出ASTBuilder，让返回类型为BaseNode

  > 重写visitor中所有的visitXXX函数，使得visitor调用visit()函数时自动dfs遍历时可以返回XXXNode类型，得到一棵AST自身Node的树(即最后返回的RootNode结点)，这个RootNode结点就是AST的语法分析树

  > AST的各个Node中存着孩子结点的关系和一些其他有用信息

  > 这棵树上的所有结点可以通过visit函数让visitor来访问自己，从而实现SematicChecker
  

## Codegen

### My LLVM IR

* SSA

  * SSA 要求的是在**静态**，即仅从代码文本层面可以看出的单一赋值，而非运行时只会被赋值一次
  * 我们通过 Value 中的 `rename()` 函数实现

* [Type](https://blog.csdn.net/weixin_42654107/article/details/122862209#:~:text=llvm%3A%3A,ArrayType%20ArrayType%E6%98%AF1%E7%A7%8D%E5%B0%86%E5%85%83%E7%B4%A0%E5%9C%A8%E5%86%85%E5%AD%98%E4%B8%AD%E9%A1%BA%E5%BA%8F%E6%8E%92%E5%88%97%E7%9A%84%E7%B1%BB%E5%9E%8B%EF%BC%8C%E6%9C%892%E4%B8%AA%E5%B1%9E%E6%80%A7%EF%BC%9Asize%E5%92%8C%E5%85%83%E7%B4%A0%E7%B1%BB%E5%9E%8B%E3%80%82)

  ![image-20221109195753530](C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109195753530.png)

  * 删去`VectorType`（未用到）
  
  * 新增 `LabelType`  `VoidType` `BoolType` `MemBoolType` `StructType`
  
  * `BoolType(i1)` and `MemBoolType(i8)`用途
    - alloca: alloca boolType -> alloca memBoolType
    - load: load memBoolType -> load boolType
    - store: store boolType -> store memBoolType
    - 我们于是需要 `IRBitcastInst` 来实现两者的相互强制转换
    
  * `StructType`
  
    即 Semantic中的 class，`Struct` 保存了 class 的 Value 传给 ClassRegistry，而 `StructType` 则作为一种 type 与 class 对应
  
  * `ArrayType`
  
    只用于 String，真正的数组是用指针实现的
  
  * `FunctionType`
  
    参数 `methodFrom` 表示函数是某些类型（`IRBaseType`）所专有的（string 或者 class）
    
  * `size()` 函数
  
    * 为之后 `alloca` 操作做准备，返回byte值
    * `IRPointerType` 的 size 是 8（64 位机）
  
* [Constant](https://blog.csdn.net/weixin_42654107/article/details/123419630?spm=1001.2014.3001.5502)

  ![image-20221109205145125](C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109205145125.png)

  * 实现`BoolConstant`  `IntergerConstant`  `NullPointerConstant`   `StrConstant`

* [Value](https://blog.csdn.net/weixin_42654107/article/details/122998347?spm=1001.2014.3001.5502)  [User](https://blog.csdn.net/weixin_42654107/article/details/123056494?spm=1001.2014.3001.5502)  [Use](https://blog.csdn.net/weixin_42654107/article/details/123086570?spm=1001.2014.3001.5502)

  <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109173451304.png" style="zoom:50%">

  * 一个 Value 可以有多个 Use，每个 Use 对应一个 User
  * 每个 value 有 `identifier()` 函数，默认是 `%name` （寄存器），全局变量是 `@name`
  
  * 一个 User 可以有多个 Use，每个 Use 对应一个 Value
  
  * **Use类的核心就是如何让Value和User高效地双向关联**
  
* hierarchy

  <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221109185847766.png" style="zoom:70%">

  * 新增 `GlobalValue` 和 `GlobalVariable`

* [Instruction](https://blog.csdn.net/qq_37206105/article/details/115274241)

  * Instruction 的 Value 表示它的返回值
  * `format()` 返回一行 instruction 指令
  * 同时 instruction 还是 value，因此也有 `identifier()` 函数返回标识符
  
  * `BinaryInst` Semantic 中的运算二元关系
  
  * `GEPInst`
  
    <img src="C:\Users\27595\AppData\Roaming\Typora\typora-user-images\image-20221120222752580.png" style="zoom:30%">
  
    为了简化，我们只使用 1/2 个 index（`i32 1`），因为一个含有多个index的GEP可以拆分成多个1/2 index 的 GEP
  
    [这里](https://blog.csdn.net/qq_42570601/article/details/107581608)对于第一个和第二个参数解释很清楚
    
    `getelementptr %A, ptr %B, i32 a(,i32 b)` 代表先移动a*A，再移动b位（struct里，一个变量算一位）
  
* IRBlock

  * 一个 block 以一个 LabelType 的 label 起始，包含了一串指令

  * 为了 instruction 中 `format()` 函数，type 设置为 `IRLabelType` ，于是 `typedIdentifier()` 输出 `label identifier `

* IRBuilder

  * varRegistry 的 value 里存地址，因为全局变量是存在内存里的，每次去 varRgistry 里找地址，然后去内存里读

  * 任何有声明语句的变量都会在内存里开一块空间，而寄存器是用来存运算的中间值的

  * `RootNode`

    * 直接子结点中的 VarDefNode 一定是全局变量，开一个 bool `isGloabalVar`，开的时候访问 VarDefNode 时声明全局变量，扫完关掉

    * 开一个 `globalVarInit` 的 IRFunction，其中存储全局变量的赋值和 new 语句，在 main 函数一开始调用它

    * 最后遍历所有 Block，如果没有结束指令（`IRRetInst` 和 `IRBrInst`）就指向所在 Function 的 exitblock

      因为会出现以下例子

      ```C++
      // void 函数
      void a() {A;}
      
      // main 函数 return 0 省略
      int main() {A;}
      ```
  
  * `FuncDefNode`
  
    * 除了 main 函数，其他函数的返回值和参数都要在内存中开一块空间
  
    * 在这里在 exitBlock 中开了两条指令，分别是从内存中读取返回值（`IRLoadInst`），以及返回这个值（`IRRetInst`），之后 visit ReturnStmtNode 只需要修改内存中的返回值，然后跳转到 exitBlock 即可
  
  * `unaryNode`
  
    BIT_NOT: -1 的原码 `100001`，反码（原码除首位取反） `111110`，补码（反码+1） `111111`
  
  * `newExprNode`
  
    新建数组的时候，在内存里，每一维（如果是多维数组）的头指针前先存一个 `i32` 的 size，便于数组内建函数 `a.size()` 查找数组信息，调用时只要用 GEPInst 往头指针之前找一个 
  
  * `BinaryNode`
  
    逻辑运算实现了强制跳转，即如果已经能算出结果将不计算后面的 expression，之后的复制通过 phi 指令进行 
  
  * `FuncDeclare`
  
    类的成员函数第一个参数额外输入 `this` 指针，指向类（的 type）

## Backend

### [**准备工作**](https://blog.csdn.net/qq_29674357/article/details/79069013)

* 消除 phi 指令

  由于底层没有 phi 指令对应的指令，需要消除 phi 指令

  ```c++
  B1:						B2:
      ...                     ...
  	x1 = a + b;             x2 = a - b;
  	...                     ...
  	
  B0:
      ...
  	x3 = PHI(x1, x2)
  	...
  ```

  修改为如下代码即可消除 phi 指令：

  ```c++
  B1:						B2:
      ...                     ...
  	x1 = a + b;             x2 = a - b;
  	x0 = x1; (COPY)         x0 = x2; (COPY)
  	...                     ...
  	
  B0:
      ...
  	x3 = x0;
  	...
  ```

* 二地址转换

  LLVM 中指令多是三地址的，而底层指令至多只有两个地址，因此需要进行二地址转换

  ```
  三地址指令:
    %a = ADD %b %c
  变为:
    %a = MOVE %b
    %a = ADD %a %c
  ```



### 寄存器分配（图染色）

**图染色方法（NP-Complete）**

**Reference：《现代编译原理-c语言描述》（虎书）**

#### [**构造（Build）**](https://zhuanlan.zhihu.com/p/55287942)

* 结点活跃期（Live Range）

  从变量第一次被定义（赋值）开始，到它下一次被赋值前的最后一次被使用为止

  两个结点之间的边表示这两个变量活跃期因为生命期（lifetime）重叠导致互相冲突或干涉

* 活跃分析（Liveness Analyzer）

  将程序分成许多block，之间由有向线段连接，代表程序的运行过程，之后对每个block做活跃分析

  对于每个结点（block），我们作如下规定：

  * use：结点中被使用的变量（即虚拟寄存器）
  * def：结点中被赋值的变量
  * in：输入结点的变量
  * out：结点输出的变量

  由数据流方程我们得到如下公式：
  $$
  in[n]=use[n] \cup (out[n] - def[n])
  $$
  以及
  $$
  out[n] = \bigcup_{s \in succ[n]} in[s]
  $$
  边界条件为
  $$
  in[exit] = \{\}
  $$
  代码如下：

  ```c++
  L1:
    a = b + c;
    d -= a;
    e = d + f;
    if (e > 0) f = 2*e;
    else {
      b = d + e;
      e = e – 1;
    } 
    b = f + c;
    goto L1
  ```

  控制流图如下：

  ![img](https://pic2.zhimg.com/80/v2-a337f55761e3d3bde27f253f57409bb5_720w.webp)

  活跃分析过程如下：（图中命名kill：def，gen：use）

  ![img](https://pic2.zhimg.com/80/v2-1768183217ed6a96783c95cab002cf65_720w.webp)

  活跃分析结果如下：

  ![img](https://pic1.zhimg.com/80/v2-aae2502cb436462da885f36f841c95a4_720w.webp)

* 寄存器干涉图（RIG，Register Interference Graph）

  寄存器干涉图是一种无向图，用于显示在特定程序点哪些变量是活跃的，哪些寄存器在分配时会互相冲突。

  如果在程序点P存在两个变量a和b同时活跃，那么就称变量a和b在程序点P互相干涉。这时，变量a和b应分配不同寄存器。换言之，如果在寄存器干涉图上，两个结点之间没有边相连，则可以分配到同一寄存器。

  构造步骤如下：

  - 为每个变量添加一个结点
  -  检查变量之间的干涉。如果存在干涉，增加一条边

  构造上述代码寄存器干涉图如下：

  ![img](https://pic2.zhimg.com/80/v2-d391ab5735c6988c357fa4e8478925f9_720w.webp)

  最后得到图染色结果如下：

  ![img](https://pic3.zhimg.com/80/v2-0027faa86a516ef08e3a06e8eff4c15a_720w.webp)

#### **简化（Simplify）**

简化图，删除度数（邻结点数）小于 K 的结点，K 为物理寄存器的数量。

原因是：假设图 G 有一个度数小于 K 的结点 m，那么如果图 G - {m} 可以用 K 色染色，那么图 G 也可以，这是因为在之前的染色中， m 的邻结点至多用了 K - 1 种颜色，于是找出一种颜色作为 m 的颜色

#### 溢出（Spill）



#### 合并（Coalesce）

之前准备工作中生成了大量的 MOVE 指令，如果指令的源结点与目标结点之间不存在边，那么可以合并这两个结点

但是注意：并不是所有符合条件的结点最后都要合并，因为有可能合并之后图从可染色变成了不可染色

## Optimization

* 

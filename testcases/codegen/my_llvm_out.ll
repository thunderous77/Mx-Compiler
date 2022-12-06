declare i8* @_bottom_malloc(i32)
declare i8* @_bottom_str_cat(i8*, i8*)
declare i1 @_bottom_str_eq(i8*, i8*)
declare i1 @_bottom_str_ne(i8*, i8*)
declare i1 @_bottom_str_slt(i8*, i8*)
declare i1 @_bottom_str_sle(i8*, i8*)
declare i1 @_bottom_str_sgt(i8*, i8*)
declare i1 @_bottom_str_sge(i8*, i8*)
declare void @print(i8*)
declare void @println(i8*)
declare void @printInt(i32)
declare void @printlnInt(i32)
declare i8* @getString()
declare i32 @getInt()
declare i8* @toString(i32)
declare i32 @_str_length(i8*)
declare i8* @_str_substring(i8*, i32, i32)
declare i32 @_str_parseInt(i8*)
declare i32 @_str_ord(i8*, i32)

@n = global i32 0, align 4
@p = global i32 0, align 4
@k = global i32 0, align 4
@i = global i32 0, align 4


@.strconst = private unnamed_addr constant [4 x i8]"<< \00", align 1
@.strconst.1 = private unnamed_addr constant [2 x i8]" \00", align 1
@.strconst.2 = private unnamed_addr constant [2 x i8]"(\00", align 1
@.strconst.3 = private unnamed_addr constant [3 x i8]") \00", align 1
@.strconst.4 = private unnamed_addr constant [4 x i8]">> \00", align 1

define i32 @main() {
entry.11:
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* @n, align 4
  %getInt.call.1 = call i32 @getInt()
  store i32 %getInt.call.1, i32* @p, align 4
  %getInt.call.2 = call i32 @getInt()
  store i32 %getInt.call.2, i32* @k, align 4
  %p.load = load i32, i32* @p
  %k.load = load i32, i32* @k
  %sub = sub i32 %p.load, %k.load
  %icmp = icmp sgt i32 %sub, 1
  br i1 %icmp, label %if.true, label %if.false

exit.11:
  ret i32 0

if.true:
  %getelementptr = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst, i32 0, i32 0
  call void @print(i8* %getelementptr)
  br label %if.exit

if.exit:
  %p.load.1 = load i32, i32* @p
  %k.load.1 = load i32, i32* @k
  %sub.1 = sub i32 %p.load.1, %k.load.1
  store i32 %sub.1, i32* @i, align 4
  br label %for.cond

if.false:
  br label %if.exit

for.cond:
  %i.load = load i32, i32* @i
  %p.load.2 = load i32, i32* @p
  %k.load.2 = load i32, i32* @k
  %add = add i32 %p.load.2, %k.load.2
  %icmp.1 = icmp sle i32 %i.load, %add
  br i1 %icmp.1, label %for.body, label %for.exit

for.incr:
  %i.load.1 = load i32, i32* @i
  %add.1 = add i32 %i.load.1, 1
  store i32 %add.1, i32* @i, align 4
  br label %for.cond

for.body:
  %i.load.2 = load i32, i32* @i
  %icmp.2 = icmp sle i32 1, %i.load.2
  br i1 %icmp.2, label %logic_and_continue, label %logic_and_exit

for.exit:
  %p.load.4 = load i32, i32* @p
  %k.load.3 = load i32, i32* @k
  %add.2 = add i32 %p.load.4, %k.load.3
  %n.load.1 = load i32, i32* @n
  %icmp.5 = icmp slt i32 %add.2, %n.load.1
  br i1 %icmp.5, label %if.true.3, label %if.false.3

if.true.1:
  %i.load.4 = load i32, i32* @i
  %p.load.3 = load i32, i32* @p
  %icmp.4 = icmp eq i32 %i.load.4, %p.load.3
  br i1 %icmp.4, label %if.true.2, label %if.false.2

if.exit.1:
  br label %for.incr

if.false.1:
  br label %if.exit.1

logic_and_continue:
  %i.load.3 = load i32, i32* @i
  %n.load = load i32, i32* @n
  %icmp.3 = icmp sle i32 %i.load.3, %n.load
  br label %logic_and_exit

logic_and_exit:
  %phi = phi i1 [%icmp.2, %for.body], [%icmp.3, %logic_and_continue]
  br i1 %phi, label %if.true.1, label %if.false.1

if.true.2:
  %getelementptr.2 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.2, i32 0, i32 0
  call void @print(i8* %getelementptr.2)
  %i.load.6 = load i32, i32* @i
  %toString.call = call i8* @toString(i32 %i.load.6)
  call void @print(i8* %toString.call)
  %getelementptr.3 = getelementptr inbounds [3 x i8], [3 x i8]* @.strconst.3, i32 0, i32 0
  call void @print(i8* %getelementptr.3)
  br label %if.exit.2

if.exit.2:
  br label %if.exit.1

if.false.2:
  %i.load.5 = load i32, i32* @i
  call void @printInt(i32 %i.load.5)
  %getelementptr.1 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.1, i32 0, i32 0
  call void @print(i8* %getelementptr.1)
  br label %if.exit.2

if.true.3:
  %getelementptr.4 = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst.4, i32 0, i32 0
  call void @print(i8* %getelementptr.4)
  br label %if.exit.3

if.exit.3:
  br label %exit.11

if.false.3:
  br label %if.exit.3
}

define void @global_var_init() {
entry.12:

exit.12:
  ret void
}


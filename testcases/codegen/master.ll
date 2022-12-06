; ModuleID = 'test'
source_filename = "test"
target datalayout = "e-m:e-p:32:32-i64:64-n32-S128"
target triple = "riscv32"

declare i8* @_bot_malloc(i32)
declare i8* @_bot_str_cat(i8*, i8*)
declare i1 @_bot_str_eq(i8*, i8*)
declare i1 @_bot_str_ne(i8*, i8*)
declare i1 @_bot_str_slt(i8*, i8*)
declare i1 @_bot_str_sle(i8*, i8*)
declare i1 @_bot_str_sgt(i8*, i8*)
declare i1 @_bot_str_sge(i8*, i8*)
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

@n = global i32 zeroinitializer, align 4
@a = global i32* zeroinitializer, align 4
@i = global i32 zeroinitializer, align 4

define i32 @main() {

entry.13:                                                ; preds =  | nexts = %for.cond.2 
  %_bot_malloc.call.1 = call i8* @_bot_malloc(i32 84)
  %bitcast.2 = bitcast i8* %_bot_malloc.call.1 to i32*
  store i32 20, i32* %bitcast.2, align 4
  %getelementptr.1 = getelementptr inbounds i32, i32* %bitcast.2, i32 1
  %bitcast.3 = bitcast i32* %getelementptr.1 to i32*
  store i32* %bitcast.3, i32** @a, align 4
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* @n, align 4
  move %phi.9, 0
  br label %for.cond.2

exit.13:                                                ; preds = %for.exit.3, %if.true.2 
  store i32 %phi.10, i32* @i, align 4
  ret i32 0

for.cond.2:                                                ; preds = %entry.13, %for.body.2  | nexts = %for.body.2, %for.exit.2 
  %phi.9 = phi i32 [0, %entry.13], [%add.6, %for.body.2]
  %n.load.2 = load i32, i32* @n, align 4
  %icmp.3 = icmp slt i32 %phi.9, %n.load.2
  br i1 %icmp.3, label %for.body.2, label %for.exit.2

for.body.2:                                                ; preds = %for.cond.2  | nexts = %for.cond.2 
  %a.load.2 = load i32*, i32** @a, align 4
  %a.load.2.elem.addr = getelementptr inbounds i32, i32* %a.load.2, i32 %phi.9
  %getInt.call.1 = call i32 @getInt()
  store i32 %getInt.call.1, i32* %a.load.2.elem.addr, align 4
  %add.6 = add i32 %phi.9, 1
  move %phi.9, %add.6
  br label %for.cond.2

for.exit.2:                                                ; preds = %for.cond.2  | nexts = %for.cond.3 
  %a.load.4 = load i32*, i32** @a, align 4
  move %phi.10, %n.load.2
  br label %for.cond.3

for.cond.3:                                                ; preds = %for.exit.2, %if.false.2  | nexts = %for.body.3, %for.exit.3 
  %phi.10 = phi i32 [%n.load.2, %for.exit.2], [%sdiv.1, %if.false.2]
  %icmp.4 = icmp sgt i32 %phi.10, 0
  br i1 %icmp.4, label %for.body.3, label %for.exit.3

for.body.3:                                                ; preds = %for.cond.3  | nexts = %for.cond.i 
  %sdiv.2 = sdiv i32 %n.load.2, %phi.10
  %sub.1 = sub i32 %phi.10, 1
  move %phi.15, null
  move %phi.16, null
  move %phi.17, 0
  move %phi.21, 0
  br label %for.cond.i

for.exit.3:                                                ; preds = %for.cond.3  | nexts = %exit.13 
  br label %exit.13

if.true.2:                                                ; preds = %exit.12.i  | nexts = %exit.13 
  call void @printInt(i32 %phi.10)
  br label %exit.13

if.false.2:                                                ; preds = %exit.12.i  | nexts = %for.cond.3 
  %sdiv.1 = sdiv i32 %phi.10, 2
  move %phi.10, %sdiv.1
  br label %for.cond.3

exit.12.i:                                                ; preds = %for.exit.i, %if.true.1.i  | nexts = %if.true.2, %if.false.2 
  %phi.12 = phi i8 [%phi.18, %if.true.1.i], [%phi.15, %for.exit.i]
  %phi.13 = phi i32 [%phi.19, %if.true.1.i], [%phi.16, %for.exit.i]
  %phi.14 = phi i32 [1, %if.true.1.i], [0, %for.exit.i]
  %icmp.5 = icmp sgt i32 %phi.14, 0
  br i1 %icmp.5, label %if.true.2, label %if.false.2

for.cond.i:                                                ; preds = %for.body.3, %if.false.1.i  | nexts = %for.body.i, %for.exit.i 
  %phi.15 = phi i8 [null, %for.body.3], [%phi.18, %if.false.1.i]
  %phi.16 = phi i32 [null, %for.body.3], [%phi.19, %if.false.1.i]
  %phi.17 = phi i32 [0, %for.body.3], [%add.7, %if.false.1.i]
  %phi.21 = phi i32 [0, %for.body.3], [%add.18, %if.false.1.i]
  %icmp.6 = icmp slt i32 %phi.17, %sdiv.2
  br i1 %icmp.6, label %for.body.i, label %for.exit.i

for.body.i:                                                ; preds = %for.cond.i  | nexts = %for.cond.1.i 
  move %phi.18, false
  move %phi.19, 0
  br label %for.cond.1.i

for.exit.i:                                                ; preds = %for.cond.i  | nexts = %exit.12.i 
  move %phi.12, %phi.15
  move %phi.13, %phi.16
  move %phi.14, 0
  br label %exit.12.i

for.cond.1.i:                                                ; preds = %for.body.i, %if.exit.i  | nexts = %for.body.1.i, %for.exit.1.i 
  %phi.18 = phi i8 [false, %for.body.i], [%phi.20, %if.exit.i]
  %phi.19 = phi i32 [0, %for.body.i], [%add.8, %if.exit.i]
  %icmp.7 = icmp slt i32 %phi.19, %sub.1
  br i1 %icmp.7, label %for.body.1.i, label %for.exit.1.i

for.body.1.i:                                                ; preds = %for.cond.1.i  | nexts = %if.true.i, %if.false.i 
  %add.9 = add i32 %phi.21, %phi.19
  %a.load.elem.addr.1 = getelementptr inbounds i32, i32* %a.load.4, i32 %add.9
  %a.load.elem.load.1 = load i32, i32* %a.load.elem.addr.1, align 4
  %add.11 = add i32 %add.9, 1
  %a.load.1.elem.addr.1 = getelementptr inbounds i32, i32* %a.load.4, i32 %add.11
  %a.load.1.elem.load.1 = load i32, i32* %a.load.1.elem.addr.1, align 4
  %icmp.8 = icmp sgt i32 %a.load.elem.load.1, %a.load.1.elem.load.1
  br i1 %icmp.8, label %if.true.i, label %if.false.i

for.exit.1.i:                                                ; preds = %for.cond.1.i  | nexts = %if.true.1.i, %if.false.1.i 
  %trunc.2 = trunc i8 %phi.18 to i1
  %xor.1 = xor i1 %trunc.2, true
  br i1 %xor.1, label %if.true.1.i, label %if.false.1.i

if.true.i:                                                ; preds = %for.body.1.i  | nexts = %if.exit.i 
  move %phi.20, true
  br label %if.exit.i

if.false.i:                                                ; preds = %for.body.1.i  | nexts = %if.exit.i 
  move %phi.20, %phi.18
  br label %if.exit.i

if.exit.i:                                                ; preds = %if.true.i, %if.false.i  | nexts = %for.cond.1.i 
  %phi.20 = phi i8 [true, %if.true.i], [%phi.18, %if.false.i]
  %add.8 = add i32 %phi.19, 1
  move %phi.18, %phi.20
  move %phi.19, %add.8
  br label %for.cond.1.i

if.true.1.i:                                                ; preds = %for.exit.1.i  | nexts = %exit.12.i 
  move %phi.12, %phi.18
  move %phi.13, %phi.19
  move %phi.14, 1
  br label %exit.12.i

if.false.1.i:                                                ; preds = %for.exit.1.i  | nexts = %for.cond.i 
  %add.18 = add i32 %phi.21, %phi.10
  %add.7 = add i32 %phi.17, 1
  move %phi.15, %phi.18
  move %phi.16, %phi.19
  move %phi.17, %add.7
  move %phi.21, %add.18
  br label %for.cond.i

}
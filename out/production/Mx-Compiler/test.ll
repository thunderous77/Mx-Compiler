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

@a = global i32* zeroinitializer, align 8


@.strconst = private unnamed_addr constant [1 x i8] c"\00", align 1

define void @global_var_init() {
entry:
  %mul = mul i32 4, 4
  %add = add i32 %mul, 4
  %_bottom_malloc.call = call noalias i8* @_bottom_malloc(i32 %add)
  %bitcast = bitcast i8* %_bottom_malloc.call to i32*
  store i32 4, i32* %bitcast, align 4
  %getelementptr = getelementptr inbounds i32, i32* %bitcast, i32 1
  %bitcast.1 = bitcast i32* %getelementptr to i32*
  store i32* %bitcast.1, i32** @a, align 8
  br label %exit

exit:
  ret void
}

define i32 @main() {
entry.12:
  call void @global_var_init()
  %mul.1 = mul i32 4, 8
  %add.1 = add i32 %mul.1, 4
  %_bottom_malloc.call.1 = call noalias i8* @_bottom_malloc(i32 %add.1)
  %bitcast.2 = bitcast i8* %_bottom_malloc.call.1 to i32*
  store i32 4, i32* %bitcast.2, align 4
  %getelementptr.1 = getelementptr inbounds i32, i32* %bitcast.2, i32 1
  %bitcast.3 = bitcast i32* %getelementptr.1 to i32**
  %b.load.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 0
  %b.load.element.load = load i32*, i32** %b.load.element.addr, align 8
  %a.load = load i32*, i32** @a, align 8
  store i32* %a.load, i32** %b.load.element.addr, align 8
  %b.load.1.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 1
  %b.load.1.element.load = load i32*, i32** %b.load.1.element.addr, align 8
  %a.load.1 = load i32*, i32** @a, align 8
  store i32* %a.load.1, i32** %b.load.1.element.addr, align 8
  %b.load.2.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 2
  %b.load.2.element.load = load i32*, i32** %b.load.2.element.addr, align 8
  %a.load.2 = load i32*, i32** @a, align 8
  store i32* %a.load.2, i32** %b.load.2.element.addr, align 8
  %b.load.3.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 3
  %b.load.3.element.load = load i32*, i32** %b.load.3.element.addr, align 8
  %a.load.3 = load i32*, i32** @a, align 8
  store i32* %a.load.3, i32** %b.load.3.element.addr, align 8
  %bitcast.4 = bitcast i32** %bitcast.3 to i32*
  %getelementptr.2 = getelementptr inbounds i32, i32* %bitcast.4, i32 -1
  %getelementptr.2.load = load i32, i32* %getelementptr.2, align 4
  %toString.call = call i8* @toString(i32 %getelementptr.2.load)
  call void @println(i8* %toString.call)
  move %phi, 0
  br label %for.cond

exit.12:
  ret i32 0

for.cond:
  %phi = phi i32 [0, %entry.12], [%add.2, %for.incr]
  %b.load.5.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 0
  %b.load.5.element.load = load i32*, i32** %b.load.5.element.addr, align 8
  %bitcast.5 = bitcast i32* %b.load.5.element.load to i32*
  %getelementptr.3 = getelementptr inbounds i32, i32* %bitcast.5, i32 -1
  %getelementptr.3.load = load i32, i32* %getelementptr.3, align 4
  %icmp = icmp slt i32 %phi, %getelementptr.3.load
  br i1 %icmp, label %for.body, label %for.exit

for.incr:
  %add.2 = add i32 %phi, 1
  move %phi, %add.2
  br label %for.cond

for.body:
  %b.load.6.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 0
  %b.load.6.element.load = load i32*, i32** %b.load.6.element.addr, align 8
  %b.load.6.element.load.element.addr = getelementptr inbounds i32, i32* %b.load.6.element.load, i32 %phi
  %b.load.6.element.load.element.load = load i32, i32* %b.load.6.element.load.element.addr, align 4
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* %b.load.6.element.load.element.addr, align 4
  br label %for.incr

for.exit:
  move %phi.1, 0
  br label %for.cond.1

for.cond.1:
  %phi.1 = phi i32 [0, %for.exit], [%add.3, %for.incr.1]
  %b.load.7.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 1
  %b.load.7.element.load = load i32*, i32** %b.load.7.element.addr, align 8
  %bitcast.6 = bitcast i32* %b.load.7.element.load to i32*
  %getelementptr.4 = getelementptr inbounds i32, i32* %bitcast.6, i32 -1
  %getelementptr.4.load = load i32, i32* %getelementptr.4, align 4
  %icmp.1 = icmp slt i32 %phi.1, %getelementptr.4.load
  br i1 %icmp.1, label %for.body.1, label %for.exit.1

for.incr.1:
  %add.3 = add i32 %phi.1, 1
  move %phi.1, %add.3
  br label %for.cond.1

for.body.1:
  %b.load.8.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 1
  %b.load.8.element.load = load i32*, i32** %b.load.8.element.addr, align 8
  %b.load.8.element.load.element.addr = getelementptr inbounds i32, i32* %b.load.8.element.load, i32 %phi.1
  %b.load.8.element.load.element.load = load i32, i32* %b.load.8.element.load.element.addr, align 4
  %toString.call.1 = call i8* @toString(i32 %b.load.8.element.load.element.load)
  call void @print(i8* %toString.call.1)
  br label %for.incr.1

for.exit.1:
  %getelementptr.5 = getelementptr inbounds [1 x i8], [1 x i8]* @.strconst, i32 0, i32 0
  call void @println(i8* %getelementptr.5)
  move %phi.2, 0
  br label %for.cond.2

for.cond.2:
  %phi.2 = phi i32 [0, %for.exit.1], [%add.4, %for.incr.2]
  %b.load.9.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 2
  %b.load.9.element.load = load i32*, i32** %b.load.9.element.addr, align 8
  %bitcast.7 = bitcast i32* %b.load.9.element.load to i32*
  %getelementptr.6 = getelementptr inbounds i32, i32* %bitcast.7, i32 -1
  %getelementptr.6.load = load i32, i32* %getelementptr.6, align 4
  %icmp.2 = icmp slt i32 %phi.2, %getelementptr.6.load
  br i1 %icmp.2, label %for.body.2, label %for.exit.2

for.incr.2:
  %add.4 = add i32 %phi.2, 1
  move %phi.2, %add.4
  br label %for.cond.2

for.body.2:
  %b.load.10.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 2
  %b.load.10.element.load = load i32*, i32** %b.load.10.element.addr, align 8
  %b.load.10.element.load.element.addr = getelementptr inbounds i32, i32* %b.load.10.element.load, i32 %phi.2
  %b.load.10.element.load.element.load = load i32, i32* %b.load.10.element.load.element.addr, align 4
  store i32 0, i32* %b.load.10.element.load.element.addr, align 4
  br label %for.incr.2

for.exit.2:
  move %phi.3, 0
  br label %for.cond.3

for.cond.3:
  %phi.3 = phi i32 [0, %for.exit.2], [%add.5, %for.incr.3]
  %b.load.11.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 3
  %b.load.11.element.load = load i32*, i32** %b.load.11.element.addr, align 8
  %bitcast.8 = bitcast i32* %b.load.11.element.load to i32*
  %getelementptr.7 = getelementptr inbounds i32, i32* %bitcast.8, i32 -1
  %getelementptr.7.load = load i32, i32* %getelementptr.7, align 4
  %icmp.3 = icmp slt i32 %phi.3, %getelementptr.7.load
  br i1 %icmp.3, label %for.body.3, label %for.exit.3

for.incr.3:
  %add.5 = add i32 %phi.3, 1
  move %phi.3, %add.5
  br label %for.cond.3

for.body.3:
  %b.load.12.element.addr = getelementptr inbounds i32*, i32** %bitcast.3, i32 3
  %b.load.12.element.load = load i32*, i32** %b.load.12.element.addr, align 8
  %b.load.12.element.load.element.addr = getelementptr inbounds i32, i32* %b.load.12.element.load, i32 %phi.3
  %b.load.12.element.load.element.load = load i32, i32* %b.load.12.element.load.element.addr, align 4
  %toString.call.2 = call i8* @toString(i32 %b.load.12.element.load.element.load)
  call void @print(i8* %toString.call.2)
  br label %for.incr.3

for.exit.3:
  br label %exit.12
}


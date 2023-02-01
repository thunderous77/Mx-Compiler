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

define void @_glb_init() {

entry.11:                                                ; preds = 
  %mul = mul i32 20, 4
  %add = add i32 %mul, 4
  %_bot_malloc.call = call noalias i8* @_bot_malloc(i32 %add)
  %bitcast = bitcast i8* %_bot_malloc.call to i32*
  store i32 20, i32* %bitcast, align 4
  %getelementptr = getelementptr inbounds i32, i32* %bitcast, i32 1
  %bitcast.1 = bitcast i32* %getelementptr to i32*
  store i32* %bitcast.1, i32** @a, align 4
  br label %exit.11

exit.11:                                                ; preds = 
  ret void

}

define i32 @jud(i32 %x) {

entry.12:                                                ; preds = 
  %flag.addr = alloca i8, align 1
  %j.addr = alloca i32, align 4
  %i.addr = alloca i32, align 4
  %x.addr = alloca i32, align 4
  %internal.ret.addr = alloca i32, align 4
  store i32 %x, i32* %x.addr, align 4
  %i.load = load i32, i32* %i.addr, align 4
  store i32 0, i32* %i.addr, align 4
  br label %for.cond

exit.12:                                                ; preds = 
  %internal.ret.load = load i32, i32* %internal.ret.addr, align 4
  ret i32 %internal.ret.load

for.cond:                                                ; preds = 
  %i.load.1 = load i32, i32* %i.addr, align 4
  %n.load = load i32, i32* @n, align 4
  %x.load = load i32, i32* %x.addr, align 4
  %sdiv = sdiv i32 %n.load, %x.load
  %icmp = icmp slt i32 %i.load.1, %sdiv
  br i1 %icmp, label %for.body, label %for.exit

for.incr:                                                ; preds = 
  %i.load.2 = load i32, i32* %i.addr, align 4
  %add.1 = add i32 %i.load.2, 1
  store i32 %add.1, i32* %i.addr, align 4
  br label %for.cond

for.body:                                                ; preds = 
  %zext = zext i1 false to i8
  store i8 %zext, i8* %flag.addr, align 1
  %j.load = load i32, i32* %j.addr, align 4
  store i32 0, i32* %j.addr, align 4
  br label %for.cond.1

for.exit:                                                ; preds = 
  store i32 0, i32* %internal.ret.addr, align 4
  br label %exit.12

for.cond.1:                                                ; preds = 
  %j.load.1 = load i32, i32* %j.addr, align 4
  %x.load.1 = load i32, i32* %x.addr, align 4
  %sub = sub i32 %x.load.1, 1
  %icmp.1 = icmp slt i32 %j.load.1, %sub
  br i1 %icmp.1, label %for.body.1, label %for.exit.1

for.incr.1:                                                ; preds = 
  %j.load.2 = load i32, i32* %j.addr, align 4
  %add.2 = add i32 %j.load.2, 1
  store i32 %add.2, i32* %j.addr, align 4
  br label %for.cond.1

for.body.1:                                                ; preds = 
  %a.load = load i32*, i32** @a, align 4
  %i.load.3 = load i32, i32* %i.addr, align 4
  %x.load.2 = load i32, i32* %x.addr, align 4
  %mul.1 = mul i32 %i.load.3, %x.load.2
  %j.load.3 = load i32, i32* %j.addr, align 4
  %add.3 = add i32 %mul.1, %j.load.3
  %a.load.elem.addr = getelementptr inbounds i32, i32* %a.load, i32 %add.3
  %a.load.elem.load = load i32, i32* %a.load.elem.addr, align 4
  %a.load.1 = load i32*, i32** @a, align 4
  %i.load.4 = load i32, i32* %i.addr, align 4
  %x.load.3 = load i32, i32* %x.addr, align 4
  %mul.2 = mul i32 %i.load.4, %x.load.3
  %j.load.4 = load i32, i32* %j.addr, align 4
  %add.4 = add i32 %mul.2, %j.load.4
  %add.5 = add i32 %add.4, 1
  %a.load.1.elem.addr = getelementptr inbounds i32, i32* %a.load.1, i32 %add.5
  %a.load.1.elem.load = load i32, i32* %a.load.1.elem.addr, align 4
  %icmp.2 = icmp sgt i32 %a.load.elem.load, %a.load.1.elem.load
  br i1 %icmp.2, label %if.true, label %if.false

for.exit.1:                                                ; preds = 
  %flag.load.1 = load i8, i8* %flag.addr, align 1
  %trunc.1 = trunc i8 %flag.load.1 to i1
  %xor = xor i1 %trunc.1, true
  br i1 %xor, label %if.true.1, label %if.false.1

if.true:                                                ; preds = 
  %flag.load = load i8, i8* %flag.addr, align 1
  %trunc = trunc i8 %flag.load to i1
  %zext.1 = zext i1 true to i8
  store i8 %zext.1, i8* %flag.addr, align 1
  br label %if.exit

if.false:                                                ; preds = 
  br label %if.exit

if.exit:                                                ; preds = 
  br label %for.incr.1

if.true.1:                                                ; preds = 
  store i32 1, i32* %internal.ret.addr, align 4
  br label %exit.12

if.false.1:                                                ; preds = 
  br label %if.exit.1

if.exit.1:                                                ; preds = 
  br label %for.incr

}

define i32 @main() {

entry.13:                                                ; preds = 
  %internal.ret.addr.1 = alloca i32, align 4
  call void @_glb_init()
  store i32 0, i32* %internal.ret.addr.1, align 4
  %n.load.1 = load i32, i32* @n, align 4
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* @n, align 4
  %i.load.5 = load i32, i32* @i, align 4
  store i32 0, i32* @i, align 4
  br label %for.cond.2

exit.13:                                                ; preds = 
  %internal.ret.load.1 = load i32, i32* %internal.ret.addr.1, align 4
  ret i32 %internal.ret.load.1

for.cond.2:                                                ; preds = 
  %i.load.6 = load i32, i32* @i, align 4
  %n.load.2 = load i32, i32* @n, align 4
  %icmp.3 = icmp slt i32 %i.load.6, %n.load.2
  br i1 %icmp.3, label %for.body.2, label %for.exit.2

for.incr.2:                                                ; preds = 
  %i.load.7 = load i32, i32* @i, align 4
  %add.6 = add i32 %i.load.7, 1
  store i32 %add.6, i32* @i, align 4
  br label %for.cond.2

for.body.2:                                                ; preds = 
  %a.load.2 = load i32*, i32** @a, align 4
  %i.load.8 = load i32, i32* @i, align 4
  %a.load.2.elem.addr = getelementptr inbounds i32, i32* %a.load.2, i32 %i.load.8
  %a.load.2.elem.load = load i32, i32* %a.load.2.elem.addr, align 4
  %getInt.call.1 = call i32 @getInt()
  store i32 %getInt.call.1, i32* %a.load.2.elem.addr, align 4
  br label %for.incr.2

for.exit.2:                                                ; preds = 
  %i.load.9 = load i32, i32* @i, align 4
  %n.load.3 = load i32, i32* @n, align 4
  store i32 %n.load.3, i32* @i, align 4
  br label %for.cond.3

for.cond.3:                                                ; preds = 
  %i.load.10 = load i32, i32* @i, align 4
  %icmp.4 = icmp sgt i32 %i.load.10, 0
  br i1 %icmp.4, label %for.body.3, label %for.exit.3

for.incr.3:                                                ; preds = 
  %i.load.11 = load i32, i32* @i, align 4
  %i.load.12 = load i32, i32* @i, align 4
  %sdiv.1 = sdiv i32 %i.load.12, 2
  store i32 %sdiv.1, i32* @i, align 4
  br label %for.cond.3

for.body.3:                                                ; preds = 
  %i.load.13 = load i32, i32* @i, align 4
  %jud.call = call i32 @jud(i32 %i.load.13)
  %icmp.5 = icmp sgt i32 %jud.call, 0
  br i1 %icmp.5, label %if.true.2, label %if.false.2

for.exit.3:                                                ; preds = 
  store i32 0, i32* %internal.ret.addr.1, align 4
  br label %exit.13

if.true.2:                                                ; preds = 
  %i.load.14 = load i32, i32* @i, align 4
  %toString.call = call i8* @toString(i32 %i.load.14)
  call void @print(i8* %toString.call)
  store i32 0, i32* %internal.ret.addr.1, align 4
  br label %exit.13

if.false.2:                                                ; preds = 
  br label %if.exit.2

if.exit.2:                                                ; preds = 
  br label %for.incr.3

}
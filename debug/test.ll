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

@n = global i32 zeroinitializer, align 4
@m = global i32 zeroinitializer, align 4
@ans = global i32 zeroinitializer, align 4
@fa = global i32* zeroinitializer, align 8
@rk = global i32* zeroinitializer, align 8
@e = global %class.Edge** zeroinitializer, align 8


%class.Edge = type {i32, i32, i32}

define void @Edge.Edge(%class.Edge* %this) {
entry.12:
  br label %exit.12

exit.12:
  ret void
}

define void @qsort(%class.Edge** %e.1, i32 %l, i32 %r) {
entry.13:
  %x.addr.1 = alloca %class.Edge*, align 8
  %j.addr = alloca i32, align 4
  %i.addr = alloca i32, align 4
  %r.addr = alloca i32, align 4
  %l.addr = alloca i32, align 4
  %e.addr = alloca %class.Edge**, align 8
  store %class.Edge** %e.1, %class.Edge*** %e.addr, align 8
  store i32 %l, i32* %l.addr, align 4
  store i32 %r, i32* %r.addr, align 4
  %l.load = load i32, i32* %l.addr, align 4
  %r.load = load i32, i32* %r.addr, align 4
  %icmp = icmp slt i32 %l.load, %r.load
  br i1 %icmp, label %if.true, label %if.false

exit.13:
  ret void

if.true:
  %l.load.1 = load i32, i32* %l.addr, align 4
  store i32 %l.load.1, i32* %i.addr, align 4
  %r.load.1 = load i32, i32* %r.addr, align 4
  store i32 %r.load.1, i32* %j.addr, align 4
  %e.load = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %l.load.2 = load i32, i32* %l.addr, align 4
  %e.load.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load, i32 %l.load.2
  %e.load.element.load = load %class.Edge*, %class.Edge** %e.load.element.addr, align 8
  store %class.Edge* %e.load.element.load, %class.Edge** %x.addr.1, align 8
  br label %while.cond

if.exit:
  br label %exit.13

if.false:
  br label %if.exit

while.cond:
  %i.load = load i32, i32* %i.addr, align 4
  %j.load = load i32, i32* %j.addr, align 4
  %icmp.1 = icmp slt i32 %i.load, %j.load
  br i1 %icmp.1, label %while.body, label %while.exit

while.body:
  br label %while.cond.1

while.exit:
  %e.load.7 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %i.load.10 = load i32, i32* %i.addr, align 4
  %e.load.7.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.7, i32 %i.load.10
  %e.load.7.element.load = load %class.Edge*, %class.Edge** %e.load.7.element.addr, align 8
  %x.load.2 = load %class.Edge*, %class.Edge** %x.addr.1, align 8
  store %class.Edge* %x.load.2, %class.Edge** %e.load.7.element.addr, align 8
  %e.load.8 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %l.load.3 = load i32, i32* %l.addr, align 4
  %i.load.11 = load i32, i32* %i.addr, align 4
  %sub.2 = sub i32 %i.load.11, 1
  call void @qsort(%class.Edge** %e.load.8, i32 %l.load.3, i32 %sub.2)
  %e.load.9 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %i.load.12 = load i32, i32* %i.addr, align 4
  %add.2 = add i32 %i.load.12, 1
  %r.load.2 = load i32, i32* %r.addr, align 4
  call void @qsort(%class.Edge** %e.load.9, i32 %add.2, i32 %r.load.2)
  br label %if.exit

while.cond.1:
  %i.load.1 = load i32, i32* %i.addr, align 4
  %j.load.1 = load i32, i32* %j.addr, align 4
  %icmp.2 = icmp slt i32 %i.load.1, %j.load.1
  br i1 %icmp.2, label %logic_and_continue, label %logic_and_exit

while.body.1:
  %j.load.3 = load i32, i32* %j.addr, align 4
  %sub = sub i32 %j.load.3, 1
  store i32 %sub, i32* %j.addr, align 4
  br label %while.cond.1

while.exit.1:
  %i.load.2 = load i32, i32* %i.addr, align 4
  %j.load.4 = load i32, i32* %j.addr, align 4
  %icmp.4 = icmp slt i32 %i.load.2, %j.load.4
  br i1 %icmp.4, label %if.true.1, label %if.false.1

logic_and_continue:
  %e.load.1 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %j.load.2 = load i32, i32* %j.addr, align 4
  %e.load.1.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.1, i32 %j.load.2
  %e.load.1.element.load = load %class.Edge*, %class.Edge** %e.load.1.element.addr, align 8
  %w.addr.1 = getelementptr inbounds %class.Edge, %class.Edge* %e.load.1.element.load, i32 0, i32 2
  %w.load = load i32, i32* %w.addr.1, align 4
  %x.load = load %class.Edge*, %class.Edge** %x.addr.1, align 8
  %w.addr.2 = getelementptr inbounds %class.Edge, %class.Edge* %x.load, i32 0, i32 2
  %w.load.1 = load i32, i32* %w.addr.2, align 4
  %icmp.3 = icmp sge i32 %w.load, %w.load.1
  br label %logic_and_exit

logic_and_exit:
  %phi = phi i1 [%icmp.2, %while.cond.1], [%icmp.3, %logic_and_continue]
  br i1 %phi, label %while.body.1, label %while.exit.1

if.true.1:
  %e.load.2 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %i.load.3 = load i32, i32* %i.addr, align 4
  %e.load.2.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.2, i32 %i.load.3
  %e.load.2.element.load = load %class.Edge*, %class.Edge** %e.load.2.element.addr, align 8
  %e.load.3 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %j.load.5 = load i32, i32* %j.addr, align 4
  %e.load.3.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.3, i32 %j.load.5
  %e.load.3.element.load = load %class.Edge*, %class.Edge** %e.load.3.element.addr, align 8
  store %class.Edge* %e.load.3.element.load, %class.Edge** %e.load.2.element.addr, align 8
  %i.load.4 = load i32, i32* %i.addr, align 4
  %add = add i32 %i.load.4, 1
  store i32 %add, i32* %i.addr, align 4
  br label %if.exit.1

if.exit.1:
  br label %while.cond.2

if.false.1:
  br label %if.exit.1

while.cond.2:
  %i.load.5 = load i32, i32* %i.addr, align 4
  %j.load.6 = load i32, i32* %j.addr, align 4
  %icmp.5 = icmp slt i32 %i.load.5, %j.load.6
  br i1 %icmp.5, label %logic_and_continue.1, label %logic_and_exit.1

while.body.2:
  %i.load.7 = load i32, i32* %i.addr, align 4
  %add.1 = add i32 %i.load.7, 1
  store i32 %add.1, i32* %i.addr, align 4
  br label %while.cond.2

while.exit.2:
  %i.load.8 = load i32, i32* %i.addr, align 4
  %j.load.7 = load i32, i32* %j.addr, align 4
  %icmp.7 = icmp slt i32 %i.load.8, %j.load.7
  br i1 %icmp.7, label %if.true.2, label %if.false.2

logic_and_continue.1:
  %e.load.4 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %i.load.6 = load i32, i32* %i.addr, align 4
  %e.load.4.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.4, i32 %i.load.6
  %e.load.4.element.load = load %class.Edge*, %class.Edge** %e.load.4.element.addr, align 8
  %w.addr.3 = getelementptr inbounds %class.Edge, %class.Edge* %e.load.4.element.load, i32 0, i32 2
  %w.load.2 = load i32, i32* %w.addr.3, align 4
  %x.load.1 = load %class.Edge*, %class.Edge** %x.addr.1, align 8
  %w.addr.4 = getelementptr inbounds %class.Edge, %class.Edge* %x.load.1, i32 0, i32 2
  %w.load.3 = load i32, i32* %w.addr.4, align 4
  %icmp.6 = icmp slt i32 %w.load.2, %w.load.3
  br label %logic_and_exit.1

logic_and_exit.1:
  %phi.1 = phi i1 [%icmp.5, %while.cond.2], [%icmp.6, %logic_and_continue.1]
  br i1 %phi.1, label %while.body.2, label %while.exit.2

if.true.2:
  %e.load.5 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %j.load.8 = load i32, i32* %j.addr, align 4
  %e.load.5.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.5, i32 %j.load.8
  %e.load.5.element.load = load %class.Edge*, %class.Edge** %e.load.5.element.addr, align 8
  %e.load.6 = load %class.Edge**, %class.Edge*** %e.addr, align 8
  %i.load.9 = load i32, i32* %i.addr, align 4
  %e.load.6.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.6, i32 %i.load.9
  %e.load.6.element.load = load %class.Edge*, %class.Edge** %e.load.6.element.addr, align 8
  store %class.Edge* %e.load.6.element.load, %class.Edge** %e.load.5.element.addr, align 8
  %j.load.9 = load i32, i32* %j.addr, align 4
  %sub.1 = sub i32 %j.load.9, 1
  store i32 %sub.1, i32* %j.addr, align 4
  br label %if.exit.2

if.exit.2:
  br label %while.cond

if.false.2:
  br label %if.exit.2
}

define void @init() {
entry.14:
  %i.addr.1 = alloca i32, align 4
  %n.load = load i32, i32* @n, align 4
  %add.3 = add i32 %n.load, 1
  %mul = mul i32 %add.3, 4
  %add.4 = add i32 %mul, 4
  %_bottom_malloc.call = call noalias i8* @_bottom_malloc(i32 %add.4)
  %bitcast = bitcast i8* %_bottom_malloc.call to i32*
  store i32 %add.3, i32* %bitcast, align 4
  %getelementptr = getelementptr inbounds i32, i32* %bitcast, i32 1
  %bitcast.1 = bitcast i32* %getelementptr to i32*
  store i32* %bitcast.1, i32** @fa, align 8
  %n.load.1 = load i32, i32* @n, align 4
  %add.5 = add i32 %n.load.1, 1
  %mul.1 = mul i32 %add.5, 4
  %add.6 = add i32 %mul.1, 4
  %_bottom_malloc.call.1 = call noalias i8* @_bottom_malloc(i32 %add.6)
  %bitcast.2 = bitcast i8* %_bottom_malloc.call.1 to i32*
  store i32 %add.5, i32* %bitcast.2, align 4
  %getelementptr.1 = getelementptr inbounds i32, i32* %bitcast.2, i32 1
  %bitcast.3 = bitcast i32* %getelementptr.1 to i32*
  store i32* %bitcast.3, i32** @rk, align 8
  store i32 1, i32* %i.addr.1, align 4
  br label %for.cond

exit.14:
  ret void

for.cond:
  %i.load.13 = load i32, i32* %i.addr.1, align 4
  %n.load.2 = load i32, i32* @n, align 4
  %icmp.8 = icmp sle i32 %i.load.13, %n.load.2
  br i1 %icmp.8, label %for.body, label %for.exit

for.incr:
  %i.load.14 = load i32, i32* %i.addr.1, align 4
  %add.7 = add i32 %i.load.14, 1
  store i32 %add.7, i32* %i.addr.1, align 4
  br label %for.cond

for.body:
  %fa.load = load i32*, i32** @fa, align 8
  %i.load.15 = load i32, i32* %i.addr.1, align 4
  %fa.load.element.addr = getelementptr inbounds i32, i32* %fa.load, i32 %i.load.15
  %fa.load.element.load = load i32, i32* %fa.load.element.addr, align 4
  %i.load.16 = load i32, i32* %i.addr.1, align 4
  store i32 %i.load.16, i32* %fa.load.element.addr, align 4
  %rk.load = load i32*, i32** @rk, align 8
  %i.load.17 = load i32, i32* %i.addr.1, align 4
  %rk.load.element.addr = getelementptr inbounds i32, i32* %rk.load, i32 %i.load.17
  %rk.load.element.load = load i32, i32* %rk.load.element.addr, align 4
  store i32 1, i32* %rk.load.element.addr, align 4
  br label %for.incr

for.exit:
  br label %exit.14
}

define i32 @find(i32 %x) {
entry.15:
  %x.addr.2 = alloca i32, align 4
  %func.ret.addr = alloca i32, align 4
  store i32 %x, i32* %x.addr.2, align 4
  %x.load.3 = load i32, i32* %x.addr.2, align 4
  %fa.load.1 = load i32*, i32** @fa, align 8
  %x.load.4 = load i32, i32* %x.addr.2, align 4
  %fa.load.1.element.addr = getelementptr inbounds i32, i32* %fa.load.1, i32 %x.load.4
  %fa.load.1.element.load = load i32, i32* %fa.load.1.element.addr, align 4
  %icmp.9 = icmp eq i32 %x.load.3, %fa.load.1.element.load
  br i1 %icmp.9, label %if.true.3, label %if.false.3

exit.15:
  %func.ret.load = load i32, i32* %func.ret.addr, align 4
  ret i32 %func.ret.load

if.true.3:
  %x.load.5 = load i32, i32* %x.addr.2, align 4
  store i32 %x.load.5, i32* %func.ret.addr, align 4
  br label %exit.15

if.exit.3:
  %fa.load.2 = load i32*, i32** @fa, align 8
  %x.load.6 = load i32, i32* %x.addr.2, align 4
  %fa.load.2.element.addr = getelementptr inbounds i32, i32* %fa.load.2, i32 %x.load.6
  %fa.load.2.element.load = load i32, i32* %fa.load.2.element.addr, align 4
  %find.call = call i32 @find(i32 %fa.load.2.element.load)
  store i32 %find.call, i32* %x.addr.2, align 4
  %fa.load.3 = load i32*, i32** @fa, align 8
  %x.load.7 = load i32, i32* %x.addr.2, align 4
  %fa.load.3.element.addr = getelementptr inbounds i32, i32* %fa.load.3, i32 %x.load.7
  %fa.load.3.element.load = load i32, i32* %fa.load.3.element.addr, align 4
  store i32 %fa.load.3.element.load, i32* %func.ret.addr, align 4
  br label %exit.15

if.false.3:
  br label %if.exit.3
}

define i1 @union(i32 %x.1, i32 %y) {
entry.16:
  %y.addr.1 = alloca i32, align 4
  %x.addr.3 = alloca i32, align 4
  %func.ret.addr.1 = alloca i8, align 1
  store i32 %x.1, i32* %x.addr.3, align 4
  store i32 %y, i32* %y.addr.1, align 4
  %x.load.8 = load i32, i32* %x.addr.3, align 4
  %find.call.1 = call i32 @find(i32 %x.load.8)
  store i32 %find.call.1, i32* %x.addr.3, align 4
  %y.load = load i32, i32* %y.addr.1, align 4
  %find.call.2 = call i32 @find(i32 %y.load)
  store i32 %find.call.2, i32* %y.addr.1, align 4
  %x.load.9 = load i32, i32* %x.addr.3, align 4
  %y.load.1 = load i32, i32* %y.addr.1, align 4
  %icmp.10 = icmp eq i32 %x.load.9, %y.load.1
  br i1 %icmp.10, label %if.true.4, label %if.false.4

exit.16:
  %func.ret.load.1 = load i8, i8* %func.ret.addr.1, align 1
  %trunc = trunc i8 %func.ret.load.1 to i1
  ret i1 %trunc

if.true.4:
  %zext = zext i1 false to i8
  store i8 %zext, i8* %func.ret.addr.1, align 1
  br label %exit.16

if.exit.4:
  %rk.load.1 = load i32*, i32** @rk, align 8
  %x.load.10 = load i32, i32* %x.addr.3, align 4
  %rk.load.1.element.addr = getelementptr inbounds i32, i32* %rk.load.1, i32 %x.load.10
  %rk.load.1.element.load = load i32, i32* %rk.load.1.element.addr, align 4
  %rk.load.2 = load i32*, i32** @rk, align 8
  %y.load.2 = load i32, i32* %y.addr.1, align 4
  %rk.load.2.element.addr = getelementptr inbounds i32, i32* %rk.load.2, i32 %y.load.2
  %rk.load.2.element.load = load i32, i32* %rk.load.2.element.addr, align 4
  %icmp.11 = icmp sgt i32 %rk.load.1.element.load, %rk.load.2.element.load
  br i1 %icmp.11, label %if.true.5, label %if.false.5

if.false.4:
  br label %if.exit.4

if.true.5:
  %fa.load.5 = load i32*, i32** @fa, align 8
  %y.load.6 = load i32, i32* %y.addr.1, align 4
  %fa.load.5.element.addr = getelementptr inbounds i32, i32* %fa.load.5, i32 %y.load.6
  %fa.load.5.element.load = load i32, i32* %fa.load.5.element.addr, align 4
  %x.load.13 = load i32, i32* %x.addr.3, align 4
  store i32 %x.load.13, i32* %fa.load.5.element.addr, align 4
  %rk.load.6 = load i32*, i32** @rk, align 8
  %x.load.14 = load i32, i32* %x.addr.3, align 4
  %rk.load.6.element.addr = getelementptr inbounds i32, i32* %rk.load.6, i32 %x.load.14
  %rk.load.6.element.load = load i32, i32* %rk.load.6.element.addr, align 4
  %rk.load.7 = load i32*, i32** @rk, align 8
  %x.load.15 = load i32, i32* %x.addr.3, align 4
  %rk.load.7.element.addr = getelementptr inbounds i32, i32* %rk.load.7, i32 %x.load.15
  %rk.load.7.element.load = load i32, i32* %rk.load.7.element.addr, align 4
  %rk.load.8 = load i32*, i32** @rk, align 8
  %y.load.7 = load i32, i32* %y.addr.1, align 4
  %rk.load.8.element.addr = getelementptr inbounds i32, i32* %rk.load.8, i32 %y.load.7
  %rk.load.8.element.load = load i32, i32* %rk.load.8.element.addr, align 4
  %add.9 = add i32 %rk.load.7.element.load, %rk.load.8.element.load
  store i32 %add.9, i32* %rk.load.6.element.addr, align 4
  br label %if.exit.5

if.exit.5:
  %zext.1 = zext i1 true to i8
  store i8 %zext.1, i8* %func.ret.addr.1, align 1
  br label %exit.16

if.false.5:
  %fa.load.4 = load i32*, i32** @fa, align 8
  %x.load.11 = load i32, i32* %x.addr.3, align 4
  %fa.load.4.element.addr = getelementptr inbounds i32, i32* %fa.load.4, i32 %x.load.11
  %fa.load.4.element.load = load i32, i32* %fa.load.4.element.addr, align 4
  %y.load.3 = load i32, i32* %y.addr.1, align 4
  store i32 %y.load.3, i32* %fa.load.4.element.addr, align 4
  %rk.load.3 = load i32*, i32** @rk, align 8
  %y.load.4 = load i32, i32* %y.addr.1, align 4
  %rk.load.3.element.addr = getelementptr inbounds i32, i32* %rk.load.3, i32 %y.load.4
  %rk.load.3.element.load = load i32, i32* %rk.load.3.element.addr, align 4
  %rk.load.4 = load i32*, i32** @rk, align 8
  %y.load.5 = load i32, i32* %y.addr.1, align 4
  %rk.load.4.element.addr = getelementptr inbounds i32, i32* %rk.load.4, i32 %y.load.5
  %rk.load.4.element.load = load i32, i32* %rk.load.4.element.addr, align 4
  %rk.load.5 = load i32*, i32** @rk, align 8
  %x.load.12 = load i32, i32* %x.addr.3, align 4
  %rk.load.5.element.addr = getelementptr inbounds i32, i32* %rk.load.5, i32 %x.load.12
  %rk.load.5.element.load = load i32, i32* %rk.load.5.element.addr, align 4
  %add.8 = add i32 %rk.load.4.element.load, %rk.load.5.element.load
  store i32 %add.8, i32* %rk.load.3.element.addr, align 4
  br label %if.exit.5
}

define i32 @main() {
entry.17:
  %ed.addr.1 = alloca %class.Edge*, align 8
  %j.addr.1 = alloca i32, align 4
  %ed.addr = alloca %class.Edge*, align 8
  %i.addr.2 = alloca i32, align 4
  %func.ret.addr.2 = alloca i32, align 4
  call void @global_var_init()
  store i32 0, i32* %func.ret.addr.2, align 4
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* @n, align 4
  %getInt.call.1 = call i32 @getInt()
  store i32 %getInt.call.1, i32* @m, align 4
  %m.load = load i32, i32* @m, align 4
  %mul.2 = mul i32 %m.load, 8
  %add.10 = add i32 %mul.2, 4
  %_bottom_malloc.call.2 = call noalias i8* @_bottom_malloc(i32 %add.10)
  %bitcast.4 = bitcast i8* %_bottom_malloc.call.2 to i32*
  store i32 %m.load, i32* %bitcast.4, align 4
  %getelementptr.2 = getelementptr inbounds i32, i32* %bitcast.4, i32 1
  %bitcast.5 = bitcast i32* %getelementptr.2 to %class.Edge**
  store %class.Edge** %bitcast.5, %class.Edge*** @e, align 8
  store i32 0, i32* %i.addr.2, align 4
  br label %for.cond.1

exit.17:
  %func.ret.load.2 = load i32, i32* %func.ret.addr.2, align 4
  ret i32 %func.ret.load.2

for.cond.1:
  %i.load.18 = load i32, i32* %i.addr.2, align 4
  %m.load.1 = load i32, i32* @m, align 4
  %icmp.12 = icmp slt i32 %i.load.18, %m.load.1
  br i1 %icmp.12, label %for.body.1, label %for.exit.1

for.incr.1:
  %i.load.19 = load i32, i32* %i.addr.2, align 4
  %add.11 = add i32 %i.load.19, 1
  store i32 %add.11, i32* %i.addr.2, align 4
  br label %for.cond.1

for.body.1:
  %_bottom_malloc.call.3 = call noalias i8* @_bottom_malloc(i32 12)
  %bitcast.6 = bitcast i8* %_bottom_malloc.call.3 to %class.Edge*
  call void @Edge.Edge(%class.Edge* %bitcast.6)
  store %class.Edge* %bitcast.6, %class.Edge** %ed.addr, align 8
  %ed.load = load %class.Edge*, %class.Edge** %ed.addr, align 8
  %x.addr.4 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load, i32 0, i32 0
  %x.load.16 = load i32, i32* %x.addr.4, align 4
  %getInt.call.2 = call i32 @getInt()
  store i32 %getInt.call.2, i32* %x.addr.4, align 4
  %ed.load.1 = load %class.Edge*, %class.Edge** %ed.addr, align 8
  %y.addr.2 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load.1, i32 0, i32 1
  %y.load.8 = load i32, i32* %y.addr.2, align 4
  %getInt.call.3 = call i32 @getInt()
  store i32 %getInt.call.3, i32* %y.addr.2, align 4
  %ed.load.2 = load %class.Edge*, %class.Edge** %ed.addr, align 8
  %w.addr.5 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load.2, i32 0, i32 2
  %w.load.4 = load i32, i32* %w.addr.5, align 4
  %getInt.call.4 = call i32 @getInt()
  store i32 %getInt.call.4, i32* %w.addr.5, align 4
  %e.load.10 = load %class.Edge**, %class.Edge*** @e, align 8
  %i.load.20 = load i32, i32* %i.addr.2, align 4
  %e.load.10.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.10, i32 %i.load.20
  %e.load.10.element.load = load %class.Edge*, %class.Edge** %e.load.10.element.addr, align 8
  %ed.load.3 = load %class.Edge*, %class.Edge** %ed.addr, align 8
  store %class.Edge* %ed.load.3, %class.Edge** %e.load.10.element.addr, align 8
  br label %for.incr.1

for.exit.1:
  %e.load.11 = load %class.Edge**, %class.Edge*** @e, align 8
  %m.load.2 = load i32, i32* @m, align 4
  %sub.3 = sub i32 %m.load.2, 1
  call void @qsort(%class.Edge** %e.load.11, i32 0, i32 %sub.3)
  call void @init()
  store i32 0, i32* %i.addr.2, align 4
  store i32 0, i32* %j.addr.1, align 4
  br label %while.cond.3

while.cond.3:
  %i.load.21 = load i32, i32* %i.addr.2, align 4
  %n.load.3 = load i32, i32* @n, align 4
  %sub.4 = sub i32 %n.load.3, 1
  %icmp.13 = icmp slt i32 %i.load.21, %sub.4
  br i1 %icmp.13, label %while.body.3, label %while.exit.3

while.body.3:
  %j.load.10 = load i32, i32* %j.addr.1, align 4
  %m.load.3 = load i32, i32* @m, align 4
  %icmp.14 = icmp sge i32 %j.load.10, %m.load.3
  br i1 %icmp.14, label %if.true.6, label %if.false.6

while.exit.3:
  %rk.load.9 = load i32*, i32** @rk, align 8
  %find.call.3 = call i32 @find(i32 1)
  %rk.load.9.element.addr = getelementptr inbounds i32, i32* %rk.load.9, i32 %find.call.3
  %rk.load.9.element.load = load i32, i32* %rk.load.9.element.addr, align 4
  %n.load.4 = load i32, i32* @n, align 4
  %icmp.15 = icmp eq i32 %rk.load.9.element.load, %n.load.4
  br i1 %icmp.15, label %if.true.8, label %if.false.8

if.true.6:
  %sub.5 = sub i32 0, 1
  call void @printInt(i32 %sub.5)
  store i32 0, i32* %func.ret.addr.2, align 4
  br label %exit.17

if.exit.6:
  %e.load.12 = load %class.Edge**, %class.Edge*** @e, align 8
  %j.load.11 = load i32, i32* %j.addr.1, align 4
  %e.load.12.element.addr = getelementptr inbounds %class.Edge*, %class.Edge** %e.load.12, i32 %j.load.11
  %e.load.12.element.load = load %class.Edge*, %class.Edge** %e.load.12.element.addr, align 8
  store %class.Edge* %e.load.12.element.load, %class.Edge** %ed.addr.1, align 8
  %j.load.12 = load i32, i32* %j.addr.1, align 4
  %add.12 = add i32 %j.load.12, 1
  store i32 %add.12, i32* %j.addr.1, align 4
  %ed.load.4 = load %class.Edge*, %class.Edge** %ed.addr.1, align 8
  %x.addr.5 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load.4, i32 0, i32 0
  %x.load.17 = load i32, i32* %x.addr.5, align 4
  %ed.load.5 = load %class.Edge*, %class.Edge** %ed.addr.1, align 8
  %y.addr.3 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load.5, i32 0, i32 1
  %y.load.9 = load i32, i32* %y.addr.3, align 4
  %union.call = call i1 @union(i32 %x.load.17, i32 %y.load.9)
  br i1 %union.call, label %if.true.7, label %if.false.7

if.false.6:
  br label %if.exit.6

if.true.7:
  %i.load.22 = load i32, i32* %i.addr.2, align 4
  %add.13 = add i32 %i.load.22, 1
  store i32 %add.13, i32* %i.addr.2, align 4
  %ans.load = load i32, i32* @ans, align 4
  %ed.load.6 = load %class.Edge*, %class.Edge** %ed.addr.1, align 8
  %w.addr.6 = getelementptr inbounds %class.Edge, %class.Edge* %ed.load.6, i32 0, i32 2
  %w.load.5 = load i32, i32* %w.addr.6, align 4
  %add.14 = add i32 %ans.load, %w.load.5
  store i32 %add.14, i32* @ans, align 4
  br label %if.exit.7

if.exit.7:
  br label %while.cond.3

if.false.7:
  br label %if.exit.7

if.true.8:
  %ans.load.1 = load i32, i32* @ans, align 4
  call void @printInt(i32 %ans.load.1)
  br label %if.exit.8

if.exit.8:
  store i32 0, i32* %func.ret.addr.2, align 4
  br label %exit.17

if.false.8:
  %sub.6 = sub i32 0, 1
  call void @printInt(i32 %sub.6)
  br label %if.exit.8
}

define void @global_var_init() {
entry:
  %w.addr = alloca i32, align 4
  %y.addr = alloca i32, align 4
  %x.addr = alloca i32, align 4
  store i32 0, i32* @ans, align 4
  store i32* null, i32** @fa, align 8
  store i32* null, i32** @rk, align 8
  store %class.Edge** null, %class.Edge*** @e, align 8
  br label %exit

exit:
  ret void
}


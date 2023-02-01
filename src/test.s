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
@p = global i32 zeroinitializer, align 4
@k = global i32 zeroinitializer, align 4
@i = global i32 zeroinitializer, align 4


@.strconst = private unnamed_addr constant [4 x i8] c"<< \00", align 1
@.strconst.1 = private unnamed_addr constant [2 x i8] c" \00", align 1
@.strconst.2 = private unnamed_addr constant [2 x i8] c"(\00", align 1
@.strconst.3 = private unnamed_addr constant [3 x i8] c") \00", align 1
@.strconst.4 = private unnamed_addr constant [4 x i8] c">> \00", align 1

define void @global_var_init() {
entry:
  br label %exit

exit:
  ret void
}

define i32 @main() {
entry.12:
  call void @global_var_init()
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* @n, align 4
  %getInt.call.1 = call i32 @getInt()
  store i32 %getInt.call.1, i32* @p, align 4
  %getInt.call.2 = call i32 @getInt()
  store i32 %getInt.call.2, i32* @k, align 4
  %p.load = load i32, i32* @p, align 4
  %k.load = load i32, i32* @k, align 4
  %sub = sub i32 %p.load, %k.load
  %icmp = icmp sgt i32 %sub, 1
  br i1 %icmp, label %if.true, label %if.false

exit.12:
  ret i32 0

if.true:
  %getelementptr = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst, i32 0, i32 0
  call void @print(i8* %getelementptr)
  br label %if.exit

if.exit:
  %p.load.1 = load i32, i32* @p, align 4
  %k.load.1 = load i32, i32* @k, align 4
  %sub.1 = sub i32 %p.load.1, %k.load.1
  store i32 %sub.1, i32* @i, align 4
  br label %for.cond

if.false:
  br label %if.exit

for.cond:
  %i.load = load i32, i32* @i, align 4
  %p.load.2 = load i32, i32* @p, align 4
  %k.load.2 = load i32, i32* @k, align 4
  %add = add i32 %p.load.2, %k.load.2
  %icmp.1 = icmp sle i32 %i.load, %add
  br i1 %icmp.1, label %for.body, label %for.exit

for.incr:
  %i.load.1 = load i32, i32* @i, align 4
  %add.1 = add i32 %i.load.1, 1
  store i32 %add.1, i32* @i, align 4
  br label %for.cond

for.body:
  %i.load.2 = load i32, i32* @i, align 4
  %icmp.2 = icmp sle i32 1, %i.load.2
  move %phi, %icmp.2
  br i1 %icmp.2, label %logic_and_continue, label %logic_and_exit

for.exit:
  %p.load.4 = load i32, i32* @p, align 4
  %k.load.3 = load i32, i32* @k, align 4
  %add.2 = add i32 %p.load.4, %k.load.3
  %n.load.1 = load i32, i32* @n, align 4
  %icmp.5 = icmp slt i32 %add.2, %n.load.1
  br i1 %icmp.5, label %if.true.3, label %if.false.3

if.true.1:
  %i.load.4 = load i32, i32* @i, align 4
  %p.load.3 = load i32, i32* @p, align 4
  %icmp.4 = icmp eq i32 %i.load.4, %p.load.3
  br i1 %icmp.4, label %if.true.2, label %if.false.2

if.exit.1:
  br label %for.incr

if.false.1:
  br label %if.exit.1

logic_and_continue:
  %i.load.3 = load i32, i32* @i, align 4
  %n.load = load i32, i32* @n, align 4
  %icmp.3 = icmp sle i32 %i.load.3, %n.load
  move %phi, %icmp.3
  br label %logic_and_exit

logic_and_exit:
  %phi = phi i1 [%icmp.2, %for.body], [%icmp.3, %logic_and_continue]
  br i1 %phi, label %if.true.1, label %if.false.1

if.true.2:
  %getelementptr.2 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.2, i32 0, i32 0
  call void @print(i8* %getelementptr.2)
  %i.load.6 = load i32, i32* @i, align 4
  %toString.call = call i8* @toString(i32 %i.load.6)
  call void @print(i8* %toString.call)
  %getelementptr.3 = getelementptr inbounds [3 x i8], [3 x i8]* @.strconst.3, i32 0, i32 0
  call void @print(i8* %getelementptr.3)
  br label %if.exit.2

if.exit.2:
  br label %if.exit.1

if.false.2:
  %i.load.5 = load i32, i32* @i, align 4
  call void @printInt(i32 %i.load.5)
  %getelementptr.1 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.1, i32 0, i32 0
  call void @print(i8* %getelementptr.1)
  br label %if.exit.2

if.true.3:
  %getelementptr.4 = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst.4, i32 0, i32 0
  call void @print(i8* %getelementptr.4)
  br label %if.exit.3

if.exit.3:
  br label %exit.12

if.false.3:
  br label %if.exit.3

mid:
  br label %logic_and_exit
}

	.text
	.globl	global_var_init
	.p2align	1
	.type	global_var_init,@function
global_var_init:
entry:
	addi	sp, sp, 0
	mv	v27, s0
	mv	v28, s1
	mv	v29, s2
	mv	v30, s3
	mv	v31, s4
	mv	v32, s5
	mv	v33, s6
	mv	v34, s7
	mv	v35, s8
	mv	v36, s9
	mv	v37, s10
	mv	v38, s11
	mv	v39, ra
	j	exit
	addi	sp, sp, 0
exit:
	mv	s0, v27
	mv	s1, v28
	mv	s2, v29
	mv	s3, v30
	mv	s4, v31
	mv	s5, v32
	mv	s6, v33
	mv	s7, v34
	mv	s8, v35
	mv	s9, v36
	mv	s10, v37
	mv	s11, v38
	mv	ra, v39
	ret
	.size	global_var_init, .-global_var_init
                                        # -- End function
	.globl	main
	.p2align	1
	.type	main,@function
main:
entry.12:
	addi	sp, sp, -16
	mv	v0, s0
	mv	v1, s1
	mv	v2, s2
	mv	v3, s3
	mv	v4, s4
	mv	v5, s5
	mv	v6, s6
	mv	v7, s7
	mv	v8, s8
	mv	v9, s9
	mv	v10, s10
	mv	v11, s11
	sw	ra, 0(sp)
	call	global_var_init
	mv	v13, a0
	call	getInt
	mv	v14, a0
	lui	v15, %hi(n)
	sw	v14, %lo(n)(v15)
	call	getInt
	mv	v16, a0
	lui	v17, %hi(p)
	sw	v16, %lo(p)(v17)
	call	getInt
	mv	v18, a0
	lui	v19, %hi(k)
	sw	v18, %lo(k)(v19)
	lui	v21, %hi(p)
	lw	v20, %lo(p)(v21)
	lui	v23, %hi(k)
	lw	v22, %lo(k)(v23)
	sub	v24, v20, v22
	li	v25, 1
	blt	v25, v24, if.true
	j	if.false
	addi	sp, sp, 16
exit.12:
	li	a0, 0
	mv	s0, v0
	mv	s1, v1
	mv	s2, v2
	mv	s3, v3
	mv	s4, v4
	mv	s5, v5
	mv	s6, v6
	mv	s7, v7
	mv	s8, v8
	mv	s9, v9
	mv	s10, v10
	mv	s11, v11
	lw	ra, 0(sp)
	ret
if.true:
	la	v27,.strconst
	mv	v26, v27
	mv	a0, v26
	call	print
	mv	v28, a0
	j	if.exit
if.exit:
	lui	v30, %hi(p)
	lw	v29, %lo(p)(v30)
	lui	v32, %hi(k)
	lw	v31, %lo(k)(v32)
	sub	v33, v29, v31
	lui	v34, %hi(i)
	sw	v33, %lo(i)(v34)
	j	for.cond
if.false:
	j	if.exit
for.cond:
	lui	v36, %hi(i)
	lw	v35, %lo(i)(v36)
	lui	v38, %hi(p)
	lw	v37, %lo(p)(v38)
	lui	v40, %hi(k)
	lw	v39, %lo(k)(v40)
	add	v41, v37, v39
	bge	v41, v35, for.body
	j	for.exit
for.incr:
	lui	v43, %hi(i)
	lw	v42, %lo(i)(v43)
	li	v45, 1
	add	v44, v42, v45
	lui	v46, %hi(i)
	sw	v44, %lo(i)(v46)
	j	for.cond
for.body:
	lui	v48, %hi(i)
	lw	v47, %lo(i)(v48)
	li	v50, 1
	slt	v49, v47, v50
	xori	v49, v49, 1
	mv	v51, v49
	bne	v49, zero, logic_and_continue
	j	logic_and_exit
for.exit:
	lui	v53, %hi(p)
	lw	v52, %lo(p)(v53)
	lui	v55, %hi(k)
	lw	v54, %lo(k)(v55)
	add	v56, v52, v54
	lui	v58, %hi(n)
	lw	v57, %lo(n)(v58)
	blt	v56, v57, if.true.3
	j	if.false.3
if.true.1:
	lui	v60, %hi(i)
	lw	v59, %lo(i)(v60)
	lui	v62, %hi(p)
	lw	v61, %lo(p)(v62)
	beq	v59, v61, if.true.2
	j	if.false.2
if.exit.1:
	j	for.incr
if.false.1:
	j	if.exit.1
logic_and_continue:
	lui	v64, %hi(i)
	lw	v63, %lo(i)(v64)
	lui	v66, %hi(n)
	lw	v65, %lo(n)(v66)
	slt	v67, v65, v63
	xori	v67, v67, 1
	mv	v51, v67
	j	logic_and_exit
logic_and_exit:
	bne	v51, zero, if.true.1
	j	if.false.1
if.true.2:
	la	v69,.strconst.2
	mv	v68, v69
	mv	a0, v68
	call	print
	mv	v70, a0
	lui	v72, %hi(i)
	lw	v71, %lo(i)(v72)
	mv	a0, v71
	call	toString
	mv	v73, a0
	mv	a0, v73
	call	print
	mv	v74, a0
	la	v76,.strconst.3
	mv	v75, v76
	mv	a0, v75
	call	print
	mv	v77, a0
	j	if.exit.2
if.exit.2:
	j	if.exit.1
if.false.2:
	lui	v79, %hi(i)
	lw	v78, %lo(i)(v79)
	mv	a0, v78
	call	printInt
	mv	v80, a0
	la	v82,.strconst.1
	mv	v81, v82
	mv	a0, v81
	call	print
	mv	v83, a0
	j	if.exit.2
if.true.3:
	la	v85,.strconst.4
	mv	v84, v85
	mv	a0, v84
	call	print
	mv	v86, a0
	j	if.exit.3
if.exit.3:
	j	exit.12
if.false.3:
	j	if.exit.3
mid:
	j	logic_and_exit
	.size	main, .-main
                                        # -- End function
	.type	n,@object
	.section	.bss
	.globl	n
n:
	.word	0
	.size	n, 4

	.type	p,@object
	.section	.bss
	.globl	p
p:
	.word	0
	.size	p, 4

	.type	k,@object
	.section	.bss
	.globl	k
k:
	.word	0
	.size	k, 4

	.type	i,@object
	.section	.bss
	.globl	i
i:
	.word	0
	.size	i, 4

	.type	.strconst,@object
	.section	.rodata
.strconst:
	.asciz	"<< "
	.size	.strconst, 3

	.type	.strconst.1,@object
	.section	.rodata
.strconst.1:
	.asciz	" "
	.size	.strconst.1, 1

	.type	.strconst.2,@object
	.section	.rodata
.strconst.2:
	.asciz	"("
	.size	.strconst.2, 1

	.type	.strconst.3,@object
	.section	.rodata
.strconst.3:
	.asciz	") "
	.size	.strconst.3, 2

	.type	.strconst.4,@object
	.section	.rodata
.strconst.4:
	.asciz	">> "
	.size	.strconst.4, 3


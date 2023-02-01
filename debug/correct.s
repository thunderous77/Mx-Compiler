.text
	.globl	_glb_init
	.p2align	1
	.type	_glb_init,@function
_glb_init:
entry.11:
	addi	sp, sp, -16
	sw	s0, 0(sp)
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	sw	ra, 4(sp)
	li	s0, 20
	slli	t0, s0, 2
	addi	a0, t0, 4
	mv	a0, a0
	call	_bot_malloc
	mv	a0, a0
	mv	a0, a0
	sw	s0, 0(a0)
	addi	t0, a0, 4
	mv	t0, t0
	mv	t0, t0
	lui	t1, %hi(a)
	sw	t0, %lo(a)(t1)
	j	exit.11
exit.11:
	lw	s0, 0(sp)
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	lw	ra, 4(sp)
	addi	sp, sp, 16
	ret
	.size	_glb_init, .-_glb_init
                                        # -- End function
	.globl	jud
	.p2align	1
	.type	jud,@function
jud:
entry.12:
	addi	sp, sp, 0
	mv	s0, s0
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	mv	ra, ra
	mv	a0, a0
	mv	t0, zero
	mv	t1, zero
	li	t3, 0
	j	for.cond
exit.12:
	mv	a0, a0
	mv	s0, s0
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	mv	ra, ra
	addi	sp, sp, 0
	ret
for.cond:
	lui	t2, %hi(n)
	lw	t2, %lo(n)(t2)
	div	t2, t2, a0
	blt	t3, t2, for.body
	j	for.exit
for.incr:
	addi	t3, t3, 1
	mv	t0, t0
	mv	t1, t1
	mv	t3, t3
	j	for.cond
for.body:
	li	t0, 0
	mv	t0, t0
	li	t1, 0
	j	for.cond.1
for.exit:
	mv	t0, t0
	mv	t1, t1
	li	a0, 0
	j	exit.12
for.cond.1:
	addi	t2, a0, -1
	blt	t1, t2, for.body.1
	j	for.exit.1
for.incr.1:
	addi	t1, t1, 1
	mv	t0, t0
	mv	t1, t1
	j	for.cond.1
for.body.1:
	lui	t2, %hi(a)
	lw	t4, %lo(a)(t2)
	mul	t2, t3, a0
	add	t2, t2, t1
	slli	t2, t2, 2
	add	t2, t4, t2
	mv	t2, t2
	lw	t4, 0(t2)
	lui	t2, %hi(a)
	lw	t5, %lo(a)(t2)
	mul	t2, t3, a0
	add	t2, t2, t1
	addi	t2, t2, 1
	slli	t2, t2, 2
	add	t2, t5, t2
	mv	t2, t2
	lw	t2, 0(t2)
	blt	t2, t4, if.true
	j	if.false
for.exit.1:
	mv	t0, t0
	xori	t2, t0, 1
	bne	t2, zero, if.true.1
	j	if.false.1
if.true:
	mv	t0, t0
	li	t0, 1
	mv	t0, t0
	j	if.exit
if.false:
	mv	t0, t0
	j	if.exit
if.exit:
	j	for.incr.1
if.true.1:
	mv	t0, t0
	mv	t1, t1
	li	a0, 1
	j	exit.12
if.false.1:
	j	if.exit.1
if.exit.1:
	j	for.incr
	.size	jud, .-jud
                                        # -- End function
	.globl	main
	.p2align	1
	.type	main,@function
main:
entry.13:
	addi	sp, sp, -16
	sw	s0, 0(sp)
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	sw	ra, 4(sp)
	call	_glb_init
	lui	t0, %hi(n)
	lw	t0, %lo(n)(t0)
	call	getInt
	mv	a0, a0
	lui	t0, %hi(n)
	sw	a0, %lo(n)(t0)
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	lui	t0, %hi(i)
	sw	zero, %lo(i)(t0)
	j	for.cond.2
exit.13:
	mv	a0, a0
	lw	s0, 0(sp)
	mv	s1, s1
	mv	s2, s2
	mv	s3, s3
	mv	s4, s4
	mv	s5, s5
	mv	s6, s6
	mv	s7, s7
	mv	s8, s8
	mv	s9, s9
	mv	s10, s10
	mv	s11, s11
	lw	ra, 4(sp)
	addi	sp, sp, 16
	ret
for.cond.2:
	lui	t0, %hi(i)
	lw	t1, %lo(i)(t0)
	lui	t0, %hi(n)
	lw	t0, %lo(n)(t0)
	blt	t1, t0, for.body.2
	j	for.exit.2
for.incr.2:
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	addi	t1, t0, 1
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	j	for.cond.2
for.body.2:
	lui	t0, %hi(a)
	lw	t1, %lo(a)(t0)
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	slli	t0, t0, 2
	add	t0, t1, t0
	mv	s0, t0
	lw	t0, 0(s0)
	call	getInt
	mv	a0, a0
	sw	a0, 0(s0)
	j	for.incr.2
for.exit.2:
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	lui	t0, %hi(n)
	lw	t1, %lo(n)(t0)
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	j	for.cond.3
for.cond.3:
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	blt	zero, t0, for.body.3
	j	for.exit.3
for.incr.3:
	lui	t0, %hi(i)
	lw	t0, %lo(i)(t0)
	lui	t0, %hi(i)
	lw	t1, %lo(i)(t0)
	li	t0, 2
	div	t1, t1, t0
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	j	for.cond.3
for.body.3:
	lui	t0, %hi(i)
	lw	a0, %lo(i)(t0)
	mv	a0, a0
	call	jud
	mv	a0, a0
	blt	zero, a0, if.true.2
	j	if.false.2
for.exit.3:
	li	a0, 0
	j	exit.13
if.true.2:
	lui	t0, %hi(i)
	lw	a0, %lo(i)(t0)
	mv	a0, a0
	call	toString
	mv	a0, a0
	mv	a0, a0
	call	print
	li	a0, 0
	j	exit.13
if.false.2:
	j	if.exit.2
if.exit.2:
	j	for.incr.3
	.size	main, .-main
                                        # -- End function
	.type	n,@object
	.section	.bss
	.globl	n
n:
	.word	0
	.size	n, 4

	.type	a,@object
	.section	.bss
	.globl	a
a:
	.word	0
	.size	a, 4

	.type	i,@object
	.section	.bss
	.globl	i
i:
	.word	0
	.size	i, 4
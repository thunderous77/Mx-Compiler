	.text
	.globl	global_var_init
	.p2align	1
	.type	global_var_init,@function
global_var_init:
entry:
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
	j	exit
exit:
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
	.size	global_var_init, .-global_var_init
                                        # -- End function
	.globl	abs
	.p2align	1
	.type	abs,@function
abs:
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
	blt	zero, a0, if.true
	j	if.false
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
if.true:
	mv	a0, a0
	j	exit.12
if.exit:
	sub	a0, zero, a0
	mv	a0, a0
	j	exit.12
if.false:
	j	if.exit
	.size	abs, .-abs
                                        # -- End function
	.globl	main
	.p2align	1
	.type	main,@function
main:
entry.13:
	addi	sp, sp, -16
	sw	s0, 4(sp)
	sw	s1, 0(sp)
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
	sw	ra, 8(sp)
	call	global_var_init
	mv	a0, a0
	lui	t0, %hi(i)
	sw	zero, %lo(i)(t0)
	j	for.cond
exit.13:
	li	a0, 0
	lw	s0, 4(sp)
	lw	s1, 0(sp)
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
	lw	ra, 8(sp)
	addi	sp, sp, 16
	ret
for.cond:
	lui	t0, %hi(i)
	lw	t1, %lo(i)(t0)
	li	t0, 5
	blt	t1, t0, for.body
	j	for.exit
for.incr:
	lui	t0, %hi(i)
	lw	t1, %lo(i)(t0)
	li	t0, 1
	add	t1, t1, t0
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	j	for.cond
for.body:
	lui	t0, %hi(j)
	sw	zero, %lo(j)(t0)
	j	for.cond.1
for.exit:
	lui	t0, %hi(r)
	lw	t0, %lo(r)(t0)
	li	s0, 2
	sub	a0, s0, t0
	mv	a0, a0
	call	abs
	mv	s1, a0
	lui	t0, %hi(c)
	lw	t0, %lo(c)(t0)
	sub	a0, s0, t0
	mv	a0, a0
	call	abs
	mv	a0, a0
	add	a0, s1, a0
	mv	a0, a0
	call	printInt
	mv	a0, a0
	j	exit.13
for.cond.1:
	lui	t0, %hi(j)
	lw	t1, %lo(j)(t0)
	li	t0, 5
	blt	t1, t0, for.body.1
	j	for.exit.1
for.incr.1:
	lui	t0, %hi(j)
	lw	t1, %lo(j)(t0)
	li	t0, 1
	add	t1, t1, t0
	lui	t0, %hi(j)
	sw	t1, %lo(j)(t0)
	j	for.cond.1
for.body.1:
	call	getInt
	mv	a0, a0
	lui	t0, %hi(n)
	sw	a0, %lo(n)(t0)
	lui	t0, %hi(n)
	lw	t1, %lo(n)(t0)
	li	t0, 1
	beq	t1, t0, if.true.1
	j	if.false.1
for.exit.1:
	j	for.incr
if.true.1:
	lui	t0, %hi(i)
	lw	t1, %lo(i)(t0)
	lui	t0, %hi(r)
	sw	t1, %lo(r)(t0)
	lui	t0, %hi(j)
	lw	t1, %lo(j)(t0)
	lui	t0, %hi(c)
	sw	t1, %lo(c)(t0)
	j	if.exit.1
if.exit.1:
	j	for.incr.1
if.false.1:
	j	if.exit.1
	.size	main, .-main
                                        # -- End function
	.type	n,@object
	.section	.bss
	.globl	n
n:
	.word	0
	.size	n, 4

	.type	r,@object
	.section	.bss
	.globl	r
r:
	.word	0
	.size	r, 4

	.type	c,@object
	.section	.bss
	.globl	c
c:
	.word	0
	.size	c, 4

	.type	i,@object
	.section	.bss
	.globl	i
i:
	.word	0
	.size	i, 4

	.type	j,@object
	.section	.bss
	.globl	j
j:
	.word	0
	.size	j, 4


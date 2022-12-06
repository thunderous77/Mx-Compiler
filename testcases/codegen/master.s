# fileName: test	 compiled by @Masterball.
	.text
	.globl	main
	.p2align	1
	.type	main,@function
main:
entry.12:
	lui	t0, %hi(sum)
	lw	t0, %lo(sum)(t0)
	li	t1, 0
	slt	t3, zero, t1
	xori	t3, t3, 1
	bne	t3, zero, for.body
	lui	t2, %hi(sum)
	sw	t0, %lo(sum)(t2)
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	li	a0, 0
	ret
for.body:
	slti	t2, t1, 1
	xori	t2, t2, 1
	and	t2, t2, t3
	bne	t2, zero, if.true
	addi	t0, t0, -1
	addi	t1, t1, 1
	slt	t3, zero, t1
	xori	t3, t3, 1
	bne	t3, zero, for.body
	lui	t2, %hi(sum)
	sw	t0, %lo(sum)(t2)
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	li	a0, 0
	ret
if.true:
	addi	t0, t0, 1
	addi	t1, t1, 1
	slt	t3, zero, t1
	xori	t3, t3, 1
	bne	t3, zero, for.body
	lui	t2, %hi(sum)
	sw	t0, %lo(sum)(t2)
	lui	t0, %hi(i)
	sw	t1, %lo(i)(t0)
	li	a0, 0
	ret
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

	.type	sum,@object
	.section	.bss
	.globl	sum
sum:
	.word	0
	.size	sum, 4
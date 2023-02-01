	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p0_m2p0"
	.file	"e1.c"
	.globl	main
	.p2align	2
	.type	main,@function
main:
	addi	sp, sp, -32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	addi	s0, sp, 32
	li	a0, 0
	sw	a0, -12(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	addi	a1, s0, -16
	addi	a2, s0, -20
	addi	a3, s0, -24
	call	scanf
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	sub	a0, a0, a1
	li	a1, 2
	blt	a0, a1, .LBB0_2
	j	.LBB0_1
.LBB0_1:
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	call	printf
	j	.LBB0_2
.LBB0_2:
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	sub	a0, a0, a1
	sw	a0, -28(s0)
	j	.LBB0_3
.LBB0_3:
	lw	a1, -28(s0)
	lw	a0, -20(s0)
	lw	a2, -24(s0)
	add	a0, a0, a2
	blt	a0, a1, .LBB0_12
	j	.LBB0_4
.LBB0_4:
	lw	a1, -28(s0)
	li	a0, 0
	bge	a0, a1, .LBB0_10
	j	.LBB0_5
.LBB0_5:
	lw	a1, -28(s0)
	lw	a0, -16(s0)
	blt	a0, a1, .LBB0_10
	j	.LBB0_6
.LBB0_6:
	lw	a0, -28(s0)
	lw	a1, -20(s0)
	bne	a0, a1, .LBB0_8
	j	.LBB0_7
.LBB0_7:
	lw	a1, -28(s0)
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	printf
	j	.LBB0_9
.LBB0_8:
	lw	a1, -28(s0)
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	call	printf
	j	.LBB0_9
.LBB0_9:
	j	.LBB0_10
.LBB0_10:
	j	.LBB0_11
.LBB0_11:
	lw	a0, -28(s0)
	addi	a0, a0, 1
	sw	a0, -28(s0)
	j	.LBB0_3
.LBB0_12:
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lw	a1, -16(s0)
	bge	a0, a1, .LBB0_14
	j	.LBB0_13
.LBB0_13:
	lui	a0, %hi(.L.str.4)
	addi	a0, a0, %lo(.L.str.4)
	call	printf
	j	.LBB0_14
.LBB0_14:
	li	a0, 0
	lw	ra, 28(sp)
	lw	s0, 24(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end0:
	.size	main, .Lfunc_end0-main

	.type	.L.str,@object
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%d%d%d"
	.size	.L.str, 7

	.type	.L.str.1,@object
.L.str.1:
	.asciz	"<< "
	.size	.L.str.1, 4

	.type	.L.str.2,@object
.L.str.2:
	.asciz	"(%d) "
	.size	.L.str.2, 6

	.type	.L.str.3,@object
.L.str.3:
	.asciz	"%d "
	.size	.L.str.3, 4

	.type	.L.str.4,@object
.L.str.4:
	.asciz	">> "
	.size	.L.str.4, 4

	.ident	"Ubuntu clang version 14.0.0-1ubuntu1"
	.section	".note.GNU-stack","",@progbits
	.addrsig
	.addrsig_sym scanf
	.addrsig_sym printf

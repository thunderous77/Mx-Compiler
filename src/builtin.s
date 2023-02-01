	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p0_m2p0"
	.file	"builtin.c"
	.globl	print
	.p2align	2
	.type	print,@function
print:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	printf
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end0:
	.size	print, .Lfunc_end0-print

	.globl	println
	.p2align	2
	.type	println,@function
println:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	call	printf
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end1:
	.size	println, .Lfunc_end1-println

	.globl	printInt
	.p2align	2
	.type	printInt,@function
printInt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	printf
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt

	.globl	printlnInt
	.p2align	2
	.type	printlnInt,@function
printlnInt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	call	printf
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end3:
	.size	printlnInt, .Lfunc_end3-printlnInt

	.globl	getString
	.p2align	2
	.type	getString,@function
getString:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	li	a0, 100
	call	malloc
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	scanf
	lw	a0, -12(s0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end4:
	.size	getString, .Lfunc_end4-getString

	.globl	getInt
	.p2align	2
	.type	getInt,@function
getInt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, s0, -12
	call	scanf
	lw	a0, -12(s0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	getInt, .Lfunc_end5-getInt

	.globl	toString
	.p2align	2
	.type	toString,@function
toString:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	li	a0, 13
	call	malloc
	sw	a0, -16(s0)
	lw	a0, -16(s0)
	lw	a2, -12(s0)
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	call	sprintf
	lw	a0, -16(s0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	toString, .Lfunc_end6-toString

	.globl	_bottom_str_eq
	.p2align	2
	.type	_bottom_str_eq,@function
_bottom_str_eq:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	seqz	a0, a0
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	_bottom_str_eq, .Lfunc_end7-_bottom_str_eq

	.globl	_bottom_str_ne
	.p2align	2
	.type	_bottom_str_ne,@function
_bottom_str_ne:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	snez	a0, a0
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end8:
	.size	_bottom_str_ne, .Lfunc_end8-_bottom_str_ne

	.globl	_bottom_str_slt
	.p2align	2
	.type	_bottom_str_slt,@function
_bottom_str_slt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	srli	a0, a0, 31
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	_bottom_str_slt, .Lfunc_end9-_bottom_str_slt

	.globl	_bottom_str_sgt
	.p2align	2
	.type	_bottom_str_sgt,@function
_bottom_str_sgt:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	mv	a1, a0
	li	a0, 0
	slt	a0, a0, a1
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end10:
	.size	_bottom_str_sgt, .Lfunc_end10-_bottom_str_sgt

	.globl	_bottom_str_sle
	.p2align	2
	.type	_bottom_str_sle,@function
_bottom_str_sle:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	slti	a0, a0, 1
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end11:
	.size	_bottom_str_sle, .Lfunc_end11-_bottom_str_sle

	.globl	_bottom_str_sge
	.p2align	2
	.type	_bottom_str_sge,@function
_bottom_str_sge:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	_bottom_str_sge, .Lfunc_end12-_bottom_str_sge

	.globl	_str_length
	.p2align	2
	.type	_str_length,@function
_str_length:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	li	a0, 0
	sw	a0, -16(s0)
	j	.LBB13_1
.LBB13_1:
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	li	a1, 0
	beq	a0, a1, .LBB13_3
	j	.LBB13_2
.LBB13_2:
	lw	a0, -16(s0)
	addi	a0, a0, 1
	sw	a0, -16(s0)
	j	.LBB13_1
.LBB13_3:
	lw	a0, -16(s0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end13:
	.size	_str_length, .Lfunc_end13-_str_length

	.globl	_bottom_str_cat
	.p2align	2
	.type	_bottom_str_cat,@function
_bottom_str_cat:
	addi	sp, sp, -32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	addi	s0, sp, 32
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	call	_str_length
	sw	a0, -20(s0)
	lw	a0, -16(s0)
	call	_str_length
	sw	a0, -24(s0)
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	addi	a0, a0, 1
	call	malloc
	sw	a0, -28(s0)
	li	a0, 0
	sw	a0, -32(s0)
	j	.LBB14_1
.LBB14_1:
	lw	a0, -32(s0)
	lw	a1, -20(s0)
	bge	a0, a1, .LBB14_4
	j	.LBB14_2
.LBB14_2:
	lw	a0, -12(s0)
	lw	a2, -32(s0)
	add	a0, a0, a2
	lb	a0, 0(a0)
	lw	a1, -28(s0)
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB14_3
.LBB14_3:
	lw	a0, -32(s0)
	addi	a0, a0, 1
	sw	a0, -32(s0)
	j	.LBB14_1
.LBB14_4:
	li	a0, 0
	sw	a0, -32(s0)
	j	.LBB14_5
.LBB14_5:
	lw	a0, -32(s0)
	lw	a1, -24(s0)
	bge	a0, a1, .LBB14_8
	j	.LBB14_6
.LBB14_6:
	lw	a0, -16(s0)
	lw	a3, -32(s0)
	add	a0, a0, a3
	lb	a0, 0(a0)
	lw	a1, -28(s0)
	lw	a2, -20(s0)
	add	a2, a2, a3
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB14_7
.LBB14_7:
	lw	a0, -32(s0)
	addi	a0, a0, 1
	sw	a0, -32(s0)
	j	.LBB14_5
.LBB14_8:
	lw	a0, -28(s0)
	lw	a1, -20(s0)
	lw	a2, -24(s0)
	add	a1, a1, a2
	add	a1, a0, a1
	li	a0, 0
	sb	a0, 0(a1)
	lw	a0, -28(s0)
	lw	ra, 28(sp)
	lw	s0, 24(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end14:
	.size	_bottom_str_cat, .Lfunc_end14-_bottom_str_cat

	.globl	_bottom_malloc
	.p2align	2
	.type	_bottom_malloc,@function
_bottom_malloc:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a0, -12(s0)
	call	malloc
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end15:
	.size	_bottom_malloc, .Lfunc_end15-_bottom_malloc

	.globl	_str_substring
	.p2align	2
	.type	_str_substring,@function
_str_substring:
	addi	sp, sp, -32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	addi	s0, sp, 32
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	sw	a2, -20(s0)
	lw	a0, -20(s0)
	lw	a1, -16(s0)
	sub	a0, a0, a1
	addi	a0, a0, 1
	call	malloc
	sw	a0, -24(s0)
	lw	a0, -16(s0)
	sw	a0, -28(s0)
	j	.LBB16_1
.LBB16_1:
	lw	a0, -28(s0)
	lw	a1, -20(s0)
	bge	a0, a1, .LBB16_4
	j	.LBB16_2
.LBB16_2:
	lw	a0, -12(s0)
	lw	a2, -28(s0)
	add	a0, a0, a2
	lb	a0, 0(a0)
	lw	a1, -24(s0)
	lw	a3, -16(s0)
	sub	a2, a2, a3
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB16_3
.LBB16_3:
	lw	a0, -28(s0)
	addi	a0, a0, 1
	sw	a0, -28(s0)
	j	.LBB16_1
.LBB16_4:
	lw	a0, -24(s0)
	lw	ra, 28(sp)
	lw	s0, 24(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end16:
	.size	_str_substring, .Lfunc_end16-_str_substring

	.globl	_str_parseInt
	.p2align	2
	.type	_str_parseInt,@function
_str_parseInt:
	addi	sp, sp, -32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	addi	s0, sp, 32
	sw	a0, -12(s0)
	li	a0, 0
	sw	a0, -16(s0)
	sw	a0, -20(s0)
	j	.LBB17_1
.LBB17_1:
	lw	a0, -12(s0)
	lw	a1, -20(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	li	a1, 0
	beq	a0, a1, .LBB17_3
	j	.LBB17_2
.LBB17_2:
	lw	a0, -16(s0)
	li	a1, 10
	mul	a0, a0, a1
	lw	a1, -12(s0)
	lw	a2, -20(s0)
	add	a1, a1, a2
	lbu	a1, 0(a1)
	add	a0, a0, a1
	addi	a0, a0, -48
	sw	a0, -16(s0)
	lw	a0, -20(s0)
	addi	a0, a0, 1
	sw	a0, -20(s0)
	j	.LBB17_1
.LBB17_3:
	lw	a0, -16(s0)
	lw	ra, 28(sp)
	lw	s0, 24(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end17:
	.size	_str_parseInt, .Lfunc_end17-_str_parseInt

	.globl	_str_ord
	.p2align	2
	.type	_str_ord,@function
_str_ord:
	addi	sp, sp, -16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	ra, 12(sp)
	lw	s0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end18:
	.size	_str_ord, .Lfunc_end18-_str_ord

	.type	.L.str,@object
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.ident	"Ubuntu clang version 14.0.0-1ubuntu1"
	.section	".note.GNU-stack","",@progbits
	.addrsig
	.addrsig_sym printf
	.addrsig_sym malloc
	.addrsig_sym scanf
	.addrsig_sym sprintf
	.addrsig_sym strcmp
	.addrsig_sym _str_length

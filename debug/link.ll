; ModuleID = 'link.bc'
source_filename = "llvm-link"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

%"class.std::ios_base::Init" = type { i8 }
%"class.std::basic_istream" = type { i32 (...)**, i64, %"class.std::basic_ios" }
%"class.std::basic_ios" = type { %"class.std::ios_base", %"class.std::basic_ostream"*, i8, i8, %"class.std::basic_streambuf"*, %"class.std::ctype"*, %"class.std::num_put"*, %"class.std::num_put"* }
%"class.std::ios_base" = type { i32 (...)**, i64, i64, i32, i32, i32, %"struct.std::ios_base::_Callback_list"*, %"struct.std::ios_base::_Words", [8 x %"struct.std::ios_base::_Words"], i32, %"struct.std::ios_base::_Words"*, %"class.std::locale" }
%"struct.std::ios_base::_Callback_list" = type { %"struct.std::ios_base::_Callback_list"*, void (i32, %"class.std::ios_base"*, i32)*, i32, i32 }
%"struct.std::ios_base::_Words" = type { i8*, i64 }
%"class.std::locale" = type { %"class.std::locale::_Impl"* }
%"class.std::locale::_Impl" = type { i32, %"class.std::locale::facet"**, i64, %"class.std::locale::facet"**, i8** }
%"class.std::locale::facet" = type <{ i32 (...)**, i32, [4 x i8] }>
%"class.std::basic_ostream" = type { i32 (...)**, %"class.std::basic_ios" }
%"class.std::basic_streambuf" = type { i32 (...)**, i8*, i8*, i8*, i8*, i8*, i8*, %"class.std::locale" }
%"class.std::ctype" = type <{ %"class.std::locale::facet.base", [4 x i8], %struct.__locale_struct*, i8, [7 x i8], i32*, i32*, i16*, i8, [256 x i8], [256 x i8], i8, [6 x i8] }>
%"class.std::locale::facet.base" = type <{ i32 (...)**, i32 }>
%struct.__locale_struct = type { [13 x %struct.__locale_data*], i16*, i32*, i32*, [13 x i8*] }
%struct.__locale_data = type opaque
%"class.std::num_put" = type { %"class.std::locale::facet.base", [4 x i8] }
%"class.std::__cxx11::basic_string" = type { %"struct.std::__cxx11::basic_string<char>::_Alloc_hider", i64, %union.anon }
%"struct.std::__cxx11::basic_string<char>::_Alloc_hider" = type { i8* }
%union.anon = type { i64, [8 x i8] }

$_ZSt4swapIcENSt9enable_ifIXsr6__and_ISt6__not_ISt15__is_tuple_likeIT_EESt21is_move_constructibleIS3_ESt18is_move_assignableIS3_EEE5valueEvE4typeERS3_SC_ = comdat any

$_ZSt4moveIRcEONSt16remove_referenceIT_E4typeEOS2_ = comdat any

@asciiTable = global i8* null, align 8
@MAXCHUNK = global i32 0, align 4
@MAXLENGTH = global i32 0, align 4
@chunks = global i32** null, align 8
@inputBuffer = global i32* null, align 8
@outputBuffer = global i32* null, align 8
@.strconst = private unnamed_addr constant [96 x i8] c" !\22#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\00", align 1
@.strconst.1 = private unnamed_addr constant [1 x i8] zeroinitializer, align 1
@.strconst.2 = private unnamed_addr constant [19 x i8] c"nChunk > MAXCHUNK!\00", align 1
@.strconst.3 = private unnamed_addr constant [14 x i8] c"Invalid input\00", align 1
@.strconst.4 = private unnamed_addr constant [11 x i8] c"Not Found!\00", align 1
@llvm.global_ctors = appending global [1 x { i32, void ()*, i8* }] [{ i32, void ()*, i8* } { i32 65535, void ()* @_GLOBAL__sub_I_builtin.cpp, i8* null }]
@_ZStL8__ioinit = internal global %"class.std::ios_base::Init" zeroinitializer, align 1
@__dso_handle = external hidden global i8
@.str = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str.1 = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str.2 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@_ZSt3cin = external global %"class.std::basic_istream", align 8

define i32 @hex2int(i8* %x) {
entry.12:
  %digit.addr = alloca i32, align 4
  %result.addr = alloca i32, align 4
  %i.addr = alloca i32, align 4
  %x.addr = alloca i8*, align 8
  %func.ret.addr = alloca i32, align 4
  store i8* %x, i8** %x.addr, align 8
  store i32 0, i32* %result.addr, align 4
  store i32 0, i32* %i.addr, align 4
  br label %for.cond

exit.12:                                          ; preds = %if.false.2, %for.exit
  %func.ret.load = load i32, i32* %func.ret.addr, align 4
  ret i32 %func.ret.load

for.cond:                                         ; preds = %for.incr, %entry.12
  %i.load = load i32, i32* %i.addr, align 4
  %x.load = load i8*, i8** %x.addr, align 8
  %_str_length.call = call i32 @_str_length(i8* %x.load)
  %icmp.1 = icmp slt i32 %i.load, %_str_length.call
  br i1 %icmp.1, label %for.body, label %for.exit

for.incr:                                         ; preds = %if.exit
  %i.load.1 = load i32, i32* %i.addr, align 4
  %add.5 = add i32 %i.load.1, 1
  store i32 %add.5, i32* %i.addr, align 4
  br label %for.cond

for.body:                                         ; preds = %for.cond
  %x.load.1 = load i8*, i8** %x.addr, align 8
  %i.load.2 = load i32, i32* %i.addr, align 4
  %_str_ord.call = call i32 @_str_ord(i8* %x.load.1, i32 %i.load.2)
  store i32 %_str_ord.call, i32* %digit.addr, align 4
  %digit.load = load i32, i32* %digit.addr, align 4
  %icmp.2 = icmp sge i32 %digit.load, 48
  br i1 %icmp.2, label %logic_and_continue, label %logic_and_exit

for.exit:                                         ; preds = %for.cond
  %result.load.3 = load i32, i32* %result.addr, align 4
  store i32 %result.load.3, i32* %func.ret.addr, align 4
  br label %exit.12

if.true:                                          ; preds = %logic_and_exit
  %result.load.2 = load i32, i32* %result.addr, align 4
  %mul.7 = mul i32 %result.load.2, 16
  %digit.load.8 = load i32, i32* %digit.addr, align 4
  %add.10 = add i32 %mul.7, %digit.load.8
  %sub.4 = sub i32 %add.10, 48
  store i32 %sub.4, i32* %result.addr, align 4
  br label %if.exit

if.exit:                                          ; preds = %if.exit.1, %if.true
  br label %for.incr

if.false:                                         ; preds = %logic_and_exit
  %digit.load.2 = load i32, i32* %digit.addr, align 4
  %icmp.4 = icmp sge i32 %digit.load.2, 65
  br i1 %icmp.4, label %logic_and_continue.1, label %logic_and_exit.1

logic_and_continue:                               ; preds = %for.body
  %digit.load.1 = load i32, i32* %digit.addr, align 4
  %icmp.3 = icmp sle i32 %digit.load.1, 57
  br label %logic_and_exit

logic_and_exit:                                   ; preds = %logic_and_continue, %for.body
  %phi.1 = phi i1 [ %icmp.2, %for.body ], [ %icmp.3, %logic_and_continue ]
  br i1 %phi.1, label %if.true, label %if.false

if.true.1:                                        ; preds = %logic_and_exit.1
  %result.load.1 = load i32, i32* %result.addr, align 4
  %mul.6 = mul i32 %result.load.1, 16
  %digit.load.7 = load i32, i32* %digit.addr, align 4
  %add.8 = add i32 %mul.6, %digit.load.7
  %sub.3 = sub i32 %add.8, 65
  %add.9 = add i32 %sub.3, 10
  store i32 %add.9, i32* %result.addr, align 4
  br label %if.exit.1

if.exit.1:                                        ; preds = %if.exit.2, %if.true.1
  br label %if.exit

if.false.1:                                       ; preds = %logic_and_exit.1
  %digit.load.4 = load i32, i32* %digit.addr, align 4
  %icmp.6 = icmp sge i32 %digit.load.4, 97
  br i1 %icmp.6, label %logic_and_continue.2, label %logic_and_exit.2

logic_and_continue.1:                             ; preds = %if.false
  %digit.load.3 = load i32, i32* %digit.addr, align 4
  %icmp.5 = icmp sle i32 %digit.load.3, 70
  br label %logic_and_exit.1

logic_and_exit.1:                                 ; preds = %logic_and_continue.1, %if.false
  %phi.2 = phi i1 [ %icmp.4, %if.false ], [ %icmp.5, %logic_and_continue.1 ]
  br i1 %phi.2, label %if.true.1, label %if.false.1

if.true.2:                                        ; preds = %logic_and_exit.2
  %result.load = load i32, i32* %result.addr, align 4
  %mul.5 = mul i32 %result.load, 16
  %digit.load.6 = load i32, i32* %digit.addr, align 4
  %add.6 = add i32 %mul.5, %digit.load.6
  %sub.2 = sub i32 %add.6, 97
  %add.7 = add i32 %sub.2, 10
  store i32 %add.7, i32* %result.addr, align 4
  br label %if.exit.2

if.exit.2:                                        ; preds = %if.true.2
  br label %if.exit.1

if.false.2:                                       ; preds = %logic_and_exit.2
  store i32 0, i32* %func.ret.addr, align 4
  br label %exit.12

logic_and_continue.2:                             ; preds = %if.false.1
  %digit.load.5 = load i32, i32* %digit.addr, align 4
  %icmp.7 = icmp sle i32 %digit.load.5, 102
  br label %logic_and_exit.2

logic_and_exit.2:                                 ; preds = %logic_and_continue.2, %if.false.1
  %phi.3 = phi i1 [ %icmp.6, %if.false.1 ], [ %icmp.7, %logic_and_continue.2 ]
  br i1 %phi.3, label %if.true.2, label %if.false.2
}

define i8* @int2chr(i32 %x.1) {
entry.13:
  %x.addr.1 = alloca i32, align 4
  %func.ret.addr.1 = alloca i8*, align 8
  store i32 %x.1, i32* %x.addr.1, align 4
  %x.load.2 = load i32, i32* %x.addr.1, align 4
  %icmp.8 = icmp sge i32 %x.load.2, 32
  br i1 %icmp.8, label %logic_and_continue.3, label %logic_and_exit.3

exit.13:                                          ; preds = %if.exit.3, %if.true.3
  %func.ret.load.1 = load i8*, i8** %func.ret.addr.1, align 8
  ret i8* %func.ret.load.1

if.true.3:                                        ; preds = %logic_and_exit.3
  %asciiTable.load = load i8*, i8** @asciiTable, align 8
  %x.load.4 = load i32, i32* %x.addr.1, align 4
  %sub.5 = sub i32 %x.load.4, 32
  %x.load.5 = load i32, i32* %x.addr.1, align 4
  %sub.6 = sub i32 %x.load.5, 31
  %_str_substring.call = call i8* @_str_substring(i8* %asciiTable.load, i32 %sub.5, i32 %sub.6)
  store i8* %_str_substring.call, i8** %func.ret.addr.1, align 8
  br label %exit.13

if.exit.3:                                        ; preds = %if.false.3
  %getelementptr.7 = getelementptr inbounds [1 x i8], [1 x i8]* @.strconst.1, i32 0, i32 0
  store i8* %getelementptr.7, i8** %func.ret.addr.1, align 8
  br label %exit.13

if.false.3:                                       ; preds = %logic_and_exit.3
  br label %if.exit.3

logic_and_continue.3:                             ; preds = %entry.13
  %x.load.3 = load i32, i32* %x.addr.1, align 4
  %icmp.9 = icmp sle i32 %x.load.3, 126
  br label %logic_and_exit.3

logic_and_exit.3:                                 ; preds = %logic_and_continue.3, %entry.13
  %phi.4 = phi i1 [ %icmp.8, %entry.13 ], [ %icmp.9, %logic_and_continue.3 ]
  br i1 %phi.4, label %if.true.3, label %if.false.3
}

define i8* @toStringHex(i32 %x.2) {
entry.14:
  %digit.addr.1 = alloca i32, align 4
  %i.addr.1 = alloca i32, align 4
  %ret.addr = alloca i8*, align 8
  %x.addr.2 = alloca i32, align 4
  %func.ret.addr.2 = alloca i8*, align 8
  store i32 %x.2, i32* %x.addr.2, align 4
  %getelementptr.8 = getelementptr inbounds [1 x i8], [1 x i8]* @.strconst.1, i32 0, i32 0
  store i8* %getelementptr.8, i8** %ret.addr, align 8
  store i32 28, i32* %i.addr.1, align 4
  br label %for.cond.1

exit.14:                                          ; preds = %for.exit.1
  %func.ret.load.2 = load i8*, i8** %func.ret.addr.2, align 8
  ret i8* %func.ret.load.2

for.cond.1:                                       ; preds = %for.incr.1, %entry.14
  %i.load.3 = load i32, i32* %i.addr.1, align 4
  %icmp.10 = icmp sge i32 %i.load.3, 0
  br i1 %icmp.10, label %for.body.1, label %for.exit.1

for.incr.1:                                       ; preds = %if.exit.4
  %i.load.4 = load i32, i32* %i.addr.1, align 4
  %sub.7 = sub i32 %i.load.4, 4
  store i32 %sub.7, i32* %i.addr.1, align 4
  br label %for.cond.1

for.body.1:                                       ; preds = %for.cond.1
  %x.load.6 = load i32, i32* %x.addr.2, align 4
  %i.load.5 = load i32, i32* %i.addr.1, align 4
  %ashr = ashr i32 %x.load.6, %i.load.5
  %and = and i32 %ashr, 15
  store i32 %and, i32* %digit.addr.1, align 4
  %digit.load.9 = load i32, i32* %digit.addr.1, align 4
  %icmp.11 = icmp slt i32 %digit.load.9, 10
  br i1 %icmp.11, label %if.true.4, label %if.false.4

for.exit.1:                                       ; preds = %for.cond.1
  %ret.load.2 = load i8*, i8** %ret.addr, align 8
  store i8* %ret.load.2, i8** %func.ret.addr.2, align 8
  br label %exit.14

if.true.4:                                        ; preds = %for.body.1
  %ret.load.1 = load i8*, i8** %ret.addr, align 8
  %digit.load.11 = load i32, i32* %digit.addr.1, align 4
  %add.12 = add i32 48, %digit.load.11
  %int2chr.call.1 = call i8* @int2chr(i32 %add.12)
  %_bottom_str_cat.call.1 = call i8* @_bottom_str_cat(i8* %ret.load.1, i8* %int2chr.call.1)
  store i8* %_bottom_str_cat.call.1, i8** %ret.addr, align 8
  br label %if.exit.4

if.exit.4:                                        ; preds = %if.false.4, %if.true.4
  br label %for.incr.1

if.false.4:                                       ; preds = %for.body.1
  %ret.load = load i8*, i8** %ret.addr, align 8
  %digit.load.10 = load i32, i32* %digit.addr.1, align 4
  %add.11 = add i32 65, %digit.load.10
  %sub.8 = sub i32 %add.11, 10
  %int2chr.call = call i8* @int2chr(i32 %sub.8)
  %_bottom_str_cat.call = call i8* @_bottom_str_cat(i8* %ret.load, i8* %int2chr.call)
  store i8* %_bottom_str_cat.call, i8** %ret.addr, align 8
  br label %if.exit.4
}

define i32 @rotate_left(i32 %x.3, i32 %shift) {
entry.15:
  %shift.addr = alloca i32, align 4
  %x.addr.3 = alloca i32, align 4
  %func.ret.addr.3 = alloca i32, align 4
  store i32 %x.3, i32* %x.addr.3, align 4
  store i32 %shift, i32* %shift.addr, align 4
  %shift.load = load i32, i32* %shift.addr, align 4
  %icmp.12 = icmp eq i32 %shift.load, 1
  br i1 %icmp.12, label %if.true.5, label %if.false.5

exit.15:                                          ; preds = %if.exit.6, %if.true.6, %if.true.5
  %func.ret.load.3 = load i32, i32* %func.ret.addr.3, align 4
  ret i32 %func.ret.load.3

if.true.5:                                        ; preds = %entry.15
  %x.load.7 = load i32, i32* %x.addr.3, align 4
  %and.1 = and i32 %x.load.7, 2147483647
  %shl = shl i32 %and.1, 1
  %x.load.8 = load i32, i32* %x.addr.3, align 4
  %ashr.1 = ashr i32 %x.load.8, 31
  %and.2 = and i32 %ashr.1, 1
  %or = or i32 %shl, %and.2
  store i32 %or, i32* %func.ret.addr.3, align 4
  br label %exit.15

if.exit.5:                                        ; preds = %if.false.5
  %shift.load.1 = load i32, i32* %shift.addr, align 4
  %icmp.13 = icmp eq i32 %shift.load.1, 31
  br i1 %icmp.13, label %if.true.6, label %if.false.6

if.false.5:                                       ; preds = %entry.15
  br label %if.exit.5

if.true.6:                                        ; preds = %if.exit.5
  %x.load.9 = load i32, i32* %x.addr.3, align 4
  %and.3 = and i32 %x.load.9, 1
  %shl.1 = shl i32 %and.3, 31
  %x.load.10 = load i32, i32* %x.addr.3, align 4
  %ashr.2 = ashr i32 %x.load.10, 1
  %and.4 = and i32 %ashr.2, 2147483647
  %or.1 = or i32 %shl.1, %and.4
  store i32 %or.1, i32* %func.ret.addr.3, align 4
  br label %exit.15

if.exit.6:                                        ; preds = %if.false.6
  %x.load.11 = load i32, i32* %x.addr.3, align 4
  %shift.load.2 = load i32, i32* %shift.addr, align 4
  %sub.9 = sub i32 32, %shift.load.2
  %shl.2 = shl i32 1, %sub.9
  %sub.10 = sub i32 %shl.2, 1
  %and.5 = and i32 %x.load.11, %sub.10
  %shift.load.3 = load i32, i32* %shift.addr, align 4
  %shl.3 = shl i32 %and.5, %shift.load.3
  %x.load.12 = load i32, i32* %x.addr.3, align 4
  %shift.load.4 = load i32, i32* %shift.addr, align 4
  %sub.11 = sub i32 32, %shift.load.4
  %ashr.3 = ashr i32 %x.load.12, %sub.11
  %shift.load.5 = load i32, i32* %shift.addr, align 4
  %shl.4 = shl i32 1, %shift.load.5
  %sub.12 = sub i32 %shl.4, 1
  %and.6 = and i32 %ashr.3, %sub.12
  %or.2 = or i32 %shl.3, %and.6
  store i32 %or.2, i32* %func.ret.addr.3, align 4
  br label %exit.15

if.false.6:                                       ; preds = %if.exit.5
  br label %if.exit.6
}

define i32 @add(i32 %x.4, i32 %y) {
entry.16:
  %high.addr = alloca i32, align 4
  %low.addr = alloca i32, align 4
  %y.addr = alloca i32, align 4
  %x.addr.4 = alloca i32, align 4
  %func.ret.addr.4 = alloca i32, align 4
  store i32 %x.4, i32* %x.addr.4, align 4
  store i32 %y, i32* %y.addr, align 4
  %x.load.13 = load i32, i32* %x.addr.4, align 4
  %and.7 = and i32 %x.load.13, 65535
  %y.load = load i32, i32* %y.addr, align 4
  %and.8 = and i32 %y.load, 65535
  %add.13 = add i32 %and.7, %and.8
  store i32 %add.13, i32* %low.addr, align 4
  %x.load.14 = load i32, i32* %x.addr.4, align 4
  %ashr.4 = ashr i32 %x.load.14, 16
  %and.9 = and i32 %ashr.4, 65535
  %y.load.1 = load i32, i32* %y.addr, align 4
  %ashr.5 = ashr i32 %y.load.1, 16
  %and.10 = and i32 %ashr.5, 65535
  %add.14 = add i32 %and.9, %and.10
  %low.load = load i32, i32* %low.addr, align 4
  %ashr.6 = ashr i32 %low.load, 16
  %add.15 = add i32 %add.14, %ashr.6
  %and.11 = and i32 %add.15, 65535
  store i32 %and.11, i32* %high.addr, align 4
  %high.load = load i32, i32* %high.addr, align 4
  %shl.5 = shl i32 %high.load, 16
  %low.load.1 = load i32, i32* %low.addr, align 4
  %and.12 = and i32 %low.load.1, 65535
  %or.3 = or i32 %shl.5, %and.12
  store i32 %or.3, i32* %func.ret.addr.4, align 4
  br label %exit.16

exit.16:                                          ; preds = %entry.16
  %func.ret.load.4 = load i32, i32* %func.ret.addr.4, align 4
  ret i32 %func.ret.load.4
}

define i32 @lohi(i32 %lo, i32 %hi) {
entry.17:
  %hi.addr = alloca i32, align 4
  %lo.addr = alloca i32, align 4
  %func.ret.addr.5 = alloca i32, align 4
  store i32 %lo, i32* %lo.addr, align 4
  store i32 %hi, i32* %hi.addr, align 4
  %lo.load = load i32, i32* %lo.addr, align 4
  %hi.load = load i32, i32* %hi.addr, align 4
  %shl.6 = shl i32 %hi.load, 16
  %or.4 = or i32 %lo.load, %shl.6
  store i32 %or.4, i32* %func.ret.addr.5, align 4
  br label %exit.17

exit.17:                                          ; preds = %entry.17
  %func.ret.load.5 = load i32, i32* %func.ret.addr.5, align 4
  ret i32 %func.ret.load.5
}

define i32* @sha1(i32* %input, i32 %length) {
entry.18:
  %temp.addr = alloca i32, align 4
  %k.addr = alloca i32, align 4
  %f.addr = alloca i32, align 4
  %e.addr = alloca i32, align 4
  %d.addr = alloca i32, align 4
  %c.addr = alloca i32, align 4
  %b.addr = alloca i32, align 4
  %a.addr = alloca i32, align 4
  %h4.addr = alloca i32, align 4
  %h3.addr = alloca i32, align 4
  %h2.addr = alloca i32, align 4
  %h1.addr = alloca i32, align 4
  %h0.addr = alloca i32, align 4
  %j.addr = alloca i32, align 4
  %i.addr.2 = alloca i32, align 4
  %nChunk.addr = alloca i32, align 4
  %length.addr = alloca i32, align 4
  %input.addr = alloca i32*, align 8
  %func.ret.addr.6 = alloca i32*, align 8
  store i32* %input, i32** %input.addr, align 8
  store i32 %length, i32* %length.addr, align 4
  %length.load = load i32, i32* %length.addr, align 4
  %add.16 = add i32 %length.load, 64
  %sub.13 = sub i32 %add.16, 56
  %sdiv = sdiv i32 %sub.13, 64
  %add.17 = add i32 %sdiv, 1
  store i32 %add.17, i32* %nChunk.addr, align 4
  %nChunk.load = load i32, i32* %nChunk.addr, align 4
  %MAXCHUNK.load.2 = load i32, i32* @MAXCHUNK, align 4
  %icmp.14 = icmp sgt i32 %nChunk.load, %MAXCHUNK.load.2
  br i1 %icmp.14, label %if.true.7, label %if.false.7

exit.18:                                          ; preds = %for.exit.5, %if.true.7
  %func.ret.load.6 = load i32*, i32** %func.ret.addr.6, align 8
  ret i32* %func.ret.load.6

if.true.7:                                        ; preds = %entry.18
  %getelementptr.9 = getelementptr inbounds [19 x i8], [19 x i8]* @.strconst.2, i32 0, i32 0
  call void @println(i8* %getelementptr.9)
  store i32* null, i32** %func.ret.addr.6, align 8
  br label %exit.18

if.exit.7:                                        ; preds = %if.false.7
  store i32 0, i32* %i.addr.2, align 4
  br label %for.cond.2

if.false.7:                                       ; preds = %entry.18
  br label %if.exit.7

for.cond.2:                                       ; preds = %for.incr.2, %if.exit.7
  %i.load.6 = load i32, i32* %i.addr.2, align 4
  %nChunk.load.1 = load i32, i32* %nChunk.addr, align 4
  %icmp.15 = icmp slt i32 %i.load.6, %nChunk.load.1
  br i1 %icmp.15, label %for.body.2, label %for.exit.2

for.incr.2:                                       ; preds = %for.exit.3
  %i.load.7 = load i32, i32* %i.addr.2, align 4
  %add.18 = add i32 %i.load.7, 1
  store i32 %add.18, i32* %i.addr.2, align 4
  br label %for.cond.2

for.body.2:                                       ; preds = %for.cond.2
  store i32 0, i32* %j.addr, align 4
  br label %for.cond.3

for.exit.2:                                       ; preds = %for.cond.2
  store i32 0, i32* %i.addr.2, align 4
  br label %for.cond.4

for.cond.3:                                       ; preds = %for.incr.3, %for.body.2
  %j.load = load i32, i32* %j.addr, align 4
  %icmp.16 = icmp slt i32 %j.load, 80
  br i1 %icmp.16, label %for.body.3, label %for.exit.3

for.incr.3:                                       ; preds = %for.body.3
  %j.load.1 = load i32, i32* %j.addr, align 4
  %add.19 = add i32 %j.load.1, 1
  store i32 %add.19, i32* %j.addr, align 4
  br label %for.cond.3

for.body.3:                                       ; preds = %for.cond.3
  %chunks.load = load i32**, i32*** @chunks, align 8
  %i.load.8 = load i32, i32* %i.addr.2, align 4
  %chunks.load.element.addr = getelementptr inbounds i32*, i32** %chunks.load, i32 %i.load.8
  %chunks.load.element.load = load i32*, i32** %chunks.load.element.addr, align 8
  %j.load.2 = load i32, i32* %j.addr, align 4
  %chunks.load.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.element.load, i32 %j.load.2
  %chunks.load.element.load.element.load = load i32, i32* %chunks.load.element.load.element.addr, align 4
  store i32 0, i32* %chunks.load.element.load.element.addr, align 4
  br label %for.incr.3

for.exit.3:                                       ; preds = %for.cond.3
  br label %for.incr.2

for.cond.4:                                       ; preds = %for.incr.4, %for.exit.2
  %i.load.9 = load i32, i32* %i.addr.2, align 4
  %length.load.1 = load i32, i32* %length.addr, align 4
  %icmp.17 = icmp slt i32 %i.load.9, %length.load.1
  br i1 %icmp.17, label %for.body.4, label %for.exit.4

for.incr.4:                                       ; preds = %for.body.4
  %i.load.10 = load i32, i32* %i.addr.2, align 4
  %add.20 = add i32 %i.load.10, 1
  store i32 %add.20, i32* %i.addr.2, align 4
  br label %for.cond.4

for.body.4:                                       ; preds = %for.cond.4
  %chunks.load.1 = load i32**, i32*** @chunks, align 8
  %i.load.11 = load i32, i32* %i.addr.2, align 4
  %sdiv.1 = sdiv i32 %i.load.11, 64
  %chunks.load.1.element.addr = getelementptr inbounds i32*, i32** %chunks.load.1, i32 %sdiv.1
  %chunks.load.1.element.load = load i32*, i32** %chunks.load.1.element.addr, align 8
  %i.load.12 = load i32, i32* %i.addr.2, align 4
  %srem = srem i32 %i.load.12, 64
  %sdiv.2 = sdiv i32 %srem, 4
  %chunks.load.1.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.1.element.load, i32 %sdiv.2
  %chunks.load.1.element.load.element.load = load i32, i32* %chunks.load.1.element.load.element.addr, align 4
  %chunks.load.2 = load i32**, i32*** @chunks, align 8
  %i.load.13 = load i32, i32* %i.addr.2, align 4
  %sdiv.3 = sdiv i32 %i.load.13, 64
  %chunks.load.2.element.addr = getelementptr inbounds i32*, i32** %chunks.load.2, i32 %sdiv.3
  %chunks.load.2.element.load = load i32*, i32** %chunks.load.2.element.addr, align 8
  %i.load.14 = load i32, i32* %i.addr.2, align 4
  %srem.1 = srem i32 %i.load.14, 64
  %sdiv.4 = sdiv i32 %srem.1, 4
  %chunks.load.2.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.2.element.load, i32 %sdiv.4
  %chunks.load.2.element.load.element.load = load i32, i32* %chunks.load.2.element.load.element.addr, align 4
  %input.load = load i32*, i32** %input.addr, align 8
  %i.load.15 = load i32, i32* %i.addr.2, align 4
  %input.load.element.addr = getelementptr inbounds i32, i32* %input.load, i32 %i.load.15
  %input.load.element.load = load i32, i32* %input.load.element.addr, align 4
  %i.load.16 = load i32, i32* %i.addr.2, align 4
  %srem.2 = srem i32 %i.load.16, 4
  %sub.14 = sub i32 3, %srem.2
  %mul.8 = mul i32 %sub.14, 8
  %shl.7 = shl i32 %input.load.element.load, %mul.8
  %or.5 = or i32 %chunks.load.2.element.load.element.load, %shl.7
  store i32 %or.5, i32* %chunks.load.1.element.load.element.addr, align 4
  br label %for.incr.4

for.exit.4:                                       ; preds = %for.cond.4
  %chunks.load.3 = load i32**, i32*** @chunks, align 8
  %i.load.17 = load i32, i32* %i.addr.2, align 4
  %sdiv.5 = sdiv i32 %i.load.17, 64
  %chunks.load.3.element.addr = getelementptr inbounds i32*, i32** %chunks.load.3, i32 %sdiv.5
  %chunks.load.3.element.load = load i32*, i32** %chunks.load.3.element.addr, align 8
  %i.load.18 = load i32, i32* %i.addr.2, align 4
  %srem.3 = srem i32 %i.load.18, 64
  %sdiv.6 = sdiv i32 %srem.3, 4
  %chunks.load.3.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.3.element.load, i32 %sdiv.6
  %chunks.load.3.element.load.element.load = load i32, i32* %chunks.load.3.element.load.element.addr, align 4
  %chunks.load.4 = load i32**, i32*** @chunks, align 8
  %i.load.19 = load i32, i32* %i.addr.2, align 4
  %sdiv.7 = sdiv i32 %i.load.19, 64
  %chunks.load.4.element.addr = getelementptr inbounds i32*, i32** %chunks.load.4, i32 %sdiv.7
  %chunks.load.4.element.load = load i32*, i32** %chunks.load.4.element.addr, align 8
  %i.load.20 = load i32, i32* %i.addr.2, align 4
  %srem.4 = srem i32 %i.load.20, 64
  %sdiv.8 = sdiv i32 %srem.4, 4
  %chunks.load.4.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.4.element.load, i32 %sdiv.8
  %chunks.load.4.element.load.element.load = load i32, i32* %chunks.load.4.element.load.element.addr, align 4
  %i.load.21 = load i32, i32* %i.addr.2, align 4
  %srem.5 = srem i32 %i.load.21, 4
  %sub.15 = sub i32 3, %srem.5
  %mul.9 = mul i32 %sub.15, 8
  %shl.8 = shl i32 128, %mul.9
  %or.6 = or i32 %chunks.load.4.element.load.element.load, %shl.8
  store i32 %or.6, i32* %chunks.load.3.element.load.element.addr, align 4
  %chunks.load.5 = load i32**, i32*** @chunks, align 8
  %nChunk.load.2 = load i32, i32* %nChunk.addr, align 4
  %sub.16 = sub i32 %nChunk.load.2, 1
  %chunks.load.5.element.addr = getelementptr inbounds i32*, i32** %chunks.load.5, i32 %sub.16
  %chunks.load.5.element.load = load i32*, i32** %chunks.load.5.element.addr, align 8
  %chunks.load.5.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.5.element.load, i32 15
  %chunks.load.5.element.load.element.load = load i32, i32* %chunks.load.5.element.load.element.addr, align 4
  %length.load.2 = load i32, i32* %length.addr, align 4
  %shl.9 = shl i32 %length.load.2, 3
  store i32 %shl.9, i32* %chunks.load.5.element.load.element.addr, align 4
  %chunks.load.6 = load i32**, i32*** @chunks, align 8
  %nChunk.load.3 = load i32, i32* %nChunk.addr, align 4
  %sub.17 = sub i32 %nChunk.load.3, 1
  %chunks.load.6.element.addr = getelementptr inbounds i32*, i32** %chunks.load.6, i32 %sub.17
  %chunks.load.6.element.load = load i32*, i32** %chunks.load.6.element.addr, align 8
  %chunks.load.6.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.6.element.load, i32 14
  %chunks.load.6.element.load.element.load = load i32, i32* %chunks.load.6.element.load.element.addr, align 4
  %length.load.3 = load i32, i32* %length.addr, align 4
  %ashr.7 = ashr i32 %length.load.3, 29
  %and.13 = and i32 %ashr.7, 7
  store i32 %and.13, i32* %chunks.load.6.element.load.element.addr, align 4
  store i32 1732584193, i32* %h0.addr, align 4
  %lohi.call = call i32 @lohi(i32 43913, i32 61389)
  store i32 %lohi.call, i32* %h1.addr, align 4
  %lohi.call.1 = call i32 @lohi(i32 56574, i32 39098)
  store i32 %lohi.call.1, i32* %h2.addr, align 4
  store i32 271733878, i32* %h3.addr, align 4
  %lohi.call.2 = call i32 @lohi(i32 57840, i32 50130)
  store i32 %lohi.call.2, i32* %h4.addr, align 4
  store i32 0, i32* %i.addr.2, align 4
  br label %for.cond.5

for.cond.5:                                       ; preds = %for.incr.5, %for.exit.4
  %i.load.22 = load i32, i32* %i.addr.2, align 4
  %nChunk.load.4 = load i32, i32* %nChunk.addr, align 4
  %icmp.18 = icmp slt i32 %i.load.22, %nChunk.load.4
  br i1 %icmp.18, label %for.body.5, label %for.exit.5

for.incr.5:                                       ; preds = %for.exit.7
  %i.load.23 = load i32, i32* %i.addr.2, align 4
  %add.21 = add i32 %i.load.23, 1
  store i32 %add.21, i32* %i.addr.2, align 4
  br label %for.cond.5

for.body.5:                                       ; preds = %for.cond.5
  store i32 16, i32* %j.addr, align 4
  br label %for.cond.6

for.exit.5:                                       ; preds = %for.cond.5
  %outputBuffer.load = load i32*, i32** @outputBuffer, align 8
  %outputBuffer.load.element.addr = getelementptr inbounds i32, i32* %outputBuffer.load, i32 0
  %outputBuffer.load.element.load = load i32, i32* %outputBuffer.load.element.addr, align 4
  %h0.load.2 = load i32, i32* %h0.addr, align 4
  store i32 %h0.load.2, i32* %outputBuffer.load.element.addr, align 4
  %outputBuffer.load.1 = load i32*, i32** @outputBuffer, align 8
  %outputBuffer.load.1.element.addr = getelementptr inbounds i32, i32* %outputBuffer.load.1, i32 1
  %outputBuffer.load.1.element.load = load i32, i32* %outputBuffer.load.1.element.addr, align 4
  %h1.load.2 = load i32, i32* %h1.addr, align 4
  store i32 %h1.load.2, i32* %outputBuffer.load.1.element.addr, align 4
  %outputBuffer.load.2 = load i32*, i32** @outputBuffer, align 8
  %outputBuffer.load.2.element.addr = getelementptr inbounds i32, i32* %outputBuffer.load.2, i32 2
  %outputBuffer.load.2.element.load = load i32, i32* %outputBuffer.load.2.element.addr, align 4
  %h2.load.2 = load i32, i32* %h2.addr, align 4
  store i32 %h2.load.2, i32* %outputBuffer.load.2.element.addr, align 4
  %outputBuffer.load.3 = load i32*, i32** @outputBuffer, align 8
  %outputBuffer.load.3.element.addr = getelementptr inbounds i32, i32* %outputBuffer.load.3, i32 3
  %outputBuffer.load.3.element.load = load i32, i32* %outputBuffer.load.3.element.addr, align 4
  %h3.load.2 = load i32, i32* %h3.addr, align 4
  store i32 %h3.load.2, i32* %outputBuffer.load.3.element.addr, align 4
  %outputBuffer.load.4 = load i32*, i32** @outputBuffer, align 8
  %outputBuffer.load.4.element.addr = getelementptr inbounds i32, i32* %outputBuffer.load.4, i32 4
  %outputBuffer.load.4.element.load = load i32, i32* %outputBuffer.load.4.element.addr, align 4
  %h4.load.2 = load i32, i32* %h4.addr, align 4
  store i32 %h4.load.2, i32* %outputBuffer.load.4.element.addr, align 4
  %outputBuffer.load.5 = load i32*, i32** @outputBuffer, align 8
  store i32* %outputBuffer.load.5, i32** %func.ret.addr.6, align 8
  br label %exit.18

for.cond.6:                                       ; preds = %for.incr.6, %for.body.5
  %j.load.3 = load i32, i32* %j.addr, align 4
  %icmp.19 = icmp slt i32 %j.load.3, 80
  br i1 %icmp.19, label %for.body.6, label %for.exit.6

for.incr.6:                                       ; preds = %for.body.6
  %j.load.4 = load i32, i32* %j.addr, align 4
  %add.22 = add i32 %j.load.4, 1
  store i32 %add.22, i32* %j.addr, align 4
  br label %for.cond.6

for.body.6:                                       ; preds = %for.cond.6
  %chunks.load.7 = load i32**, i32*** @chunks, align 8
  %i.load.24 = load i32, i32* %i.addr.2, align 4
  %chunks.load.7.element.addr = getelementptr inbounds i32*, i32** %chunks.load.7, i32 %i.load.24
  %chunks.load.7.element.load = load i32*, i32** %chunks.load.7.element.addr, align 8
  %j.load.5 = load i32, i32* %j.addr, align 4
  %chunks.load.7.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.7.element.load, i32 %j.load.5
  %chunks.load.7.element.load.element.load = load i32, i32* %chunks.load.7.element.load.element.addr, align 4
  %chunks.load.8 = load i32**, i32*** @chunks, align 8
  %i.load.25 = load i32, i32* %i.addr.2, align 4
  %chunks.load.8.element.addr = getelementptr inbounds i32*, i32** %chunks.load.8, i32 %i.load.25
  %chunks.load.8.element.load = load i32*, i32** %chunks.load.8.element.addr, align 8
  %j.load.6 = load i32, i32* %j.addr, align 4
  %sub.18 = sub i32 %j.load.6, 3
  %chunks.load.8.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.8.element.load, i32 %sub.18
  %chunks.load.8.element.load.element.load = load i32, i32* %chunks.load.8.element.load.element.addr, align 4
  %chunks.load.9 = load i32**, i32*** @chunks, align 8
  %i.load.26 = load i32, i32* %i.addr.2, align 4
  %chunks.load.9.element.addr = getelementptr inbounds i32*, i32** %chunks.load.9, i32 %i.load.26
  %chunks.load.9.element.load = load i32*, i32** %chunks.load.9.element.addr, align 8
  %j.load.7 = load i32, i32* %j.addr, align 4
  %sub.19 = sub i32 %j.load.7, 8
  %chunks.load.9.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.9.element.load, i32 %sub.19
  %chunks.load.9.element.load.element.load = load i32, i32* %chunks.load.9.element.load.element.addr, align 4
  %xor = xor i32 %chunks.load.8.element.load.element.load, %chunks.load.9.element.load.element.load
  %chunks.load.10 = load i32**, i32*** @chunks, align 8
  %i.load.27 = load i32, i32* %i.addr.2, align 4
  %chunks.load.10.element.addr = getelementptr inbounds i32*, i32** %chunks.load.10, i32 %i.load.27
  %chunks.load.10.element.load = load i32*, i32** %chunks.load.10.element.addr, align 8
  %j.load.8 = load i32, i32* %j.addr, align 4
  %sub.20 = sub i32 %j.load.8, 14
  %chunks.load.10.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.10.element.load, i32 %sub.20
  %chunks.load.10.element.load.element.load = load i32, i32* %chunks.load.10.element.load.element.addr, align 4
  %xor.1 = xor i32 %xor, %chunks.load.10.element.load.element.load
  %chunks.load.11 = load i32**, i32*** @chunks, align 8
  %i.load.28 = load i32, i32* %i.addr.2, align 4
  %chunks.load.11.element.addr = getelementptr inbounds i32*, i32** %chunks.load.11, i32 %i.load.28
  %chunks.load.11.element.load = load i32*, i32** %chunks.load.11.element.addr, align 8
  %j.load.9 = load i32, i32* %j.addr, align 4
  %sub.21 = sub i32 %j.load.9, 16
  %chunks.load.11.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.11.element.load, i32 %sub.21
  %chunks.load.11.element.load.element.load = load i32, i32* %chunks.load.11.element.load.element.addr, align 4
  %xor.2 = xor i32 %xor.1, %chunks.load.11.element.load.element.load
  %rotate_left.call = call i32 @rotate_left(i32 %xor.2, i32 1)
  store i32 %rotate_left.call, i32* %chunks.load.7.element.load.element.addr, align 4
  br label %for.incr.6

for.exit.6:                                       ; preds = %for.cond.6
  %h0.load = load i32, i32* %h0.addr, align 4
  store i32 %h0.load, i32* %a.addr, align 4
  %h1.load = load i32, i32* %h1.addr, align 4
  store i32 %h1.load, i32* %b.addr, align 4
  %h2.load = load i32, i32* %h2.addr, align 4
  store i32 %h2.load, i32* %c.addr, align 4
  %h3.load = load i32, i32* %h3.addr, align 4
  store i32 %h3.load, i32* %d.addr, align 4
  %h4.load = load i32, i32* %h4.addr, align 4
  store i32 %h4.load, i32* %e.addr, align 4
  store i32 0, i32* %j.addr, align 4
  br label %for.cond.7

for.cond.7:                                       ; preds = %for.incr.7, %for.exit.6
  %j.load.10 = load i32, i32* %j.addr, align 4
  %icmp.20 = icmp slt i32 %j.load.10, 80
  br i1 %icmp.20, label %for.body.7, label %for.exit.7

for.incr.7:                                       ; preds = %if.exit.8
  %j.load.11 = load i32, i32* %j.addr, align 4
  %add.23 = add i32 %j.load.11, 1
  store i32 %add.23, i32* %j.addr, align 4
  br label %for.cond.7

for.body.7:                                       ; preds = %for.cond.7
  %j.load.12 = load i32, i32* %j.addr, align 4
  %icmp.21 = icmp slt i32 %j.load.12, 20
  br i1 %icmp.21, label %if.true.8, label %if.false.8

for.exit.7:                                       ; preds = %for.cond.7
  %h0.load.1 = load i32, i32* %h0.addr, align 4
  %a.load.2 = load i32, i32* %a.addr, align 4
  %add.call.4 = call i32 @add(i32 %h0.load.1, i32 %a.load.2)
  store i32 %add.call.4, i32* %h0.addr, align 4
  %h1.load.1 = load i32, i32* %h1.addr, align 4
  %b.load.7 = load i32, i32* %b.addr, align 4
  %add.call.5 = call i32 @add(i32 %h1.load.1, i32 %b.load.7)
  store i32 %add.call.5, i32* %h1.addr, align 4
  %h2.load.1 = load i32, i32* %h2.addr, align 4
  %c.load.6 = load i32, i32* %c.addr, align 4
  %add.call.6 = call i32 @add(i32 %h2.load.1, i32 %c.load.6)
  store i32 %add.call.6, i32* %h2.addr, align 4
  %h3.load.1 = load i32, i32* %h3.addr, align 4
  %d.load.6 = load i32, i32* %d.addr, align 4
  %add.call.7 = call i32 @add(i32 %h3.load.1, i32 %d.load.6)
  store i32 %add.call.7, i32* %h3.addr, align 4
  %h4.load.1 = load i32, i32* %h4.addr, align 4
  %e.load.1 = load i32, i32* %e.addr, align 4
  %add.call.8 = call i32 @add(i32 %h4.load.1, i32 %e.load.1)
  store i32 %add.call.8, i32* %h4.addr, align 4
  br label %for.incr.5

if.true.8:                                        ; preds = %for.body.7
  %b.load.4 = load i32, i32* %b.addr, align 4
  %c.load.4 = load i32, i32* %c.addr, align 4
  %and.17 = and i32 %b.load.4, %c.load.4
  %b.load.5 = load i32, i32* %b.addr, align 4
  %xor.7 = xor i32 %b.load.5, -1
  %d.load.4 = load i32, i32* %d.addr, align 4
  %and.18 = and i32 %xor.7, %d.load.4
  %or.9 = or i32 %and.17, %and.18
  store i32 %or.9, i32* %f.addr, align 4
  store i32 1518500249, i32* %k.addr, align 4
  br label %if.exit.8

if.exit.8:                                        ; preds = %if.exit.9, %if.true.8
  %a.load = load i32, i32* %a.addr, align 4
  %rotate_left.call.1 = call i32 @rotate_left(i32 %a.load, i32 5)
  %e.load = load i32, i32* %e.addr, align 4
  %add.call = call i32 @add(i32 %rotate_left.call.1, i32 %e.load)
  %f.load = load i32, i32* %f.addr, align 4
  %k.load = load i32, i32* %k.addr, align 4
  %add.call.1 = call i32 @add(i32 %f.load, i32 %k.load)
  %add.call.2 = call i32 @add(i32 %add.call, i32 %add.call.1)
  %chunks.load.12 = load i32**, i32*** @chunks, align 8
  %i.load.29 = load i32, i32* %i.addr.2, align 4
  %chunks.load.12.element.addr = getelementptr inbounds i32*, i32** %chunks.load.12, i32 %i.load.29
  %chunks.load.12.element.load = load i32*, i32** %chunks.load.12.element.addr, align 8
  %j.load.15 = load i32, i32* %j.addr, align 4
  %chunks.load.12.element.load.element.addr = getelementptr inbounds i32, i32* %chunks.load.12.element.load, i32 %j.load.15
  %chunks.load.12.element.load.element.load = load i32, i32* %chunks.load.12.element.load.element.addr, align 4
  %add.call.3 = call i32 @add(i32 %add.call.2, i32 %chunks.load.12.element.load.element.load)
  store i32 %add.call.3, i32* %temp.addr, align 4
  %d.load.5 = load i32, i32* %d.addr, align 4
  store i32 %d.load.5, i32* %e.addr, align 4
  %c.load.5 = load i32, i32* %c.addr, align 4
  store i32 %c.load.5, i32* %d.addr, align 4
  %b.load.6 = load i32, i32* %b.addr, align 4
  %rotate_left.call.2 = call i32 @rotate_left(i32 %b.load.6, i32 30)
  store i32 %rotate_left.call.2, i32* %c.addr, align 4
  %a.load.1 = load i32, i32* %a.addr, align 4
  store i32 %a.load.1, i32* %b.addr, align 4
  %temp.load = load i32, i32* %temp.addr, align 4
  store i32 %temp.load, i32* %a.addr, align 4
  br label %for.incr.7

if.false.8:                                       ; preds = %for.body.7
  %j.load.13 = load i32, i32* %j.addr, align 4
  %icmp.22 = icmp slt i32 %j.load.13, 40
  br i1 %icmp.22, label %if.true.9, label %if.false.9

if.true.9:                                        ; preds = %if.false.8
  %b.load.3 = load i32, i32* %b.addr, align 4
  %c.load.3 = load i32, i32* %c.addr, align 4
  %xor.5 = xor i32 %b.load.3, %c.load.3
  %d.load.3 = load i32, i32* %d.addr, align 4
  %xor.6 = xor i32 %xor.5, %d.load.3
  store i32 %xor.6, i32* %f.addr, align 4
  store i32 1859775393, i32* %k.addr, align 4
  br label %if.exit.9

if.exit.9:                                        ; preds = %if.exit.10, %if.true.9
  br label %if.exit.8

if.false.9:                                       ; preds = %if.false.8
  %j.load.14 = load i32, i32* %j.addr, align 4
  %icmp.23 = icmp slt i32 %j.load.14, 60
  br i1 %icmp.23, label %if.true.10, label %if.false.10

if.true.10:                                       ; preds = %if.false.9
  %b.load.1 = load i32, i32* %b.addr, align 4
  %c.load.1 = load i32, i32* %c.addr, align 4
  %and.14 = and i32 %b.load.1, %c.load.1
  %b.load.2 = load i32, i32* %b.addr, align 4
  %d.load.1 = load i32, i32* %d.addr, align 4
  %and.15 = and i32 %b.load.2, %d.load.1
  %or.7 = or i32 %and.14, %and.15
  %c.load.2 = load i32, i32* %c.addr, align 4
  %d.load.2 = load i32, i32* %d.addr, align 4
  %and.16 = and i32 %c.load.2, %d.load.2
  %or.8 = or i32 %or.7, %and.16
  store i32 %or.8, i32* %f.addr, align 4
  %lohi.call.4 = call i32 @lohi(i32 48348, i32 36635)
  store i32 %lohi.call.4, i32* %k.addr, align 4
  br label %if.exit.10

if.exit.10:                                       ; preds = %if.false.10, %if.true.10
  br label %if.exit.9

if.false.10:                                      ; preds = %if.false.9
  %b.load = load i32, i32* %b.addr, align 4
  %c.load = load i32, i32* %c.addr, align 4
  %xor.3 = xor i32 %b.load, %c.load
  %d.load = load i32, i32* %d.addr, align 4
  %xor.4 = xor i32 %xor.3, %d.load
  store i32 %xor.4, i32* %f.addr, align 4
  %lohi.call.3 = call i32 @lohi(i32 49622, i32 51810)
  store i32 %lohi.call.3, i32* %k.addr, align 4
  br label %if.exit.10
}

define void @computeSHA1(i8* %input.1) {
entry.19:
  %result.addr.1 = alloca i32*, align 8
  %i.addr.3 = alloca i32, align 4
  %input.addr.1 = alloca i8*, align 8
  store i8* %input.1, i8** %input.addr.1, align 8
  store i32 0, i32* %i.addr.3, align 4
  br label %for.cond.8

exit.19:                                          ; preds = %for.exit.9
  ret void

for.cond.8:                                       ; preds = %for.incr.8, %entry.19
  %i.load.30 = load i32, i32* %i.addr.3, align 4
  %input.load.1 = load i8*, i8** %input.addr.1, align 8
  %_str_length.call.1 = call i32 @_str_length(i8* %input.load.1)
  %icmp.24 = icmp slt i32 %i.load.30, %_str_length.call.1
  br i1 %icmp.24, label %for.body.8, label %for.exit.8

for.incr.8:                                       ; preds = %for.body.8
  %i.load.31 = load i32, i32* %i.addr.3, align 4
  %add.24 = add i32 %i.load.31, 1
  store i32 %add.24, i32* %i.addr.3, align 4
  br label %for.cond.8

for.body.8:                                       ; preds = %for.cond.8
  %inputBuffer.load = load i32*, i32** @inputBuffer, align 8
  %i.load.32 = load i32, i32* %i.addr.3, align 4
  %inputBuffer.load.element.addr = getelementptr inbounds i32, i32* %inputBuffer.load, i32 %i.load.32
  %inputBuffer.load.element.load = load i32, i32* %inputBuffer.load.element.addr, align 4
  %input.load.2 = load i8*, i8** %input.addr.1, align 8
  %i.load.33 = load i32, i32* %i.addr.3, align 4
  %_str_ord.call.1 = call i32 @_str_ord(i8* %input.load.2, i32 %i.load.33)
  store i32 %_str_ord.call.1, i32* %inputBuffer.load.element.addr, align 4
  br label %for.incr.8

for.exit.8:                                       ; preds = %for.cond.8
  %inputBuffer.load.1 = load i32*, i32** @inputBuffer, align 8
  %input.load.3 = load i8*, i8** %input.addr.1, align 8
  %_str_length.call.2 = call i32 @_str_length(i8* %input.load.3)
  %sha1.call = call i32* @sha1(i32* %inputBuffer.load.1, i32 %_str_length.call.2)
  store i32* %sha1.call, i32** %result.addr.1, align 8
  store i32 0, i32* %i.addr.3, align 4
  br label %for.cond.9

for.cond.9:                                       ; preds = %for.incr.9, %for.exit.8
  %i.load.34 = load i32, i32* %i.addr.3, align 4
  %result.load.4 = load i32*, i32** %result.addr.1, align 8
  %bitcast.8 = bitcast i32* %result.load.4 to i32*
  %getelementptr.10 = getelementptr inbounds i32, i32* %bitcast.8, i32 -1
  %getelementptr.10.load = load i32, i32* %getelementptr.10, align 4
  %icmp.25 = icmp slt i32 %i.load.34, %getelementptr.10.load
  br i1 %icmp.25, label %for.body.9, label %for.exit.9

for.incr.9:                                       ; preds = %for.body.9
  %i.load.35 = load i32, i32* %i.addr.3, align 4
  %add.25 = add i32 %i.load.35, 1
  store i32 %add.25, i32* %i.addr.3, align 4
  br label %for.cond.9

for.body.9:                                       ; preds = %for.cond.9
  %result.load.5 = load i32*, i32** %result.addr.1, align 8
  %i.load.36 = load i32, i32* %i.addr.3, align 4
  %result.load.5.element.addr = getelementptr inbounds i32, i32* %result.load.5, i32 %i.load.36
  %result.load.5.element.load = load i32, i32* %result.load.5.element.addr, align 4
  %toStringHex.call = call i8* @toStringHex(i32 %result.load.5.element.load)
  call void @print(i8* %toStringHex.call)
  br label %for.incr.9

for.exit.9:                                       ; preds = %for.cond.9
  %getelementptr.11 = getelementptr inbounds [1 x i8], [1 x i8]* @.strconst.1, i32 0, i32 0
  call void @println(i8* %getelementptr.11)
  br label %exit.19
}

define i32 @nextLetter(i32 %now) {
entry.20:
  %now.addr = alloca i32, align 4
  %func.ret.addr.7 = alloca i32, align 4
  store i32 %now, i32* %now.addr, align 4
  %now.load = load i32, i32* %now.addr, align 4
  %icmp.26 = icmp eq i32 %now.load, 122
  br i1 %icmp.26, label %if.true.11, label %if.false.11

exit.20:                                          ; preds = %if.exit.13, %if.true.13, %if.true.12, %if.true.11
  %func.ret.load.7 = load i32, i32* %func.ret.addr.7, align 4
  ret i32 %func.ret.load.7

if.true.11:                                       ; preds = %entry.20
  %sub.22 = sub i32 0, 1
  store i32 %sub.22, i32* %func.ret.addr.7, align 4
  br label %exit.20

if.exit.11:                                       ; preds = %if.false.11
  %now.load.1 = load i32, i32* %now.addr, align 4
  %icmp.27 = icmp eq i32 %now.load.1, 90
  br i1 %icmp.27, label %if.true.12, label %if.false.12

if.false.11:                                      ; preds = %entry.20
  br label %if.exit.11

if.true.12:                                       ; preds = %if.exit.11
  store i32 97, i32* %func.ret.addr.7, align 4
  br label %exit.20

if.exit.12:                                       ; preds = %if.false.12
  %now.load.2 = load i32, i32* %now.addr, align 4
  %icmp.28 = icmp eq i32 %now.load.2, 57
  br i1 %icmp.28, label %if.true.13, label %if.false.13

if.false.12:                                      ; preds = %if.exit.11
  br label %if.exit.12

if.true.13:                                       ; preds = %if.exit.12
  store i32 65, i32* %func.ret.addr.7, align 4
  br label %exit.20

if.exit.13:                                       ; preds = %if.false.13
  %now.load.3 = load i32, i32* %now.addr, align 4
  %add.26 = add i32 %now.load.3, 1
  store i32 %add.26, i32* %func.ret.addr.7, align 4
  br label %exit.20

if.false.13:                                      ; preds = %if.exit.12
  br label %if.exit.13
}

define i1 @nextText(i32* %now.1, i32 %length.1) {
entry.21:
  %i.addr.4 = alloca i32, align 4
  %length.addr.1 = alloca i32, align 4
  %now.addr.1 = alloca i32*, align 8
  %func.ret.addr.8 = alloca i8, align 1
  store i32* %now.1, i32** %now.addr.1, align 8
  store i32 %length.1, i32* %length.addr.1, align 4
  %length.load.4 = load i32, i32* %length.addr.1, align 4
  %sub.23 = sub i32 %length.load.4, 1
  store i32 %sub.23, i32* %i.addr.4, align 4
  br label %for.cond.10

exit.21:                                          ; preds = %if.false.14, %for.exit.10
  %func.ret.load.8 = load i8, i8* %func.ret.addr.8, align 1
  %trunc = trunc i8 %func.ret.load.8 to i1
  ret i1 %trunc

for.cond.10:                                      ; preds = %for.incr.10, %entry.21
  %i.load.37 = load i32, i32* %i.addr.4, align 4
  %icmp.29 = icmp sge i32 %i.load.37, 0
  br i1 %icmp.29, label %for.body.10, label %for.exit.10

for.incr.10:                                      ; preds = %if.exit.14
  %i.load.38 = load i32, i32* %i.addr.4, align 4
  %sub.24 = sub i32 %i.load.38, 1
  store i32 %sub.24, i32* %i.addr.4, align 4
  br label %for.cond.10

for.body.10:                                      ; preds = %for.cond.10
  %now.load.4 = load i32*, i32** %now.addr.1, align 8
  %i.load.39 = load i32, i32* %i.addr.4, align 4
  %now.load.4.element.addr = getelementptr inbounds i32, i32* %now.load.4, i32 %i.load.39
  %now.load.4.element.load = load i32, i32* %now.load.4.element.addr, align 4
  %now.load.5 = load i32*, i32** %now.addr.1, align 8
  %i.load.40 = load i32, i32* %i.addr.4, align 4
  %now.load.5.element.addr = getelementptr inbounds i32, i32* %now.load.5, i32 %i.load.40
  %now.load.5.element.load = load i32, i32* %now.load.5.element.addr, align 4
  %nextLetter.call = call i32 @nextLetter(i32 %now.load.5.element.load)
  store i32 %nextLetter.call, i32* %now.load.4.element.addr, align 4
  %now.load.6 = load i32*, i32** %now.addr.1, align 8
  %i.load.41 = load i32, i32* %i.addr.4, align 4
  %now.load.6.element.addr = getelementptr inbounds i32, i32* %now.load.6, i32 %i.load.41
  %now.load.6.element.load = load i32, i32* %now.load.6.element.addr, align 4
  %sub.25 = sub i32 0, 1
  %icmp.30 = icmp eq i32 %now.load.6.element.load, %sub.25
  br i1 %icmp.30, label %if.true.14, label %if.false.14

for.exit.10:                                      ; preds = %for.cond.10
  %zext.1 = zext i1 false to i8
  store i8 %zext.1, i8* %func.ret.addr.8, align 1
  br label %exit.21

if.true.14:                                       ; preds = %for.body.10
  %now.load.7 = load i32*, i32** %now.addr.1, align 8
  %i.load.42 = load i32, i32* %i.addr.4, align 4
  %now.load.7.element.addr = getelementptr inbounds i32, i32* %now.load.7, i32 %i.load.42
  %now.load.7.element.load = load i32, i32* %now.load.7.element.addr, align 4
  store i32 48, i32* %now.load.7.element.addr, align 4
  br label %if.exit.14

if.exit.14:                                       ; preds = %if.true.14
  br label %for.incr.10

if.false.14:                                      ; preds = %for.body.10
  %zext = zext i1 true to i8
  store i8 %zext, i8* %func.ret.addr.8, align 1
  br label %exit.21
}

define i1 @array_equal(i32* %a, i32* %b) {
entry.22:
  %i.addr.5 = alloca i32, align 4
  %b.addr.1 = alloca i32*, align 8
  %a.addr.1 = alloca i32*, align 8
  %func.ret.addr.9 = alloca i8, align 1
  store i32* %a, i32** %a.addr.1, align 8
  store i32* %b, i32** %b.addr.1, align 8
  %a.load.3 = load i32*, i32** %a.addr.1, align 8
  %bitcast.9 = bitcast i32* %a.load.3 to i32*
  %getelementptr.12 = getelementptr inbounds i32, i32* %bitcast.9, i32 -1
  %getelementptr.12.load = load i32, i32* %getelementptr.12, align 4
  %b.load.8 = load i32*, i32** %b.addr.1, align 8
  %bitcast.10 = bitcast i32* %b.load.8 to i32*
  %getelementptr.13 = getelementptr inbounds i32, i32* %bitcast.10, i32 -1
  %getelementptr.13.load = load i32, i32* %getelementptr.13, align 4
  %icmp.31 = icmp ne i32 %getelementptr.12.load, %getelementptr.13.load
  br i1 %icmp.31, label %if.true.15, label %if.false.15

exit.22:                                          ; preds = %if.true.16, %for.exit.11, %if.true.15
  %func.ret.load.9 = load i8, i8* %func.ret.addr.9, align 1
  %trunc.1 = trunc i8 %func.ret.load.9 to i1
  ret i1 %trunc.1

if.true.15:                                       ; preds = %entry.22
  %zext.2 = zext i1 false to i8
  store i8 %zext.2, i8* %func.ret.addr.9, align 1
  br label %exit.22

if.exit.15:                                       ; preds = %if.false.15
  store i32 0, i32* %i.addr.5, align 4
  br label %for.cond.11

if.false.15:                                      ; preds = %entry.22
  br label %if.exit.15

for.cond.11:                                      ; preds = %for.incr.11, %if.exit.15
  %i.load.43 = load i32, i32* %i.addr.5, align 4
  %a.load.4 = load i32*, i32** %a.addr.1, align 8
  %bitcast.11 = bitcast i32* %a.load.4 to i32*
  %getelementptr.14 = getelementptr inbounds i32, i32* %bitcast.11, i32 -1
  %getelementptr.14.load = load i32, i32* %getelementptr.14, align 4
  %icmp.32 = icmp slt i32 %i.load.43, %getelementptr.14.load
  br i1 %icmp.32, label %for.body.11, label %for.exit.11

for.incr.11:                                      ; preds = %if.exit.16
  %i.load.44 = load i32, i32* %i.addr.5, align 4
  %add.27 = add i32 %i.load.44, 1
  store i32 %add.27, i32* %i.addr.5, align 4
  br label %for.cond.11

for.body.11:                                      ; preds = %for.cond.11
  %a.load.5 = load i32*, i32** %a.addr.1, align 8
  %i.load.45 = load i32, i32* %i.addr.5, align 4
  %a.load.5.element.addr = getelementptr inbounds i32, i32* %a.load.5, i32 %i.load.45
  %a.load.5.element.load = load i32, i32* %a.load.5.element.addr, align 4
  %b.load.9 = load i32*, i32** %b.addr.1, align 8
  %i.load.46 = load i32, i32* %i.addr.5, align 4
  %b.load.9.element.addr = getelementptr inbounds i32, i32* %b.load.9, i32 %i.load.46
  %b.load.9.element.load = load i32, i32* %b.load.9.element.addr, align 4
  %icmp.33 = icmp ne i32 %a.load.5.element.load, %b.load.9.element.load
  br i1 %icmp.33, label %if.true.16, label %if.false.16

for.exit.11:                                      ; preds = %for.cond.11
  %zext.4 = zext i1 true to i8
  store i8 %zext.4, i8* %func.ret.addr.9, align 1
  br label %exit.22

if.true.16:                                       ; preds = %for.body.11
  %zext.3 = zext i1 false to i8
  store i8 %zext.3, i8* %func.ret.addr.9, align 1
  br label %exit.22

if.exit.16:                                       ; preds = %if.false.16
  br label %for.incr.11

if.false.16:                                      ; preds = %for.body.11
  br label %if.exit.16
}

define void @crackSHA1(i8* %input.2) {
entry.23:
  %out.addr = alloca i32*, align 8
  %digit.addr.2 = alloca i32, align 4
  %MAXDIGIT.addr = alloca i32, align 4
  %i.addr.6 = alloca i32, align 4
  %target.addr = alloca i32*, align 8
  %input.addr.2 = alloca i8*, align 8
  store i8* %input.2, i8** %input.addr.2, align 8
  %mul.10 = mul i32 5, 4
  %add.28 = add i32 %mul.10, 4
  %_bottom_malloc.call.4 = call noalias i8* @_bottom_malloc(i32 %add.28)
  %bitcast.12 = bitcast i8* %_bottom_malloc.call.4 to i32*
  store i32 5, i32* %bitcast.12, align 4
  %getelementptr.15 = getelementptr inbounds i32, i32* %bitcast.12, i32 1
  %bitcast.13 = bitcast i32* %getelementptr.15 to i32*
  store i32* %bitcast.13, i32** %target.addr, align 8
  %input.load.4 = load i8*, i8** %input.addr.2, align 8
  %_str_length.call.3 = call i32 @_str_length(i8* %input.load.4)
  %icmp.34 = icmp ne i32 %_str_length.call.3, 40
  br i1 %icmp.34, label %if.true.17, label %if.false.17

exit.23:                                          ; preds = %for.exit.16, %for.exit.14, %if.true.17
  ret void

if.true.17:                                       ; preds = %entry.23
  %getelementptr.16 = getelementptr inbounds [14 x i8], [14 x i8]* @.strconst.3, i32 0, i32 0
  call void @println(i8* %getelementptr.16)
  br label %exit.23

if.exit.17:                                       ; preds = %if.false.17
  store i32 0, i32* %i.addr.6, align 4
  br label %for.cond.12

if.false.17:                                      ; preds = %entry.23
  br label %if.exit.17

for.cond.12:                                      ; preds = %for.incr.12, %if.exit.17
  %i.load.47 = load i32, i32* %i.addr.6, align 4
  %icmp.35 = icmp slt i32 %i.load.47, 5
  br i1 %icmp.35, label %for.body.12, label %for.exit.12

for.incr.12:                                      ; preds = %for.body.12
  %i.load.48 = load i32, i32* %i.addr.6, align 4
  %add.29 = add i32 %i.load.48, 1
  store i32 %add.29, i32* %i.addr.6, align 4
  br label %for.cond.12

for.body.12:                                      ; preds = %for.cond.12
  %target.load = load i32*, i32** %target.addr, align 8
  %i.load.49 = load i32, i32* %i.addr.6, align 4
  %target.load.element.addr = getelementptr inbounds i32, i32* %target.load, i32 %i.load.49
  %target.load.element.load = load i32, i32* %target.load.element.addr, align 4
  store i32 0, i32* %target.load.element.addr, align 4
  br label %for.incr.12

for.exit.12:                                      ; preds = %for.cond.12
  store i32 0, i32* %i.addr.6, align 4
  br label %for.cond.13

for.cond.13:                                      ; preds = %for.incr.13, %for.exit.12
  %i.load.50 = load i32, i32* %i.addr.6, align 4
  %icmp.36 = icmp slt i32 %i.load.50, 40
  br i1 %icmp.36, label %for.body.13, label %for.exit.13

for.incr.13:                                      ; preds = %for.body.13
  %i.load.51 = load i32, i32* %i.addr.6, align 4
  %add.30 = add i32 %i.load.51, 4
  store i32 %add.30, i32* %i.addr.6, align 4
  br label %for.cond.13

for.body.13:                                      ; preds = %for.cond.13
  %target.load.1 = load i32*, i32** %target.addr, align 8
  %i.load.52 = load i32, i32* %i.addr.6, align 4
  %sdiv.9 = sdiv i32 %i.load.52, 8
  %target.load.1.element.addr = getelementptr inbounds i32, i32* %target.load.1, i32 %sdiv.9
  %target.load.1.element.load = load i32, i32* %target.load.1.element.addr, align 4
  %target.load.2 = load i32*, i32** %target.addr, align 8
  %i.load.53 = load i32, i32* %i.addr.6, align 4
  %sdiv.10 = sdiv i32 %i.load.53, 8
  %target.load.2.element.addr = getelementptr inbounds i32, i32* %target.load.2, i32 %sdiv.10
  %target.load.2.element.load = load i32, i32* %target.load.2.element.addr, align 4
  %input.load.5 = load i8*, i8** %input.addr.2, align 8
  %i.load.54 = load i32, i32* %i.addr.6, align 4
  %i.load.55 = load i32, i32* %i.addr.6, align 4
  %add.31 = add i32 %i.load.55, 4
  %_str_substring.call.1 = call i8* @_str_substring(i8* %input.load.5, i32 %i.load.54, i32 %add.31)
  %hex2int.call = call i32 @hex2int(i8* %_str_substring.call.1)
  %i.load.56 = load i32, i32* %i.addr.6, align 4
  %sdiv.11 = sdiv i32 %i.load.56, 4
  %srem.6 = srem i32 %sdiv.11, 2
  %sub.26 = sub i32 1, %srem.6
  %mul.11 = mul i32 %sub.26, 16
  %shl.10 = shl i32 %hex2int.call, %mul.11
  %or.10 = or i32 %target.load.2.element.load, %shl.10
  store i32 %or.10, i32* %target.load.1.element.addr, align 4
  br label %for.incr.13

for.exit.13:                                      ; preds = %for.cond.13
  store i32 4, i32* %MAXDIGIT.addr, align 4
  store i32 1, i32* %digit.addr.2, align 4
  br label %for.cond.14

for.cond.14:                                      ; preds = %for.incr.14, %for.exit.13
  %digit.load.12 = load i32, i32* %digit.addr.2, align 4
  %MAXDIGIT.load = load i32, i32* %MAXDIGIT.addr, align 4
  %icmp.37 = icmp sle i32 %digit.load.12, %MAXDIGIT.load
  br i1 %icmp.37, label %for.body.14, label %for.exit.14

for.incr.14:                                      ; preds = %while.exit
  %digit.load.13 = load i32, i32* %digit.addr.2, align 4
  %add.32 = add i32 %digit.load.13, 1
  store i32 %add.32, i32* %digit.addr.2, align 4
  br label %for.cond.14

for.body.14:                                      ; preds = %for.cond.14
  store i32 0, i32* %i.addr.6, align 4
  br label %for.cond.15

for.exit.14:                                      ; preds = %for.cond.14
  %getelementptr.18 = getelementptr inbounds [11 x i8], [11 x i8]* @.strconst.4, i32 0, i32 0
  call void @println(i8* %getelementptr.18)
  br label %exit.23

for.cond.15:                                      ; preds = %for.incr.15, %for.body.14
  %i.load.57 = load i32, i32* %i.addr.6, align 4
  %digit.load.14 = load i32, i32* %digit.addr.2, align 4
  %icmp.38 = icmp slt i32 %i.load.57, %digit.load.14
  br i1 %icmp.38, label %for.body.15, label %for.exit.15

for.incr.15:                                      ; preds = %for.body.15
  %i.load.58 = load i32, i32* %i.addr.6, align 4
  %add.33 = add i32 %i.load.58, 1
  store i32 %add.33, i32* %i.addr.6, align 4
  br label %for.cond.15

for.body.15:                                      ; preds = %for.cond.15
  %inputBuffer.load.2 = load i32*, i32** @inputBuffer, align 8
  %i.load.59 = load i32, i32* %i.addr.6, align 4
  %inputBuffer.load.2.element.addr = getelementptr inbounds i32, i32* %inputBuffer.load.2, i32 %i.load.59
  %inputBuffer.load.2.element.load = load i32, i32* %inputBuffer.load.2.element.addr, align 4
  store i32 48, i32* %inputBuffer.load.2.element.addr, align 4
  br label %for.incr.15

for.exit.15:                                      ; preds = %for.cond.15
  br label %while.cond

while.cond:                                       ; preds = %if.exit.19, %for.exit.15
  br i1 true, label %while.body, label %while.exit

while.body:                                       ; preds = %while.cond
  %inputBuffer.load.3 = load i32*, i32** @inputBuffer, align 8
  %digit.load.15 = load i32, i32* %digit.addr.2, align 4
  %sha1.call.1 = call i32* @sha1(i32* %inputBuffer.load.3, i32 %digit.load.15)
  store i32* %sha1.call.1, i32** %out.addr, align 8
  %out.load = load i32*, i32** %out.addr, align 8
  %target.load.3 = load i32*, i32** %target.addr, align 8
  %array_equal.call = call i1 @array_equal(i32* %out.load, i32* %target.load.3)
  br i1 %array_equal.call, label %if.true.18, label %if.false.18

while.exit:                                       ; preds = %if.true.19, %while.cond
  br label %for.incr.14

if.true.18:                                       ; preds = %while.body
  store i32 0, i32* %i.addr.6, align 4
  br label %for.cond.16

if.exit.18:                                       ; preds = %if.false.18
  %inputBuffer.load.5 = load i32*, i32** @inputBuffer, align 8
  %digit.load.17 = load i32, i32* %digit.addr.2, align 4
  %nextText.call = call i1 @nextText(i32* %inputBuffer.load.5, i32 %digit.load.17)
  %xor.8 = xor i1 %nextText.call, true
  br i1 %xor.8, label %if.true.19, label %if.false.19

if.false.18:                                      ; preds = %while.body
  br label %if.exit.18

for.cond.16:                                      ; preds = %for.incr.16, %if.true.18
  %i.load.60 = load i32, i32* %i.addr.6, align 4
  %digit.load.16 = load i32, i32* %digit.addr.2, align 4
  %icmp.39 = icmp slt i32 %i.load.60, %digit.load.16
  br i1 %icmp.39, label %for.body.16, label %for.exit.16

for.incr.16:                                      ; preds = %for.body.16
  %i.load.61 = load i32, i32* %i.addr.6, align 4
  %add.34 = add i32 %i.load.61, 1
  store i32 %add.34, i32* %i.addr.6, align 4
  br label %for.cond.16

for.body.16:                                      ; preds = %for.cond.16
  %inputBuffer.load.4 = load i32*, i32** @inputBuffer, align 8
  %i.load.62 = load i32, i32* %i.addr.6, align 4
  %inputBuffer.load.4.element.addr = getelementptr inbounds i32, i32* %inputBuffer.load.4, i32 %i.load.62
  %inputBuffer.load.4.element.load = load i32, i32* %inputBuffer.load.4.element.addr, align 4
  %int2chr.call.2 = call i8* @int2chr(i32 %inputBuffer.load.4.element.load)
  call void @print(i8* %int2chr.call.2)
  br label %for.incr.16

for.exit.16:                                      ; preds = %for.cond.16
  %getelementptr.17 = getelementptr inbounds [1 x i8], [1 x i8]* @.strconst.1, i32 0, i32 0
  call void @println(i8* %getelementptr.17)
  br label %exit.23

if.true.19:                                       ; preds = %if.exit.18
  br label %while.exit

if.exit.19:                                       ; preds = %if.false.19
  br label %while.cond

if.false.19:                                      ; preds = %if.exit.18
  br label %if.exit.19
}

define i32 @main() {
entry.24:
  %input.addr.3 = alloca i8*, align 8
  %op.addr = alloca i32, align 4
  %func.ret.addr.10 = alloca i32, align 4
  call void @global_var_init()
  store i32 0, i32* %func.ret.addr.10, align 4
  br label %while.cond.1

exit.24:                                          ; preds = %while.exit.1
  %func.ret.load.10 = load i32, i32* %func.ret.addr.10, align 4
  ret i32 %func.ret.load.10

while.cond.1:                                     ; preds = %if.exit.21, %entry.24
  br i1 true, label %while.body.1, label %while.exit.1

while.body.1:                                     ; preds = %while.cond.1
  %getInt.call = call i32 @getInt()
  store i32 %getInt.call, i32* %op.addr, align 4
  %op.load = load i32, i32* %op.addr, align 4
  %icmp.40 = icmp eq i32 %op.load, 0
  br i1 %icmp.40, label %if.true.20, label %if.false.20

while.exit.1:                                     ; preds = %if.true.20, %while.cond.1
  store i32 0, i32* %func.ret.addr.10, align 4
  br label %exit.24

if.true.20:                                       ; preds = %while.body.1
  br label %while.exit.1

if.exit.20:                                       ; preds = %if.false.20
  %op.load.1 = load i32, i32* %op.addr, align 4
  %icmp.41 = icmp eq i32 %op.load.1, 1
  br i1 %icmp.41, label %if.true.21, label %if.false.21

if.false.20:                                      ; preds = %while.body.1
  br label %if.exit.20

if.true.21:                                       ; preds = %if.exit.20
  %getString.call.1 = call i8* @getString()
  store i8* %getString.call.1, i8** %input.addr.3, align 8
  %input.load.7 = load i8*, i8** %input.addr.3, align 8
  call void @computeSHA1(i8* %input.load.7)
  br label %if.exit.21

if.exit.21:                                       ; preds = %if.exit.22, %if.true.21
  br label %while.cond.1

if.false.21:                                      ; preds = %if.exit.20
  %op.load.2 = load i32, i32* %op.addr, align 4
  %icmp.42 = icmp eq i32 %op.load.2, 2
  br i1 %icmp.42, label %if.true.22, label %if.false.22

if.true.22:                                       ; preds = %if.false.21
  %getString.call = call i8* @getString()
  store i8* %getString.call, i8** %input.addr.3, align 8
  %input.load.6 = load i8*, i8** %input.addr.3, align 8
  call void @crackSHA1(i8* %input.load.6)
  br label %if.exit.22

if.exit.22:                                       ; preds = %if.false.22, %if.true.22
  br label %if.exit.21

if.false.22:                                      ; preds = %if.false.21
  br label %if.exit.22
}

define void @global_var_init() {
entry:
  %getelementptr = getelementptr inbounds [96 x i8], [96 x i8]* @.strconst, i32 0, i32 0
  store i8* %getelementptr, i8** @asciiTable, align 8
  store i32 100, i32* @MAXCHUNK, align 4
  %MAXCHUNK.load = load i32, i32* @MAXCHUNK, align 4
  %sub = sub i32 %MAXCHUNK.load, 1
  %mul = mul i32 %sub, 64
  %sub.1 = sub i32 %mul, 16
  store i32 %sub.1, i32* @MAXLENGTH, align 4
  %MAXCHUNK.load.1 = load i32, i32* @MAXCHUNK, align 4
  %mul.1 = mul i32 %MAXCHUNK.load.1, 8
  %add.1 = add i32 %mul.1, 4
  %_bottom_malloc.call = call noalias i8* @_bottom_malloc(i32 %add.1)
  %bitcast = bitcast i8* %_bottom_malloc.call to i32*
  store i32 %MAXCHUNK.load.1, i32* %bitcast, align 4
  %getelementptr.1 = getelementptr inbounds i32, i32* %bitcast, i32 1
  %bitcast.1 = bitcast i32* %getelementptr.1 to i32**
  %getelementptr.2 = getelementptr inbounds i32*, i32** %bitcast.1, i32 %MAXCHUNK.load.1
  br label %new.cond

exit:                                             ; preds = %new.exit
  ret void

new.cond:                                         ; preds = %new.body, %entry
  %phi = phi i32** [ %bitcast.1, %entry ], [ %getelementptr.3, %new.body ]
  %icmp = icmp ne i32** %phi, %getelementptr.2
  br i1 %icmp, label %new.body, label %new.exit

new.body:                                         ; preds = %new.cond
  %mul.2 = mul i32 80, 4
  %add.2 = add i32 %mul.2, 4
  %_bottom_malloc.call.1 = call noalias i8* @_bottom_malloc(i32 %add.2)
  %bitcast.2 = bitcast i8* %_bottom_malloc.call.1 to i32*
  store i32 80, i32* %bitcast.2, align 4
  %getelementptr.4 = getelementptr inbounds i32, i32* %bitcast.2, i32 1
  %bitcast.3 = bitcast i32* %getelementptr.4 to i32*
  store i32* %bitcast.3, i32** %phi, align 8
  %getelementptr.3 = getelementptr inbounds i32*, i32** %phi, i32 1
  br label %new.cond

new.exit:                                         ; preds = %new.cond
  store i32** %bitcast.1, i32*** @chunks, align 8
  %MAXLENGTH.load = load i32, i32* @MAXLENGTH, align 4
  %mul.3 = mul i32 %MAXLENGTH.load, 4
  %add.3 = add i32 %mul.3, 4
  %_bottom_malloc.call.2 = call noalias i8* @_bottom_malloc(i32 %add.3)
  %bitcast.4 = bitcast i8* %_bottom_malloc.call.2 to i32*
  store i32 %MAXLENGTH.load, i32* %bitcast.4, align 4
  %getelementptr.5 = getelementptr inbounds i32, i32* %bitcast.4, i32 1
  %bitcast.5 = bitcast i32* %getelementptr.5 to i32*
  store i32* %bitcast.5, i32** @inputBuffer, align 8
  %mul.4 = mul i32 5, 4
  %add.4 = add i32 %mul.4, 4
  %_bottom_malloc.call.3 = call noalias i8* @_bottom_malloc(i32 %add.4)
  %bitcast.6 = bitcast i8* %_bottom_malloc.call.3 to i32*
  store i32 5, i32* %bitcast.6, align 4
  %getelementptr.6 = getelementptr inbounds i32, i32* %bitcast.6, i32 1
  %bitcast.7 = bitcast i32* %getelementptr.6 to i32*
  store i32* %bitcast.7, i32** @outputBuffer, align 8
  br label %exit
}

; Function Attrs: noinline uwtable
define internal void @_GLOBAL__sub_I_builtin.cpp() #0 section ".text.startup" {
  call void @__cxx_global_var_init()
  ret void
}

; Function Attrs: noinline uwtable
define internal void @__cxx_global_var_init() #0 section ".text.startup" {
  call void @_ZNSt8ios_base4InitC1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1) @_ZStL8__ioinit)
  %1 = call i32 @__cxa_atexit(void (i8*)* bitcast (void (%"class.std::ios_base::Init"*)* @_ZNSt8ios_base4InitD1Ev to void (i8*)*), i8* getelementptr inbounds (%"class.std::ios_base::Init", %"class.std::ios_base::Init"* @_ZStL8__ioinit, i32 0, i32 0), i8* @__dso_handle) #3
  ret void
}

declare void @_ZNSt8ios_base4InitC1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1)) unnamed_addr #1

; Function Attrs: nounwind
declare void @_ZNSt8ios_base4InitD1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1)) unnamed_addr #2

; Function Attrs: nounwind
declare i32 @__cxa_atexit(void (i8*)*, i8*, i8*) #3

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local void @print(i8* noundef %0) #4 {
  %2 = alloca i8*, align 8
  store i8* %0, i8** %2, align 8
  %3 = load i8*, i8** %2, align 8
  %4 = call i32 (i8*, ...) @printf(i8* noundef getelementptr inbounds ([3 x i8], [3 x i8]* @.str, i64 0, i64 0), i8* noundef %3)
  ret void
}

declare i32 @printf(i8* noundef, ...) #1

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local void @println(i8* noundef %0) #4 {
  %2 = alloca i8*, align 8
  store i8* %0, i8** %2, align 8
  %3 = load i8*, i8** %2, align 8
  %4 = call i32 (i8*, ...) @printf(i8* noundef getelementptr inbounds ([4 x i8], [4 x i8]* @.str.1, i64 0, i64 0), i8* noundef %3)
  ret void
}

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local void @printInt(i32 noundef %0) #4 {
  %2 = alloca i32, align 4
  store i32 %0, i32* %2, align 4
  %3 = load i32, i32* %2, align 4
  %4 = call i32 (i8*, ...) @printf(i8* noundef getelementptr inbounds ([3 x i8], [3 x i8]* @.str.2, i64 0, i64 0), i32 noundef %3)
  ret void
}

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local void @printlnInt(i32 noundef %0) #4 {
  %2 = alloca i32, align 4
  store i32 %0, i32* %2, align 4
  %3 = load i32, i32* %2, align 4
  %4 = call i32 (i8*, ...) @printf(i8* noundef getelementptr inbounds ([4 x i8], [4 x i8]* @.str.3, i64 0, i64 0), i32 noundef %3)
  ret void
}

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local noundef i8* @getString() #4 personality i8* bitcast (i32 (...)* @__gxx_personality_v0 to i8*) {
  %1 = alloca %"class.std::__cxx11::basic_string", align 8
  %2 = alloca i8*, align 8
  %3 = alloca i32, align 4
  %4 = alloca i8*, align 8
  %5 = alloca i8*, align 8
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEC1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1) #3
  %6 = invoke noundef nonnull align 8 dereferenceable(16) %"class.std::basic_istream"* @_ZStrsIcSt11char_traitsIcESaIcEERSt13basic_istreamIT_T0_ES7_RNSt7__cxx1112basic_stringIS4_S5_T1_EE(%"class.std::basic_istream"* noundef nonnull align 8 dereferenceable(16) @_ZSt3cin, %"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1)
          to label %7 unwind label %19

7:                                                ; preds = %0
  %8 = call noundef i8* @_ZNKSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEE5c_strEv(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1) #3
  %9 = call i64 @strlen(i8* noundef %8) #8
  %10 = add i64 %9, 1
  %11 = invoke noalias noundef nonnull i8* @_Znam(i64 noundef %10) #9
          to label %12 unwind label %19

12:                                               ; preds = %7
  store i8* %11, i8** %4, align 8
  %13 = load i8*, i8** %4, align 8
  %14 = call noundef i8* @_ZNKSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEE5c_strEv(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1) #3
  %15 = call i8* @strcpy(i8* noundef %13, i8* noundef %14) #3
  %16 = load i8*, i8** %4, align 8
  %17 = getelementptr inbounds i8, i8* %16, i64 0
  store i8* %17, i8** %5, align 8
  %18 = load i8*, i8** %5, align 8
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEED1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1) #3
  ret i8* %18

19:                                               ; preds = %7, %0
  %20 = landingpad { i8*, i32 }
          cleanup
  %21 = extractvalue { i8*, i32 } %20, 0
  store i8* %21, i8** %2, align 8
  %22 = extractvalue { i8*, i32 } %20, 1
  store i32 %22, i32* %3, align 4
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEED1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %1) #3
  br label %23

23:                                               ; preds = %19
  %24 = load i8*, i8** %2, align 8
  %25 = load i32, i32* %3, align 4
  %26 = insertvalue { i8*, i32 } undef, i8* %24, 0
  %27 = insertvalue { i8*, i32 } %26, i32 %25, 1
  resume { i8*, i32 } %27
}

declare i32 @__gxx_personality_v0(...)

; Function Attrs: nounwind
declare void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEC1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32)) unnamed_addr #2

declare noundef nonnull align 8 dereferenceable(16) %"class.std::basic_istream"* @_ZStrsIcSt11char_traitsIcESaIcEERSt13basic_istreamIT_T0_ES7_RNSt7__cxx1112basic_stringIS4_S5_T1_EE(%"class.std::basic_istream"* noundef nonnull align 8 dereferenceable(16), %"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32)) #1

; Function Attrs: nounwind
declare noundef i8* @_ZNKSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEE5c_strEv(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32)) #2

; Function Attrs: nounwind readonly willreturn
declare i64 @strlen(i8* noundef) #5

; Function Attrs: nobuiltin allocsize(0)
declare noundef nonnull i8* @_Znam(i64 noundef) #6

; Function Attrs: nounwind
declare i8* @strcpy(i8* noundef, i8* noundef) #2

; Function Attrs: nounwind
declare void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEED1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32)) unnamed_addr #2

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local noundef i32 @getInt() #4 {
  %1 = alloca i32, align 4
  %2 = call i32 (i8*, ...) @__isoc99_scanf(i8* noundef getelementptr inbounds ([3 x i8], [3 x i8]* @.str.2, i64 0, i64 0), i32* noundef %1)
  %3 = load i32, i32* %1, align 4
  ret i32 %3
}

declare i32 @__isoc99_scanf(i8* noundef, ...) #1

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local noundef i8* @toString(i32 noundef %0) #4 personality i8* bitcast (i32 (...)* @__gxx_personality_v0 to i8*) {
  %2 = alloca i8*, align 8
  %3 = alloca i32, align 4
  %4 = alloca %"class.std::__cxx11::basic_string", align 8
  %5 = alloca i32, align 4
  %6 = alloca i8*, align 8
  %7 = alloca i8*, align 8
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  %10 = alloca i8*, align 8
  %11 = alloca i32, align 4
  %12 = alloca i32, align 4
  store i32 %0, i32* %3, align 4
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEC1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4) #3
  store i32 0, i32* %5, align 4
  %13 = load i32, i32* %3, align 4
  %14 = icmp eq i32 %13, 0
  br i1 %14, label %15, label %28

15:                                               ; preds = %1
  %16 = invoke noalias noundef nonnull i8* @_Znam(i64 noundef 2) #9
          to label %17 unwind label %24

17:                                               ; preds = %15
  store i8* %16, i8** %6, align 8
  %18 = load i8*, i8** %6, align 8
  %19 = getelementptr inbounds i8, i8* %18, i64 0
  store i8 48, i8* %19, align 1
  %20 = load i8*, i8** %6, align 8
  %21 = getelementptr inbounds i8, i8* %20, i64 1
  store i8 0, i8* %21, align 1
  %22 = load i8*, i8** %6, align 8
  %23 = getelementptr inbounds i8, i8* %22, i64 0
  store i8* %23, i8** %2, align 8
  store i32 1, i32* %9, align 4
  br label %89

24:                                               ; preds = %53, %50, %38, %15
  %25 = landingpad { i8*, i32 }
          cleanup
  %26 = extractvalue { i8*, i32 } %25, 0
  store i8* %26, i8** %7, align 8
  %27 = extractvalue { i8*, i32 } %25, 1
  store i32 %27, i32* %8, align 4
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEED1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4) #3
  br label %91

28:                                               ; preds = %1
  %29 = load i32, i32* %3, align 4
  %30 = icmp slt i32 %29, 0
  br i1 %30, label %31, label %34

31:                                               ; preds = %28
  store i32 1, i32* %5, align 4
  %32 = load i32, i32* %3, align 4
  %33 = sub nsw i32 0, %32
  store i32 %33, i32* %3, align 4
  br label %34

34:                                               ; preds = %31, %28
  br label %35

35:                                               ; preds = %44, %34
  %36 = load i32, i32* %3, align 4
  %37 = icmp ne i32 %36, 0
  br i1 %37, label %38, label %47

38:                                               ; preds = %35
  %39 = load i32, i32* %3, align 4
  %40 = srem i32 %39, 10
  %41 = add nsw i32 %40, 48
  %42 = trunc i32 %41 to i8
  %43 = invoke noundef nonnull align 8 dereferenceable(32) %"class.std::__cxx11::basic_string"* @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEpLEc(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4, i8 noundef signext %42)
          to label %44 unwind label %24

44:                                               ; preds = %38
  %45 = load i32, i32* %3, align 4
  %46 = sdiv i32 %45, 10
  store i32 %46, i32* %3, align 4
  br label %35, !llvm.loop !6

47:                                               ; preds = %35
  %48 = load i32, i32* %5, align 4
  %49 = icmp ne i32 %48, 0
  br i1 %49, label %50, label %53

50:                                               ; preds = %47
  %51 = invoke noundef nonnull align 8 dereferenceable(32) %"class.std::__cxx11::basic_string"* @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEpLEc(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4, i8 noundef signext 45)
          to label %52 unwind label %24

52:                                               ; preds = %50
  br label %53

53:                                               ; preds = %52, %47
  %54 = call noundef i8* @_ZNKSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEE5c_strEv(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4) #3
  %55 = call i64 @strlen(i8* noundef %54) #8
  %56 = add i64 %55, 1
  %57 = invoke noalias noundef nonnull i8* @_Znam(i64 noundef %56) #9
          to label %58 unwind label %24

58:                                               ; preds = %53
  store i8* %57, i8** %10, align 8
  %59 = load i8*, i8** %10, align 8
  %60 = call noundef i8* @_ZNKSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEE5c_strEv(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4) #3
  %61 = call i8* @strcpy(i8* noundef %59, i8* noundef %60) #3
  %62 = load i8*, i8** %10, align 8
  %63 = call i64 @strlen(i8* noundef %62) #8
  %64 = trunc i64 %63 to i32
  store i32 %64, i32* %11, align 4
  %65 = load i32, i32* %11, align 4
  %66 = sub nsw i32 %65, 1
  %67 = sdiv i32 %66, 2
  store i32 %67, i32* %12, align 4
  br label %68

68:                                               ; preds = %83, %58
  %69 = load i32, i32* %12, align 4
  %70 = icmp sge i32 %69, 0
  br i1 %70, label %71, label %86

71:                                               ; preds = %68
  %72 = load i8*, i8** %10, align 8
  %73 = load i32, i32* %12, align 4
  %74 = sext i32 %73 to i64
  %75 = getelementptr inbounds i8, i8* %72, i64 %74
  %76 = load i8*, i8** %10, align 8
  %77 = load i32, i32* %11, align 4
  %78 = load i32, i32* %12, align 4
  %79 = sub nsw i32 %77, %78
  %80 = sub nsw i32 %79, 1
  %81 = sext i32 %80 to i64
  %82 = getelementptr inbounds i8, i8* %76, i64 %81
  call void @_ZSt4swapIcENSt9enable_ifIXsr6__and_ISt6__not_ISt15__is_tuple_likeIT_EESt21is_move_constructibleIS3_ESt18is_move_assignableIS3_EEE5valueEvE4typeERS3_SC_(i8* noundef nonnull align 1 dereferenceable(1) %75, i8* noundef nonnull align 1 dereferenceable(1) %82) #3
  br label %83

83:                                               ; preds = %71
  %84 = load i32, i32* %12, align 4
  %85 = add nsw i32 %84, -1
  store i32 %85, i32* %12, align 4
  br label %68, !llvm.loop !8

86:                                               ; preds = %68
  %87 = load i8*, i8** %10, align 8
  %88 = getelementptr inbounds i8, i8* %87, i64 0
  store i8* %88, i8** %2, align 8
  store i32 1, i32* %9, align 4
  br label %89

89:                                               ; preds = %86, %17
  call void @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEED1Ev(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32) %4) #3
  %90 = load i8*, i8** %2, align 8
  ret i8* %90

91:                                               ; preds = %24
  %92 = load i8*, i8** %7, align 8
  %93 = load i32, i32* %8, align 4
  %94 = insertvalue { i8*, i32 } undef, i8* %92, 0
  %95 = insertvalue { i8*, i32 } %94, i32 %93, 1
  resume { i8*, i32 } %95
}

declare noundef nonnull align 8 dereferenceable(32) %"class.std::__cxx11::basic_string"* @_ZNSt7__cxx1112basic_stringIcSt11char_traitsIcESaIcEEpLEc(%"class.std::__cxx11::basic_string"* noundef nonnull align 8 dereferenceable(32), i8 noundef signext) #1

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define linkonce_odr dso_local void @_ZSt4swapIcENSt9enable_ifIXsr6__and_ISt6__not_ISt15__is_tuple_likeIT_EESt21is_move_constructibleIS3_ESt18is_move_assignableIS3_EEE5valueEvE4typeERS3_SC_(i8* noundef nonnull align 1 dereferenceable(1) %0, i8* noundef nonnull align 1 dereferenceable(1) %1) #7 comdat {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  %5 = alloca i8, align 1
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %6 = load i8*, i8** %3, align 8
  %7 = call noundef nonnull align 1 dereferenceable(1) i8* @_ZSt4moveIRcEONSt16remove_referenceIT_E4typeEOS2_(i8* noundef nonnull align 1 dereferenceable(1) %6) #3
  %8 = load i8, i8* %7, align 1
  store i8 %8, i8* %5, align 1
  %9 = load i8*, i8** %4, align 8
  %10 = call noundef nonnull align 1 dereferenceable(1) i8* @_ZSt4moveIRcEONSt16remove_referenceIT_E4typeEOS2_(i8* noundef nonnull align 1 dereferenceable(1) %9) #3
  %11 = load i8, i8* %10, align 1
  %12 = load i8*, i8** %3, align 8
  store i8 %11, i8* %12, align 1
  %13 = call noundef nonnull align 1 dereferenceable(1) i8* @_ZSt4moveIRcEONSt16remove_referenceIT_E4typeEOS2_(i8* noundef nonnull align 1 dereferenceable(1) %5) #3
  %14 = load i8, i8* %13, align 1
  %15 = load i8*, i8** %4, align 8
  store i8 %14, i8* %15, align 1
  ret void
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define linkonce_odr dso_local noundef nonnull align 1 dereferenceable(1) i8* @_ZSt4moveIRcEONSt16remove_referenceIT_E4typeEOS2_(i8* noundef nonnull align 1 dereferenceable(1) %0) #7 comdat {
  %2 = alloca i8*, align 8
  store i8* %0, i8** %2, align 8
  %3 = load i8*, i8** %2, align 8
  ret i8* %3
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_eq(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp eq i32 %7, 0
  ret i1 %8
}

; Function Attrs: nounwind readonly willreturn
declare i32 @strcmp(i8* noundef, i8* noundef) #5

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_ne(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp ne i32 %7, 0
  ret i1 %8
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_slt(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp slt i32 %7, 0
  ret i1 %8
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_sgt(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp sgt i32 %7, 0
  ret i1 %8
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_sle(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp sle i32 %7, 0
  ret i1 %8
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef zeroext i1 @_bottom_str_sge(i8* noundef %0, i8* noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %5 = load i8*, i8** %3, align 8
  %6 = load i8*, i8** %4, align 8
  %7 = call i32 @strcmp(i8* noundef %5, i8* noundef %6) #8
  %8 = icmp sge i32 %7, 0
  ret i1 %8
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef i32 @_str_length(i8* noundef %0) #7 {
  %2 = alloca i8*, align 8
  %3 = alloca i32, align 4
  store i8* %0, i8** %2, align 8
  store i32 0, i32* %3, align 4
  br label %4

4:                                                ; preds = %12, %1
  %5 = load i8*, i8** %2, align 8
  %6 = load i32, i32* %3, align 4
  %7 = sext i32 %6 to i64
  %8 = getelementptr inbounds i8, i8* %5, i64 %7
  %9 = load i8, i8* %8, align 1
  %10 = sext i8 %9 to i32
  %11 = icmp ne i32 %10, 0
  br i1 %11, label %12, label %15

12:                                               ; preds = %4
  %13 = load i32, i32* %3, align 4
  %14 = add nsw i32 %13, 1
  store i32 %14, i32* %3, align 4
  br label %4, !llvm.loop !9

15:                                               ; preds = %4
  %16 = load i32, i32* %3, align 4
  ret i32 %16
}

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local noundef i8* @_bottom_str_cat(i8* noundef %0, i8* noundef %1) #4 {
  %3 = alloca i8*, align 8
  %4 = alloca i8*, align 8
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i8*, align 8
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  store i8* %0, i8** %3, align 8
  store i8* %1, i8** %4, align 8
  %10 = load i8*, i8** %3, align 8
  %11 = call noundef i32 @_str_length(i8* noundef %10)
  store i32 %11, i32* %5, align 4
  %12 = load i8*, i8** %4, align 8
  %13 = call noundef i32 @_str_length(i8* noundef %12)
  store i32 %13, i32* %6, align 4
  %14 = load i32, i32* %5, align 4
  %15 = load i32, i32* %6, align 4
  %16 = add nsw i32 %14, %15
  %17 = add nsw i32 %16, 1
  %18 = sext i32 %17 to i64
  %19 = call noalias noundef nonnull i8* @_Znam(i64 noundef %18) #9
  store i8* %19, i8** %7, align 8
  store i32 0, i32* %8, align 4
  br label %20

20:                                               ; preds = %34, %2
  %21 = load i32, i32* %8, align 4
  %22 = load i32, i32* %5, align 4
  %23 = icmp slt i32 %21, %22
  br i1 %23, label %24, label %37

24:                                               ; preds = %20
  %25 = load i8*, i8** %3, align 8
  %26 = load i32, i32* %8, align 4
  %27 = sext i32 %26 to i64
  %28 = getelementptr inbounds i8, i8* %25, i64 %27
  %29 = load i8, i8* %28, align 1
  %30 = load i8*, i8** %7, align 8
  %31 = load i32, i32* %8, align 4
  %32 = sext i32 %31 to i64
  %33 = getelementptr inbounds i8, i8* %30, i64 %32
  store i8 %29, i8* %33, align 1
  br label %34

34:                                               ; preds = %24
  %35 = load i32, i32* %8, align 4
  %36 = add nsw i32 %35, 1
  store i32 %36, i32* %8, align 4
  br label %20, !llvm.loop !10

37:                                               ; preds = %20
  store i32 0, i32* %9, align 4
  br label %38

38:                                               ; preds = %54, %37
  %39 = load i32, i32* %9, align 4
  %40 = load i32, i32* %6, align 4
  %41 = icmp slt i32 %39, %40
  br i1 %41, label %42, label %57

42:                                               ; preds = %38
  %43 = load i8*, i8** %4, align 8
  %44 = load i32, i32* %9, align 4
  %45 = sext i32 %44 to i64
  %46 = getelementptr inbounds i8, i8* %43, i64 %45
  %47 = load i8, i8* %46, align 1
  %48 = load i8*, i8** %7, align 8
  %49 = load i32, i32* %5, align 4
  %50 = load i32, i32* %9, align 4
  %51 = add nsw i32 %49, %50
  %52 = sext i32 %51 to i64
  %53 = getelementptr inbounds i8, i8* %48, i64 %52
  store i8 %47, i8* %53, align 1
  br label %54

54:                                               ; preds = %42
  %55 = load i32, i32* %9, align 4
  %56 = add nsw i32 %55, 1
  store i32 %56, i32* %9, align 4
  br label %38, !llvm.loop !11

57:                                               ; preds = %38
  %58 = load i8*, i8** %7, align 8
  %59 = load i32, i32* %5, align 4
  %60 = load i32, i32* %6, align 4
  %61 = add nsw i32 %59, %60
  %62 = sext i32 %61 to i64
  %63 = getelementptr inbounds i8, i8* %58, i64 %62
  store i8 0, i8* %63, align 1
  %64 = load i8*, i8** %7, align 8
  ret i8* %64
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef i8* @_bottom_malloc(i32 noundef %0) #7 {
  %2 = alloca i32, align 4
  store i32 %0, i32* %2, align 4
  %3 = load i32, i32* %2, align 4
  %4 = sext i32 %3 to i64
  %5 = call noalias i8* @malloc(i64 noundef %4) #3
  ret i8* %5
}

; Function Attrs: nounwind
declare noalias i8* @malloc(i64 noundef) #2

; Function Attrs: mustprogress noinline optnone uwtable
define dso_local noundef i8* @_str_substring(i8* noundef %0, i32 noundef %1, i32 noundef %2) #4 {
  %4 = alloca i8*, align 8
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i8*, align 8
  %8 = alloca i32, align 4
  store i8* %0, i8** %4, align 8
  store i32 %1, i32* %5, align 4
  store i32 %2, i32* %6, align 4
  %9 = load i32, i32* %6, align 4
  %10 = load i32, i32* %5, align 4
  %11 = sub nsw i32 %9, %10
  %12 = sext i32 %11 to i64
  %13 = call noalias noundef nonnull i8* @_Znam(i64 noundef %12) #9
  store i8* %13, i8** %7, align 8
  %14 = load i32, i32* %5, align 4
  store i32 %14, i32* %8, align 4
  br label %15

15:                                               ; preds = %31, %3
  %16 = load i32, i32* %8, align 4
  %17 = load i32, i32* %6, align 4
  %18 = icmp slt i32 %16, %17
  br i1 %18, label %19, label %34

19:                                               ; preds = %15
  %20 = load i8*, i8** %4, align 8
  %21 = load i32, i32* %8, align 4
  %22 = sext i32 %21 to i64
  %23 = getelementptr inbounds i8, i8* %20, i64 %22
  %24 = load i8, i8* %23, align 1
  %25 = load i8*, i8** %7, align 8
  %26 = load i32, i32* %8, align 4
  %27 = load i32, i32* %5, align 4
  %28 = sub nsw i32 %26, %27
  %29 = sext i32 %28 to i64
  %30 = getelementptr inbounds i8, i8* %25, i64 %29
  store i8 %24, i8* %30, align 1
  br label %31

31:                                               ; preds = %19
  %32 = load i32, i32* %8, align 4
  %33 = add nsw i32 %32, 1
  store i32 %33, i32* %8, align 4
  br label %15, !llvm.loop !12

34:                                               ; preds = %15
  %35 = load i8*, i8** %7, align 8
  ret i8* %35
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef i32 @_str_parseInt(i8* noundef %0) #7 {
  %2 = alloca i8*, align 8
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  store i8* %0, i8** %2, align 8
  store i32 0, i32* %3, align 4
  store i32 0, i32* %4, align 4
  br label %5

5:                                                ; preds = %13, %1
  %6 = load i8*, i8** %2, align 8
  %7 = load i32, i32* %4, align 4
  %8 = sext i32 %7 to i64
  %9 = getelementptr inbounds i8, i8* %6, i64 %8
  %10 = load i8, i8* %9, align 1
  %11 = sext i8 %10 to i32
  %12 = icmp ne i32 %11, 0
  br i1 %12, label %13, label %26

13:                                               ; preds = %5
  %14 = load i32, i32* %3, align 4
  %15 = mul nsw i32 %14, 10
  %16 = load i8*, i8** %2, align 8
  %17 = load i32, i32* %4, align 4
  %18 = sext i32 %17 to i64
  %19 = getelementptr inbounds i8, i8* %16, i64 %18
  %20 = load i8, i8* %19, align 1
  %21 = sext i8 %20 to i32
  %22 = add nsw i32 %15, %21
  %23 = sub nsw i32 %22, 48
  store i32 %23, i32* %3, align 4
  %24 = load i32, i32* %4, align 4
  %25 = add nsw i32 %24, 1
  store i32 %25, i32* %4, align 4
  br label %5, !llvm.loop !13

26:                                               ; preds = %5
  %27 = load i32, i32* %3, align 4
  ret i32 %27
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local noundef i32 @_str_ord(i8* noundef %0, i32 noundef %1) #7 {
  %3 = alloca i8*, align 8
  %4 = alloca i32, align 4
  store i8* %0, i8** %3, align 8
  store i32 %1, i32* %4, align 4
  %5 = load i8*, i8** %3, align 8
  %6 = load i32, i32* %4, align 4
  %7 = sext i32 %6 to i64
  %8 = getelementptr inbounds i8, i8* %5, i64 %7
  %9 = load i8, i8* %8, align 1
  %10 = sext i8 %9 to i32
  ret i32 %10
}

attributes #0 = { noinline uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #2 = { nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { nounwind }
attributes #4 = { mustprogress noinline optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #5 = { nounwind readonly willreturn "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #6 = { nobuiltin allocsize(0) "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #7 = { mustprogress noinline nounwind optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #8 = { nounwind readonly willreturn }
attributes #9 = { builtin allocsize(0) }

!llvm.ident = !{!0}
!llvm.module.flags = !{!1, !2, !3, !4, !5}

!0 = !{!"Ubuntu clang version 14.0.0-1ubuntu1"}
!1 = !{i32 1, !"wchar_size", i32 4}
!2 = !{i32 7, !"PIC Level", i32 2}
!3 = !{i32 7, !"PIE Level", i32 2}
!4 = !{i32 7, !"uwtable", i32 1}
!5 = !{i32 7, !"frame-pointer", i32 2}
!6 = distinct !{!6, !7}
!7 = !{!"llvm.loop.mustprogress"}
!8 = distinct !{!8, !7}
!9 = distinct !{!9, !7}
!10 = distinct !{!10, !7}
!11 = distinct !{!11, !7}
!12 = distinct !{!12, !7}
!13 = distinct !{!13, !7}

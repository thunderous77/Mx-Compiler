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

@n = global i32 0, align 4
@p = global i32 0, align 4
@k = global i32 0, align 4
@i = global i32 0, align 4
@.strconst = private unnamed_addr constant [4 x i8] c"<< \00", align 1
@.strconst.1 = private unnamed_addr constant [2 x i8] c" \00", align 1
@.strconst.2 = private unnamed_addr constant [2 x i8] c"(\00", align 1
@.strconst.3 = private unnamed_addr constant [3 x i8] c") \00", align 1
@.strconst.4 = private unnamed_addr constant [4 x i8] c">> \00", align 1
@llvm.global_ctors = appending global [1 x { i32, void ()*, i8* }] [{ i32, void ()*, i8* } { i32 65535, void ()* @_GLOBAL__sub_I_builtin.cpp, i8* null }]
@_ZStL8__ioinit = internal global %"class.std::ios_base::Init" zeroinitializer, align 1
@__dso_handle = external hidden global i8
@.str = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str.1 = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str.2 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@_ZSt3cin = external global %"class.std::basic_istream", align 8

define i32 @main() {
entry.12:
  %func.ret.addr = alloca i32, align 4
  call void @global_var_init()
  store i32 0, i32* %func.ret.addr, align 4
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

exit.12:                                          ; preds = %if.exit.3
  %func.ret.load = load i32, i32* %func.ret.addr, align 4
  ret i32 %func.ret.load

if.true:                                          ; preds = %entry.12
  %getelementptr = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst, i32 0, i32 0
  call void @print(i8* %getelementptr)
  br label %if.exit

if.exit:                                          ; preds = %if.false, %if.true
  %p.load.1 = load i32, i32* @p, align 4
  %k.load.1 = load i32, i32* @k, align 4
  %sub.1 = sub i32 %p.load.1, %k.load.1
  store i32 %sub.1, i32* @i, align 4
  br label %for.cond

if.false:                                         ; preds = %entry.12
  br label %if.exit

for.cond:                                         ; preds = %for.incr, %if.exit
  %i.load = load i32, i32* @i, align 4
  %p.load.2 = load i32, i32* @p, align 4
  %k.load.2 = load i32, i32* @k, align 4
  %add = add i32 %p.load.2, %k.load.2
  %icmp.1 = icmp sle i32 %i.load, %add
  br i1 %icmp.1, label %for.body, label %for.exit

for.incr:                                         ; preds = %if.exit.1
  %i.load.1 = load i32, i32* @i, align 4
  %add.1 = add i32 %i.load.1, 1
  store i32 %add.1, i32* @i, align 4
  br label %for.cond

for.body:                                         ; preds = %for.cond
  %i.load.2 = load i32, i32* @i, align 4
  %icmp.2 = icmp sle i32 1, %i.load.2
  br i1 %icmp.2, label %logic_and_continue, label %logic_and_exit

for.exit:                                         ; preds = %for.cond
  %p.load.4 = load i32, i32* @p, align 4
  %k.load.3 = load i32, i32* @k, align 4
  %add.2 = add i32 %p.load.4, %k.load.3
  %n.load.1 = load i32, i32* @n, align 4
  %icmp.5 = icmp slt i32 %add.2, %n.load.1
  br i1 %icmp.5, label %if.true.3, label %if.false.3

if.true.1:                                        ; preds = %logic_and_exit
  %i.load.4 = load i32, i32* @i, align 4
  %p.load.3 = load i32, i32* @p, align 4
  %icmp.4 = icmp eq i32 %i.load.4, %p.load.3
  br i1 %icmp.4, label %if.true.2, label %if.false.2

if.exit.1:                                        ; preds = %if.exit.2, %if.false.1
  br label %for.incr

if.false.1:                                       ; preds = %logic_and_exit
  br label %if.exit.1

logic_and_continue:                               ; preds = %for.body
  %i.load.3 = load i32, i32* @i, align 4
  %n.load = load i32, i32* @n, align 4
  %icmp.3 = icmp sle i32 %i.load.3, %n.load
  br label %logic_and_exit

logic_and_exit:                                   ; preds = %logic_and_continue, %for.body
  %phi = phi i1 [ %icmp.2, %for.body ], [ %icmp.3, %logic_and_continue ]
  br i1 %phi, label %if.true.1, label %if.false.1

if.true.2:                                        ; preds = %if.true.1
  %getelementptr.2 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.2, i32 0, i32 0
  call void @print(i8* %getelementptr.2)
  %i.load.6 = load i32, i32* @i, align 4
  %toString.call = call i8* @toString(i32 %i.load.6)
  call void @print(i8* %toString.call)
  %getelementptr.3 = getelementptr inbounds [3 x i8], [3 x i8]* @.strconst.3, i32 0, i32 0
  call void @print(i8* %getelementptr.3)
  br label %if.exit.2

if.exit.2:                                        ; preds = %if.false.2, %if.true.2
  br label %if.exit.1

if.false.2:                                       ; preds = %if.true.1
  %i.load.5 = load i32, i32* @i, align 4
  call void @printInt(i32 %i.load.5)
  %getelementptr.1 = getelementptr inbounds [2 x i8], [2 x i8]* @.strconst.1, i32 0, i32 0
  call void @print(i8* %getelementptr.1)
  br label %if.exit.2

if.true.3:                                        ; preds = %for.exit
  %getelementptr.4 = getelementptr inbounds [4 x i8], [4 x i8]* @.strconst.4, i32 0, i32 0
  call void @print(i8* %getelementptr.4)
  br label %if.exit.3

if.exit.3:                                        ; preds = %if.false.3, %if.true.3
  store i32 0, i32* %func.ret.addr, align 4
  br label %exit.12

if.false.3:                                       ; preds = %for.exit
  br label %if.exit.3
}

define void @global_var_init() {
entry:
  br label %exit

exit:                                             ; preds = %entry
  ret void
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

import os
import time

# export CLASSPATH="/mnt/e/Compiler-Design/antlr-4.10.1-complete.jar:$CLASSPATH"

def cleanup ():    
    os.system("cd ../src && (find -name '*.class' | xargs rm) && cd ../debug")

cleanup()

judge_list = open("../testcases/codegen/judgelist.txt").readlines()

cnt = 0

fail_collect = list()


for judge in judge_list:
    cnt += 1

    code_file = judge.replace("\n", "").replace("./", "../testcases/codegen/")
    print(code_file)

    input_file = "1.in"
    output_file = "1.out"
    std_file = "1.ans"
    #这三个文件名不能改(要和test-asm.sh中保持一致)

    input_fp = open(input_file, "w")
    output_fp = open(output_file, "w")
    std_fp = open(std_file, "w")

    fp = open(code_file)
    lines = fp.readlines()

    input_start = False
    std_start = False

    for i in range(len(lines)):
        if lines[i].find("= output =") > 0:
            std_start = True
        elif lines[i].find("= input =") > 0:
            input_start = True
        elif lines[i].find("= end =") > 0:
            input_start = False
            std_start = False
        elif input_start:
            input_fp.write(lines[i])
        elif std_start:
            std_fp.write(lines[i])

    input_fp.close()
    output_fp.close()
    std_fp.close()

    print("\033[34m Loading finish. Start to run.")
    
    os.system("cd ../src && javac Compiler.java &&  cp {code_file} test.mx && java Compiler<test.mx>test.s &&cd ../debug".format(code_file=code_file))
    
    os.system("cp ../src/test.s test.s && ./test-asm.sh")


    wrap = os.popen(
        "diff {file1} {file2} -w -B".format(file1=output_file, file2=std_file))
    info = wrap.readlines()

    if len(info) == 0:
        print("\033[32m[Success] [test]: in {testpoint} \033[0m".format(
            testpoint=code_file + ", point " + str(cnt)))
    else:
        print("\033[31m[Failed] [test]: in {testpoint} \033[0m".format(
            testpoint=code_file + ", point " + str(cnt)))
        # print("[info]: ", info)
        fail_collect.append(code_file)
        break

    time.sleep(1)

print(fail_collect)

cleanup()


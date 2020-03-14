#!/usr/bin/env bash

# simple demonstration of the getopts command

#getopts会将获取到的选项所对应的参数 自动保存到OPTARG这个变量中

#getopts [option[:]] [DESCPRITION] VARIABLE
#option：表示为某个脚本可以使用的选项
#":"：如果某个选项（option）后面出现了冒号（":"），则表示这个选项后面可以接参数（即一段描述信息DESCPRITION）
#VARIABLE：表示将某个选项保存在变量VARIABLE中

#Bash内置getopt函数可以用于解析长选项，方法是在optspec中添加一个破折号字符，后面跟着冒号：

#getopt （系统外部用法，后来增加的）与 getopts（内部，不支持长选项 只能是单个字符的短选项）


#区别
#getopts

#支持多个选项连续使用如 -vl
#自动处理 "-"
#缺点:
#不支持长选项，如果需要支持长选项使用getopt
#原理：
#1、getopts隐含了两个变量$OPTARG(选项的参数) $OPTIND(下一个要处理的参数的索引，初始为1)
#2、getopts第一个参数定义了一系列合法的选项”:f:vql“
#2.1 开头的冒号":"可选：作用是getopts不返回任何错误信息，当遇到非法选项时，更新getopts变量的值为"?"，并保存非法选项到$OPTARG
#2.2 f选项后跟一个冒号":"，表示f有一个必须存在的参数，（实际在使用-f选项时，选项和参数中可以无空格，也可以有空格，根据IFS截取，例如./1.sh -f -v a则-v为参数,注意顺序！）
#3、getopts第二个参数为变量名，用于保存每次getopts运行时找到的合法选项字母，找到不合法选项时getopts为问号。

#getopt
#优点：
#支持长选项
#
#缺点：
#不自动处理"-"
#没有像getopts那样的隐含变量OPTARG和OPTIND，需要手动处理
#
#原理：
#根据用户指定的合法选项重新对选项进行整理排序
#关于定义合法选项的双冒号意思为：该选项的参数是可选的，其它同getopts
#-o定义短选项
#-l定义长选项
#-n定义当需要有参数的选项没有给于参数时的错误提示符


while getopts :ab:c opt
do
    case "$opt" in
        a) echo "Found the -a option";;
        b) echo "Found the -b option, with value $OPTARG";;
        c) echo "Found the -c option";;
        *) echo "Unknown option:$opt";;
    esac
done


# 执行脚本  getoptsOpt.sh -a
#Found the -a option
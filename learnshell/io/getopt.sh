#!/usr/bin/env bash

#Extract command line options & values with getopt
#getopt命令可以接受一系列任意形式的命令行选项和参数，并自动将它们转换成适当的格式。
#getopt 命令并不擅长处理带空格和引号的参数值



#set命令的选项之一是双破折线（–），它会将命令行参数替换成shell中的各种变量


#$@: 参数列表 所有参数 逐个输出 如 ./xx.sh 1 2 3 $@ 值为"1" “2” “3”
#$*: 参数列表 所有的参数 作为一个整体 如 ./xx.sh 1 2 3, $*值为“1 2 3”

#basename $0值显示当前脚本或命令的名字

#shift 命令每执行一次，变量的个数($#)减一，而变量值提前一位
# 比如shift 3表示原来的$4现在变成$1，原来的$5现在变成$2等等，
# 原来的$1、$2、$3丢弃，$0不移动。不带参数的shift命令相当于shift 1



set -- $(getopt -q ab:cd "$@")
#
echo
while [ -n "$1" ]
do
    case "$1" in
        -a) echo "Found the -a option" ;;
        -b) param="$2"
        echo "Found the -b option, with parameter value $param"
        shift ;;
        -c) echo "Found the -c option" ;;
        --) shift
        break ;;
        *) echo "$1 is not option" ;;
    esac

    shift
done
#
count=1
for param in "$@"
do
    echo "Parameter #$count: $param"
    count=$[ $count + 1 ]
done


#执行脚本，查看结果：

#bash getopt.sh -ac
#Found the -a option
#Found the -c option

#bash getopt.sh -a -b test1 -c test2 test3
#Found the -a option
#Found the -b option, with parameter value 'test1'
#Found the -c option
#Parameter #1: 'test2'
#Parameter #2: 'test3'
#!/usr/bin/env bash


#exit 是一个 Shell 内置命令，用来退出当前 Shell 进程，并返回一个退出状态；使用$?可以接收这个退出状态，这一点已在《Shell $?》中进行了讲解。
#
#exit 命令可以接受一个整数值作为参数，代表退出状态。如果不指定，默认状态值是 0。
#
#一般情况下，退出状态为 0 表示成功，退出状态为非 0 表示执行失败（出错）了。
#
#exit 退出状态只能是一个介于 0~255 之间的整数，其中只有 0 表示成功，其它值都表示失败。
#
#Shell 进程执行出错时，可以根据退出状态来判断具体出现了什么错误，比如打开一个文件时，我们可以指定 1 表示文件不存在，2 表示文件没有读取权限，3 表示文件类型不对。

#exit 0
#exit 10
#exit 254
exit 255
exit 300
# $? = 44 = 300 -256

pwd
#!/usr/bin/env bash

#一. trap捕捉到信号之后，可以有三种反应方式：
#　　(1)执行一段程序来处理这一信号
#　　(2)接受信号的默认操作
#　　(3)忽视这一信号
#
#二. trap对上面三种方式提供了三种基本形式：
#　　第一种形式的trap命令在shell接收到signal list清单中数值相同的信号时，将执行双
#　　引号中的命令串。
#　　trap 'commands' signal-list
#　　trap "commands" signal-list
#　　为了恢复信号的默认操作，使用第二种形式的trap命令：
#　　trap signal-list

#第三种形式的trap命令允许忽视信号
#　　trap " " signal-list


#testing signal trapping
trap "echo 'sorry! I have trapped Ctrl - C'" SIGINT SIGTERM
echo this is a test program

count=1
while [ $count -le 10 ]
do
    echo "Loop #$count"
    sleep 5
    count=$[ $count + 1 ]
done
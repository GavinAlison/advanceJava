#!/usr/bin/env bash

# testing the reading command

#read命令 -n(不换行) -p(提示语句) -n(字符个数) -t(等待时间) -s(不回显)

echo -n "Enter your name:" # 参数-n的作用是不换行，echo默认是换行
read name               # 从键盘输入
echo "Hello $name, welcome to my program"

read -p "Pleaser enter your age: " age
days=$[ $age * 365]
echo "That makes you over $days old"

#指定多个变量，输入的每个数据值都会分配给表中的下一个变量，如果用完了，就全分配各最后一个变量； 如果太少输入不会结束。
read -p "Please enter name:" first last
echo "Checking data for $last. $first..."

#如果不指定变量，read命令就会把它收到的任何数据都放到特殊环境变量REPLY中
#环境变量REPLY中包含输入的所有数据，可以像使用其他变量一样在shell脚本中使用环境变量REPLY.
read -p "Enter a number:"
factorial=1
for (( count=1; count<=$REPLY; count++))
do
    factorial=$[ $factorial * $count ]
done
echo "The factorial of $REPLY is $factorial"



#!/usr/bin/env bash
# using a global variable to pass a value

function db1 {
    # $1和$2 不能从命令行中传递，只能调用函数时，手动传递
    echo $[ $1 * $2 ]
}

if [ $# -eq 2 ]
then
    value=`db1 $1 $2`
    echo "The result is $value"
else
    echo "Usage: badtest1 a b"
fi

# 使用函数输出
# using the echo to return a value

function db1 {
    read -p "Enter a value:" value
    echo $[ $value*2 ]
}

result=`db1`
echo "The new value is $result"
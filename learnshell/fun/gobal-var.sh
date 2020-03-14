#!/usr/bin/env bash

#全局变量
# using a global variable to pass a value

function db1 {
    value=$[ $value * 2 ]
}

read -p "Enter a value: " value
db1
echo "The new value is : $value"

# 使用局部变量
# demonstrating the local keyword

function func1 {
    local temp=$[ $value +5 ]
    result=$[ $temp * 2 ]
}

temp=4
value=6
func1
echo "The result is $result"
if [ $temp -gt $value ];
then
    echo "temp is larger"
else
    echo "temp is smaller"
fi

#使用库函数

#using a library file the wrong way

source ./script.sh

result=`add 10 15`
echo "The result is $result"
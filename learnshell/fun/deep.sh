#!/usr/bin/env bash

#函数递归 全排列--对于n个数按照顺序进行全排列
function factorial {
    if [ $1 -eq 1 ]
    then
        echo 1
    else
        local temp=$[ $1 -1 ]
        local result=`factorial $temp`
        echo $[ $result * $1 ]
    fi
}

#返回有两种
#1. return $?
#2. echo  =

read -p "Please input a value: " value
result=`factorial $value`
echo "The factorial of $value is: $result"

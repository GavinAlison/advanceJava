#!/usr/bin/env bash

# using the return command in a function

function db1 {
    read -p "Enter a value:" value
    echo "doubling the value"
    return $[ $value * 2 ]
}
#$? 获取函数的返回值
db1
echo "The new value is $?"
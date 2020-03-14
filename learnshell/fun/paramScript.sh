#!/usr/bin/env bash

#函数传数组数据
# trying to pass an array variable
echo "----------------------------"
echo " trying to pass an array variable"
echo "----------------------------"
function testit {
    echo "The paramters are : $@"

    #函数只会读取数组变量的第一个值
    thisarrays=$1
    echo "the received array is ${thisarrays[*]}"

    local newarrays
    newarrays=(`echo "$@"`)
    echo "The new array value is :${newarrays[*]}"

}

newarray=(1 2 3 4 5)
echo "The original array is : ${newarray[*]}"

#将数组变量当成一个函数参数，函数只会去函数变量第一个值
#testit $myarray

testit ${newarray[*]}

echo "----------------------------"
echo "returning an array value"
echo "----------------------------"
##从函数返回数组
# returning an array value

function arraydblr {
    local origarry
    local newarray
    local elements
    local i
    origarry=(`echo "$@"`)
    newarray=(`echo "$@"`)
    elements=$[ $# - 1 ]
    for (( i=0; i<=$elements; i++ ))
    {
        newarray[$i]=$[ ${origarry[$i]} * 2 ]
    }
    echo ${newarray[*]}
}

myarray=(1 2 3 4 5)
echo "The original array is : ${myarray[*]}"
arg1=`echo ${myarray[*]}`
result=(`arraydblr $arg1`)
echo "The new array is : ${result[*]}"

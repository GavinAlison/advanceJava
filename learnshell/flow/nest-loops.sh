#!/usr/bin/env bash
#nesting for loops

for (( a=1; a<=3; a++ ))
do
    echo "Starting loop $a:"
    for (( b=1; b<=3; b++))
    do
        echo "Inside loog: $b:"
    done
done


# using a variable to hold the list

list="Alabama Alaska Arizona"
list=$list" Connecticut"

for state in $list
do
    echo "Have you ever visited $state"
done

#reading values from a file
file="state"
#更改字段分隔符，使其只能识别换行符
IFS=$'\n'

#处理长脚本是，在一个地方修改了该值，然后可能忘了修改过该值
#IFS.OLD=$IFS
#IFS=$'\n'
#具体代码
#IFS=$IFS.OLD
for state in `cat $file`
do
    echo "Visit beautiful $state"
done

#testing the C-style for loop
for (( i=1; i<=10; i++ ))
do
    echo "The next number is $i"
done
#!/usr/bin/env bash

# break n，默认为1

for (( a=1; a<=3; a++ ))
do
    echo "Outer loop : $a"
#   (()) 里面不需要加引号
    for (( b=1; b < 100; b++ ))
    do
        if [ $b -gt 4 ]
        then
            break 2 # 跳出最外层循环
        fi
        echo " Inner loop:$b"
    done
done


#using the continue command

for (( var1 = 1; var1 < 15; var1++ ))
do
    if [ $var1 -gt 5 ] && [ $var1 -lt 10 ]
    then
        continue
    fi
    echo "Iteration number:$var1"
done
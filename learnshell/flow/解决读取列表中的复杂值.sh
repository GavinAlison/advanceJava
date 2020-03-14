#!/usr/bin/env bash

for test in I don\'t know if "this'll" work
do
echo "word:$test"
done


# 读取列表中的值
# basic for command
for test in Alabama Alaska Arizona
do
    echo The next state is $test
done

#读取里表中复杂的值
# another example of how not to use the for command
for test in I don't know if this'll work
do
    echo "word:$test"
done
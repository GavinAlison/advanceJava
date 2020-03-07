#!/usr/bin/env bash

# 使用expr执行数学运算

var1=10
var2=20
var3=`expr $var2 / $var1`
echo "the result is $var3"
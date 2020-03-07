#!/usr/bin/env bash

var1=10
var2=50
var3=45
var4=$[$var1 * ($var2 - $var3)]
var5=`expr $var1 / $var2`

echo $var5
echo 'The final result is '$var4
#!/usr/bin/env bash

#placing a for loop inside a while loop
var1=5
#var3=0
while [ $var1 -ge 0 ]
do
    echo "outer loop: $var1"
    for (( var2=1; var2 < 3; var2++ ))
    do
#        注意等号不可有空格
        var3=$[$var1*$var2]
        echo "Inner loop: $var1 * $var2 = $var3 "
    done
    var1=$[ $var1 -1 ]
done

# while command test
var1=10
while [ $var1 -gt 0 ]
do
    echo $var1
    var1=$[ $var1 - 1 ]
done

#testing a multicommand while loop

var1=10
while echo $var1
[ $var1 -ge 0 ]
do
    echo 'This is inside the loop'
    var1=$[ $var1 - 1 ]
done
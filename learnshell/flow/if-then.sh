#!/usr/bin/env bash
#testing the if statement
if date
then
    echo "it worked"
fi
echo -e '\n'
if asd
then
    echo "it not worked"
fi
echo 'We are outside the if statement'

# looking for a possible value

if [ $USER = "alison" ]
then
    echo "Welcome $USER"
    echo "Please enjoy your visit"
elif [ $USER = testing ]
then
    echo "Welcome $USER"
    echo "Please enjoy your visit"
elif [ $USER = barbar ]
then
    echo "Do not forget to logout when you're done"
else
    echo "Sorry, you are not allowed here"
fi


# test

var1=10
var2=5
if [ $var1 -gt 5 ]
then
    echo "The test value $var1 is greater than 5"
fi
if [ $var1 -eq $var2 ]
then
    echo "The values is equal"
else
    echo "The values are different"
fi
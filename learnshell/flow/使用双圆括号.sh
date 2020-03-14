#!/usr/bin/env bash
# using double parenthesis

var1=10

if (( $var1 ** 2 > 90))
then
    (( var2 = $var1 ** 2))
    echo "The square of $var1 if $var2"
fi

# another example of how not to use the for command

for test in Newada "New Hampshire"
do
    echo "Now going to $test"
done
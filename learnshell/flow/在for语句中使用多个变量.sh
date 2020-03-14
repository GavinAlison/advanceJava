#!/usr/bin/env bash
# multiple variables

for (( a=1, b=10; a<=10; a++,b-- ))
do
    echo "$a - $b"
done


#testing multiple commands in the then section
testuser=gpadmin
if grep $testuser /etc/passwd
then
    echo The bash files from user $testuser are:
    ls -a /home/$testuser/.b*
fi
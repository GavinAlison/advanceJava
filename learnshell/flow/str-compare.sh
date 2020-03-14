#!/usr/bin/env bash
#testing string equality

testuser=alison

if [ $USER = $testuser ]
then
    echo "Welcome $testuser"
fi
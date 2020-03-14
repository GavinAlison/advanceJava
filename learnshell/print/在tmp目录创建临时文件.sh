#!/usr/bin/env bash

# creating a temp file in /tmp

tempfile=`mktemp -t tmp.XXXXXX`

echo $tempfile

echo "This is a test file">$tempfile
echo "This is the second line of the test">>$tempfile

cat $tempfile
rm -f ${tempfile}
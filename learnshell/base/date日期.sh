#!/usr/bin/env bash

#copy the /usr/bin directory listing to a log file

today=`date +%Y%m%d`
echo $today
ls /usr/bin -al > log.$today
#!/bin/bash

#redirecting SQL output to a variable

MYSQL=`which mysql`
dbs=`$MYSQL   -u root -Bse 'show tables;'`
for db in $dbs
do
	echo $db
done


#使用xml输出数据
$MYSQL   -u root -X -e 'select * from em_admin'

#使用table标签输出数据
$MYSQL   -u root -H -e 'select * from em_admin'


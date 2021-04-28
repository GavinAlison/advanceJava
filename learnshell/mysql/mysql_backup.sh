#!/usr/bin/env bash


set  -u
set  -e
time1=` date  +%Y%m%d `
time2= ` date   +%Y-%m-%d%t%H:%M:%S `
mysql  -uroot  -proot  -e "show databases" | sed 1,2d >mysql.name
while  read  line
do
    dir=/data/mysql/${line}/
    log=/data/mysql/${line}/${line}-${time1}.out
    [ -d $dir ]  &&  echo " "> /dev/null 2>&1 || mkdir -p $dir
    [ -f $log ] &&echo " "> /dev/null 2>&1 || touch $log
    echo  ${time1}>>${log}
    mysqldump  -uroot  -proot  -B ${line} > ${dir}/${line}-${time1}.sql
    if [ S? -eq 0 ];then
      echo "【${time2}】，数据库名：${line}，备份成功！">>${log}
    else
      echo "【${time2}】，数据库名：${line}，备份失败！">>${log}
    fi
done < mysql.name


#> https://www.jianshu.com/p/838d2ffc6537

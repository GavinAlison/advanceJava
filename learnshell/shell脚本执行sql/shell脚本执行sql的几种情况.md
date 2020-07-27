# menu
-	将SQL语句直接嵌入到shell脚本文件中
-	命令行调用单独的SQL文件
-	使用管道符调用SQL文件
-	shell脚本中MySQL提示符下调用SQL
-	shell脚本中变量输入与输出

## 1. 将SQL语句直接嵌入到shell脚本文件中

```shell
more /etc/issue  
  
root@localhost[(none)]> show variables like 'version';  
+---------------+------------+  
| Variable_name | Value      |  
+---------------+------------+  
| version       | 5.6.12-log |  
+---------------+------------+  
  
[root@SZDB ~]# more shell_call_sql1.sh   
#!/bin/bash  
# Define log  
TIMESTAMP=`date +%Y%m%d%H%M%S`  
LOG=call_sql_${TIMESTAMP}.log  
echo "Start execute sql statement at `date`." >>${LOG}  

# execute sql stat  
mysql -uroot -p123456 -e "  
tee /tmp/temp.log  
drop database if exists tempdb;  
create database tempdb;  
use tempdb  
create table if not exists tb_tmp(id smallint,val varchar(20));  
insert into tb_tmp values (1,'jack'),(2,'robin'),(3,'mark');  
select * from tb_tmp;  
notee  
quit"  
  
echo -e "\n">>${LOG}  
echo "below is output result.">>${LOG}  
cat /tmp/temp.log>>${LOG}  
echo "script executed successful.">>${LOG}  
exit;  
  
[root@SZDB ~]# ./shell_call_sql1.sh   
Logging to file '/tmp/temp.log'  
+------+-------+  
| id   | val   |  
+------+-------+  
|    1 | jack  |  
|    2 | robin |  
|    3 | mark  |  
+------+-------+  
Outfile disabled.  
```

## 2、命令行调用单独的SQL文件


```shell
[root@SZDB ~]# more temp.sql   
tee /tmp/temp.log  
drop database if exists tempdb;  
create database tempdb;  
use tempdb  ;
create table if not exists tb_tmp(id smallint,val varchar(20)) engine=innodb character set=utf8;  
insert into tb_tmp values (1,'jack'),(2,'robin'),(3,'mark');  
select * from tb_tmp;  
notee  

[root@SZDB ~]# mysql -uroot -p123456 -e "source /root/temp.sql"  
Logging to file '/tmp/temp.log'  
+------+-------+  
| id   | val   |  
+------+-------+  
|    1 | jack  |  
|    2 | robin |  
|    3 | mark  |  
+------+-------+  
Outfile disabled.  
```

## 3、使用管道符调用SQL文件

```
[root@SZDB ~]# mysql -uroot -p123456 </root/temp.sql  
Logging to file '/tmp/temp.log'  
id      val  
1       jack  
2       robin  
3       mark  
Outfile disabled.  
  
#使用管道符调用SQL文件以及输出日志  
[root@SZDB ~]# mysql -uroot -p123456 </root/temp.sql >/tmp/temp.log  
[root@SZDB ~]# more /tmp/temp.log  
Logging to file '/tmp/temp.log'  
id      val  
1       jack  
2       robin  
3       mark  
Outfile disabled.  
```

## 4、shell脚本中MySQL提示符下调用SQL

```
[root@SZDB ~]# more shell_call_sql2.sh  
#!/bin/bash  
mysql -uroot -p123456 <<EOF  
source /root/temp.sql;  
select current_date();  
delete from tempdb.tb_tmp where id=3;  
select * from tempdb.tb_tmp where id=2;  
EOF
exit;  
[root@SZDB ~]# ./shell_call_sql2.sh  
Logging to file '/tmp/temp.log'  
id      val  
1       jack  
2       robin  
3       mark  
Outfile disabled.  
current_date()  
2014-10-14  
id      val  
2       robin  

```

## 5、shell脚本中变量输入与输出

```
[root@SZDB ~]# more shell_call_sql3.sh  
#!/bin/bash  
cmd="select count(*) from tempdb.tb_tmp"  
cnt=$(mysql -uroot -p123456 -s -e "${cmd}")  
echo "Current count is : ${cnt}"  
exit   
[root@SZDB ~]# ./shell_call_sql3.sh   
Warning: Using a password on the command line interface can be insecure.  
Current count is : 3  
  
[root@SZDB ~]# echo "select count(*) from tempdb.tb_tmp"|mysql -uroot -p123456 -s  
3  

[root@SZDB ~]# more shell_call_sql4.sh  
#!/bin/bash  
id=1  
cmd="select count(*) from tempdb.tb_tmp where id=${id}"  
cnt=$(mysql -uroot -p123456 -s -e "${cmd}")  
echo "Current count is : ${cnt}"  
exit   

[root@SZDB ~]# ./shell_call_sql4.sh   
Current count is : 1   
```

下面附上通过shell命令行非交互式的操作数据库的方法：

mysql -hhostname -Pport -uusername -ppassword -e 相关mysql的sql语句，不用在mysql的提示符下运行mysql，即可以在shell中操作mysql的方法。

#!/bin/bash

HOSTNAME="192.168.111.84"  #数据库信息

PORT="3306"

USERNAME="root"

PASSWORD=""

 

DBNAME="test_db_test"  #数据库名称

TABLENAME="test_table_test" #数据库中表的名称

 

#创建数据库

create_db_sql="create database IF NOT EXISTS ${DBNAME}"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} -e "${create_db_sql}"

 

#创建表

create_table_sql="create table IF NOT EXISTS ${TABLENAME} ( name varchar(20), id int(11) default 0 )"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${create_table_sql}"

 

#插入数据

insert_sql="insert into ${TABLENAME} values('billchen',2)"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${insert_sql}"

 

#查询

select_sql="select * from ${TABLENAME}"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${select_sql}"

 

#更新数据

update_sql="update ${TABLENAME} set id=3"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${update_sql}"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${select_sql}"

 

#删除数据

delete_sql="delete from ${TABLENAME}"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${delete_sql}"

mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} -e "${select_sql}"






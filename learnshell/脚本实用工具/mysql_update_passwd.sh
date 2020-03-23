#!/usr/bin/env bash

ps -aux | pgrep mysql | xargs kill -9

/usr/local/mysql/bin/mysqld_safe --skip-grant-tables &

/usr/local/mysql/bin/mysql -S /usr/local/mysql/data/mysql.socket <<EOF
use mysql;
update mysql.user set host='%' where user='root' and host='localhost.localdomain';
update mysql.user set password=password('root') where user='root';

create database If Not Exists mysqlts Character Set UTF8;
flush privileges;
commit;
EOF

ps -aux | pgrep mysql | xargs kill -9
/usr/local/mysql/support-files/mysql.server start

sleep 5

# 创建用户mysql
/usr/local/mysql/bin/mysql -uroot -proot <<EOF
create user 'mysql'@'%' identified by 'mysql';
grant all  on *.* to 'mysql'@'%';
flush privileges;
EOF
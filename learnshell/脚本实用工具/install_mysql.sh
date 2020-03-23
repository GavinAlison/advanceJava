#!/usr/bin/env bash


#@author: alison
#@times: 2020-03-22
#@systemVersion: CentOs-7

# root 用户进入

if [ -d '/usr/local/mysql' ]; then
    rm -rf /usr/local/mysql/
fi

cd /opt/

#1.下载安装包
#url=https://mirrors.tuna.tsinghua.edu.cn/mysql/downloads/MySQL-5.6/mysql-5.6.45.tar.gz
#url=http://cdn.mysql.com/archives/mysql-5.6/mysql-5.6.26-linux-glibc2.5-x86_64.tar.gz
wget $url
#2.解压安装包
tar zxvf mysql-5.6.26-linux-glibc2.5-x86_64.tar.gz

#3. move path
mv mysql-5.6.26-linux-glibc2.5-x86_64 /usr/local/mysql

#4. 添加mysql用户组和mysql用户
groups mysql
groupadd mysql
useradd -r -g mysql mysql

#5. 修改权限
cd /usr/local/mysql
mkdir data
#更改mysql目录下所有的目录及文件夹所属的用户组和用户，以及权限
chown -R mysql:mysql /usr/local/mysql

#编译安装并初始化mysql,务必记住初始化输出日志末尾的密码（数据库管理员临时密码）
# 6.初始化数据库
./scripts/mysql_install_db --user=mysql

#7.修改当前目录拥有者
chown -R root:root ./
chown -R mysql:mysql data
#pid=`ps aux|egrep mysql`
#if [ -n "$pid" ];  then
#    `kill -9 $pid`
#fi
#8. 添加mysql为系统服务
cp support-files/mysql.server /etc/init.d/mysql

#9.修改mysql服务路径配置
flag=`cat /etc/profile | grep 'MYSQL_HOME'`
if [ -z "$flag"  ]; then
    cat >>/etc/profile <<\EOF
export MYSQL_HOME=/usr/local/mysql
export PATH=$MYSQL_HOME/bin:$PATH
EOF
fi

source /etc/profile

#10. 复制配置mysql配置文件, 没有就创建文件my.cnf, location: /usr/local/mysql/
#cp support-files/my-default.cnf my.cnf
touch my.cnf
cat >my.cnf<<\EOF
[client]
port=3306
socket=/usr/local/mysql/data/mysql.socket
[mysqld]
# innodb_buffer_pool_size = 128M
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
user=mysql
#server_id=
socket=/usr/local/mysql/data/mysql.socket
pid-file=/usr/local/mysql/data/mysqld.pid
log-error=/usr/local/mysql/data/mysqld-error.log
#join_buffer_size = 128M
#sort_buffer_size = 2M
#read_rnd_buffer_size = 2M
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
#skip-grant-tables
bind-address=0.0.0.0
#explicit_defaults_for_timestamp=true
EOF

#11. 启动服务 mysqld_safe, 修改root密码
/usr/local/mysql/bin/mysqld_safe --skip-grant-tables &

source /etc/profile
sleep 10

#source mysql_update_passwd
#insert into user values('%', 'root', password('root'),'Y','Y','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',
#'N','N,'N','N','N','N','N','','','','','0','0','0','0','mysql_native_password','','N');
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
grant all privileges on *.* to 'mysql'@'%';
flush privileges;
EOF

sleep 5

#12.设置iptables自动启动为关闭
chkconfig iptables off
service iptables stop

#添加 mysql 服务至 work zone
firewall-cmd --zone=public --permanent --add-service=mysql
systemctl restart firewalld

# 关闭
systemctl stop firewalld
# 取消开机启动
systemctl disable firewalld

# find port of mysql
netstat -nltp

# 13. 启动服务mysql
/usr/local/mysql/support-files/mysql.server start


# find port of mysql
netstat -nltp


source /etc/profile

#./bin/mysqladmin -u root  password 'root'
#./bin/mysql -h127.0.0.1 -uroot -proot

#https://www.cnblogs.com/jessica-test/p/9047431.html

# 问题1
#如果系统缺少Data:Dumper模块需要打开下面命令
#yum -y install autoconf;

# 2
#/usr/sbin/mysqld: Table 'mysql.plugin' doesn't exist

#原因：
#table ‘mysql.host’不存在的原因是因为新安装的mysql服务后，
#一般需要执行数据库初始化操作 ，从而生成与权限相关的表，

#/usr/bin/mysql_install_db --user=mysql

# 3
#mysql 添加远程用户或者允许远程访问三种方法
#
#添加远程用户admin密码为password
#GRANT ALL PRIVILEGES ON *.* TO admin@localhost IDENTIFIED BY \'password\' WITH GRANT OPTION
#GRANT ALL PRIVILEGES ON *.* TO admin@\"%\" IDENTIFIED BY \'password\' WITH GRANT OPTION
#grant 权限 on 数据库教程名.表名 to 用户@登录主机 identified by "用户密码";
#@ 后面是访问mysql的客户端ip地址（或是 主机名） % 代表任意的客户端，如果填写 localhost为本地访问（那此用户就不能远程访问该mysql数据库了）。
#同时也可以为现有的用户设置是否具有远程访问权限



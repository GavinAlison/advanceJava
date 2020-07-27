# 创建用户并赋权
```
#foo表示你要建立的用户名，后面的123表示密码,
#localhost限制在固定地址localhost登陆
CREATE USER nacos@localhost IDENTIFIED BY 'nacos';
CREATE USER nacos@% IDENTIFIED BY 'nacos';

#创建数据库并指定字符编码
CREATE SCHEMA `database_name` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

授权
GRANT privileges ON databasename.tablename TO 'username'@'host'

GRANT INSERT,DELETE,UPDATE,SELECT ON test.user TO 'foo'@'localhost';
flush privileges;


设置与更改用户密码
SET PASSWORD FOR 'username'@'host' = PASSWORD('newpassword')
如果是当前登陆用户
SET PASSWORD = PASSWORD("newpassword");

update mysql.user set password=password('新密码') where User="phplamp" and Host="localhost";

```

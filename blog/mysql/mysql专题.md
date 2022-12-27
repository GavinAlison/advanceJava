**mysql 问题： ERROR 1040: Too many connections**

解决

只是在当前连接有效， 下次重启之后失效

```
SHOW VARIABLES LIKE '%connections%';
SHOW STATUS LIKE 'Thread%';
set GLOBAL max_connections=1024;
SHOW VARIABLES LIKE '%connections%';
```

永久有效， 修改配置文件

```
修改mysql配置文件my.cnf，在[mysqld]段中添加或修改max_connections值：
max_connections=1024
重启mysql
```


# 清库清数据，解决因为外键依赖的问题

mysql -h36.10.12.42 -ujrxany -pany1234  -e "
-- 禁用外键约束
set  foreign_key_checks=0;

truncate tmp;

-- 启用外键约束
set foreign_key_checks=1;
"


# MySQL 下批量清空某个库下的所有表(库不要删除，保留空库)
mysql -h36.10.12.42 -ujrxany -pany1234  -e "
-- 禁用外键约束
set  foreign_key_checks=0;

::sql

-- 启用外键约束
set foreign_key_checks=1;
"

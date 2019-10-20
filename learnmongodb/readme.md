什么是MongoDB ?    
MongoDB 是由C++语言编写的，是一个基于分布式文件存储的开源数据库系统。
在高负载的情况下，添加更多的节点，可以保证服务器性能。
MongoDB 旨在为WEB应用提供可扩展的高性能数据存储解决方案。
MongoDB 将数据存储为一个文档，数据结构由键值(key=>value)对组成。
MongoDB 文档类似于 JSON 对象。字段值可以包含其他文档，数组及文档数组。

MongoDB 是一个面向文档存储的数据库，操作起来比较简单和容易。



MongoDB 概念解析
SQL术语/概念	MongoDB术语/概念	解释/说明
database |	database	数据库
table |	collection	数据库表/集合
row	| document |	数据记录行/文档
column	| field | 	数据字段/域
index	| index	| 索引
table joins	|   | 	表连接,MongoDB不支持
primary key	| primary key	| 主键,MongoDB自动将_id字段设置为主键

数据库
一个mongodb中可以建立多个数据库。
MongoDB的默认数据库为"db"，该数据库存储在data目录中。
MongoDB的单个实例可以容纳多个独立的数据库，每一个都有自己的集合和权限，不同的数据库也放置在不同的文件中。
"show dbs" 命令可以显示所有数据的列表。











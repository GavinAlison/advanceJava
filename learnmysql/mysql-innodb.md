## innodb如何查看是否是锁表或者锁行?



TODO:

## 关于innodb什么时候使用行级锁和什么时候使用表级锁

只有在你增删改查时匹配的条件字段带有索引时，innodb才会使用行级锁，
在你增删改查时匹配的条件字段不带有索引时，innodb使用的将是表级锁。

注意：如果这个索引是一个范围过滤的话，是表级锁。

因为当你匹配条件字段不带有所引时，数据库会全表查询，所以这需要将整张表加锁,才能保证查询匹配的正确性。

在生产环境中我们往往需要满足多人同时对一张表进行增删改查，所以就需要使用行级锁，所以这个时候一定要记住为匹配条件字段加索引。

提到行级锁和表级锁时我们就很容易联想到读锁和写锁，因为只有触发了读写锁，我们才会谈是进行行级锁定还是进行表级锁定。
那么什么时候触发读锁，就是在你用select 命令时触发读锁，
什么时候触发写锁，就是在你使用update,delete,insert时触发写锁，并且使用rollback或commit后解除本次锁定

行级锁是基于索引的实现的，没有索引，或者该索引条件不唯一，不会走行级锁，只会走表锁。


```
InnoDB中的select .. for update语句：

	1)select .. for update语句仅适用于InnoDB
	2)select .. for update语句必须在事务中才能生效。
	3)在执行事务中的select .. for update语句时，MySQL会对查询结果集中的每行数据都添加排他锁(行锁、表锁)，其它线程对锁定行的 更新、删除、select .. for update查询 这3种操作都会被阻塞，一般的select语句不会被阻塞。
	4)查看自动提交是否开启(1表示开启，0表示关闭，默认开启)： select @@autocommit
	5)InnoDB行级锁的实现：InnoDB的行级锁是通过在索引上加锁来实现的，所以只有通过明确的索引来查找数据时才会使用行级锁。

排它锁的选择：

	若where条件中明确指定了主键，且该行数据存在，则只锁定该行，故排它锁为行锁(row lock)。
	若where条件中明确指定了主键，但是该行数据不存在，则不会加锁。
	
	若where条件中明确指定了索引，且该行数据存在，则只锁定该行，故排它锁为行锁(row lock)。
	若where条件中明确指定了索引，但是该行数据不存在，则不会加锁。
	
	若where条件中未明确指定主键或索引，则会锁定全表，故排它锁为表锁(table lock)。
	注：未明确指定 即 未指定(主键/索引) 或 指定的是(主键/索引)的范围

eg：
	# 只锁定message_id为1的行
	set autocommit=0;
	begin;
	select * from t_message where message_id=1 for update; # message_id为主键
	commit;

	# 锁定全表
	set autocommit=0;
	begin;
	select * from t_message where message_id>1 for update; # message_id为主键
	commit;
	
	# 锁定全表
	set autocommit=0;
	begin;
	select * from t_message where type='good' for update; # good非索引列
	commit;


	其它线程因为等待(排它锁)超时而报错：
	update t_message set title='asdf' where message_id=1;
	[Err] 1205 - Lock wait timeout exceeded; try restarting transaction
```
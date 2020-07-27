# greenplum常用命令&运维命令

目录
[toc]

## 元数据

#### 查看所有表基本信息

1. 所有表信息，不包含分区表的子表

```
SELECT
	psut.relid,
	psut.relname,
	psut.schemaname
FROM
	pg_statio_user_tables psut
	LEFT JOIN pg_inherits pi ON psut.relid = pi.inhrelid 
WHERE
	schemaname = 'public' 
	AND pi.inhparent IS NULL
ORDER BY 2
```

2. 所有表信息，包含分区表的子表

```
SELECT
	psut.relid,
	psut.relname,
	psut.schemaname
FROM
	pg_statio_user_tables psut
	LEFT JOIN pg_inherits pi ON psut.relid = pi.inhrelid 
WHERE
	schemaname = 'public' 
	-- AND pi.inhparent IS NULL
ORDER BY 2
```


#### 查看表索引信息

包含索引的名称和索引的SQL定义语句。

1. 查看所有表的索引信息

```
SELECT 
	A.SCHEMANAME,
	A.TABLENAME,
	A.INDEXNAME,
	A.TABLESPACE,
	A.INDEXDEF,
	B.AMNAME,
	C.INDEXRELID,
	C.INDNATTS,
	C.INDISUNIQUE,
	C.INDISPRIMARY,
	C.INDISCLUSTERED,
	D.DESCRIPTION 
FROM
	PG_AM B
	LEFT JOIN PG_CLASS F ON B.OID = F.RELAM
	LEFT JOIN PG_STAT_ALL_INDEXES E ON F.OID = E.INDEXRELID
	LEFT JOIN PG_INDEX C ON E.INDEXRELID = C.INDEXRELID
	LEFT OUTER JOIN PG_DESCRIPTION D ON C.INDEXRELID = D.OBJOID,
	PG_INDEXES A 
WHERE
	A.SCHEMANAME = E.SCHEMANAME 
	AND A.TABLENAME = E.RELNAME 
	AND A.INDEXNAME = E.INDEXRELNAME 
	AND E.SCHEMANAME = 'public'
```

2. 查看单表的索引信息

```
SELECT 
	A.SCHEMANAME,
	A.TABLENAME,
	A.INDEXNAME,
	A.TABLESPACE,
	A.INDEXDEF,
	B.AMNAME,
	C.INDEXRELID,
	C.INDNATTS,
	C.INDISUNIQUE,
	C.INDISPRIMARY,
	C.INDISCLUSTERED,
	D.DESCRIPTION 
FROM
	PG_AM B
	LEFT JOIN PG_CLASS F ON B.OID = F.RELAM
	LEFT JOIN PG_STAT_ALL_INDEXES E ON F.OID = E.INDEXRELID
	LEFT JOIN PG_INDEX C ON E.INDEXRELID = C.INDEXRELID
	LEFT OUTER JOIN PG_DESCRIPTION D ON C.INDEXRELID = D.OBJOID,
	PG_INDEXES A 
WHERE
	A.SCHEMANAME = E.SCHEMANAME 
	AND A.TABLENAME = E.RELNAME 
	AND A.INDEXNAME = E.INDEXRELNAME 
	AND E.SCHEMANAME = 'public' 
	AND E.RELNAME = 'table_name';
```

#### 查询表的所有列信息
1. 查看单表的列信息

```
SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'table_name';
```


2. 查看单表列名称，逗号分隔

```
SELECT string_agg(column_name, ',') FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'table_name';
```


3. 查看所有表的列信息

```
SELECT * FROM information_schema.columns WHERE table_schema = 'public' ;
```


4. 查看所有表的列名称，逗号分隔

```
SELECT string_agg(column_name, ','),table_name FROM information_schema.columns WHERE table_schema = 'public' GROUP BY 2;
```


#### 查看哪些表是AO表哪些是HEAP表

1. 查询某个表是否为AO表，查询出来有记录就为AO表，无记录就是HEAP表

```
SELECT relid::regclass table_name,compresslevel,compresstype,columnstore FROM pg_appendonly WHERE relid::regclass = 'bm_acct_loan'::regclass;
```

2. 查询所有AO表信息

```
SELECT relid::regclass table_name,* FROM pg_appendonly;
```

#### 查看AO表的分布键

1. 查询所有表的分布键

```
SELECT 
    att.attname distributed_str,
    gpdp.localoid::regclass table_name
FROM 
    gp_distribution_policy gpdp,
    pg_attribute att,
    pg_class pg,
    pg_namespace pn
WHERE pg.oid = gpdp.localoid
    AND pn.oid = pg.relnamespace
    AND gpdp.localoid = att.attrelid
    AND att.attnum = any(gpdp.distkey)
    AND pn.nspname = 'public';
```

2. 查询所有表的分布键，逗号分隔

```
SELECT 
    string_agg(att.attname,',') distributed_str,
    gpdp.localoid::regclass table_name
FROM gp_distribution_policy gpdp,
    pg_attribute att,
    pg_class pg,
    pg_namespace pn
WHERE  pg.oid = gpdp.localoid
    ANd pn.oid = pg.relnamespace
    AND gpdp.localoid = att.attrelid
    AND att.attnum = any(gpdp.distkey)
    AND pn.nspname = 'public'
GROUP BY 2;
```

#### 查看分区表信息

1. 查看某个分区表的信息

```
SELECT
	tablename,
	partitiontablename,
	partitiontype,
	partitionboundary 
FROM
	pg_partitions 
WHERE
	tablename = 'table_name' 
ORDER BY
	partitionboundary DESC;
```

#### 修改表字段类型

```
修改类型为 numeric(10,2)
ALTER TABLE "public”.table_name ALTER COLUMN column_name TYPE numeric(10,2) USING column_name::numeric(10,2);

修改类型为 INT
alter table "member" alter COLUMN imgfileid type int using imgfileid::int ;

```

## 数据运维

#### 启动和停止Greenplum数据库

1. 启动Greenplum数据库

```
gpstart
```

2. 重新启动Greenplum数据库，停止Greenplum数据库系统，然后重新启动它。
    
```
gpstop -r
```

3. 仅重新加载配置文件更改，将更改重新加载到Greenplum数据库配置文件，而不会中断系统。
            
```
gpstop -u
```

4. 在维护模式下启动主机，仅启动主服务器以执行维护或管理任务，而不会影响段上的数据。
            
```
gpstart -m
```

5. 停止Greenplum数据库

```
gpstop
```

6. 要以快速模式停止Greenplum数据库
    
```
gpstop -M fast
```

### 节点情况
#### greenplum总体运行状态
```
[gpadmin@VM01 ~]$ gpstate

20200605:14:24:58:016044 gpstate:VM01:gpadmin-[INFO]:-Starting gpstate with args:
20200605:14:24:58:016044 gpstate:VM01:gpadmin-[INFO]:-local Greenplum Version: 'postgres (Greenplum Database) 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c'
20200605:14:24:58:016044 gpstate:VM01:gpadmin-[INFO]:-master Greenplum Version: 'PostgreSQL 9.4.24 (Greenplum Database 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c) on x86_64-unknown-linux-gnu, compiled by gcc (GCC) 6.4.0, 64-bit compiled on Sep  4 2019 01:12:33'
20200605:14:24:58:016044 gpstate:VM01:gpadmin-[INFO]:-Obtaining Segment details from master...
20200605:14:24:58:016044 gpstate:VM01:gpadmin-[INFO]:-Gathering data from segments...
gpadmin@vm01's password: .
.
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-Greenplum instance status summary
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Master instance                                = Active
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Master standby                                 = No master standby configured
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total segment instance count from metadata     = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Primary Segment Status
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total primary segments                         = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total primary segment valid (at master)        = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total primary segment failures (at master)     = 0
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of postmaster.pid files missing   = 0
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of postmaster.pid files found     = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of postmaster.pid PIDs missing    = 0
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of postmaster.pid PIDs found      = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of /tmp lock files missing        = 0
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number of /tmp lock files found          = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number postmaster processes missing      = 0
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Total number postmaster processes found        = 2
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Mirror Segment Status
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-   Mirrors not configured on this array
20200605:14:25:01:016044 gpstate:VM01:gpadmin-[INFO]:-----------------------------------------------------
```
#### 查看segment节点状态
```
select * from gp_segment_configuration;
```
#### 查看segment节点故障等历史信息
```
select * from gp_configuration_history order by 1 desc ;
```
#### 检查segment instance同步状态

该命令会输出各节点实例的同步状态，各节点的状态为“synchronizing”时为不正常

如果有数据，表示segment instance正在同步，隔几分钟再做一次，如果有实例长时间都不能同步完成，需要报给DBA做进一步监控
```
[gpadmin@VM01 ~]$ gpstate -m
20200605:14:35:54:016749 gpstate:VM01:gpadmin-[INFO]:-Starting gpstate with args: -m
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[INFO]:-local Greenplum Version: 'postgres (Greenplum Database) 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c'
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[INFO]:-master Greenplum Version: 'PostgreSQL 9.4.24 (Greenplum Database 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c) on x86_64-unknown-linux-gnu, compiled by gcc (GCC) 6.4.0, 64-bit compiled on Sep  4 2019 01:12:33'
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[INFO]:-Obtaining Segment details from master...
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[WARNING]:--------------------------------------------------------------
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[WARNING]:-Mirror not used
20200605:14:35:55:016749 gpstate:VM01:gpadmin-[WARNING]:--------------------------------------------------------------
```
#### 查看segment节点磁盘空闲情况
```
SELECT * FROM gp_toolkit.gp_disk_free;
```
#### 检查standby同步状态
该命令会输出Standby Master的同步状态，Standby Master状态为“synchronizing”时为不正常
```
[gpadmin@csmysql02 ~]$ gpstate -f

20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-Starting gpstate with args: -f
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-local Greenplum Version: 'postgres (Greenplum Database) 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c'
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-master Greenplum Version: 'PostgreSQL 9.4.24 (Greenplum Database 6.0.0 build commit:a2ff2fc318c327d702e66ab58ae4aff34c42296c) on x86_64-unknown-linux-gnu, compiled by gcc (GCC) 6.4.0, 64-bit compiled on Sep  4 2019 01:12:33'
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-Obtaining Segment details from master...
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-Standby master instance not configured
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:--------------------------------------------------------------
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:--pg_stat_replication
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:--------------------------------------------------------------
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:-No entries found.
20200605:16:21:22:040090 gpstate:csmysql02:gpadmin-[INFO]:--------------------------------------------------------------
```
#### 检查数据库日志
日志级别： PANIC > FATAL > ERROR

```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "source /usr/local/greenplum-db/greenplum_path.sh &&  gplogfilter -b '2020-05-30 10:00:00' -e '2020-05-30 18:30:00' -f 'FATAL'" /data/primary*/gpseg*/pg_log/gpdb-2020-05-30_000000.csv


gpssh -f /app/gpadmin/gpconfigs/seghosts -e "source /usr/local/greenplum-db/greenplum_path.sh &&  gplogfilter -b '2020-05-30 10:00:00' -e '2020-05-30 18:30:00' -f 'PANIC'" /data/primary*/gpseg*/pg_log/gpdb-2020-05-30_000000.csv


gpssh -f /app/gpadmin/gpconfigs/seghosts -e "source /usr/local/greenplum-db/greenplum_path.sh &&  gplogfilter -b '2020-05-30 10:00:00' -e '2020-05-30 18:30:00' -f 'ERROR'" /data/primary*/gpseg*/pg_log/gpdb-2020-05-30_000000.csv
```
#### 显示在系统表中被标记为掉线的Segment的信息
```
SELECT * from gp_toolkit.gp_pgdatabase_invalid;
```

### 系统情况
#### 检查OS的日志中是否有出错告警
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "cat /var/log/messages"
```
#### 检查/tmp目录空间使用率
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "df -h |grep /tmp"
```
#### 检查GP数据目录空间使用率
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "df -h |grep /data"
```
#### 检查Raid卡状态
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "omreport storage vdisk"


status：ok
state：ready
Read Policy：Read Ahead
Write Policy:Write Back
```
#### 检查磁盘
是否有错误报出
```
1.gpssh -f /app/gpadmin/gpconfigs/seghosts -e "omreport storage pdisk controller=0 |grep -i fail/state "

2.gpssh -f /app/gpadmin/gpconfigs/seghosts -e "omreport storage pdisk controller=1 grep -i fail/state"
```
#### OS关键目录剩余空间监控
剩余空间应大于30%
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "df -h |grep tmp"
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "df -h |grep sda1"
```
#### 检查RAID卡电池状态
检查是否有充电中的节点，如果有，需要请系统管理员及时跟进充电状态，如果持续10小时未能完成充电，需要请硬件原厂服务判断是否需要更换RAID卡电池
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "omreport storage battery"
```
#### 时钟同步情况
使集群内所有机器的时间一致

如果时间不一致，需要立即同步

```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "date"
```
#### Hosts文件检查
收集各台主机的/etc/hosts文件，检查各台主机hosts文件内容是否一致
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "cat /etc/hosts"
```
#### 系统参数核查
系统参数核查是否正确
```
gpssh -f /app/gpadmin/gpconfigs/seghosts -e "more /etc/project"
```

### 其他
#### 查看正在执行的SQL进程
1. 指定列名称查询
```
SELECT datname,pid,usename,waiting,waiting_reason,state,query,application_name,client_addr,client_port,backend_start,query_start FROM pg_stat_activity;
```
2. 正在活动的进程
```
select * from pg_stat_activity where state ='active’; 
```
3. 空闲的进程
```
select * from pg_stat_activity where state =‘idle'; 
```
#### Kill正在执行的SQL进程
1. 这个函数只能 kill、Select查询，而updae、delete DML不生效
```
select pg_cancel_backend(进程pid);
```
2. 可以kill 各种DML(SELECT,UPDATE,DELETE,DROP)操作
```
select  pg_terminate_backend(线程id);
```

#### 资源队列检查
#### 查看greenplum资源队列状态
```
SELECT * FROM gp_toolkit.gp_resqueue_status;
```
#### 查看greemplum资源队列锁
```
SELECT * FROM gp_toolkit.gp_locks_on_resqueue WHERE lorwaiting='true';
```
#### 查看greemplum资源队列优先级
```
select *  from gp_toolkit.gp_resq_priority_statement;
```
#### 表数据重新分布
1. 不会改变分布键，会清理过期空间 
```
ALTER TABLE table_name SET WITH (REORGANIZE=true); 
```
2. 指定新的分布键，会清理过期空间
```
ALTER TABLE table_name SET WITH (REORGANIZE=true) DISTRIBUTED BY (column_name,column_name); 
```
3. 修改表分布策略为随机分布，但是不移动数据
```
ALTER TABLE table_name SET WITH (REORGANIZE=false) DISTRIBUTED randomly; 
```
#### 查看数据库使用容量
1. greemplum所有数据库大小（占用空间）
```
select datname,pg_size_pretty(pg_database_size(datname)) from pg_database;
```
2. greemplum查看指定数据库大小（占用空间）
```
select pg_size_pretty(pg_database_size('postgres'));
```
#### 查看表容量
1. 查询分区表容量，优化容量大小为可读
```
SELECT pg_size_pretty((t1.raw_data_size)) FROM (
    SELECT SUM(pg_relation_size(psut.relid)) as raw_data_size
             FROM pg_statio_user_tables  psut
       INNER JOIN pg_inherits pi ON psut.relid = pi.inhrelid
            WHERE pi.inhparent = ‘scheme.table_name'::regclass
) t1;
```
2. 查询分区表容量
```
SELECT SUM(pg_relation_size(psut.relid)) as raw_data_size
    FROM pg_statio_user_tables  psut
    INNER JOIN pg_inherits pi ON psut.relid = pi.inhrelid
        WHERE pi.inhparent = ‘scheme.table_name'::regclass;
```
3. 查询表容量，优化容量大小为可读
```
SELECT pg_size_pretty(pg_relation_size('scheme.table_name’));
```
4. 查询表容量
```
select pg_size_pretty(pg_total_relation_size('scheme.table_name'));
```
#### 查看表是否需要ANALYZE
此视图显示没有统计信息的表，因此可能需要在表上运行分析。
```
SELECT * FROM gp_toolkit.gp_stats_missing;
SELECT * FROM gp_toolkit.gp_stats_missing where smisize = 'f’;
```
#### 收集统计分析信息 ANALYZE TABLE_NAME
```
ANALYZE table_name;
```
#### 查看表膨胀情况
这个视图显示了具有膨胀的常规堆存储表(给定表统计数据，磁盘上的实际页面数超过了预期的页面数)。膨胀的表需要一个VACUUM或一个VACUUM FULL，以回收被删除或废弃的行占用的磁盘空间。
```
SELECT * FROM gp_toolkit.gp_bloat_diag;
```
#### 查看数据分布和倾斜情况
```
select gp_segment_id,count(*) from table_name group by gp_segment_id order by 1;

SELECT
    t1.gp_segment_id,
    t1.count_tatol,
    round( t1.count_tatol - ( AVG ( t1.count_tatol ) OVER ( ) ), 0 ) 
FROM
    ( SELECT gp_segment_id, COUNT ( * ) count_tatol FROM table_name
    GROUP BY gp_segment_id ) t1 
ORDER BY
    3

```
#### SQL执行时间检查
该命令会输出当前正在执行的SQL信息，检查各语句的“query_start”字段中的时间是否在6个小时之前。

与应用联系确认该语句执行时间是否正常。如不正常，通知DBA处理。
```
select
   procpid , usename ,replace(current_query,E'\x09','') as current_query, 
   waiting ,date_trunc('seconds', query_start)::timestamp as query_start, 
   to_char( (now() - query_start), 'HH24:MI:SS') as duration  , 
   client_addr, client_port, application_name, xact_start
   from pg_stat_activity;
```
#### 数据增长状态
与前一月的数据做对比,如果增长量出现异常，需要进一步分析
```
select pg_size_pretty(pg_database_size('db_name'));
```
#### Statistics 状态检查
```
select * from gp_toolkit.gp_stats_missing  where smisize='f';
```

#### 数据库参数核查
```
max_connections
max_prepared_transaction
gp_fault_action
gp_interconnect_setup_timeout
max_fsm_relations
max_fsm_pages
gp_vmem_protect_limit
work_mem
stats_queue_level

1. 查看参数
gpconfig -s param_name

2. 设置参数
gpconfig -c gp_enable_global_deadlock_detector -v on
```
#### 重新建立数据库目录的索引
提高数据库目录性能
```
Reindex system <databasename>;
```



## 备份和导入导出

#### 并发数据备份
```
[gpadmin@VM01 bin]$ gpbackup -dbname dw_szsbank_est_import --backup-dir /home/gpadmin/gpbackup/

20200603:09:26:41 gpbackup:gpadmin:VM01:027659-[INFO]:-Starting backup of database dw_szsbank_est_import
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Backup Timestamp = 20200603092641
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Backup Database = dw_szsbank_est_import
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Gathering table state information
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Acquiring ACCESS SHARE locks on tables
Locks acquired:  58 / 58 [==============================================================] 100.00% 0s
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Gathering additional table metadata
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Getting partition definitions
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Getting storage information
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Metadata will be written to /home/gpadmin/gpbackup/gpseg-1/backups/20200603/20200603092641/gpbackup_20200603092641_metadata.sql
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Writing global database metadata
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Global database metadata backup complete
20200603:09:26:42 gpbackup:gpadmin:VM01:027659-[INFO]:-Writing pre-data metadata
20200603:09:26:43 gpbackup:gpadmin:VM01:027659-[INFO]:-Pre-data metadata backup complete
20200603:09:26:43 gpbackup:gpadmin:VM01:027659-[INFO]:-Writing post-data metadata
20200603:09:26:43 gpbackup:gpadmin:VM01:027659-[INFO]:-Post-data metadata backup complete
20200603:09:26:43 gpbackup:gpadmin:VM01:027659-[INFO]:-Writing data to file
Tables backed up:  58 / 58 [==========================================================] 100.00% 5m1s
20200603:09:31:45 gpbackup:gpadmin:VM01:027659-[INFO]:-Data backup complete
20200603:09:31:46 gpbackup:gpadmin:VM01:027659-[INFO]:-Found neither /usr/local/greenplum-db/./bin/gp_email_contacts.yaml nor /home/gpadmin/gp_email_contacts.yaml
20200603:09:31:46 gpbackup:gpadmin:VM01:027659-[INFO]:-Email containing gpbackup report /home/gpadmin/gpbackup/gpseg-1/backups/20200603/20200603092641/gpbackup_20200603092641_report will not be sent
20200603:09:31:46 gpbackup:gpadmin:VM01:027659-[INFO]:-Backup completed successfully
```
#### 并发数据恢复
```
[gpadmin@VM01 ~]$ gprestore --backup-dir /home/gpadmin/gpbackup --timestamp 20200603092641 --redirect-db dw_szsbank_est_import_restore --create-db

20200604:07:33:47 gprestore:gpadmin:VM01:000423-[INFO]:-Restore Key = 20200603092641
20200604:07:33:48 gprestore:gpadmin:VM01:000423-[INFO]:-Creating database
20200604:07:33:50 gprestore:gpadmin:VM01:000423-[INFO]:-Database creation complete for: dw_szsbank_est_import_restore
20200604:07:33:50 gprestore:gpadmin:VM01:000423-[INFO]:-Restoring pre-data metadata
Pre-data objects restored:  1384 / 1384 [===============================================] 100.00% 7s
20200604:07:33:57 gprestore:gpadmin:VM01:000423-[INFO]:-Pre-data metadata restore complete
Tables restored:  58 / 58 [=========================================================] 100.00% 13m55s
20200604:07:47:52 gprestore:gpadmin:VM01:000423-[INFO]:-Data restore complete
20200604:07:47:52 gprestore:gpadmin:VM01:000423-[INFO]:-Restoring post-data metadata
20200604:07:47:53 gprestore:gpadmin:VM01:000423-[INFO]:-Post-data metadata restore complete
20200604:07:47:53 gprestore:gpadmin:VM01:000423-[INFO]:-Found neither /usr/local/greenplum-db/./bin/gp_email_contacts.yaml nor /home/gpadmin/gp_email_contacts.yaml
20200604:07:47:53 gprestore:gpadmin:VM01:000423-[INFO]:-Email containing gprestore report /home/gpadmin/gpbackup/gpseg-1/backups/20200603/20200603092641/gprestore_20200603092641_20200604073347_report will not be sent
20200604:07:47:53 gprestore:gpadmin:VM01:000423-[INFO]:-Restore completed successfully

```
#### COPY方式导出数据
##### 导出数据带表头
```
COPY (SELECT * FROM table_name ) TO 'xxx.csv DELIMITER AS E'\001' HEADER CSV;
```
##### 导出数据不带表头
```
COPY (SELECT * FROM table_name ) TO 'xxx.csv DELIMITER AS E'\001' HEADER CSV;
```

##### copy导入数据
```
COPY  table_name FROM 'xxx.csv DELIMITER AS E'\001' HEADER NULL AS '' CSV;

COPY  table_name FROM 'xxx.csv DELIMITER AS E'\001' NULL AS '' CSV;
```



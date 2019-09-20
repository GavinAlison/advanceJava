Redis设计与实现
-   1.特性及应用场景
    -   特性
        -   单线程 
        -   纯内存访问
        -   支持数据持久化
        -   非阻塞IO(epoll)
    -   应用场景
        -   缓存
        -   队列
        -   网站访问统计
        -   分布式Session
        -   应用排行榜
        -   社交关系图
    -   版本新特性
        -   2.8
            -   主从断线重连后采取部分复制(Psync)
            -   Redis Sentinel Stable
        -   3.0
            -   Redis Cluster
        -   3.2
            -   GEO
        -   4.0
            -   提供模块系统方便第三方拓展
            -   非阻塞del和flushall/flushdb功能
            -   RDB和AOF混合持久化模式
            -   Redis Cluster兼容NAT和Docker
        -   5.0
            -   新数据类型Stream(借鉴了Kafka的设计,消息可持久化)
            -   新Redis模块API:Timers and Cluster API
            -   RDB现在存储LFU和LRU信息
            -   集群管理器从Ruby(redis-trib.rb)改成C重写
            -   新sorted set命令:ZPOPMIN/MAX和阻塞变量
            -   主动碎片整理
            -   增强HyperLogLog实现
            -   内存统计报告更直观
            -   Jemalloc升级到5.1版
-   2.数据结构及内部编码
    -   基本数据类型
        -   字符串(String)
        -   整型(int)
        -   embstr编码的简单动态字符串
        -   简单动态字符串
    -   列表(List)
        -   链表(Linkedlist)
        -   快速列表(Quicklist)
    -   哈希(Hash)
        -   哈希表(Hashtable)
        -   压缩表(Ziplist)
    -   集合(Set)
        -   整型集合(intset)
        -   哈希表(Hashtable)
        有序集合(Zset)
        -   压缩表(Ziplist)
        -   跳跃表(Skiplist)
    -   其他数据类型
        -   Bitmap
        -   Hyperloglog
        -   GEO
-   3.持久化
    -   RDB
        -   内存快照
        -   恢复速度快,持久化性能高.但是存在数据丢失风险
    -   AOF
        -   日志文件追加记录
        -   实时持久化,数据安全性更高.持久化效率低
-   4.复制
    -   原理
        -   同步RDB文件
        -   复制缓冲区
    -   类型
        -   部分复制(Psync,2.8版本之后)
        -   全量复制(Sync)
    -   拓扑
        -   星型
            -   主 - 从(单节点)
            -   主 -  从(多节点)
        -   树型
            -   主 - 从(主) - 从
-   5.高可用
    -   Redis Sentinel
    -   Redis Cluster(集群自带高可用)
    -   Keepalived
-   6.分布式
    -   方案
        -   集群
            -   Redis Cluster
        -   中间件分片
            -   TwemProxy
            -   CodisProxy
        -   客户端分片
            -   业务程序
    -   原理
        -   客户端分片
            -   一致性哈希算法
        -   Codis
            -   虚拟槽分区(1024个槽)
            -   集群内部数据节点独立运作,无需相互通信
        -   Redis Cluster
            -   Gossip协议 -- 集群数据节点内部相互通信
            -   Raft算法 -- 集群内选主
            -   虚拟槽分区(16384个槽)
-   7.阻塞
    -   持久化阻塞
        -   Fork子进程
            -   RDB持久化
            -   AOF文件重写
    -   命令阻塞
        -   keys *
        -   smembers
        -   lrange
        -   hgetall
-   8.内存
    -   内存消耗
        -   对象内存
        -   存储着所有数据
    -   缓冲内存
        -   客户端缓冲
            -   通过参数client-output-buffer-limit控制
        -   复制积压缓冲区
            -   根据repl-backlog-size参数控制
        -   AOF缓冲区
            -   用于在Redis重写AOF文件期间保存最近的写入命令
    -   内存碎片
        -   可采用数据对齐和安全重启等方式规避内存碎片的问题
    -   内存回收策略
        -   惰性删除
        -   定时任务删除
    -   内存优化
        -   缩减键值对象的长度
        -   共享对象池
        -   字符串优化
        -   编码优化(使用ziplist编码能节约内存但会提高耗时-空间换时间)
        -   控制键的数量(如使用hash结构重构字符串结构)
-   9.客户端
    -   Jedis(Java)
    -   Redis-py(Python)
    -   Redigo(GO)
-   10.辅助功能
    -   慢查询(slowquery)
    -   管道(pipeline)
-   11.缓存问题隐患
    -   缓存无底洞
    -   缓存穿透
    -   缓存雪崩
    -   热点key倾斜
    -   热点key重建
-   12.性能调优
    -   vm.overcommit_memory=1
    -   vm.swapiness=1
    -   关闭THP特性
        -   echo never > /sys/kernel/mm/transparent_hugepage/enabled
    -   调大ulimit
    -   调大TCP Backlog
    -   Redis参数调优
        -   appendfsync
        -   no-appendfsync-on-rewrite
        -   auto-aof-rewrite-percentage
        -   auto-aof-rewrite-min-size
        -   hash-max-ziplist-entries
        -   hash-max-ziplist-value
        -   list-max-ziplist-size
        -   set-max-intset-entries
        -   zset-max-ziplist-entries
        -   zset-max-ziplist-value
        -   hll-sparse-max-bytes
        -   client-output-buffer-limit
-   13.监控
    -   info
    -   redis-stat
    -   redislive
    -   redis-cli monitor
    -   redis-cli --latency
    -   Codis Dashboard
    -   Zabbix
    -   Grafana




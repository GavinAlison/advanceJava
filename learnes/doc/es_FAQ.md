## 1. 如何让一个字段不进行分词
设置字段mapping-->`index: not_analyzed`, 这个not_analyzed是不进行分词，但是可以被搜索


## 2. 亿级 Elasticsearch 性能优化
索引效率优化
查询效率优化
JVM 配置优化

### 批量提交

当有大量数据提交的时候，建议采用批量提交。

比如在做 ELK 过程中 ，Logstash indexer 提交数据到 Elasticsearch 中 ，batch size 就可以作为一个优化功能点。
但是优化 size 大小需要根据文档大小和服务器性能而定。

像 Logstash 中提交文档大小超过 20MB ，Logstash 会请一个批量请求切分为多个批量请求。

如果在提交过程中，遇到 EsRejectedExecutionException 异常的话，则说明集群的索引性能已经达到极限了。
这种情况，要么提高服务器集群的资源，要么根据业务规则，减少数据收集速度，比如只收集 Warn、Error 级别以上的日志。

### 优化硬件
优化硬件设备一直是最快速有效的手段。

在经济压力能承受的范围下， 尽量使用固态硬盘 SSD。SSD 相对于机器硬盘，无论随机写还是顺序写，都较大的提升。

磁盘备份采用 RAID0。因为 Elasticsearch 在自身层面通过副本，已经提供了备份的功能，所以不需要利用磁盘的备份功能，
同时如果使用磁盘备份功能的话，对写入速度有较大的影响。

### 增加 Refresh 时间间隔

为了提高索引性能，Elasticsearch 在写入数据时候，采用延迟写入的策略，即数据先写到内存中，当超过默认 1 秒 （index.refresh_interval）会进行一次写入操作，就是将内存中 segment 数据刷新到操作系统中，此时我们才能将数据搜索出来，所以这就是为什么 Elasticsearch 提供的是近实时搜索功能，而不是实时搜索功能。

当然像我们的内部系统对数据延迟要求不高的话，我们可以通过延长 refresh 时间间隔，可以有效的减少 segment 合并压力，提供索引速度。
在做全链路跟踪的过程中，我们就将 index.refresh_interval 设置为 30s，减少 refresh 次数。

同时，在进行全量索引时，可以将 refresh 次数临时关闭，即 index.refresh_interval 设置为 -1，数据导入成功后再打开到正常模式，比如 30s。


-	search 一下not_analyzed 找问题
-	记录一次mapping field修改过程--》https://www.cnblogs.com/Creator/p/3722408.html
-	Elasticsearch Map对not_analyzed文档不敏感--》https://codeday.me/bug/20180824/222086.html
-	Elasticsearch 基础理论 & 配置调优-->https://www.cnblogs.com/zhengchunyuan/p/8065335.html


###  updated host [http://172.16.101.41:9200] already in blacklist

es被拉入到一个blackList，解决方法，使用其他es的ip




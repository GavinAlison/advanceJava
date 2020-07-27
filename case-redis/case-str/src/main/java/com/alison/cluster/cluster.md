## 查考链接
-   [redis5.0.4-cluster集群搭建及jedis客户端操作](https://www.cnblogs.com/dijia478/p/10755580.html)
√
-   [Redis Cluster集群搭建与应用](https://www.cnblogs.com/wlandwl/p/rediscluster.html)
-   [redis cluster管理工具redis-trib.rb详解](http://weizijun.cn/2016/01/08/redis%20cluster%E7%AE%A1%E7%90%86%E5%B7%A5%E5%85%B7redis-trib-rb%E8%AF%A6%E8%A7%A3/)
-   [redis集群报错Node is not empty](https://www.jianshu.com/p/338bc2a74300)
-   [Redis Cluster日常操作命令梳理](https://www.cnblogs.com/kevingrace/p/7910692.html) --有关主从节点添加与删除
-   redis cluster 部署--->https://www.cnblogs.com/wlandwl/p/rediscluster.html
-   [Redis Cluster部署、管理和测试](https://www.cnblogs.com/zhoujinyi/p/6477133.html)  ---看原理就可以
-   [全面剖析Redis Cluster原理和应用](https://blog.csdn.net/dc_726/article/details/48552531)  ---看原理就可以


## 步骤
1. 安装插件， rubygems, ruby, redis-5.0.5, redis-gem
2. 运行redis实例， 3主3从， 主要配置文件cluster-enabled:yes
3. 利用redis-cli --cluster命令配置cluster
`redis-cli --cluster create ip:port --cluster-replicas 1 -a passwd`

## 原理
1. 存储
例如分配三个主节点分别是：7000, 7001, 7002。三个从节点分别是7003，7004，7005。
它们可以是一台机器上的六个端口，也可以是六台不同的服务器。
采用哈希槽 (hash slot)的方式来分配16384个slot 的话，

2. 获取数据
如果存入一个值，按照redis cluster哈希槽的算法： CRC16('key')%16384 = 6782。 就会把这个key 的存储分配到7001 上了。
同样，当连接(7000，7001，7002)任何一个节点想获取'key'这个key时，也会这样的算法，然后内部跳转到7001节点上获取数据 。


## tips
reshard的注意点， slot nums, received node id , source node id 





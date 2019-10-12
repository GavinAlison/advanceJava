## issue
-   参考：https://www.cnblogs.com/littleatp/p/10035786.html，出现只发不收
-   redis数据类型     
-   针对2种场景设计一个redis缓存系统（场景一：并发低、实时性要求高； 场景二：并发高、实时性要求不是特别高）       
-   redis内存满了，为何还能set数据进去；redis内存淘汰策略；        
-   redis的分布式锁如何设置
-   spring data redis 连接超时    
查看redis 是否启动，linux防火墙是否关闭， 在查看redis的password是否设置
我解决的方法是设置password
-   Caused by: io.netty.channel.ConnectTimeoutException: connection timed out: /192.168.56.103:6379     
    才发现redis 并没有启动



## redis的分布式锁如何设置
>[曾奇：谈谈我所认识的分布式锁](ttps://juejin.im/post/5cd01e5ce51d456e537ef3be#heading-10)

-   朴素redis
> setnx + delete
> setnx + setex
-   ZooKeeper
>利用 顺序临时节点和Watch机制
-   Redisson
> Redisson 架设在 redis 基础上的 Java 驻内存数据网格（In-Memory Data Grid），基于NIO的 Netty 框架上，利用了 redis 键值数据库。功能非常强大，解决了很多分布式架构中的问题。


## 什么是分布式锁？
在分布式环境中，多进程共同抢占一个资源，为了保证一个方法在某一时刻，只有一台机器里一个进程的一个线程在运行

## 分布式锁需要的特性
-   可扩展
-   可靠性，容错性
-   可重入性
-   可获取锁和释放锁

## 实现
-   朴素redis
> setnx + delete
> setnx + setex
-   ZooKeeper
>利用 顺序临时节点和Watch机制
-   Redisson
> Redisson 架设在 redis 基础上的 Java 驻内存数据网格（In-Memory Data Grid），基于NIO的 Netty 框架上，利用了 redis 键值数据库。功能非常强大，解决了很多分布式架构中的问题。
> Redission通过Netty Future机制、Semaphore （jdk信号量）、redis锁实现。
> trylock 意思就是尝试获取锁，获取不到就说明别人没有获取到锁，然后就可以加锁了

-   RedisLockRegistry
> spring-integration-redis 提供， 原理，通过本地锁 + redis锁 双重锁机制。

-   RedLock



## FAQ
1.  分布锁出现`ERR Error running script (call to f_9401052d872adfd0179ef8c8e8c028512707629a): @user_script:1: WR`问题
> 注意是编码问题， 对于字符使用StringCoderc, 默认使用的是JackJsonCoderc


相应查考链接： https://github.com/redisson/redisson/issues/480
https://www.lovecto.cn/20180912/314.html




https://www.zhihu.com/search?type=content&q=Redisson%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%20%E9%9C%80%E8%A6%81%E9%87%8A%E6%94%BE%E5%90%97
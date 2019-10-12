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
-   RedisLockRegistry
> spring-integration-redis 提供， 原理，通过本地锁 + redis锁 双重锁机制。
> 

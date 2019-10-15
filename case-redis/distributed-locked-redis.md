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

-   RedLock思想
> 获取当前Unix时间，以毫秒为单位。    
> 依次尝试从5个实例，使用相同的key和具有唯一性的value（例如UUID）获取锁。当向Redis请求获取锁时，客户端应该设置一个网络连接和响应超时时间，这个超时时间应该小于锁的失效时间。例如你的锁自动失效时间为10秒，则超时时间应该在5-50毫秒之间。这样可以避免服务器端Redis已经挂掉的情况下，客户端还在死死地等待响应结果。如果服务器端没有在规定时间内响应，客户端应该尽快尝试去另外一个Redis实例请求获取锁。    
> 客户端使用当前时间减去开始获取锁时间（步骤1记录的时间）就得到获取锁使用的时间。当且仅当从大多数（N/2+1，这里是3个节点）的Redis节点都取到锁，并且使用的时间小于锁失效时间时，锁才算获取成功。  
> 如果取到了锁，key的真正有效时间等于有效时间减去获取锁所使用的时间（步骤3计算的结果）。 
> 如果因为某些原因，获取锁失败（没有在至少N/2+1个Redis实例取到锁或者取锁时间已经超过了有效时间），客户端应该在所有的Redis实例上进行解锁（即便某些Redis实例根本就没有加锁成功，防止某些节点获取到锁但是客户端没有得到响应而导致接下来的一段时间不能被重新获取锁）。
  
 -  Redisson
 注意点： 
 ```
 set命令要用set key value px milliseconds nx；
 set的value要具有唯一性；UUID+threadId 
 释放锁时要验证value值，不能误解锁；
 ```
 获取锁
 ```
 获取锁时向5个redis实例发送的命令
 首先分布式锁的KEY不能存在，如果确实不存在，那么执行hset命令（hset REDLOCK_KEY uuid+threadId 1），并通过pexpire设置失效时间（也是锁的租约时间）
 如果分布式锁的KEY已经存在，并且value也匹配，表示是当前线程持有的锁，那么重入次数加1，并且设置失效时间
 获取分布式锁的KEY的失效时间毫秒数, 并返回
 ```
释放锁
```
 向5个redis实例都执行如下命令
 如果分布式锁KEY不存在，那么向channel发布一条消息
 如果分布式锁存在，但是value不匹配，表示锁已经被占用，那么直接返回
 如果就是当前线程占有分布式锁，那么将重入次数减1
 重入次数减1后的值如果大于0，表示分布式锁有重入过，那么只设置失效时间，还不能删除
 重入次数减1后的值如果为0，表示分布式锁只获取过1次，那么删除这个KEY，并发布解锁消息
```  


## FAQ
1.  分布锁出现`ERR Error running script (call to f_9401052d872adfd0179ef8c8e8c028512707629a): @user_script:1: WR`问题
> 注意是编码问题， 对于字符使用StringCodec, 默认使用的是JackJsonCodec
在v3.1.4中redission的RedissionLock默认使用的是LongCodec


相应查考链接： https://github.com/redisson/redisson/issues/480
https://www.lovecto.cn/20180912/314.html




https://www.zhihu.com/search?type=content&q=Redisson%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%20%E9%9C%80%E8%A6%81%E9%87%8A%E6%94%BE%E5%90%97


>  https://zhuanlan.zhihu.com/p/59256821
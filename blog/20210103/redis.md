# 个人整理 - Java- 缓存篇
>link: https://ld246.com/article/1584005501825

先总结一下这些问题，想想自己会怎么回答，而不是照搬过来就行，有一定的使用场景

问题：
1.  ★Redis 的主从复制怎么做的？
2.  ★Redis 为什么读写速率快性能好？
3.  Redis 为什么是单线程的？
4.  缓存的优点？
5.  ★aof 与 rdb 的优点和区别？
6.  Redis 的 List 能用做什么场景？
7.  用 Java 自己实现一个 LRU。
8.  ★ 为什么用缓存，用过哪些缓存，Redis 和 memcache 的区别
9.  Redis 的持久化方式，以及项目中用的哪种，为什么
10. ★Redis 集群的理解，怎么动态增加或者删除一个节点，而保证数据不丢失。（一致性哈希问题）
11. ★Redis 的缓存失效策略
12. 缓存穿透的解决办法
13. ★Redis 集群，高可用，原理
14. MySQL 里有 2000w 数据，Redis 中只存 20w 的数据，如何保证 Redis 中的数据都是热点数据
15. 用 Redis 和任意语言实现一段恶意登录保护的代码，限制 1 小时内每用户 Id 最多只能登录 5 次
16. ★ 如何做到缓存数据一致性。
17. 如何防止缓存穿透、雪崩、击穿。
18. Redis 的 list 结构相关的操作。
19. ★redis2 和 redis3 的区别，redis3 内部通讯机制。
20. Redis 和 Memcached 的内存管理的区别。
21. ★Redis 的并发竞争问题如何解决，了解 Redis 事务的 CAS 操作吗。
22. Redis 的选举算法和流程是怎样的。
23. ★Redis 的集群怎么同步的数据的(Reids 的主从复制机制原理)。
24. Redis 优化详解
25. ★Redis 的线程模型是什么。
26. 集中式缓存和分布式缓存？
27. Elasticsearch 索引数据多了怎么办，如何调优，部署。
28. Elasticsearch 在部署时，对 Linux 的设置有哪些优化方法？
29. Elasticsearch 是如何实现 master 选举的。
30. 详细描述一下 Elasticsearch 搜索的过程。
31. Redis 和 memcache 的区别
32. Redis 过期淘汰策略
33. 如何将数据分布在 Redis 第几个库？
34. Elasticsearch 分片使用优化?
35. Elasticsearch 如何解决深度分页的问题？
36. lucence 倒排索引
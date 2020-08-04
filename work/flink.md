## 数仓开发

1、数据仓库研发；   
2、数据集市研发；   
3、用户画像研发；   
4、实时数仓开发；   
#### 任职要求    
1、有hive，kafka，spark，storm，hbase，flink等两种以上两年以上使用经验；          
2、熟悉数据仓库建设方法，对3NF范式，星型模型和雪花模型有一定理解；      
3、熟练使用SQL，对类SQL有过优化经验，对数据倾斜有深度的理解，了解特征工程常用方法；   
4、具备优秀的数学思维和建模思维；   
5、两年以上工作经验。 



##1. 模拟面试

#### 公司
快手、阿里、PDD、一点资讯、美菜、水滴公司、美团、易顺百顺、爱奇艺

#### 基本情况
alison 28  4year data engine  22k   15k

熟悉java , io, nio, java8 stream, java map, java snchronied and AQS,
threadlocal, thread pool, countdown, 信号量, 栅栏, JVM, JVM 的参数设置, jvm 的调优,
jenkins, 自动化部署， shell, python, 
spring , spring ioc, spring aop, spring transaction,
spring boot, spring mvc , spring cloud, feign, zuul, gateway， hyristic, zeekpin, 
nacos, zookeeper, dubbo, kafka, redis, hadoop, flink, 
elasticsearch, xxl-job

#### 问题

1. 简单介绍一下你，以及你所负责的项目
2. 那介绍一下数据仓库的搭建过程与遇到的问题
3. 数据量多少
4. java8的特性，流的使用
5. 简单说一下线程池，你对线程池所作的优化，
6. 简单说一下kafka，你对kafka所作用的优化，介绍一下kafka的架构图，如何保证顺序性，保存数据不丢失，
保证不重复消费， 什么原因引起的重复消费
7. 说一下spring cloud的， 网关的实现与作用，spring 中链路追踪是什么？ 服务熔断，服务宕机，服务的注册时间
介绍一下nacos的架构，为什么不使用spring cloud的eureka，而使用nacos， nacos使用过程中遇到什么问题？
8. spring cloud中服务与服务之间的通信是什么？ http协议，为什么不用dubbo, 服务调用实现方式是什么？
feign, feign的原理，使用feign中遇到什么问题， 负载均衡， 打印日志，设置连接时间，超时时间,
9. 使用了哪些spring注解，spring的自动化注册机制的实现方式与方法，区别
10. spring 本身的事件监听事件的实现原理， spring中的@Transactional的实现原理
11. 分布式中事务的实现方式与原理
12. 那你做DW中遇到什么问题，
13. 你们做实时数仓同步的组件是什么？maxwell， 为什么不选择阿里的cancel， maxwell的测试压力是多少？
5000/s, 基本上可以做到实时同步，性能优化点在哪儿，在kafka那块添加了10个消费线程。
14. 统计模型的计算是如何实现的，通过ignite进行内存统计的，
15. 统计模型的压力测试是多少，tps,
16. 讲讲为什么设计多租户，多租户的实现方式是什么？
多租户是根据综管中心配置的业务群，也就是租户，来动态的切换数据库， 数据库的实现原理使用了spring的DynimcdataSource机制，
在系统启动的时候，根据公共的配置文件，将多个数据源落到一个map中，同时设置一个默认数据源。在用户登录的时候，一般会带租户号，
利用切面，获取对应的租户号，根据租户号获取map中的数据源，由数据源连接信息动态切换对应的数据源，实现多租户的数据库切换。
第二点，在声明feign调用的时候，使用自定义的FeignCglibAop的实现类，进行自动代理对应的feign实现类，在feign调用之前，
动态设置租户号，然后生成对应的代理类，同时使用里ali的插件，主要利用threadlocal类来实现一个请求之间保持租户号的一致。
第三点，在初始化统计指标的时候，对原先缓存的key加入对应的租户号操作，实现不同的租户号的缓存信息隔离性。
统计指标的缓存同样是带租户号的，实现数据隔离，计算与获取的时候不会出现错位。
第四点，在服务同步的时候，通过feign之间的调用，cglibAop实现动态代理，会通知对应的服务进行缓存信息的同步，
还有统计模型的建表与实现方式。
17. flink的原理，如何实现大数据量的数据同步，判断对应的数据重复，数据丢失，监控数据的运行情况
18. http的三次握手与四次挥手，过程，参数，为什么需要三次，其他的需要四次，长连接是什么意思
19. http2与http的区别
20. spring mvc的流转，过程，遇到的问题
21. 字符串如何转换成对象，
22. hadoop的原理，架构，mapreduce的过程，数据倾斜的过程，如何解决
23. hbase的架构，如何实现
24. spring 的IOC 与 AOP
25. 你所做的埋点是如何实现的
26. BI是如何实现的
27. 你所做的用户画像是如何实现的，策略的作用是什么？ 实现原理是什么
28. mysql的锁，事务，mysql的sql 的执行过程， sql的 优化， sql 的慢查询



1. 数据结构
leetcode算法，选择，排序，二分法，



1. https://my.oschina.net/u/3434392/blog/1625493
2. https://www.jianshu.com/p/97ff129a9827
3. https://www.jianshu.com/u/a1d9579d6425
4. https://www.jianshu.com/p/325a8acc97ba
https://blog.csdn.net/weixin_30265171/article/details/99307487
https://www.iteye.com/blog/bencmai-392239




## issue: 
-	AspectJ有几种实现方式
-	AspectJ中关于顺序如何定义
-	用处在哪里？ 数据验证， 权限验证，事务添加，分布式跟踪， 拦截注解方法，对注解进行解释
-	微服务之间的熔断是如何实现，这块有几个点，如何手动的设置熔断，如何判断这个服务是否不可用，如何设置服务降级
-	微服务是如何设置网关的，解决了哪些问题， 权限，登录，
-	分布式事务的实现方式，有几种，MQ如何如何实现分布式事务，如何保证这种服务的一致性，或者是如何回退的
-	分布式事务中如何实现当某些个节点做完之后，才进行下面的工作
-	MQ如何加快处理速度， 如何解决重复问题，顺序问题
-	MQ的集群是如何搭建的


-	@EnableAsync与@Async的原理
-	动态参数定时导入数据
-	bruied使用队列导入
-	调用方式使用feign方式，不适用httpClient形式

## Done
异步调用的方式
1. @EnableAsync + @Async
2. 

@EnableAsync与@Async的原理，
底层开启一个异步线程，执行任务。
缺点，开启的多了，线程就多了，内存占用大。

下面这篇文章很好：
[spring的@Async](https://www.cnblogs.com/wihainan/p/6516858.html)


基于@Async的无返回值，也可以有返回值的


-	研究一下方法的执行时间，使用注解进行动态注入.





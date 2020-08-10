# 总结
静态代理

一个接口，两个类，一类是接口的实现，一个是代理对象，使用利用代理对象调用实现类的方法。
代理对象包含实现类的对象。


动态代理

jdk
cglib

jdk   
同样是一个接口，两个类，一个实现类，
一个继承 InvocationHandler接口的实现类，
使用，利用Proxy.newProxy()创建代理类，进行方法的调用



遇到的问题

1. 什么是CGLIB
2. 为什么使用CGLIB
3. 使用cglib失效的场景？ final/static
4. cglib的应用场景. 
5. Cglib的多重代理实现机制--》https://www.jianshu.com/p/9ba77d8f200b
6. cglib 源码中的特色，以及性能测试的结果
7. cglib如何不对本类进行增强
8. invoke与invokesuper的区别
9. 组件介绍MethodInterceptor、Dispatcher、ProxyRefDispatcher、LazyLoader
NoOp、FixedValue、InvocationHandler
10. spring aop中涉及的代理Cglib2AopProxy
11. cglib中对方法的过滤或者对不同的方法指定不同的逻辑



proxy.invokeSuper和proxy.invoke的区别。invokeSuper是退出当前interceptor的处理，
进入下一个callback处理，invoke则会继续回调该方法，如果传递给invoke的obj参数出错
容易造成递归调用。



cglib2AopProxy
```
看几个ProxyFactoryBean中跟cglib相关的参数： 来自于spring 1.x版本的文档描述

proxyTargetClass：这个属性为true时，目标类本身被代理而不是目标类的接口。如果这个属性值被设为true，CGLIB代理将被创建
optimize：用来控制通过CGLIB创建的代理是否使用激进的优化策略。除非完全了解AOP代理如何处理优化，否则不推荐用户使用这个设置。目前这个属性仅用于CGLIB代理；对于JDK动态代理（缺省代理）无效。
frozen：用来控制代理工厂被配置之后，是否还允许修改通知。缺省值为false（即在代理被配置之后，不允许修改代理的配置）。

里面针对haveAdvice和isFrozen做了些优化

1. 如果是finalize方法，直接返回NoOp，不做任何代理

2. 如果proxy对象是spring advised的子类，则通过cglib的Dispatcher，
直接委托给advisor对象进行方法调用

3. 如果是equals或者hashcode方法，委托给对应的EqualsInterceptor,
HashCodeInterceptor，不会走入spring的inteceptor chian。

4. 如果spring没有配置advise，则走了cglib的MethodInterceptor进行处理，
直接delegate到相关proxy.method上(例如：StaticUnadvisedExposedInterceptor,
DynamicUnadvisedExposedInterceptor会区分targetSource是否是单例，exposeProxy属性做相应的优化),也不会走入spring的inteceptor chian。

5. 如果spring配置了advise ， 而且是isFrozen，默认值为false，
这样会走到基于cglib MethodInterceptor的DynamicAdvisedInterceptor进行处理。

6. 如果spring配置了advise，还会区分是否是isFrozen=true(inteceptor chian不
会动态变化), 这样会根据事先分析好的method，比如有些method不是Advisor的pointcut
匹配目标，则会直接走Dispatcher进行处理，不做代理。

spring的advise的一系列inteceptor，都是每次请求动态调用
advised.getInterceptorsAndDynamicInterceptionAdvice获取，
然后涉及global inteceptor(.*匹配) , pointcut filter(方法匹配), 
inteceptor等处理

```

cglib
```
一般常用的也就是 BeanCopier, BulkBean, BeanMap, FastClass, 
Enhancer(MethodInterceptor)。

简单的概括几个常用的类:

BeanCopier：适用两个Pojo Bean之间，所有属性的全复制，两边的source和target的
属性可以不一致,以setter方法为准，调用getter方法获取数据。

BulkBean : 相比于BeanCopier，它可以指定特定的一组属性进行处理，然后可以调用getter，
setter方法进行属性拷贝。一般会应用在动态注入属性是通过xml配置时，特别有用，
拷贝特定的属性到指定的目标对象上。

BeanMap : 相比于BeanCopier和BulkBean，它可以用于解决pojo bean和map之间的转化，
比如将一个map的属性赋值给一个bean上，很方便把。

FastClass : 相比于java class对象，它利用了所谓的Index[]数组进行存储method,
constructor。实现method.invoke实际上是先找到FastClass存储的index下标，然后进行调用。使用上和Class没什么特殊的区别，就是多了这么一个index下标概念。

Enhancer: 一般知道个MethodInterceptor就差不多了，其他的callback可以不关注。
目前直接写Enhancer应该不太多，用spring aop会比较多。
```

>https://blog.csdn.net/danchu/article/details/70238002
>https://www.jianshu.com/p/9ba77d8f200b
>https://www.iteye.com/topic/801577
>https://www.iteye.com/topic/799827
>https://github.com/cglib/cglib



## 问题1

1. 什么是循环依赖？
2. 什么情况下循环依赖可以被处理？
3. Spring是如何解决的循环依赖？
    a. 简单的循环依赖， 没有AOP
    b. 结合AOP的循环依赖
4. 三级缓存真的提高了效率吗？
为什么需要三级缓存，不使用二级缓存

### 1. 什么是循环依赖？
从字面上来理解就是A依赖B的同时B也依赖了A， 相互依赖
或者，一个A自己依赖自己

### 2. 什么情况下循环依赖可以被处理

在回答这个问题之前首先要明确一点，Spring解决循环依赖是有前置条件的

-   出现循环依赖的Bean必须要是单例
-   依赖注入的方式不能全是构造器注入的方式（很多博客上说，只能解决setter方法的循环依赖，这是错误的）

在上面的例子中，A中注入B的方式是通过构造器，B中注入A的方式也是通过构造器，这个时候循环依赖是无法被解决，
如果你的项目中有两个这样相互依赖的Bean，在启动时就会报出以下错误：
`Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?`

为了测试循环依赖的解决情况跟注入方式的关系，我们做如下四种情况的测试

依赖情况	依赖注入方式	循环依赖是否被解决
AB相互依赖（循环依赖）	均采用setter方法注入	是
AB相互依赖（循环依赖）	均采用构造器注入	否
AB相互依赖（循环依赖）	A中注入B的方式为setter方法，B中注入A的方式为构造器	是
AB相互依赖（循环依赖）	B中注入A的方式为setter方法，A中注入B的方式为构造器	否

从上面的测试结果我们可以看到，不是只有在setter方法注入的情况下循环依赖才能被解决，即使存在构造器注入的场景下，循环依赖依然被可以被正常处理掉。

### Spring到底是怎么处理的循环依赖呢？

关于循环依赖的解决方式应该要分两种情况来讨论

-   简单的循环依赖（没有AOP）
-   结合了AOP的循环依赖

> 简单的循环依赖（没有AOP）
```
@Component
public class A {
    // A中注入了B
 @Autowired
 private B b;
}

@Component
public class B {
    // B中也注入了A
 @Autowired
 private A a;
}
```

通过上文我们已经知道了这种情况下的循环依赖是能够被解决的，那么具体的流程是什么呢？我们一步步分析

首先，我们要知道Spring在创建Bean的时候默认是按照自然排序来进行创建的，所以第一步Spring会去创建A。

与此同时，我们应该知道，Spring在创建Bean的过程中分为三步

实例化，对应方法：AbstractAutowireCapableBeanFactory中的createBeanInstance方法

属性注入，对应方法：AbstractAutowireCapableBeanFactory的populateBean方法

初始化，对应方法：AbstractAutowireCapableBeanFactory的initializeBean

这些方法在之前源码分析的文章中都做过详细的解读了，如果你之前没看过我的文章，那么你只需要知道

实例化，简单理解就是new了一个对象
属性注入，为实例化中new出来的对象填充属性
初始化，执行aware接口中的方法，初始化方法，完成AOP代理








>https://mp.weixin.qq.com/s/4Wrs4a9Ig_Q8xuuSHTADYA

# 目录
-   spring 如何解决循环依赖 
-   讲讲Spring加载流程 
-   spring利用了java的什么特性
-   annotationApplicationContext的作用

## 1. spring 如何解决循环依赖？
### 1.1 何为循环依赖？
![circulate](https://pic4.zhimg.com/v2-c64fc4eab3d14785a7d6a46a756cd1bf_b.jpg)
A、B、C 之间相互依赖
### 1.2. 造成的问题？
实例化 A 的时候发现 A 依赖于 B 于是去实例化 B（此时 A 创建未结束，处于创建中的状态），而发现 B 又依赖于 A ，于是就这样循环下去，最终导致 OOM
### 1.3. 循环依赖发生的时机
-   实例化
-   填充属性
-   初始化initializeBean

### 1.4. spring 如何解决的？
-   Spring 为了解决单例的循环依赖问题，使用了 三级缓存 ，递归调用时发现 Bean 还在创建中即为循环依赖
-   单例模式的 Bean 保存在如下的数据结构中：
``` 
/** 一级缓存：用于存放完全初始化好的 bean **/
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);

/** 二级缓存：存放原始的 bean 对象（尚未填充属性），用于解决循环依赖 */
private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

/** 三级级缓存：存放 bean 工厂对象，用于解决循环依赖 */
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>(16);

/**
bean 的获取过程：先从一级获取，失败再从二级、三级里面获取
创建中状态：是指对象已经 new 出来了但是所有的属性均为 null 等待被 init
*/
```
检测循环依赖的过程如下：
-   A 创建过程中需要 B，于是 A 将自己放到三级缓里面  ，去实例化 B
-   B 实例化的时候发现需要 A，于是 B 先查一级缓存，没有，再查二级缓存，还是没有，再查三级缓存，找到了！
-   然后把三级缓存里面的这个 A 放到二级缓存里面，并删除三级缓存里面的 A
-   B 顺利初始化完毕，将自己放到一级缓存里面（此时B里面的A依然是创建中状态）
-   然后回来接着创建 A，此时 B 已经创建结束，直接从一级缓存里面拿到 B ，然后完成创建，
-   并将自己A放到一级缓存里面，如此一来便解决了循环依赖的问题

### 1.5. 回顾一下如何解决的？
一句话：先让最底层对象完成初始化，通过三级缓存与二级缓存提前曝光创建中的 Bean，让其他 Bean 率先完成初始化。

### 1.6. 还有纰漏吗？
Spring 还是有一些无法解决的循环依赖，需要我们写代码的时候注意，
例如：使用构造器注入其他 Bean 的实例，这个就没办法了。要手动改代码


## 2. 讲讲Spring加载流程。
初始化环境—>加载配置文件—>实例化Bean—>调用Bean显示信息

首先从大的几个核心步骤来去说明，因为Spring中的具体加载过程和用到的类实在是太多了。

（1）、首先是先从AbstractBeanFactory中去调用doGetBean（name, requiredType, 
final Object[] args, boolean typeCheckOnly【这个是判断进行创建bean还是仅仅用来做类型检查】）方法，
然后第一步要做的就是先去对传入的参数name进行做转换，因为有可能传进来的name=“&XXX”之类，
需要去除&符号

（2）、然后接着是去调用getSingleton（）方法，其实在上一个面试题中已经提到了这个方法，
这个方法就是利用“三级缓存” 来去避免循环依赖问题的出现的。
【这里补充一下，只有在是单例的情况下才会去解决循环依赖问题】

（3）、对从缓存中拿到的bean其实是最原始的bean，还未长大，所以这里还需要调用
getObjectForBeanInstance（Object beanInstance, String name, String beanName, 
RootBeanDefinition mbd）方法去进行实例化。

（4）、然后会解决单例情况下尝试去解决循环依赖，如果isPrototypeCurrentlyInCreation（beanName）
返回为true的话，会继续下一步，否则throw new BeanCurrentlyInCreationException(beanName);

（5）、因为第三步中缓存中如果没有数据的话，就直接去parentBeanFactory中去获取bean，
然后判断containsBeanDefinition（beanName）中去检查已加载的XML文件中是否包含有这样的bean存在，
不存在的话递归到getBean（）获取，如果没有继续下一步

（6）、这一步是把存储在XML配置文件中的GernericBeanDifinition转换为RootBeanDifinition对象。
这里主要进行一个转换，如果父类的bean不为空的话，会一并合并父类的属性

（7）、这一步核心就是需要跟这个Bean有关的所有依赖的bean都要被加载进来，
通过刚刚的那个RootBeanDifinition对象去拿到所有的beanName,
然后通过registerDependentBean（dependsOnBean, beanName）注册bean的依赖

（8）、然后这一步就是会根据我们在定义bean的作用域的时候定义的作用域是什么，然后进行判断在
进行不同的策略进行创建（比如isSingleton、isPrototype）

（9）、这个是最后一步的类型装换，会去检查根据需要的类型是否符合bean的实际类型去做一个类型转换。
Spring中提供了许多的类型转换器


## spring利用了java的什么特性
Java 的字节码、反射、注解、包扫描等机制支撑了 Spring 全家桶能够实现 AOP 开发、依赖注入、声明式事务、模块自动化加载等核心特性

## AnnotationApplicationContext的作用
使用它可以实现基于Java的配置类的应用上下文， 避免使用application.xml进行配置

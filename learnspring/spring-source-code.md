## AnnotationConfigApplicationContext容器创建过程
-   AnnotationConfigApplicationContext继承GenericApplicationContext这个通用应用上下文，
GenericApplicationContext内部定义了一个DefaultListableBeanFactory实例，
GenericApplicationContext实现了BeanDefinitionRegistry接口，
所以可以通过AnnotationConfigApplicationContext实例注册bean defintion，
然后调用refresh()方法来初始化上下文。

-   AnnotationConfigApplicationContext继承AbstractApplicationContext，
AbstractApplicationContext提供了ApplicationContext的抽象实现。

几个步骤：
根据bean配置类，使用BeanDefinition解析Bean的定义信息，主要是一些注解信
### 分析AnnotationConfigApplicationContext的初始化过程：
1. this() 初始化bean读取器和扫描器
2. register(annotatedClasses) 实现注解bean的读取
```
register方法重点完成了bean配置类本身的解析和注册，处理过程可以分为以下息
Bean作用域的处理，默认缺少@Scope注解，解析成单例
借助AnnotationConfigUtils工具类解析通用注解
将bean定义信息已beanname，beandifine键值对的形式注册到ioc容器中
```
3. refresh()刷新上下文
refresh方法在AbstractApplicationContext容器中实现，refresh()方法的作用加载或者刷新当前的配置信息，
如果已经存在spring容器，则先销毁之前的容器，重新创建spring容器，载入bean定义，完成容器初始化工作，
所以可以看出AnnotationConfigApplicationContext容器是通过调用其父类AbstractApplicationContext
的refresh()函数启动整个IoC容器完成对Bean定义的载入。     


> 查考：https://www.cnblogs.com/ashleyboy/p/9662119.html

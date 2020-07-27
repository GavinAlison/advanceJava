# 1. spring  ioc  xml形式

## 关键词
IOC、beanFactory、 ClassPathxmlApplicationContext、beanDefinition、FactoryBean、循环依赖、
Resource、PropertySoruce

### enviroment
idea 2020, spring framework 5.3 , gradle 6.4
## 目录

-   



> bean的历险记  
 主人公是一个主类， 携带着xml文件，进入spring容器的大门，经历几道加工厂房，最后会到达成品库。
  下面讲述一下xml文件需要经过的几道spring加工厂房。
1. 根据applicatinContext构建父类applicationContext
2. 设置资源文件
3. refresh      
3.1 准备refresh       
    设置一些启动标识、开始时间       
    设置国际化的扩展点       
    构建环境变量类，检查必备的参数     
    清除一些earliApplicationListener集合中数据
4. 获取对应的beanFactory容器
   获取存在beanfactory，消费beanfactory中的单例缓存与自己
   创建beanfactory, DefaultListableBeanFactory
   设置DefaultListableBeanFactory的一些属性，比如allowBeanDefinitionOverriding,allowCircularRefrences
   根据xml加载一些BeanDefinition，到对应的BeanDefinitionMap中
   -    具体的是，委托给xmlBeanDefinitionReader去处理XmlBeanDefinitionReader加载对应的resource
   -    根据给的文件*.xml封装成对应的Resource资源，然后由xml
   -    然后加载出Document，然后根据xml的语法，检查对应的文件
   -    然后依据配置文件解析， 先解析profile属性，在解析标签, 一般有四个, import, alias, bean, beans
   -    将解析成BeanDefinitionHolder对象，然后将BeanDefinitionHolder注册到DefaultListableBeanFactory的BeanDefinitionMap中去
   -    同时注册到BeanDefinitionRegistry中的单例中，
5. Application.getBean("name")

    5.1 先判断状态， active, closed, 只有active中有值才可以       
    5.2 获取对应的beanFactory容器，然后从容器中加载对应的bean，         
    5.3 根据name到aliasMap中查找真正的beanName       
    

 首先spring容器根据资源加载器 找到对应的xml文件
  
  
  



 容器初始化
![spring-ioc](https://res.gameboys.cn/spring/3.png)
IOC依赖注入
![ioc](https://res.gameboys.cn/spring/4.png)

#TODO
-   为什么ClassPathXmlApplicationCotext需要设置parentContext, 
-   ClassPathXmlApplicationContext设置多个locations
-   initPropertySources()作用，如何实现国际化
-   getEnvironment().validateRequiredProperties();
获取环境变量，检查哪些必须的参数设置，可以利用这个来配置数据库的地址
-   earlyApplicationListener的作用
-   Assert工具类的使用
-   @Nullable
-   TheadLocal继承类，初始化内部类的数据量大小
-   ResourcePatternResolver的作用与属性介绍
-   获取指定classLoader
org.springframework.util.ClassUtils#getDefaultClassLoader()
org.springframework.util.ClassUtils#forName(String, ClassLoader)
-   Threadlocal的设计
-   DefaultSingletonBeanRegistry的属性与作用




>https://www.gameboys.cn/article/178
>https://www.gameboys.cn/article/171
>https://www.cnblogs.com/zedosu/p/6709921.html
>https://www.cnblogs.com/davidwang456/category/461542.html
>https://blog.csdn.net/u010013573/article/details/86547687
>https://blog.csdn.net/nuomizhende45/article/details/81158383
>https://mp.weixin.qq.com/s?__biz=MzU5MDgzOTYzMw==&mid=2247484564&idx=1&sn=84bd8fee210c0d00687c3094431482a7&scene=21#wechat_redirect
>https://mp.weixin.qq.com/s?__biz=MzU5MDgzOTYzMw==&mid=2247484561&idx=1&sn=a7281dae7aaaa3499d59dec730464e63&scene=21#wechat_redirect
>
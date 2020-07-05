# 1. spring  ioc  xml形式

## 关键词
IOC、beanFactory、 ClassPathxmlApplicationContext、beanDefinition、FactoryBean、循环依赖、
Resource、PropertySoruce

## 目录
-   



> bean的历险记  
> 主人公是一个主类， 携带着xml文件，进入spring容器的大门，经历几道加工厂房，最后会到达成品库。
> 下面讲述一下xml文件需要经过的几道spring加工厂房。
>1. 资源加载器
>2. 
> 首先spring容器根据资源加载器 找到对应的xml文件
> 
>
>



 容器初始化
![spring-ioc](https://res.gameboys.cn/spring/3.png)
IOC依赖注入
![ioc](https://res.gameboys.cn/spring/4.png)

#TODO
-   为什么ClassPathXmlApplicationCotext需要设置parentContext, 
-   ClassPathXmlApplicationContext设置多个locations
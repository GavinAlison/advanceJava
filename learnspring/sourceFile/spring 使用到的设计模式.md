#浅谈spring中用到的设计模式及应用场景


1、工厂模式，在各种BeanFactory以及ApplicationContext创建中都用到了

2、模版模式，在各种BeanFactory以及ApplicationContext实现中也都用到了

3、代理模式，Spring AOP 利用了 AspectJ AOP实现了动态代理

动态代理有两种

目标方法有接口时候自动选用 JDK 动态代理

目标方法没有接口时候选择 CGLib 动态代理

4、策略模式(strategy pattern)，加载资源文件的方式，使用了不同的方法，比如：ClassPathResourece，FileSystemResource，ServletContextResource，UrlResource但他们都有共同的借口Resource；在Aop的实现中，采用了两种不同的方式，JDK动态代理和CGLIB代理

5、单例模式(singleton)，比如在创建bean的时候。

>https://www.jb51.net/article/121475.htm
## Spring单例Bean与单例模式的区别
区别在于它们关联的环境不一样，单例模式是指在一个JVM进程中仅有一个实例，而Spring单例是指一个Spring Bean容器(ApplicationContext)中仅有一个实例。

现在枚举是单例模式的最佳实践,内置的Runtime

// 基于懒汉模式实现, 在一个JVM实例中始终只有一个实例
Runtime.getRuntime() == Runtime.getRuntime()

Spring的单例Bean是与其容器（ApplicationContext）密切相关的，所以在一个JVM进程中，如果有多个Spring容器，即使是单例bean，也一定会创建多个实例，代码示例如下：
//  第一个Spring Bean容器
```ApplicationContext context_1 = new FileSystemXmlApplicationContext("classpath:/ApplicationContext.xml");
Person yiifaa_1 = context_1.getBean("yiifaa", Person.class);
//  第二个Spring Bean容器
ApplicationContext context_2 = new FileSystemXmlApplicationContext("classpath:/ApplicationContext.xml");
Person yiifaa_2 = context_2.getBean("yiifaa", Person.class);
//  这里绝对不会相等，因为创建了多个实例System.out.println(yiifaa_1 == yiifaa_2);
```
## spring 有多少个容器
BeanFactory		-->BeanFactory是延迟加载。BeanFactory初始化完成后，并不加载任何Bean，只有在第一次获取Bean时，BeanFactory才会加载该Bean
ApplicationContext -->ApplicationContext会在启动后主动加载好单例Bean，这样，从ApplicationContext中获取Bean时效率更高。
ClassPathXmlApplicationContext -->用在在类路径加载xml文件的场景
FileSystemXmlApplicationContext -->用在在文件系统加载xml文件的场景
XMLWebApplicationContext	-->用在在Web中加载xml文件的场景
AnnotationApplicationContext -->用在在注解的时候加载bean


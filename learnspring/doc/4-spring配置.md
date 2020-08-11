## 配置Spring的方式

1.  基于xml配置
2.  基于java config注解配置


###   基于xml配置
-   只是使用xml配置

在xml文件中配置对应的bean

-   还支持注解方式, annotation-config

<context:annotation-config>
```
这是一条向Spring容器中注册

AutowiredAnnotationBeanPostProcessor

CommonAnnotationBeanPostProcessor

PersistenceAnnotationBeanPostProcessor

RequiredAnnotationBeanPostProcessor

这4个BeanPostProcessor.注册这4个BeanPostProcessor的作用，就是为了你的系统能够识别相应的注解。

那么那些注释依赖这些Bean呢。

如果想使用@Resource 、@PostConstruct、@PreDestroy等注解就必须声明CommonAnnotationBeanPostProcessor。 
如果想使用@PersistenceContext注解，就必须声明PersistenceAnnotationBeanPostProcessor的Bean。 
如果想使用@Autowired注解，那么就必须事先在 Spring 容器中声明 AutowiredAnnotationBeanPostProcessor Bean。 
如果想使用@Required的注解，就必须声明RequiredAnnotationBeanPostProcessor的Bean。

同样，传统的声明方式如下： 

<bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor "/> 

但是，就一般而言，这些注解我们是经常使用，比如Antowired,Resuource等注解，如果总是按照传统的方式一条一条的配置，感觉比较繁琐和机械。于是Spring给我们提供了<context:annotation-config/>的简化的配置方式，自动帮助你完成声明，并且还自动搜索@Component , @Controller , @Service , @Repository等标注的类。

<context:component-scan base-package="com.**.impl"/>

因此当使用 <context:component-scan/> 后，就可以将 <context:annotation-config/> 移除了。
支持类上面添加对应的注解，如@Component
```

```
注解	        说明
@Required	用于bean属性的setter方法
@Autowired	可以应用于bean属性setter方法、非setter方法、构造函数和属性
@Qualifier	@Qualifier注解和@Autowired一起可以通过指定连接哪个bean来消除混淆。
JSR-250 Annotations	Spring支持基于JSR-250的注解，包括@Resource、@PostConstruct和@PreDestroy注解。

```

### 基于java config的方式 
@Configuration & @Bean

使用@Configuration注解一个类表明该类可以被Spring IoC容器用作bean定义的来源。
该@Bean注解告诉Spring与@Bean注释的方法将返回应注册为Spring应用程序上下文的bean的对象。
最简单的@Configuration类如下：

```java
@Configuration
public class HelloWorldConfig {
   @Bean 
   public HelloWorld helloWorld(){
      return new HelloWorld();
   }
}

public class HelloWorldApp{
    public static void main(String[] args) {
       ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
       
       HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
       helloWorld.setMessage("Hello World!");
       helloWorld.getMessage();
    }
}
```

可以使用如下方式加载类
```
public static void main(String[] args) {
   AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

   ctx.register(AppConfig.class, OtherConfig.class);
   ctx.register(AdditionalConfig.class);
   ctx.refresh();

   MyService myService = ctx.getBean(MyService.class);
   myService.doStuff();
}
```
@Import
```
public class ConfigA {
}

@Configuration
@Import(ConfigA.class)
public class ConfigB {
   @Bean
   public B a() {
      return new A(); 
   }
}


public static void main(String[] args) {
   ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);
   
   // now both beans A and B will be available...
   A a = ctx.getBean(A.class);
   B b = ctx.getBean(B.class);
}
```
生命周期回调

@Bean注解支持指定任意初始化和销毁回调方法，就像Spring XML的bean元素的init-method和destroy-method属性一样
```
public class Foo {
   public void init() {
      // initialization logic
   }
   public void cleanup() {
      // destruction logic
   }
}
@Configuration
public class AppConfig {
   @Bean(initMethod = "init", destroyMethod = "cleanup" )
   public Foo foo() {
      return new Foo();
   }
}


          XML配置	                注解配置
创建对象	<bean id="" class="">	    @Controller @Service @Repository @Component
指定名称	通过id或者name值指定	        @Controller("指定的名称")
注入数据	<property name="" ref="">	@Autowired @Qualifier @Resource @Value
作用范围	<bean id="" class="" scope>	@Scope
生命周期	<bean id="" class="" init-method="" destroy-method=""/>	@PostConstruct @PreDestroy

```
指定Bean范围

```
@Configuration
public class AppConfig {
   @Bean
   @Scope("prototype")
   public Foo foo() {
      return new Foo();
   }
}
```


>https://www.jianshu.com/p/d646172014bd
>https://www.cnblogs.com/ideal-20/p/12357188.html





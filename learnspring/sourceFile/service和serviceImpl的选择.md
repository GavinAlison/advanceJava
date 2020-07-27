# service和serviceImpl的选择
>昨天看一个切面编程的例子，该描述是关于切面对应的注解，发现注解不起作用，今天思考其为何不起作用。

有几个问题：
-	service与serviceImpl的风格
-	spring 管理的是serviceImpl还是service， 为何注解@Service注解在实现类上，@Autowired注入的是Service类，不是实现类
-	@Service 实现原理, @Autowired实现原理


## service与serviceImpl的风格

有些同行公司的代码风格是service层=service接口+serviceImpl实现类；
而有的同行公司的代码风格是service层=service类；

《spring实战》书中描述， 
spring鼓励应用程序的各个层以接口的形式暴露功能，在service层，可以使用service接口+serviceImple实现类，
也可以使用service类，但考虑到“接口时实现松耦合的关键”，所以更加推荐使用。

一句话： 为了解耦。

为了应对底层变动。
本来是要的，因为 java 本身用代理功能需要 interface
但是后面 spring 用 cglib 做动态代理了，直接修改字节码，也就不需要强制这套写法了


## @Service 实现原理, @Autowired实现原理
@Service 在没有指定bean的id的情况下，自动根据类型进行定义对应的bean, bean的名字为类型首字母小写，
@Autowired只有在由Spring容器管理的bean才能注入成功， 需要对应的类也由Spring容器管理，才能注入成功。
只有被 Spring 管理起来的 bean 才能使用注入， @Autowired 默认 required 是 true，注入是 null 直接启动失败


## 在Web Application中获取容器对象
-	写的工具类为SpringUtil，实现ApplicationContextAware接口，并加入Component注解，让spring扫描到该bean



2019-11-22 22:00:00
>https://www.cnblogs.com/zqsky/p/6143319.html
https://www.cnblogs.com/youzhibing/p/11031216.html
https://www.cnblogs.com/youzhibing/p/10559330.html
https://blog.csdn.net/y418662591/article/details/86702734
https://blog.csdn.net/renyaoyao_1215/article/details/70650137


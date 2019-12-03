# 记录一下mybatis与spring boot的集成

> 以前学习 spring mvc ，orm 是hirenate， ibatis的时候，使用的是溜得飞起。然后接触mybatis ， 使用这个与spring 集成，也是快速地使用。然后学习spring boot + jpa的集成，我去，原来这个东西越来越简单。但是今天配置mybatis的时候，发现自己居然不会配置了，这个东西真是不经常使用，满满地都会遗忘。在这里记录一下spring boot + mybatis是如何配置的， 以便之后查询。

[SpringBoot整合Mybatis完整详细版](https://blog.csdn.net/iku5200/article/details/82856621)
主要查看其目录结构，配置文件的编写，注意几点
-  配置文件定义的mapper文件是*Mapper.xml ，而文中编写的确实Mapping.xml
-  该配置属于xml配置，不是注解annotation方式

还是官网--》[https://mybatis.org/mybatis-3/zh/getting-started.html](https://mybatis.org/mybatis-3/zh/getting-started.html)

入门一节， 从xml中构建sqlSessionFactory, 之前需要配置对应的driver、class、username、password。

获取到SqlsessionFactory后，可以根据它获取SqlSession, 
在根据SqlSession执行相应的语句， 执行的方式有两种， 一种使用全限定名，另一种获取对应Mapper接口，
`Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);` 与
`BlogMapper mapper = session.getMapper(BlogMapper.class);
Blog blog = mapper.selectBlog(101);`
还有一种表示映射关系， Java注解
```
public interface BlogMapper {
  @Select("SELECT * FROM blog WHERE id = #{id}")
  Blog selectBlog(int id);
}
```

## 作用域
SqlSessionFactoryBuilder

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了

SqlSessionFactory

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。

因此 SqlSessionFactory 的最佳作用域是应用作用域。
最简单的就是使用单例模式或者静态单例模式。

SqlSession

每个线程都应该有它自己的 SqlSession 实例。
SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。

映射器实例

映射器是一些由你创建的、绑定你映射的语句的接口。
可以是mapper接口，可以是映射语句。

把映射器放在方法作用域内。



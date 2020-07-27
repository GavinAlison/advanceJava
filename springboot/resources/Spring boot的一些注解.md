Spring boot的一些注解

1.使用@Condition多条件注册bean对象
2.@Import注解快速注入第三方bean对象
3.@EnableXXXX 开启原理
4.基于ImportBeanDefinitionRegistrar注册bean
5.基于FactoryBean注册bean对象


## 2.@Import [快速的给容器导入一个组件]
	1.@Import(要导入的组件class)
	2.ImportSelector：返回需要导入的组件的全类名数组
	3.ImportBeanDefinitionRegistrar: 手动注册bean到容器

@Import上面的使用方式属于静态的导入依赖

自动装配
	Spring利用依赖注入(DI),完成对IOC容器中各个组件的依赖关系赋值
	1).@Autowired，自动注入：
		1.默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class);
		2.如果找到多个相同类型的组件，再将属性方法的名称作为组件的id去容器中查找
	                        applicationContext.getBean("bookDao");
		3.@Qualifier("bookDao"):使用@Qualifier指定需要装配的组件id,而不是使用属性名
		4.自动装配默认一定要将属性赋值好，没有就会报错， 使用@Autoeired(required=false),没有默认值也不会报错
		5.@Primary, 让Spring进行自动装配的时候，默认使用首先的Bean
	2).Spring还支持使用@Resource(JSR250)和@Inject(JSR330) [java规范的注解]
	3).@Autowired :构造器，参数，方法，属性

这里注意， @Import可以导入第三方类，导入第三方配置类，导入XXXImportSelector类，
导入ImportBeanDefinitionRegistrar实现类，进行手动注册


## 3. @EnableXXXX 开启原理
enable字面意思启动，亦即开关的概念，有@EnableXXXX注解的地方，基本会看到@Import这个注解，一般他们都是结合起来使用的
`@Import(XXXImportSelector.class)`, XXXImportSelector implements ImportSelector , 重写selectImports方法，
selectImports会根据条件，选择不同的配置类。
selectImports返回的是String[]


## 4.基于ImportBeanDefinitionRegistrar注册bean
ImportBeanDefinitionRegistrar接口的registerBeanDefinitions的方法, 会将相应的类注入到容器中。

## 5. 基于FactoryBean注册bean对象
FactoryBean和 BeanFactory的区别，FactoryBean是创建bean对象，BeanFactory是获取bean对象。


## @Import与 继承第三方jar的区别
@Import 可以将第三方包中的bean注入到 当前容器中， 可以进行一些初始化工作。
第三方jar包，没有使用@Import，只能提供一些工厂类进行构建对象，然后调用对象的方法，或者使用一些工具类方法。

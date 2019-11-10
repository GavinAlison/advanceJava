# spring bean 生命周期
bean生命周期需要的哪些bean都有什么作用？

## BeanFactoryPostProcessor 

主要是在对象实例化前对beanDefinition中的元数据进行修改并且BeanDefinitionRegistryPostProcessor继承BeanFactoryPostProcessor ，它的实现类ConfigurationClassPostProcessor完成了对类的扫描。

Bean工厂后处理器在ApplicationContext中声明时会自动执行，以便将更改应用于定义容器的配置元数据。
Spring包含许多预定义的bean工厂后处理器，例如PropertyOverrideConfigurer和PropertySourcesPlaceholderConfigurer。 您还可以使用自定义BeanFactoryPostProcessor，例如注册自定义属性编辑器。


## BeanPostProcessor

主要是在对象实例化之后完成属性注入和AOP


## BeanNameAware 
让Bean对Name有知觉, 
作用：让Bean获取自己在BeanFactory配置中的名字（根据情况是id或者name）

在程序中使用BeanFactory.getBean(String beanName)之前，Bean的名字就已经设定好了。所以，程序中可以尽情的使用BeanName而不用担心它没有被初始化。

## BeanFactoryAware 
让Bean对工厂有知觉

作用：让Bean获取配置他们的BeanFactory的引用

实现：实现BeanFactoryAware接口，其中只有一个方法即setFactory(BeanFactory factory).
使用上与BeanNameAware接口无异，只不过BeanFactoryAware注入的是个工厂，BeanNameAware注入的是个Bean的名字。

## InitializingBean
InitializingBean接口为bean提供了属性初始化后的处理方法，它只包括afterPropertiesSet方法，凡是继承该接口的类，在bean的属性初始化后都会执行该方法。


>https://www.jianshu.com/p/05be6234faa1
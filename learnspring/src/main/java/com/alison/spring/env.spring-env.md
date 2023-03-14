AbstractApplicationContext

environment: 操作系统变量

systemEnvironment: 

systemProperties: JVM system变量


Test-->1. 现在开始初始化容器
MyBeanFactoryPostProcessor-->2. 初始化BeanFactoryPostProcessor构造器
MyBeanFactoryPostProcessor-->2.1. 调用BeanFactoryPostProcessor的postProcessBeanFactory方法
MyBeanPostProcessor-->2.2. 调用BeanPostProcessor构造器
MyInstantiationAwareBeanPostProcessor-->2.3. 调用InstantiationAwareBeanPostProcessorAdapter构造器
MyInstantiationAwareBeanPostProcessor-->3. 开始进行实例化bean
MyInstantiationAwareBeanPostProcessor-->3.1. 调用InstantiationAwareBeanPostProcessor的postProcessBeforeInstantiation方法
Person-->3.2. 调用Person的构造器
MyInstantiationAwareBeanPostProcessor-->3.3. 调用InstantiationAwareBeanPostProcessor的postProcessAfterInstantiation方法
MyInstantiationAwareBeanPostProcessor-->3.4. 调用InstantiationAwareBeanPostProcessosr的postProcessPropertyValues方法
Person-->【注入属性】注入属性address
Person-->【注入属性】注入属性name
Person-->【注入属性】注入属性phone
Person-->3.5. 调用BeanNameAware.setBeanName方法--->person
Person-->3.6. 调用BeanFactoryAware.setBeanFactory方法--->beanFactory: org.springframework.beans.factory.support.DefaultListableBeanFactory@26ba2a48: defining beans [beanPostProcessor,instantiationAwareBeanPostProcessor,beanFactoryPostProcessor,person]; root of factory hierarchy
MyBeanPostProcessor-->3.7. 调用BeanPostProcessor的postProcessBeforeInitialization方法
MyInstantiationAwareBeanPostProcessor-->3.8. 调用InstantiationAwareBeanPostProcessor调用postProcessBeforeInitialization方法
Person-->3.9. 调用InitializingBean的afterPropertiesSet方法
Person-->3.10. 调用<bean>的init-method属性指定的初始化方法
MyBeanPostProcessor-->3.11.  BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！
MyInstantiationAwareBeanPostProcessor-->3.12.  InstantiationAwareBeanPostProcessor调用postProcessAfterInitialization方法
Test-->容器初始化成功
Person-->Person [address=广州, name=张三, phone=182]
Test-->现在开始关闭容器！
Person-->调用DiposibleBean的destory方法
Person-->调用<bean>的destroy-method属性指定的初始化方法
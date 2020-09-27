#SpringBoot核心注解原理

- @SpringBootApplication
- @SpringBootConfiguration
- @EnableAutoConfiguration
- @ComponentScan
- @Filter
- @Import
- @AutoConfigurationPackage

### @SpringBootApplication
@SpringBootConfiguration  配置类，将自己当成配置类，注入到beanfactory中
@EnableAutoConfiguration 自动装配， 通过@import导入一个自动装配选择器类，读取装配工厂的配置文件，进行装配
@ComponentScan  组件扫描，类路径方式



@EnableAutoConfiguration
- @AutoConfigurationPackage
让包中的类以及子包中的类能够被自动扫描到spring容器中
- @Import(EnableAutoConfigurationImportSelector.class)
通过@import导入一个自动装配选择器类，读取装配工厂的配置文件，进行装配
这个类会帮你扫描那些类自动去添加到程序当中。我们可以看到getCandidateConfigurations()这个方法，
他的作用就是引入系统已经加载好的一些类，




>https://mp.weixin.qq.com/s/8kp6LJyJk1E5PKqtskHzjg
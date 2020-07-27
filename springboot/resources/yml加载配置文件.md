List<PropertySource<?>> thirdDatas = new YamlPropertySourceLoader().load("thirdData", new ClassPathResource("thirdData.yml"));

? 类型为 LinkedHashMap


spring boot 加载yml 配置文件的类： YamlPropertySourceLoader

在本地使用
1.	@PropertySource 和 @Value 组合使用
可以将自定义属性文件中的属性变量值注入到当前类的使用@Value注解的成员变量中。
2.	@PropertySource 和 @ConfigurationProperties 组合使用
可以将属性文件与一个Java类绑定，将属性文件中的变量值注入到该Java类的成员变量中。

@PropertySource
attribute:
-	name: 属性源的名称
-	value: 属性文件的存放路径
-	ignoreResourceNotFound: 如果指定的属性源不存在，是否要忽略这个错误
-	encoding:  属性源的编码格式
-	factory:  属性源工厂, PropertySourceFactory及其子类

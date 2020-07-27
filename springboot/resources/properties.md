## 配置文件
为了区分不同的配置，需要将不同环境信息写到不同的文件中， spring boot 的配置信息可以使用
spring.profile.active指定。

### 位置
-	当前目录/config子目录下
-	当前目录下
-	classpath根目录的/config包下
-	classpath的根目录下

### 使用方式
-	`@Profile("dev")`
-	properties或者yml中配置上`spring.profiles.active=dev`
### 运行方式
-	`java -jar ***.jar -spring.profiles.active=prod`
-	maven配置profiles标签
https://blog.csdn.net/jisu30miao1225/article/details/80745035

### 解析配置文件的原理
使用的类，流程， 动态refresh的机制


### yml，yaml文件加载工具类
List<PropertySource<?>> load(String name, Resource resource)
? 里面是LinkedHashMap类型


PropertySource
-	name
-	source





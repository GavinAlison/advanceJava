## 记录遇到的问题
Q：not found properties或者属性指定generatorConfig.xml         
A: 在pom.xml中<configurationFile>属性指定generatorConfig.xml

Q：Failed to execute goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate (default-cli) on project mybatis-person: Execution default-cli of goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate failed: Cannot resolve classpath entry: "C:\Users\hy\.m2\repository\mysql\mysql-connector-java\8.0.11\mysql-connector-java-8.0.11.jar" -> [Help 1]          
A: 注意： c:\中的\， 修改为\\

Q:XML Parser Error on line 76: 必须为元素类型 "table" 声明属性 "service"。 -> [Help 1]      
A: 对应的标签错误，修改过来

Q:Table configuration with catalog null, schema null, and table reguser did not resolve to any tables       
A: generator 会生成: Dao，Model，Mapping基础文件， 不会自动生成table表。




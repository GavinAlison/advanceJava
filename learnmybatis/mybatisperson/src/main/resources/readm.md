>简介： 摘自https://github.com/zhisheng17/mybatis/blob/master/Mybatis.md

###1、#{ } 和 ${ } 的区别
```
- #{}是预编译处理，${}是字符串替换。
- 使用#{}可以有效的防止SQL注入，提高系统安全性。
- ${}对传递进来的参数原样拼接在SQL中
- #{}解析传递进来的参数数据
```

###2、parameterType 和 resultType 区别 
- parameterType：指定输入参数类型，mybatis 通过 ognl 从输入对象中获取参数值拼接在 sql 中。
- resultType：指定输出结果类型，mybatis 将 sql 查询结果的一行记录数据映射为 resultType 指定类型的对象。

###3、selectOne 和 selectList 区别
- selectOne 查询一条记录来进行映射，如果使用selectOne查询多条记录则抛出异常：
org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to bereturned by selectOne(), but found: 3 at
- selectList 可以查询一条或多条记录来进行映射。

###4. 自增主键返回 与 非自增主键返回
-  mysql
-  自增主键通过LAST_INSERT_ID()获取刚插入记录的自增主键值，
-  非自增主键通过uuid()获取
-  Oracle 使用序列生成主键

####5 原始 Dao 开发方法
思路：
需要程序员编写 Dao 接口和 Dao 实现类；
需要在 Dao 实现类中注入 SqlsessionFactory ，在方法体内通过 SqlsessionFactory 创建 Sqlsession。
####6 Mybatis 的 mapper 接口
思路
程序员需要编写 mapper.xml 映射文件
只需要程序员编写Mapper接口（相当于Dao接口），需遵循一些开发规范，mybatis 可以自动生成 mapper 接口类代理对象。
```
开发规范：
在 mapper.xml 中 namespace 等于 mapper 接口地址
<mapper namespace="cn.zhisheng.mybatis.mapper.UserMapper"></mapper>
在 xxxmapper.java 接口中的方法名要与 xxxMapper.xml 中 statement 的 id 一致。
在 xxxmapper.java 接口中的输入参数类型要与 xxxMapper.xml 中 statement 的 parameterType 指定的参数类型一致。
在 xxxmapper.java 接口中的返回值类型要与 xxxMapper.xml 中 statement 的 resultType 指定的类型一致。
UserMapper.java
//根据id查询用户信息
    public User findUserById(int id) throws Exception;
UserMapper.xml
<select id="findUserById" parameterType="int" resultType="cn.zhisheng.mybatis.po.User">
        select * from user where id = #{1}
</select>
```

####7 代理对象内部调用 selectOne 或者 selectList
如果 mapper 方法返回单个 pojo 对象（非集合对象），代理对象内部通过 selectOne 查询数据库
如果 mapper 方法返回集合对象，代理对象内部通过 selectList 查询数据库
####8 mapper接口方法参数只能有一个是否影响系统开发
mapper 接口方法参数只能有一个，系统是否不利于维护？
系统框架中，dao层的代码是被业务层公用的。
即使 mapper 接口只有一个参数，可以使用包装类型的 pojo 满足不同的业务方法的需求。
注意：持久层方法的参数可以包装类型、map.... ，service方法中不建议使用包装类型。（不利于业务层的可扩展性）
####9 SqlMapConfig.xml 文件
Mybatis 的全局配置变量，配置内容和顺序如下：
```
properties（属性）
settings（全局配置参数）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境集合属性对象）
​	environment（环境子属性对象）
​	transactionManager（事务管理）
​	dataSource（数据源）
mappers（映射器）
```
####10 properties 属性
需求：将数据库连接参数单独配置在 db.properties 中，只需要在 SqlMapConfig.xml 中加载该配置文件 db.properties 的属性值。在 SqlMapConfig.xml 中就不需要直接对数据库的连接参数进行硬编码了。方便以后对参数进行统一的管理，其他的xml文件可以引用该 db.properties 。
```
db.properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_test?characterEncoding=utf-8
jdbc.username=root
jdbc.password=root
```
那么 SqlMapConfig.xml 中的配置变成如下：
```
<!--加载配置文件-->
    <properties resource="db.properties"></properties>
    <!-- 和spring整合后 environments配置将废除-->
    <environments default="development">
        <environment id="development">
            <!-- 使用jdbc事务管理,事务由 Mybatis 控制-->
            <transactionManager type="JDBC" />
            <!-- 数据库连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}" />
                <property name="url" value="${jdbc.url}" />
                <property name="username" value="${jdbc.username}" />
                <property name="password" value="${jdbc.password}" />
            </dataSource>
        </environment>
    </environments>
```
注意： MyBatis 将按照下面的顺序来加载属性：
    在 properties 元素体内定义的属性首先被读取。
    然后会读取 properties 元素中 resource 或 url 加载的属性，它会覆盖已读取的同名属性。
    最后读取 parameterType 传递的属性，它会覆盖已读取的同名属性。
 因此，通过parameterType传递的属性具有最高优先级，resource或 url 加载的属性次之，最低优先级的是 properties 元素体内定义的属性。
建议：
    不要在 properties 元素体内添加任何属性值，只将属性值定义在 db.properties 文件之中。
    在 db.properties 文件之中定义的属性名要有一定的特殊性。如 xxx.xxx.xxx
settings（全局配置参数）
Mybatis 框架在运行时可以调整一些运行参数
比如：开启二级缓存、开启延迟加载。。。
![全局配置参数](https://github.com/zhisheng17/mybatis/blob/master/pic/setting.jpg)

###11 typeAliases（类型别名）
需求：
```
在mapper.xml中，定义很多的statement，statement需要parameterType指定输入参数的类型、需要resultType指定输出结果的映射类型。
如果在指定类型时输入类型全路径，不方便进行开发，可以针对parameterType或resultType指定的类型定义一些别名，在mapper.xml中通过别名定义，方便开发。
```
**Mybatis支持的别名：**
```
别名	映射的类型
_byte	byte
_long	long
_short	short
_int	int
_integer int
_double	double
_float	float
_boolean boolean
string	String
byte	Byte
long	Long
short	Short
int	Integer
integer	Integer
double	Double
float	Float
boolean	Boolean
date	Date
decimal	BigDecimal
bigdecimal	BigDecimal
```
**自定义别名：**

在 SqlMapConfig.xml 中配置：(设置别名)
```
<typeAliases>
	<!-- 单个别名定义 -->
	<typeAlias alias="user" type="cn.zhisheng.mybatis.po.User"/>
	<!-- 批量别名定义，扫描整个包下的类，别名为类名（首字母大写或小写都可以） -->
	<package name="cn.zhisheng.mybatis.po"/>
	<package name="其它包"/>
</typeAliases>
```
在 UserMapper.xml 中引用别名：( resultType 为 user )
```
<select id="findUserById" parameterType="int" resultType="user">
        select * from user where id = #{id}
</select>
```
#### typeHandlers（类型处理器）
mybatis中通过typeHandlers完成jdbc类型和java类型的转换。
 通常情况下，mybatis提供的类型处理器满足日常需要，不需要自定义.
#### mybatis支持类型处理器：
```
类型处理器	Java类型	JDBC类型
BooleanTypeHandler	Boolean，boolean	任何兼容的布尔值
ByteTypeHandler	Byte，byte	任何兼容的数字或字节类型
ShortTypeHandler	Short，short	任何兼容的数字或短整型
IntegerTypeHandler	Integer，int	任何兼容的数字和整型
LongTypeHandler	Long，long	任何兼容的数字或长整型
FloatTypeHandler	Float，float	任何兼容的数字或单精度浮点型
DoubleTypeHandler	Double，double	任何兼容的数字或双精度浮点型
BigDecimalTypeHandler	BigDecimal	任何兼容的数字或十进制小数类型
StringTypeHandler	String	CHAR和VARCHAR类型
ClobTypeHandler	String	CLOB和LONGVARCHAR类型
NStringTypeHandler	String	NVARCHAR和NCHAR类型
NClobTypeHandler	String	NCLOB类型
ByteArrayTypeHandler	byte[]	任何兼容的字节流类型
BlobTypeHandler	byte[]	BLOB和LONGVARBINARY类型
DateTypeHandler	Date（java.util）	TIMESTAMP类型
DateOnlyTypeHandler	Date（java.util）	DATE类型
TimeOnlyTypeHandler	Date（java.util）	TIME类型
SqlTimestampTypeHandler	Timestamp（java.sql）	TIMESTAMP类型
SqlDateTypeHandler	Date（java.sql）	DATE类型
SqlTimeTypeHandler	Time（java.sql）	TIME类型
ObjectTypeHandler	任意	其他或未指定类型
EnumTypeHandler	Enumeration类型	VARCHAR-任何兼容的字符串类型，作为代码存储（而不是索引）。
```
mappers（映射器）
使用相对于类路径的资源，如：
使用完全限定路径 如：
使用 mapper 接口类路径
如：
注意：此种方法要求 mapper 接口名称和 mapper 映射文件名称相同，且放在同一个目录中。
注册指定包下的所有mapper接口 如： 注意：此种方法要求 mapper 接口名称和 mapper 映射文件名称相同，且放在同一个目录中。
Mapper.xml 映射文件
Mapper.xml映射文件中定义了操作数据库的sql，每个sql是一个statement，映射文件是mybatis的核心。
输入映射
通过 parameterType 指定输入参数的类型，类型可以是简单类型、hashmap、pojo的包装类型。
传递 pojo 包装对象 （重点）
开发中通过pojo传递查询条件 ，查询条件是综合的查询条件，不仅包括用户查询条件还包括其它的查询条件（比如将用户购买商品信息也作为查询条件），这时可以使用包装对象传递输入参数。
定义包装对象
定义包装对象将查询条件(pojo)以类组合的方式包装起来。


动态sql 
利用ognl表达式可以实现动态拼接sql
if, trim, foreach, 





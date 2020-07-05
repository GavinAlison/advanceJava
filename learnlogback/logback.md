## 1. logback架构

-   Logger, Appender 和 Layouts

### Logger

-   Logger 上下文
    -   命名层次结构
    -   等级继承
-   Appender and Layout
    -   一个 logger 可以有多个 appender
    -   additivity = false, appender不具有叠加性
    -   参数化日志, {}
### logback 配置

初始化步骤：
-   logback 会在类路径下寻找名为 logback-test.xml 的文件；
-   如果没有找到，logback 会继续寻找名为 logback.groovy 的文件；
-   如果没有找到，logback 会继续寻找名为 logback.xml 的文件；
-   如果没有找到，将会通过 JDK 提供的 ServiceLoader 工具在类路径下寻找文件 ；
META-INFO/services/ch.qos.logback.classic.spi.Configurator，
该文件的内容为实现了 Configurator 接口的实现类的全限定类名；
-   如果以上都没有成功，logback 会通过 BasicConfigurator 为自己进行配置，并且日志将会全部在控制台打印出来。

#### 默认的配置

假设配置文件 `logback-test.xml` 或者 `logback.xml` 不存在，logback 会调用 `BasicConfigurator` 
进行最小的配置。最小的配置包含一个附加到 root logger 上的 `ConsoleAppender`，格式化输出
使用 `PatternLayoutEncoder` 对模版 `%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n` 
进行格式化。root logger 默认的日志级别为 DEBUG。

#### Status 

输出内部状态
`StatusPrinter.print(LoggerContext lc)`








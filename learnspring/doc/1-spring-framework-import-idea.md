# spring framework 源码导入到idea过程

> 这篇文章记录下在idea导入spring framework 5.3.0-shapshot
>版本的源码。 主要参考文章，https://www.cnblogs.com/mazhichu/p/13163979.html.


## 流程
1.  下载配置gradle并配置环境变量
2.  下载spring源码
3.  参考`import-into-idea.md`文档
4.  修改maven的仓库地址
5.  构建
6.  导入
7.  验证
8.  总结




### 1. 下载配置gradle并配置环境变量
下载 gradle-6.4.1-bin.zip 文件
`curl -OL https://services.gradle.org/distributions/gradle-6.4.1-bin.zip`

设置环境变量GRADLE_HOME与path, 
配置gradle的仓库环境变量，GRADLE_USER_HOME
```
GRADLE_USER_HOME=D:\tool\gradle-6.4.1-bin
GRADLE_HOME=D:\tool\gradle-6.4.1-bin
path=%path%;%GRADLE_HOME%\bin
```
gradle的本地仓库依赖位置是D:\tool\gradle-6.4.1-bin\caches\modules-2\files-2.1



### 2. 下载spring framework的源码
`curl -OL https://github.com/spring-projects/spring-framework/archive/master.zip`


### 3-4 参考`import-into-idea.md`文档
在源码包中查看一下`import-into-idea.md`，
这里介绍了如何导入到idea中.

注意： 由于国外地址仓库访问速度慢，需要修改一下maven的仓库地址。
找到settings.gradle文件，在头部添加内容
```
pluginManagement {
	repositories {
		maven{ url 'https://maven.aliyun.com/repository/public'}
		maven { url 'https://maven.aliyun.com/repository/central' }
		maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
		maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter'}
		maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
		maven { url 'https://maven.aliyun.com/repository/jcenter' }
		maven { url 'https://repo.spring.io/plugins-release' }
		repositories { maven { url "http://repo.springsource.org/plugins-release" } }
		gradlePluginPortal()
		jcenter()
		mavenCentral()
	}
}
```
修改gradle.properties文件
```
version=5.3.0-SNAPSHOT
## 设置此参数主要是编译下载包会占用大量的内存，可能会内存溢出
org.gradle.jvmargs=-Xmx1024M
## 开启 Gradle 缓存
org.gradle.caching=true
## 开启并行编译
org.gradle.parallel=true
## 启用新的孵化模式
org.gradle.configureondemand=true
## 开启守护进程 通过开启守护进程，下一次构建的时候，将会连接这个守护进程进行构建，而不是重新fork一个gradle构建进程
org.gradle.daemon=true
file.encoding=UTF-8
#gradle.user.home=D:\\dev\\.gradle
```
修改build.gradle文件, 在头部添加
```
buildscript {
	repositories {
		maven{ url 'https://maven.aliyun.com/repository/public'}
		maven { url 'https://maven.aliyun.com/repository/central' }
		maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
		maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter'}
		maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
		maven { url 'https://maven.aliyun.com/repository/jcenter' }
		maven { url 'https://repo.spring.io/plugins-release' }
	}
}
```

### 5.  构建

预编译spring-oxm，根据import-into-idea.md要求，
我们需要先预编译spring-oxm和spring-core，使用命令./gradlew.bat :spring-oxm:compileTestJava.
预编译spring-core，使用命令./gradlew.bat :spring-core:compileTestJava


### 6. 导入
IDEA导入Spring源码
可以发现IDEA在导入spring源码会自动开始构建编译spring源码。

此时需要设置idea的gradle的位置
`idea-->file-->settings-->gradle`
这是
```
gradle home path = D:\tool\gradle-6.4.1-bin
use gradle frome = specified location   D:\tool\gradle-6.4.1-bin
gradle jvm= 1.8
```
构建编译spring源码之后，十几分钟可以了

### 7. 验证
在spring-framework-master项目下新建我们自己的测试module，选择使用gradle，输入自己的module名，
既然要验证，那我们肯定需要依赖spring的模块，我们在我们刚刚新建的模块下build.gradle文件中修改使用阿里云仓库，同时依赖spring-context模块。

```
plugins {
    id 'java'
}

group 'org.springframework'
version '5.3.0-SNAPSHOT'

tasks.withType(JavaCompile ) {
    options.encoding = "UTF-8"
}

repositories {
    maven{ url 'https://maven.aliyun.com/repository/public'}
    maven { url 'https://maven.aliyun.com/repository/central' }
    maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
    maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter'}
    mavenCentral()
}

dependencies {
    annotationProcessor 'org.projectlombok:lombok:1.18.12'
    compileOnly  'org.projectlombok:lombok:1.18.12'
    testAnnotationProcessor  'org.projectlombok:lombok:1.18.12'
    testCompileOnly  'org.projectlombok:lombok:1.18.12'
    testCompile group: 'junit', name: 'junit', version: '4.12'
    implementation(project(":spring-context"))

}

```
 
刷新依赖，重新编译，速度同样飞快            
具体见[使用IDEA+Gradle构建Spring5源码并调试](https://www.cnblogs.com/mazhichu/p/13163979.html)


### 8. 总结

构建spring framework 到idea主要问题在于添加setting.gradle中的maven仓库地址，以及配置maven的环境变量与
环境变量的maven仓库地址

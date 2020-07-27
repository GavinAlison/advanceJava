# idea 构建spring framework 5的源码经历

##  一直卡在Gradle:download
时间花费: 6h
结果: 失败
原因： 一直在下载，网络上说jar的仓库地址在国外，内地无法访问或者访问速度太慢
寻求解决方法： 
使用阿里云的国内镜像仓库地址
https://blog.csdn.net/CTO_1649900265/article/details/79543600

完成

下回构建项目
关闭自动构建
在项目中添加对应的镜像地址

## gradle
```
build.gradle

buildscript {
	repositories {
		maven{ url 'https://maven.aliyun.com/repository/public'}
	}
}
plugins...
...

allprojects {
	repositories {
		maven { url 'https://maven.aliyun.com/repository/public' }
	}
}

```


## 禁止gradle下载source.jar
失败，还是设置一下maven url 

设置本地仓库
GRADLE_USER_HOME

使用IDEA+Gradle构建Spring5源码并调试
https://www.cnblogs.com/mazhichu/p/13163979.html


# 关闭kotlin
删除了对应的额kotlin生成的文件夹
## gradle build 失败， 
查看是否存在多余的配置，比如repository
## IDEA Gradle工程控制台输出中文乱码
1. 设置IDEA的Gradle vm option
2. 在build.gralde添加
```
tasks.withType(JavaCompile) {  
    options.encoding = "UTF-8"  
} 
```

## idea 激活码激活



## gradle编译出错

Plugin [id: 'com.gradle.enterprise', version: '3.3.4'] was not found in any of the following sources:

- Gradle Core Plugins (plugin is not in 'org.gradle' namespace)
- Plugin Repositories (could not resolve plugin artifact 'com.gradle.enterprise:com.gradle.enterprise.gradle.plugin:3.3.4')
  Searched in the following repositories:
    Gradle Central Plugin Repository

## Gradle的缓存路径修改的四种方法
>https://blog.csdn.net/github_38616039/article/details/79933133



# idea启动报错: Failed to create JVM.JVM.Path XXXXXXX\jbr\

把C:\Users\你的用户名\AppData\Roaming\JetBrains\下的文件全部删除





# 解决“Caused by: org.gradle.api.plugins.UnknownPluginException: Plugin with id 'org.springframework.boot' not found.”

mavenLocal()

这个函数会优先查找本地本地的maven仓库，按照这个顺序查找依赖：USER_HOME/.m2/settings.xml >> M2_HOME/conf/settings.xml >> USER_HOME/.m2/repository

而我本地这个文件夹并没有springboot的最新版本依赖，所以会提示找不到sringboot




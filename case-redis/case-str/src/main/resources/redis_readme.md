>来源： https://juejin.im/post/5a912b3f5188257a5c608729#heading-20


## redis三种启动方式
### redis安装
``` 
$ wget http://download.redis.io/releases/redis-4.0.8.tar.gz
$ tar xzf redis-4.0.8.tar.gz
$ cd redis-4.0.8
$ make
```

### redis可执行文件说明

```

------------------------------------
+----命令名------+----命令说明----+
------------------------------------
+  redis-server  +  redis服务器 + 
------------------------------------
+  redis-cli + redis命令行客户端 +
------------------------------------
+  redis-benchmark + redis性能测试工具 + 
------------------------------------
+  redis-check-aof +  aof文件修复工具 + 
-------------------------------------
+  redis-check-dump + RDB文件检查工具 + 
-------------------------------------
+  redis-sentinel + sentinel服务器（2.8以后） + 
-------------------------------------


```
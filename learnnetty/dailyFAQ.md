1.  new ServerBootstrap().group(bossGroup, workerGroup)， 其中bossGroup与workerGroup的意思?

>查看源码可知，group(parentGroup, childGroup), 
原来bossGroup就是parentGroup，是负责处理TCP/IP连接的，
而workerGroup就是childGroup，是负责处理Channel（通道）的I/O事件。

2.  

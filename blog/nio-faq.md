# NIO
同步、非阻塞

## menu
1. Channel and Buffer
2. Scatter and Gather
3. Channel Transfers
4. Socket and ServerSocket
5. DatagramChannel
6. Selector
7. pipe
8. nio vs. io
9. nio.files
10. nio path
11. NIO AsynchronousFileChannel

##  说明文档
http://tutorials.jenkov.com/java-nio/channels.html

### nio channel 
-   FileChannel
-   DatagramChannel
-   SocketChannel
-   ServerSocketChannel

## nio系列
-   channel and buffer  
标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中
-   nio 
Java NIO可以让你非阻塞的使用IO，例如：当线程从通道读取数据到缓冲区时，线程还是可以进行其他事情。
当数据被写入到缓冲区时，线程可以继续处理它。从缓冲区写入通道也类似
-   Selectors       
Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。
因此，单个的线程可以监听多个数据通道
-   Channel Transfers
channel 之间的转换
-   Socket and ServerSocket
基于TCP的socket I/O 连接
-   nio vs. io      
http://wiki.jikexueyuan.com/project/java-nio/nio-io.html
对应的github: https://github.com/jjenkov/java-nio-server


##  FAQ
1.为什么需要selector?        
selector 是多路复用器， 普通的io, 通过socket -->I/O, 每次都需要一个线程；     
selector 的模式， socket-->channel --> selector ---> I/O, 一个selector需要一个线程，     
多个socket 有多个channel , 所以说单个线程可以监听多个数据通道     

2. 

## 参考链接
1.  [pipe](http://wiki.jikexueyuan.com/project/java-nio/pipe.html)
2.  [什么是NIO？NIO的原理是什么机制？](https://blog.csdn.net/qq_36520235/article/details/81318189)
3.  [Java NIO浅析](https://tech.meituan.com/2016/11/04/nio.html)
4.  [文章相当全面的Java NIO教程](https://cloud.tencent.com/developer/article/1354567)
5.  [java IO、NIO、AIO详解](https://www.cnblogs.com/sxkgeek/p/9488703.html)




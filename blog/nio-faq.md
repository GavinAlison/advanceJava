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

##  NIO-selector
开启多路复用器，一个线程管理多个 socket, 每个socket 通过channel 与selector连接，
底层开始selector监听机制，监听注册的事件，其实底层使用了poll/epoll机制(linux), windows使用poll0

## 直接缓存区-内存映射文件

利用内存映射文件，操作内存等于操作文件，省去了从磁盘文件拷贝数据到用户缓存区的过程。
这个缓存区的位置在堆外，不由JVM管理，Java可以直接操作。         
使用方向在于，追求性能的I/O操作 , zore-copy             
实现方式： byteBuffer.allocateDirect()和Channel.map(MapMode, pos, size)                  
注意：All or part of a mapped byte buffer may become
inaccessible at any time, for example if the mapped file is truncated.  An
attempt to access an inaccessible region of a mapped byte buffer will not
change the buffer's content and will cause an unspecified exception to be
thrown either at the time of the access or at some later time.  It is
therefore strongly recommended that appropriate precautions be taken to
avoid the manipulation of a mapped file by this program, or by a
concurrently running program, except to read or write the file's content.       
简单说，如果操作不可访问的区域，试图访问该区域，该区域不会变化，还可能抛出异常。

-   非直接缓存区
需要经过用户空间的缓存区，才可以操作文件。

-   两者区别方法
isDirect()


allocateDirect, 
transferTo, 
map()--->MappedByteBuffer

MappedByteBuffer 使用了内存映射文件进行文件的读写。
内存映射文件就是在内存中找一段空白内存，然后将这部分内存与文件的内容对应起来。
我们对内存的所有操作都会直接反应到文件中去。mmap的主要功能就是建立内存与文件这种对应关系。
所以才被命名为memory map。

一句话： 通过操作内存来操作文件，省了从磁盘文件拷贝数据到内核，再到用户缓冲区的步骤，所以十分高效。

![mmap](https://pic4.zhimg.com/v2-07966aa3187a38a9e7e0def1bf07836f_b.png)


    
## 用户空间和内核空间
系统会将内存一分为二，一部分是内核空间，一部分是用户空间

## I/O运行过程
数据会从网络或者文件中拷贝到操作系统内核的缓冲区中，然后才会从操作系统内核的缓冲区
拷贝到应用程序的地址空间，所以一般会经历两个阶段：
-   1.等待所有数据都准备好或者一直在等待数据，有数据的时候将数据拷贝到系统内核，  文件/网络--> 内核
-   2.内核--> 用户空间

## 网络IO的模型大致包括下面几种
-   同步模型（synchronous IO）
    -   阻塞IO（bloking IO）  
    -   非阻塞IO（non-blocking IO） NIO
    -   多路复用IO（multiplexing IO） 
    -   信号驱动式IO（signal-driven IO）
-   异步IO（asynchronous IO）
    -   异步IO
    
![IO图比较](https://pic2.zhimg.com/v2-fba5dcdad48d31bd560fd3634acaa551_b.jpg)

### 阻塞IO 
---> 等待1,2     
### 非阻塞IO 
---> 不需要等待1， 等待2， 不等待1的实现，不断轮询，查看系统是否准备好了         
### 多路复用IO 
---> linux , 调用select/poll/epoll/pselect实现           
在Linux下对文件的操作是利用文件描述符(file descriptor)来实现的。         
poll实现是，传入多个文件描述符，如果有一个文件描述符就绪，则返回，否则阻塞直到超时

```
int poll(struct pollfd *fds,nfds_t nfds, int timeout);
typedef struct pollfd {
        int fd;                         // 需要被检测或选择的文件描述符
        short events;                   // 对文件描述符fd上感兴趣的事件
        short revents;                  // 文件描述符fd上当前实际发生的事件 
} pollfd_t;
```
多个的进程的IO可以注册到一个复用器（select）上，然后用一个进程调用该select， 
select会监听所有注册进来的IO,其实监听感兴趣的事件的数据是否准备好。
当监听到对应的事件数据准备好了，返回用户空间准备好了标识。
然后select 调用进程 将内核缓存区数据拷贝到用户空间的缓存区，拷贝完成，通知用户进程，
然后用户进程就可以处理数据了

>如果select没有监听的IO在内核缓冲区都没有可读数据，select调用进程会被阻塞；
 而当任一IO在内核缓冲区中有可数据时，select调用就会返回；
 
1、典型应用：select、poll、epoll三种方案，nginx都可以选择使用这三个方案;Java NIO;     
2、特点：       
 专一进程解决多个进程IO的阻塞问题，性能好；Reactor模式;       
 实现、开发应用难度较大；      
 适用高并发服务应用开发：一个进程（线程）响应多个请求；  
 
3、select、poll、epoll             
Linux中IO复用的实现方式主要有select、poll和epoll;                    
Select：注册IO、阻塞扫描，监听的IO最大连接数不能多于FD_SIZE；         
Poll：原理和Select相似，没有数量限制，但IO数量大扫描线性性能下降；                         
Epoll ：事件驱动不阻塞，mmap实现内核与用户空间的消息传递，数量很大，Linux2.6后内核支持；

### 信号驱动IO模型
当进程发起一个IO操作，会向内核注册一个信号处理函数，然后进程返回不阻塞；
当内核数据就绪时会发送一个信号给进程，进程便在信号处理函数中调用IO读取数据。

1、特点：回调机制，实现、开发应用难度大；

### 异步IO模型
当进程发起一个IO操作，进程返回（不阻塞），但也不能返回果结；内核把整个IO处理完后，会通知进程结果。
如果IO操作成功则进程直接获取到数据。

1、典型应用：JAVA7 AIO、高性能服务器应用           
2、特点：                   
不阻塞，数据一步到位；Proactor模式；                  
需要操作系统的底层支持，LINUX 2.5 版本内核首现，2.6 版本产品的内核标准特性；             
实现、开发应用难度大；             
非常适合高性能高并发应用；               

![select](https://pic3.zhimg.com/v2-bae9d6368b741d17e09c791a664a43ba_b.jpg)
![阻塞IO模型](https://pic2.zhimg.com/v2-81c86f89a7a4ce7059587bb8ba9dd169_b.jpg)
![非阻塞IO模型](https://pic4.zhimg.com/v2-db6ed5d47439c29103a47a82569a3a17_b.jpg)
![IO复用模型](https://pic1.zhimg.com/v2-44d272775711821ad3b8240552f4e69c_b.jpg)
![信号驱动IO模型](https://pic4.zhimg.com/v2-993cb75dd22b8a7b3339617db32e3dbf_b.jpg)
![异步IO模型](https://pic1.zhimg.com/v2-a8a7a333ea88b334a0e6734e8235c2ec_b.jpg)

## zero-copy
### 一句话
零拷贝的“零”是指用户态和内核态间copy数据的次数为零。       
传统的数据copy（文件到文件、client到server等）涉及到四次用户态内核态切换、四次copy。
四次copy中，两次在用户态和内核态间copy需要CPU参与、两次在内核态与IO设备间copy为DMA方式不需要CPU参与。
零拷贝避免了用户态和内核态间的copy、减少了两次用户态内核态间的切换。

### 实现
java.nio.channel.FileChannel的transferTo()，transferFrom()






## 其他
-   [channel and buffer](https://github.com/GavinAlison/advanceJava/tree/master/JavaBase/src/main/java/com/alison/nio/case1_channelAndBuffer/buffer.md)
-   [Scatter/Gather](https://github.com/GavinAlison/advanceJava/tree/master/JavaBase/src/main/java/com/alison/nio/case2_scatterAndGather/scatterAndGather.md)
-   [channelTransfers](https://github.com/GavinAlison/advanceJava/tree/master/JavaBase/src/main/java/com/alison/nio/case3_channelTransfers)
-   [channelTransfers](https://github.com/GavinAlison/advanceJava/tree/master/JavaBase/src/main/java/com/alison/nio/case3_channelTransfers)


## 参考链接
1.  [pipe](http://wiki.jikexueyuan.com/project/java-nio/pipe.html)
2.  [什么是NIO？NIO的原理是什么机制？](https://blog.csdn.net/qq_36520235/article/details/81318189)
3.  [Java NIO浅析](https://tech.meituan.com/2016/11/04/nio.html)
4.  [文章相当全面的Java NIO教程](https://cloud.tencent.com/developer/article/1354567)
5.  [java IO、NIO、AIO详解](https://www.cnblogs.com/sxkgeek/p/9488703.html)
6.  [zero-copy](https://www.cnblogs.com/z-sm/p/6547709.html)



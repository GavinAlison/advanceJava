# netty
> 提供异步的、基于事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络IO程序。

## 为什么选择netty?
-   原生的io, 没有处理高并发的能力
-   原生的nio, 每次都需要写一堆代码
-   netty使用了线程池，可以处理大数据量，并节约内存
-   JDK的nio 有个Epoll bug, selector 没有连接时，会导致selector 空轮询， CPU 一直空转，等待连接
-   处理了nio没有处理的问题，比如出路的断开连接重新连接，半包读写，失败缓存等问题

## netty 模型
-   reactor
Reactor线程模型

一个NIO线程+一个accept线程：
![reactor](https://pic4.zhimg.com/v2-5fa6e169e61ff155e15522610bd99553_b.jpg)

Reactor主从模型

主从Reactor多线程：多个acceptor的NIO线程池用于接受客户端的连接
![主从](https://pic4.zhimg.com/v2-013373296ce16bb0cff3c9c5444629f3_b.jpg)

### Netty整体设计

**单线程模型**
服务器端用一个线程通过多路复用搞定所有的IO操作（包括连接，读、写等），编码简单，简单明了，
但是如果客户端连接数量较多，将无法支撑。
毕竟服务器中select同步处理

**线程池模型**
服务器端采用一个线程专门处理客户端连接请求，采用一个线程组负责IO操作。
在绝大数场景下，该模型都能满足使用。

**Netty模型**
Netty抽象出两组线程池，BossGroup专门负责接收客户端连接，WorkderGroup专门负责网络读写操作。
NioEventLoop表示一个不断循环执行处理任务的线程，每个NioEventLoop都有一个selector，
用于监听绑定在其上的socket网络通道。
NioEventLoop内部采用串行化设计，从消息的读取-》解码-》处理-》编码-》发送，始终由IO线程NioEventLoop负责。
一个NioEventLoopGroup下包含多个NioEventLoop    
每个NioEventLoop中包含有一个Selector，一个taskQueue    
每个NioChannel只会绑定在唯一的NioEventLoop上   
每个NioChannel都绑定有一个自己的ChannelPipeline    


### 异步模型
FUTURE、CALLBACK和HANDLER
Netty的异步模型是建立在future和callback的之上的。
Future的核心思想是 ：假设一个方法fun，计算过程可能非常耗时，等待fun返回显然不适合。
那么可以在调用fun的时候，立马返回一个Future，后续可以通过Future去监控方法fun的处理过程。

## 核心API
-   ChannelHandler及其实现类
>ChannelHandler接口定义了许多事件处理的方法，我们可以通过重写这些方法区实现具体的业务逻辑。API关系如下图所示：
```
自定义一个Handler类去继承ChannelInboundHandlerAdapter，然后通过重写相应方法实现业务逻辑，一般都需要重写以下方法：
public void channelActive(ChannelHandlerContext ctx)，通道就绪事件
public void channelRead(ChannelHandlerContext ctx,Object msg)，通道读取数据事件
public void channelReadComplete(ChannelHandlerContext ctx)，数据读取完毕事件
public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)，通道发生异常事件
```
-   Pipeline和ChannelPipline
>ChannelPipeline是一个Handler的集合，它负责处理和拦截inbound或者outbound的事件和操作，相当于一个贯穿Netty的链。
![Pipeline和ChannelPipline](https://pic1.zhimg.com/80/v2-41d740f046ddf84fd47d66c5aa0d7050_hd.jpg)

ChannelPipeline addFirst(ChannelHandler…handlers)，把一个业务处理类（handler）添加到链中的第一个位置
ChannelPipeline addLast(ChannelHandler…handlers)，把一个业务处理类（handler）添加到链中的最后一个位置

-   ChannelHandlerContext
这是事件处理器上下文对象，Pipeline链中的实际处理节点。每个处理节点ChannelHandlerContext中包含一个具体的事件处理器ChannelHandler，同时ChannelHandlerContext中也绑定了对应的pipeline和Channel的信息，方便对ChannelHandler进行调用。
常用方法如下所示 ：
ChannelFuture close()，关闭通道
ChannelOutboundInvoker flush()，刷新
ChannelFuture writeAndFlush(Object msg)，将数据写到ChannelPipeline中当前ChannelHandler的下一个ChanelHandler开始处理（出站）
-   ChannelOption
Netty在创建Channel实例后，一般都需要设置ChannelOption参数。ChannelOption是Socket的标准参数，而非Netty独创的。常用的参数配置有 ：
```
1.ChannelOption.SO_BACKLOG    
    
对应TCP/IP协议listen函数中的backlog参数，用来初始化服务器可连接队列大小。
服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。
多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，
backlog参数指定了队列d大小。

2. ChannelOption.SO_KEEPALIVE
一直保持连接活动状态。
```

-   ChannelFuture
>表示Channel中异步I/O操作的结果，在Netty中所有的I/O操作都是异步的，I/O的调用会直接返回，调用者并不能立刻获得结果，但是可以通过ChannelFuture来获取I/O操作的处理状态。常用方法如下所示 ：
Channel channel()，返回当前正在进行IO操作的通道
ChannelFuture sync()，等待异步操作执行完毕

-   EventLoopGroup和其实现类NioEventLoopGroup
>EventLoopGroup是一组EventLoop的抽象，Netty为了更好的利用多核CPU资源，一般会有多个EventLoop同时工作，每个EventLoop维护着一个Selector实例。
EventLoopGroup提供next接口，可以从组里面按照一定规则获取其中一个EventLoop来处理任务。在Netty服务器端编程中，我们一般都需要提供两个EventLoopGroup，例如：BossEventLoopGroup和WorkderEventLoopGroup。
通常一个服务端口即一个ServerSocketChannel对应一个Selector和一个EventLoop线程。BossEventLoop负责接收客户端的连接并将SocketChannel交给WorkerEventLoopGroup来进行IO处理，如下图所示 ：

![event](https://pic2.zhimg.com/80/v2-55ee71f5da034c2f80a9b3691cea88dd_hd.jpg)

BossEventLoopGroup通常是一个单线程的EventLoop，EventLoop维护着一个注册了ServerSocketChannel的Selector实例，BossEventLoop不断轮询Selector将连接事件分离出来，通常是OP_ACCEPT事件，
然后将接收到的SocketChannel交给WorkderEventLoopGroup，WorkderEventLoopGroup
会由next选择其中一个EventLoopGroup来将这个SocketChannel注册到其维护的Selector
并对其后续的IO事件进行处理。

## 什么是TCP 粘包/拆包/半包

**现象**      
client端重复写1000次数据给server端，server端会收到数据，
这个数据可能是正常的字符串，可能是多个字符串“粘”在了一起，
可能是一个字符串被“拆”开，形成一个破碎的包。

**原因**      
在操作系统底层，数据传输是通过字节流传输的。
在TCP网路层，数据通过封装成TCP 数据包传输。
尽管我们的应用层是按照 ByteBuf 为 单位来发送数据，server按照Bytebuf读取，但是到了底层操作系统仍然是按照字节流发送数据，因此，数据到了服务端，也是按照字节流的方式读入，然后到了 Netty 应用层面，重新拼装成 ByteBuf，而这里的 ByteBuf 与客户端按顺序发送的 ByteBuf 可能是不对等的。因此，我们需要在客户端根据自定义协议来组装我们应用层的数据包，然后在服务端根据我们的应用层的协议来组装数据包，这个过程通常在服务端称为拆包，而在客户端称为粘包。拆包和粘包是相对的，一端粘了包，另外一端就需要将粘过的包拆开，发送端将三个数据包粘成两个 TCP 数据包发送到接收端，接收端就需要根据应用协议将两个数据包重新组装成三个数据包。

**如何解决**
在没有 Netty 的情况下，用户如果自己需要拆包，基本原理就是不断从 TCP 缓冲区中读取数据，每次读取完都需要判断是否是一个完整的数据包 如果当前读取的数据不足以拼接成一个完整的业务数据包，那就保留该数据，继续从 TCP 缓冲区中读取，直到得到一个完整的数据包。 如果当前读到的数据加上已经读取的数据足够拼接成一个数据包，那就将已经读取的数据拼接上本次读取的数据，构成一个完整的业务数据包传递到业务逻辑，多余的数据仍然保留，以便和下次读到的数据尝试拼接。


在Netty中， 可以使用拆包器，它有许多类型的拆包器     
-   固定长度拆包器FixedLengthFrameDecoder
-   行拆包器LineBasedFrameDecoder
-   分隔符拆包器DelimiterBasedFrameDecoder
-   基于长度域拆包器LengthFieldBasedFrameDecoder

## Netty 的零拷贝

### 传统意义的拷贝
是在发送数据的时候，传统的实现方式是：
```
File.read(bytes)
Socket.send(bytes)
```
这种方式需要四次数据拷贝和四次上下文切换：
```
数据从磁盘读取到内核的read buffer
数据从内核缓冲区拷贝到用户缓冲区
数据从用户缓冲区拷贝到内核的socket buffer
数据从内核的socket buffer拷贝到网卡接口（硬件）的缓冲区
```
### 零拷贝的概念
明显上面的第二步和第三步是没有必要的，通过java的FileChannel.transferTo方法，可以避免上面两次多余的拷贝（当然这需要底层操作系统支持）
```
调用transferTo,数据从文件由DMA引擎拷贝到内核read buffer
接着DMA从内核read buffer将数据拷贝到网卡接口buffer上面的两次操作都不需要CPU参与，所以就达到了零拷贝。
```

### Netty中的零拷贝

主要体现在三个方面：

1、bytebuffer

Netty发送和接收消息主要使用bytebuffer，bytebuffer使用堆外内存（DirectMemory）直接进行Socket读写。
原因：如果使用传统的堆内存进行Socket读写，JVM会将堆内存buffer拷贝一份到直接内存中然后再写入socket，多了一次缓冲区的内存拷贝。
DirectMemory中可以直接通过DMA发送到网卡接口

2、Composite Buffers

传统的ByteBuffer，如果需要将两个ByteBuffer中的数据组合到一起，
我们需要首先创建一个size=size1+size2大小的新的数组，
然后将两个数组中的数据拷贝到新的数组中。
但是使用Netty提供的组合ByteBuf，就可以避免这样的操作，
因为CompositeByteBuf并没有真正将多个Buffer组合起来，
而是保存了它们的引用，从而避免了数据的拷贝，实现了零拷贝。

3、对于FileChannel.transferTo的使用

Netty中使用了FileChannel的transferTo方法，该方法依赖于操作系统实现零拷贝。

## 编码和解码
>在编写网络应用程序的时候需要注意codec（编解码器），因为数据在网络中传输的都是二进制字节吗数据，而我们拿到的目标数据往往不是字节吗数据。因此在发送数据时就需要编码，接收数据时需要解码。
 codec的组成部分有两个 ：decoder（解码器）和encoder（编码器）。encoder负责把业务数据转换成字节码数据，decoder负责把字节码数据转换成业务数据。
 
### 其实java的序列化技术就可以作为codec去使用，但是它的硬伤太多 
```
无法跨语言，这应该是Java序列化最致命的问题了。
序列化后体积太大，是二进制编码的5倍多。
序列化性能太低。
由于Java序列化硬伤太多，因此Netty自身提供了一些codec，如下所示 ：
Netty提供的解码器 ：
StringDecoder，对字符串数据解码
ObjectDecoder，对Java对象进行解码
Netty提供的解码器：
StringEncoder,对字符串数据进行编码
ObjectEncoder，对Java对象进行编码
Netty自带的ObjectDecoder和ObjectEncoder可以用来实现POJO对象或各种业务对象的编码和解码，但其内部使用的仍是Java序列化技术，所以不建议使用。
```

### Google的Protobuf
>Protobuf是Google发布的开源项目，全称Google Protocol Buffers，特定如下 ：
```
支持跨平台、多语言（支持目前绝大多数语言，例如C++、C#、Java、Python等）
高性能，高可靠性
使用protobuf编译器能自动生成代码，Protpbuf是将类的定义使用.protp文件进行描述，然后通过protoc.exe编译器根据.proto自动生成.java文件
```
目前在使用Netty开发时，经常会结合Protobuf作为codec（编解码器）去使用  

## 自定义RPC
>RPC（Remote Procedure Call），即远程过程调用，它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络实现的技术。常见的RPC框架有 ：Dubbo，Grpc。
```
服务消费房（client）以本地调用方式调用服务
client stub 接收到调用后负责将方法、参数等封装成能够进行传输的消息体
client stub 将消息进行编码并发送到服务端
server stub 收到消息后进行解码
server stub 根据解码结果调用本地的服务
本地服务执行并将结果返回给server stub
server stub将返回导入结果进行编码并发送至消费方
client stub接收到消息并进行解码
服务消费方（client）得到结果
```
RPC的目标就是将2-8这些步骤都封装起来，用户无需关系这些细节，可以像调用本地方法一样即可完成远程服务调用。







> link: https://www.zhihu.com/search?type=content&q=netty
> link: https://juejin.im/post/5c97ae12e51d45580b681b0b#heading-1
> link: https://zhuanlan.zhihu.com/p/78665014




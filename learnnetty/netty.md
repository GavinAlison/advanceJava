# netty
> 提供异步的、基于事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络IO程序。

## 为什么选择netty?
-   原生的io, 没有处理高并发的能力
-   原生的nio, 每次都需要写一堆代码
-   netty使用了线程池，可以处理大数据量，并节约内存
-   JDK的nio 有个bug, 没有连接时，CPU 一直空转，等待连接
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



>https://zhuanlan.zhihu.com/p/78665014




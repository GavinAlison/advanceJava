## bootstrap
Netty 应用程序通过设置 bootstrap（引导）类的开始，该类提供了一个 用于应用程序网络层配置的容器,
启动类

## channel
底层网络传输 API 必须提供给应用 I/O操作的接口，如读，写，连接，绑定等等。
提供I/O操作接口

## channelhandler
ChannelHandler 支持很多协议，并且提供用于数据处理的容器
一句话，业务逻辑经常存活于一个或者多个 ChannelInboundHandler。


## ChannelPipeline 
ChannelPipeline 提供了一个容器给 ChannelHandler 链并提供了一个API 用于管理沿着链入站和出站事件的流动.
每个 Channel 都有自己的ChannelPipeline，当 Channel 创建时自动创建的。
ChannelHandler 是如何安装在 ChannelPipeline ?
主要是实现了ChannelHandler 的抽象 ChannelInitializer。
ChannelInitializer子类 通过 ServerBootstrap 进行注册。
当它的方法 initChannel() 被调用时，这个对象将安装自定义的 ChannelHandler 集到 pipeline。
当这个操作完成时，ChannelInitializer 子类则 从 ChannelPipeline 自动删除自身。

## eventloop
EventLoop 用于处理 Channel 的 I/O 操作。一个单一的 EventLoop通常会处理多个 Channel 事件。
一个 EventLoopGroup 可以含有多于一个的 EventLoop 和 提供了一种迭代用于检索清单中的下一个。

## channelfuture
Netty 所有的 I/O 操作都是异步。因为一个操作可能无法立即返回，我们需要有一种方法在以后确定它的结果。出于这个目的，Netty 提供了接口 ChannelFuture,它的 addListener 方法注册了一个 ChannelFutureListener ，当操作完成时，可以被通知（不管成功与否）。






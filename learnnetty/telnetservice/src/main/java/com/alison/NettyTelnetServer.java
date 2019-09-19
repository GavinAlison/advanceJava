package com.alison;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author alison
 * @Date 2019/9/19  22:06
 * @Version 1.0
 * @Description 主程序
 * @link https://www.jianshu.com/p/5dcbc0456376
 */
public class NettyTelnetServer {
    // 指定端口号
    private static final int PORT = 8888;
    private ServerBootstrap serverBootstrap;
//    NioEventLoopGroup对应一个被封装好的NIO线程池，bossGroup负责收集客户端连接，workerGroup负责处理每个连接的IO读写。
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {
//        ServerBootstrap是Socket服务端启动类。
        serverBootstrap = new ServerBootstrap();
        // 指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)  // 指定是一个NIO连接通道
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        // 绑定对应的端口号,并启动开始监听端口上的连接
        Channel ch = serverBootstrap.bind(PORT).sync().channel();

        // 等待关闭,同步端口
        ch.closeFuture().sync();
    }
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

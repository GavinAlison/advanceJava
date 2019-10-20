package com.alison.conn;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author alison
 * @Date 2019/10/15  23:29
 * @Version 1.0
 * @Description
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建一个线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 2. 创建客户端的启动助手，完成配置
        Bootstrap bootstrap = new Bootstrap();
        // 3.设置线程组
        bootstrap.group(group)
                // 4. 设置客户端通道的实现类
                .channel(NioSocketChannel.class)
                // 5. 创建一个通道初始化对象
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 6. 往pipline链中添加自定义handler
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        // 7. 启动客户端去连接服务器 异步非阻塞，connect是异步的，它会立马返回一个future对象，sync是同步阻塞的用于等待主线程
        System.out.println("...Client is ready ...");
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();
        // 8. 关闭连接 异步非阻塞
        sync.channel().closeFuture().sync();
    }
}

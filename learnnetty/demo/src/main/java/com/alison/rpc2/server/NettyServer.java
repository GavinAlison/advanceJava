package com.alison.rpc2.server;

import com.alison.rpc2.common.JSONSerializer;
import com.alison.rpc2.protocol.RpcDecoder;
import com.alison.rpc2.protocol.RpcEncoder;
import com.alison.rpc2.protocol.RpcRequest;
import com.alison.rpc2.protocol.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import javax.imageio.spi.ServiceRegistry;

@Slf4j
public class NettyServer implements InitializingBean {
    private EventLoopGroup boss = null;
    private EventLoopGroup worker = null;
    @Autowired
    private ServerHandler serverHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        //此处使用了zookeeper做注册中心，本文不涉及，可忽略
//        ServiceRegistry registry = new ZkServiceRegistry("127.0.0.1:2181");
//        start(registry);
    }

    public void start(ServiceRegistry registry) throws Exception {
        //负责处理客户端连接的线程池
        boss = new NioEventLoopGroup();
        //负责处理读写操作的线程池
        worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加解码器
                        pipeline.addLast(new RpcEncoder(RpcResponse.class, new JSONSerializer()));
                        //添加编码器
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        //添加请求处理器
                        pipeline.addLast(serverHandler);

                    }
                });
        bind(serverBootstrap, 8888);
    }

    /**
     * 如果端口绑定失败，端口数+1,重新绑定
     *
     * @param serverBootstrap
     * @param port
     */
    public void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("端口[ {} ] 绑定成功", port);
            } else {
                log.error("端口[ {} ] 绑定失败", port);
                bind(serverBootstrap, port + 1);
            }
        });
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        boss.shutdownGracefully().sync();
        worker.shutdownGracefully().sync();
        log.info("关闭Netty");
    }
}

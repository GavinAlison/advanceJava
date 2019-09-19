package com.alison;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author alison
 * @Date 2019/9/19  22:08
 * @Version 1.0
 * @Description 初始化配置类
 */
public class NettyTelnetInitializer  extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // Add the text line codec combination first,
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 添加编码和解码的类
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        // 添加处理业务的类
        pipeline.addLast(new NettyTelnetHandler());

    }
}

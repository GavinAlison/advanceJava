package com.alison.conn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author alison
 * @Date 2019/10/15  23:29
 * @Version 1.0
 * @Description
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("服务器端发来的消息 ： " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client : " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板，我换工作，终于涨薪了", CharsetUtil.UTF_8));
    }
}

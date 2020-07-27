package com.alison.rpc2.protocol;

import com.alison.rpc2.common.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 约定好协议格式和序列化方式之后，我们还需要编解码器，
 * 编码器将请求对象转换为适合于传输的格式（一般来说是字节流），
 * 而对应的解码器是将网络字节流转换回应用程序的消息格式。
 * 编码器实现：
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clazz;

    private Serializer serializer;

    public RpcEncoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
        if (clazz != null && clazz.isInstance(msg)) {
            byte[] bytes = serializer.serialize(msg);
            //先传递byte的长度， 服务端会先接收数据
            byteBuf.writeInt(bytes.length);
            // 写入数据
            byteBuf.writeBytes(bytes);
        }
    }

}

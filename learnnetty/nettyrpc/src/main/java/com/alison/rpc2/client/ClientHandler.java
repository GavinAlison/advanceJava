package com.alison.rpc2.client;

import com.alison.rpc2.common.DefaultFuture;
import com.alison.rpc2.protocol.RpcRequest;
import com.alison.rpc2.protocol.RpcResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//  数据的处理重点在于ClientHandler类上，它继承了ChannelDuplexHandler类，可以对出站和入站的数据进行处理
public class ClientHandler extends ChannelDuplexHandler {
    /**
     * 使用Map维护请求对象ID与响应结果Future的映射关系
     * 目的是为了客户端用来验证服务端响应是否与请求相匹配，因为Netty的channel可能被多个线程使用，
     * 当结果返回时，你不知道是从哪个线程返回的，所以需要一个映射关系。
     */
    private final Map<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            //  获取响应对象
            RpcResponse response = (RpcResponse) msg;
            DefaultFuture defaultFuture = futureMap.get(response.getRequestId());
            //将结果写入DefaultFuture
            defaultFuture.setResponse(response);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) msg;
            //发送请求对象之前，先把请求ID保存下来，并构建一个与响应Future的映射关系
            futureMap.putIfAbsent(request.getRequestId(), new DefaultFuture());
        }
        super.write(ctx, msg, promise);
    }

    /**
     * 获取响应结果
     *
     * @param requsetId
     * @return
     */
    public RpcResponse getRpcResponse(String requsetId) {
        try {
            DefaultFuture future = futureMap.get(requsetId);
            return future.getRpcResponse(5000);
        } finally {
            //获取成功以后，从map中移除
            futureMap.remove(requsetId);
        }
    }
}

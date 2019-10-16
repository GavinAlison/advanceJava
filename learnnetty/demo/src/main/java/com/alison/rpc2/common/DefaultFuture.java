package com.alison.rpc2.common;

import com.alison.rpc2.protocol.RpcResponse;

// 存储响应对象的结果
public class DefaultFuture {

    private RpcResponse rpcResponse;

    private volatile boolean isSucceed = false;

    private final Object object = new Object();

    public RpcResponse getRpcResponse(int timeout) {
        synchronized (object) {
            while (!isSucceed) {
                try {
                    object.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        }
    }

    public void setResponse(RpcResponse response) {
        if (isSucceed) {
            return;
        }
        synchronized (object) {
            this.rpcResponse = response;
            this.isSucceed = true;
            object.notify();
        }
    }
}

package com.alison.rpc2.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcRequest {
    /**
     * 请求对象的ID 调用编号
     * 请求对象的ID是客户端用来验证服务器请求和响应是否匹配
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;

}

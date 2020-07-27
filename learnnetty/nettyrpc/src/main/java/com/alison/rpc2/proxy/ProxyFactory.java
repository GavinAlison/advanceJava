package com.alison.rpc2.proxy;

import java.lang.reflect.Proxy;

/**
 * @Author alison
 * @Date 2019/10/16  21:53
 * @Version 1.0
 * @Description
 */
public class ProxyFactory {
    public static <T> T create(Class<T> interfaceClass) throws Exception {
//        通过Proxy.newProxyInstance创建接口的代理类
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[] {interfaceClass},
                new RpcClientDynamicProxy<T>(interfaceClass));
    }
}

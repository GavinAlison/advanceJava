package com.alison.proxy.dynamicProxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//如何实现像AOP一样 调用自身无法增强
public class ProxyFactory implements MethodInterceptor {
    //如何实现像AOP一样 调用自身无法增强
    private Object target;
    //如何实现像AOP一样 调用自身无法增强
    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstaance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTime = System.currentTimeMillis();
        System.out.println("使用cglib代理，执行" + method.getName() + "方法,耗时" + (endTime - startTime) + "毫秒");
        return result;
    }
}

package com.alison.proxy.dynamicProxy.cglib2;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("interceptor before");
//        System.out.println(obj);// obj 是增强类对象，此时也会被拦截走代理， java.lang.StackOverflowError
        System.out.println(method.getName());
        // obj 是增强类对象,代理后的子类, method是target.f(), methodProxy 包含target.f()方法和代理对象的f()方法, args 是方法入参
//        Object object = method.invoke(obj, args); //Caused by: java.lang.reflect.InvocationTargetException
//        Object object = methodProxy.invoke(obj, args);// java.lang.StackOverflowError
        Object object = methodProxy.invokeSuper(obj, args); //ok 调用代理对象的方法，会走 被代理对象的方法
        System.out.println("interceptor after");
        return object;
    }
}

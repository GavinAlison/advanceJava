package com.alison.proxy.dynamicProxy.multicastcglib;

import net.sf.cglib.proxy.MethodProxy;

import java.util.concurrent.TimeUnit;

public class Point2 implements Chain.Point {

    @Override
    public Object proceed(Chain chain, MethodProxy methodProxy, Object proxy, Object[] args) throws Throwable {
        System.out.println("point 2 before");
        TimeUnit.MICROSECONDS.toSeconds(20);
        Object result = chain.proceed(methodProxy, proxy, args);
        TimeUnit.MICROSECONDS.toSeconds(20);
        System.out.println("point 2 after");
        return result;
    }
}
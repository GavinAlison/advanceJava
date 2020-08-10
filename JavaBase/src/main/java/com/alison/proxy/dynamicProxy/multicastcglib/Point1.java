package com.alison.proxy.dynamicProxy.multicastcglib;

import com.alison.object.ObjectUtil;
import net.sf.cglib.proxy.MethodProxy;

import java.util.concurrent.TimeUnit;

public class Point1 implements Chain.Point {

    @Override
    public Object proceed(Chain chain, MethodProxy methodProxy, Object proxy, Object[] args) throws Throwable {
        System.out.println("point 1 before");
        TimeUnit.MICROSECONDS.toSeconds(20);
        Object result = chain.proceed(methodProxy, proxy, args);
        System.out.println("chain result = " + result);
        TimeUnit.MICROSECONDS.toSeconds(20);
        System.out.println("point 1 after");
        return result;
    }
}



package com.alison.proxy.dynamicProxy.multicastcglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

public class ProxyFactory {
    private ProxyFactory() {
    }

    public static ProxyFactory create() {
        return new ProxyFactory();
    }

    public Object getProxy(Object origin) {
        final Enhancer en = new Enhancer();
        en.setSuperclass(origin.getClass());
        List<Chain.Point> list = new ArrayList<>();
        list.add(new Point1());
        list.add(new Point2());
        en.setCallback(new Interceptor(new Chain(list, origin)));
        return en.create();
    }

    private class Interceptor implements MethodInterceptor {
        Chain chain;

        public Interceptor(Chain chain) {
            this.chain = chain;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return chain.proceed(methodProxy, proxy, args);
        }
    }
}
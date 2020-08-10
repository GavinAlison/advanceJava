package com.alison.proxy.dynamicProxy.multicastcglib;

import com.alison.object.ObjectUtil;
import net.sf.cglib.proxy.MethodProxy;

import java.util.List;

public class Chain {
    private List<Point> list;
    private int index = -1;
    private Object target;

    public Chain(List<Point> list, Object target) {
        this.list = list;
        this.target = target;
    }

    public Object proceed(MethodProxy methodProxy, Object proxy, Object[] args) throws Throwable {
        Object result;
        if (++index == list.size()) {
            result = (target.toString());
            System.err.println("Target Method invoke result : " + result);
            index = -1;// 支持多个方法的拦截
        } else {
            Point point = list.get(index);
            result = methodProxy.invokeSuper(proxy, args);
            // 不断递归调用proceed方法
            point.proceed(this, methodProxy, proxy, args);
        }
        return result;
    }

    interface Point {
        Object proceed(Chain chain, MethodProxy methodProxy, Object object, Object[] args) throws Throwable;
    }
}
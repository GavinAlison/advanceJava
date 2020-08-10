package com.alison.proxy.dynamicProxy.cglib2;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibTest {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d:\\code");
        Enhancer enhancer = new Enhancer();
        //设置目标类
        enhancer.setSuperclass(Target.class);
        //设置拦截对象
        enhancer.setCallback(new Interceptor());
        Target proxy = (Target) enhancer.create();
//        Interceptor interceptor = new Interceptor();
//        Target proxy = (Target)interceptor.newInstance(Target.class);
        proxy.f("123");
//        proxy.g();

    }
}

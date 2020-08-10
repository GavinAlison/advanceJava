package com.alison.proxy.dynamicProxy.cglib2;

public class Target {
    public void f(String str) {
        System.out.println(this.toString());// this 指向的是cglib代理的子类, toString, hashCode都会被拦截，走代理
        System.out.println("target f()--" + str);
    }

    public void g() {
        System.out.println("target g()");
    }
}

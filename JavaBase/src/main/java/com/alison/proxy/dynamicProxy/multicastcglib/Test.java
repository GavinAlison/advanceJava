package com.alison.proxy.dynamicProxy.multicastcglib;

public class Test {
    public static void main(String[] args) {
        SayHello proxy = (SayHello) ProxyFactory.create().getProxy(new SayHello());
        proxy.toString();
        proxy.hello("world");
    }


    static class SayHello {

        public String hello(String input) {
            return "hello - " + input;
        }


        @Override
        public String toString() {
            return "hello cglib !";
        }
    }
}

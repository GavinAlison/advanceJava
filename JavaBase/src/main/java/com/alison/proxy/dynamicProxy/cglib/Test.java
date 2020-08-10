package com.alison.proxy.dynamicProxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

public class Test {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        UserDaoImpl dao = new UserDaoImpl();
        UserDaoImpl proxy = (UserDaoImpl) new ProxyFactory(dao).getProxyInstaance();
        System.out.println(proxy.save("666","15533445566"));
    }
}

package com.alison.proxy.dynamicProxy.cglib3;

import com.alison.object.ObjectUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * link: https://blog.csdn.net/danchu/article/details/70238002
 */
public class SampleClass {

    public String test(String input) {
        return "hello world";
    }

    public static String test02() {
        return "1";
    }

}

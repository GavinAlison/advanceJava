package com.alison.proxy.dynamicProxy;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

// 回调过滤器CallbackFilter
// 作用
// 在CGLib回调时可以设置对不同方法执行不同的回调逻辑，或者根本不执行回调。
// 在JDK动态代理中并没有类似的功能，对InvocationHandler接口方法的调用对代理类内的所以方法都有效。
public class cglibFilterTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CompositeClss.class);
        CallbackFilter filter = new ConcreteClassCallbackFilter();
        enhancer.setCallbackFilter(filter);
        Callback interceptor = new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object o = methodProxy.invokeSuper(obj, args);
                return o;
            }
        };//(1)
        Callback noOp = NoOp.INSTANCE;//(2)
        // 指定返回值， 注意代理的方法的返回值类型
        Callback fixedValue = new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                System.out.println("FixedValue loadObject ...");
                Object object = 999;
                return object;
            }
        };//(3)
        Callback[] callbacks = new Callback[]{interceptor, noOp, fixedValue};
        enhancer.setCallbacks(callbacks);
        CompositeClss proxyObject = (CompositeClss) enhancer.create();
        System.out.println("*** NoOp Callback ***");
        proxyObject.methodB(1);
        System.out.println("*** MethodInterceptor Callback ***");
        proxyObject.methodA("abcd");
        System.out.println("*** FixedValue Callback ***");
        int fixed1 = proxyObject.methodFixedValue(128);
        System.out.println("fixedValue1:" + fixed1);
        int fixed2 = proxyObject.methodFixedValue(256);
        System.out.println("fixedValue2:" + fixed2);
    }
}
class CompositeClss {

    public String methodA(String str) {
        System.out.println("method A ... " + str);
        return str;
    }

    public int methodB(int n) {
        System.out.println("method B ... " + n);
        return n + 10;
    }

    public int methodFixedValue(int n) {
        System.out.println("method fixed value..." + n);
        return n + 10;
    }
}

//其中return值为被代理类的各个方法在回调数组Callback[]中的位置索引（见下文）。
class ConcreteClassCallbackFilter implements CallbackFilter {
    public int accept(Method method) {
        if ("methodA".equals(method.getName())) {
            return 0;//Callback callbacks[0]
        } else if ("methodB".equals(method.getName())) {
            return 1;//Callback callbacks[1]
        } else if ("methodFixedValue".equals(method.getName())) {
            return 2;//Callback callbacks[2]
        }
        return 1;
    }
}

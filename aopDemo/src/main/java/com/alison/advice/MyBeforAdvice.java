package com.alison.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author alison
 * @Date 2020/6/23
 * @Version 1.0
 * @Description
 */
public class MyBeforAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("这是一个前置通知！");
        System.out.println("Method:"+method+", Object[]:"+objects+", Object:"+o);
    }
}

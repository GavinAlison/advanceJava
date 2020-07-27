package com.alison.threadlocal;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.core.NamedThreadLocal;

import java.util.Set;
import java.util.function.Supplier;

// 测试threadLocal的例子
public class ThreadLocalTest {
    ThreadLocal<Integer> integerThreadLocal;
    ThreadLocal<Set<Long>> setThreadLocal;

    @Test
    public void testCreateThreadLocal() {
        this.createThreadLocal();
        int i = integerThreadLocal.get();
        System.out.printf("i = " + i);
    }

    @Test
    public void testInitValue() {
        integerThreadLocal = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 2;
            }
        };
        System.out.println(integerThreadLocal.get());
    }

    @Test
    public void testNameThreadLocal() {
        ThreadLocal<Object> threadLocal = new NamedThreadLocal("nameThreadlocal") {
            @Override
            protected Object initialValue() {
                return Lists.newArrayList("alison", "tome");
            }
        };
        System.out.println(threadLocal.toString());
        System.out.println(threadLocal.get().toString());
    }

    public void createThreadLocal() {
        integerThreadLocal = new ThreadLocal<>();
        integerThreadLocal.set(1);
    }


    @Test
    public void testSuppliedThreadLocal() {
        ThreadLocal threadLocal = ThreadLocal.withInitial(()->1);
        System.out.println(threadLocal.get());
    }

}

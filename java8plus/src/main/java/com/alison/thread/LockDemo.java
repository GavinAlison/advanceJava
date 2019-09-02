package com.alison.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class LockDemo {
    volatile int count = 0;

    synchronized void increment() {
        count = count + 1;
    }

    void incrementSync() {
        synchronized (this) {
            count = count + 1;
        }
    }

    AtomicInteger num = new AtomicInteger(0);

    void incrementSyncNum() {
        synchronized (this) {
            num.incrementAndGet();
        }
    }

    private void testIncrement() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10000)
                .forEach(i -> {
                    executor.submit(this::incrementSync);
                });
//        executor.awaitTermination(2L, TimeUnit.SECONDS);
        executor.shutdown();
        System.out.println(executor.isTerminated());
        System.out.println(count);  // 4238
    }

    private void testIncrementNum() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10000)
                .forEach(i -> {
                    executor.submit(this::incrementSyncNum);
                });
//        executor.awaitTermination(1L, TimeUnit.SECONDS);
        executor.shutdown();
        System.out.println(executor.isTerminated());
        System.out.println(num.get());  // 4238
    }

    public static void main(String[] args) throws Exception {
        LockDemo lockDemo = new LockDemo();
        lockDemo.testIncrement();
        lockDemo.testIncrementNum();
    }
}

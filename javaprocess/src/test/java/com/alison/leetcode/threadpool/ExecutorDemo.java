//package com.alison.leetcode.threadpool;
//
//import org.junit.Test;
//
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ExecutorDemo {
//    @Test
//    public void test() throws Exception {
//        MyThreadFactory myThreadFactory = new MyThreadFactory();
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<Runnable>(), myThreadFactory);
//        executor.allowCoreThreadTimeOut(true);
//        for (int i = 0; i <= 20; i++) {
//            executor.submit(() -> {
//                try {
//                    Thread.sleep(100);
//                } catch (Exception e) {
//                }
//            });
//        }
//        System.out.println(myThreadFactory.getThreadGroup().activeCount());     // 7
//        Thread.sleep(20000);
//        System.out.println("After destroy, active thread count:" + myThreadFactory.getThreadGroup().activeCount());             // 6/1
//        executor.shutdown();
//    }
//}
//
//class MyThreadFactory implementsq ThreadFactory {
//    static final AtomicInteger poolNumber = new AtomicInteger(1);
//    final ThreadGroup group;
//    final AtomicInteger threadNumber = new AtomicInteger(1);
//    final String namePrefix;
//
//    MyThreadFactory() {
//        SecurityManager s = System.getSecurityManager();
//        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
//        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
//    }
//
//    public Thread newThread(Runnable r) {
//        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
//        if (t.isDaemon())
//            t.setDaemon(false);
//        if (t.getPriority() != Thread.NORM_PRIORITY)
//            t.setPriority(Thread.NORM_PRIORITY);
//        return t;
//    }
//
//    // 我所增加的方法，为了获取线程组
//    public ThreadGroup getThreadGroup() {
//        return this.group;
//    }
//}

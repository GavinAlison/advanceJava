package com.alison.thread;

import java.util.concurrent.*;

/**
 * @Classname ThreadDemo
 * @Date 2021/9/24 0:06
 * @Created by alison
 */
public class ThreadDemo {

    // 方式一： 继承Thread
    class Thread1 extends Thread {
        @Override
        public void run() {
            //dosomething()
            System.out.println("Thread1");
        }
    }

    // 方式二： 实现Runnable接口
    class Runner1 implements Runnable {
        @Override
        public void run() {
            //dosomething()
            System.out.println("Runner1");
        }
    }

    // 方式三： 创建Callable接口的实现类
    class Callable1 implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "1";
        }
    }

    // 方式四： 使用流创建
    public void createThreadForStream() {
        Runnable runnable = () -> {
            System.out.println("createThreadForStream");
        };
        new Thread(runnable).start();
    }

    // 方式五：继承 RecursiveAction, 无返回值
    class PrintTask extends RecursiveAction {

        @Override
        protected void compute() {
            System.out.println("RecursiveAction 子类");
        }
    }

    // 继承 RecursiveTask, 带返回值的
    class CalcuteTask extends RecursiveTask<Integer> {

        @Override
        protected Integer compute() {
            return 10;
        }
    }

    // 调用
    public void test() throws ExecutionException, InterruptedException {
        new Thread1().start();
        new Thread(new Runner1()).start();
        FutureTask<String> stringFutureTask = new FutureTask<>(new Callable1());
        stringFutureTask.run();
        String s = stringFutureTask.get();
        System.out.println(s);
        new Thread(stringFutureTask).start();
        // 线程池调用executors
        Executors.newSingleThreadExecutor().execute(new Thread1());
        Executors.newFixedThreadPool(8).execute(new Thread1());
        Executors.newWorkStealingPool(8).execute(new Thread1());
        Executors.newCachedThreadPool().execute(new Thread1());
        Executors.newSingleThreadScheduledExecutor().schedule(new Thread1(), 2, TimeUnit.SECONDS);
        Executors.newScheduledThreadPool(8).schedule(new Thread1(), 2, TimeUnit.SECONDS);
        // 并发包类 CompletableFuture
        CompletableFuture.runAsync(() -> {
            System.out.println("CompletableFuture");
        });
        // 使用ForkJoinPool池调用
        new ForkJoinPool().execute(new PrintTask());
        ForkJoinTask<Integer> submit = new ForkJoinPool().submit(new CalcuteTask());
        Integer res = submit.get();
        System.out.println(res);


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        threadPoolExecutor.execute(new Thread1());

        new ForkJoinPool().submit(new Thread1());

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.test();
        threadDemo.createThreadForStream();
    }

}



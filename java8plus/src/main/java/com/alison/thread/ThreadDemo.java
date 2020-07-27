package com.alison.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ThreadDemo {


    private void testThread() {
        Runnable task = () -> {
            System.out.println("hello " + Thread.currentThread().getName());
        };
        task.run();
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Done!");


        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread2 = new Thread(runnable);
        thread2.start();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("-----Hello " + threadName);
        });
        executor.shutdown();
// => Hello pool-1-thread-1
    }

    private void testCall() {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
    }

    private void testFuture() throws Exception {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);
        System.out.println("future donw? " + future.isDone());

//        Integer result = future.get();
        Integer result = future.get(1, TimeUnit.SECONDS);

        System.out.println("future done? " + future.isDone());
        System.out.println("result " + result);


        executor.shutdown();
    }

    private void testAllTask() throws Exception {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }

    Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    private void testAnyTask() throws Exception {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        String result = executor.invokeAny(callables);
        System.out.println(result);
    }

    private void testScheduleTask() throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("scheduleing: " + System.nanoTime());
        ScheduledFuture future = executor.schedule(task, 3, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(1337);
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("remaining delay : %sms", remainingDelay);

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        Runnable task2 = () -> System.out.println("Scheduling: " + System.nanoTime());
        int initialDelay = 0;
        int period = 1;
        executor2.scheduleAtFixedRate(task2, initialDelay, period, TimeUnit.SECONDS);

        ScheduledExecutorService executor3 = Executors.newScheduledThreadPool(1);
        Runnable task3 = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        executor3.scheduleWithFixedDelay(task3, 0, 1, TimeUnit.SECONDS);
    }


    private void testLock() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();
        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        });
        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });
        executor.shutdown();
    }

    public static void main(String[] args) throws Exception {
        ThreadDemo threadDemo = new ThreadDemo();
//        threadDemo.testFuture();
//        threadDemo.testAllTask();
        threadDemo.testAnyTask();
    }
}

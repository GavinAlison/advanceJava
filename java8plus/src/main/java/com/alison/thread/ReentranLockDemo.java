package com.alison.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentranLockDemo {
    ReentrantLock lock = new ReentrantLock();
    int count = 0;

    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void testInrement() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();
        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } catch (InterruptedException e) {
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
        executor.awaitTermination(1L, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        ReentranLockDemo reentranLockDemo = new ReentranLockDemo();
        reentranLockDemo.testInrement();
    }

}

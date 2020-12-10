package com.alison.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNumber1 extends Thread {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private int id;
    private static int cnt = 0;
    public PrintNumber1(int id) {
        this.id = id;
    }

    private static void print(int id) {

    }

    @Override
    public void run() {
        while (cnt <= 100) {
            lock.lock();
            System.out.println("thread_" + id + " num:" + cnt);
            cnt++;
            condition.signal();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread0 = new PrintNumber(0);
        Thread thread1 = new PrintNumber(1);
        thread0.start();
        thread1.start();
    }
}

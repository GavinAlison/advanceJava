package thread.consumeAndProduce;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * @Author Alison
 * @Date 2019/5/7
 **/

public class LockDemo {
    /**
     * java.util.concurrent.lock 中的 Lock 框架是锁定的一个抽象，通过对lock的lock()方法和unlock()方法实现了对锁的显示控制，
     * 而synchronize()则是对锁的隐性控制, 对对象的控制。
     * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响，简单来说，
     * 该锁维护这一个与获取锁相关的计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，函数调用结束计数器就减1，
     * 然后锁需要被释放两次才能获得真正释放。已经获取锁的线程进入其他需要相同锁的同步代码块不会被阻塞。
     */
    static class Demo1 {
        private static int count = 0;
        private static int capacity = 10;
        private Lock lock = new ReentrantLock();
        private Condition notFull = lock.newCondition();// 生产者条件
        private Condition notEmpty = lock.newCondition(); // 消费者条件
        class Prouducer implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    lock.lock();
                    try {
                        Thread.sleep(100);
                        while (count >= capacity) {
                            notFull.await();
                        }
                        count++;
                        System.out.println(Thread.currentThread().getName() + " produce ,  count=" + count);
                        notEmpty.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        class Consumer implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    lock.lock();
                    try {
                        Thread.sleep(100);
                        while (count <= 0) {
                            notEmpty.await();
                        }
                        count--;
                        System.out.println(Thread.currentThread().getName() + " consume ,  count=" + count);
                        notFull.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
        public void test() {
            new Thread(new Prouducer()).start();
            new Thread(new Consumer()).start();
            new Thread(new Prouducer()).start();
            new Thread(new Consumer()).start();
        }
    }

    public static void main(String[] args) {
        Demo1 d = new Demo1();
        d.test();
    }
}

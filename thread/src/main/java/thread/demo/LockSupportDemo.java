package thread.demo;

import java.util.concurrent.locks.LockSupport;

/***
 * @Author Alison
 * @Date 2019/5/5
 * @Desc: lockSupport 没有获取同步锁的操作， 只有在wait方法使用前需要获取同步锁
 * locksupport使用前， 如果有获取锁操作，那么会一致卡住， 亲测
 **/

public class LockSupportDemo {

    private static Thread mainThread;

    public static void main(String[] args) {
        LockSupportDemo l = new LockSupportDemo();
//        l.testThreadA();
        l.testLock();
    }

    static class ThreadA extends Thread {
        ThreadA(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " thread  wakeup others");
                notifyAll();
            }
        }
    }

    static class ThreadB extends Thread {
        ThreadB(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " thread  wakeup others");
            // 唤醒“主线程”
            LockSupport.unpark(mainThread);
        }
    }

    public void testThreadA() {
        ThreadA ta = new ThreadA("ta");
        synchronized (ta) {
            try {
                System.out.println(Thread.currentThread().getName() + " start tb thread ");
                ta.start();
                System.out.println(Thread.currentThread().getName() + " block");
                // 主线程等待, 关联对象ta
                ta.wait();
                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testLock() {
        ThreadB tb = new ThreadB("tb");
        try {
            // 获取主线程
            mainThread = Thread.currentThread();
            System.out.println(Thread.currentThread().getName() + " start tb thread ");
            tb.start();
            System.out.println(Thread.currentThread().getName() + " block");
            // 主线程等待, 关联对象tb
//                tb.wait();
            // 主线程阻塞
            LockSupport.park(mainThread);
            System.out.println(Thread.currentThread().getName() + " continue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

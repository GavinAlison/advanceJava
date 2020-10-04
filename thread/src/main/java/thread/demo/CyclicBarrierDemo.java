package thread.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/***
 * @Author Alison
 * @Date 2019/5/5
 *
 *
 * 至此我们难免会将CyclicBarrier与CountDownLatch进行一番比较。
 * 这两个类都可以实现一组线程在到达某个条件之前进行等待，它们内部都有一个计数器，当计数器的值不断的减为0的时候所有阻塞的线程将会被唤醒。
 * 有区别的是CyclicBarrier的计数器由自己控制，而CountDownLatch的计数器则由使用者来控制，
 * 在CyclicBarrier中线程调用await方法不仅会将自己阻塞还会将计数器减1，而在CountDownLatch中线程调用await方法只是将自己阻塞而不会减少计数器的值。
 *
 * 另外，CountDownLatch只能拦截一轮，而CyclicBarrier可以实现循环拦截。
 * 一般来说用CyclicBarrier可以实现CountDownLatch的功能，而反之则不能，
 * 例如上面的赛马程序就只能使用CyclicBarrier来实现。总之，这两个类的异同点大致如此，
 * 至于何时使用CyclicBarrier，何时使用CountDownLatch，还需要读者自己去拿捏。
 *
 * 除此之外，CyclicBarrier还提供了：resert()、getNumberWaiting()、isBroken()等比较有用的方法。
 **/

public class CyclicBarrierDemo {

    public static void main(String[] args) {
//        CyclicBarrier1.testCyclic();
        CyclicBarrierTest2.testCyclic();
    }

    static class CyclicBarrier1 {
        private static int SIZE = 5;
        private static CyclicBarrier cb;

        public static void testCyclic() {
            cb = new CyclicBarrier(SIZE);
            for (int i = 0; i < 5; i++) {
                new InnerThread().start();
            }
        }

        static class InnerThread extends Thread {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");
                    // 将cb的参与者数量加1
                    cb.await();
                    // cb的参与者数量等于5时，才继续往后执行
                    System.out.println(Thread.currentThread().getName() + " continued.");
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class CyclicBarrierTest2 {
        private static int SIZE = 5;
        private static CyclicBarrier cb;

        public static void testCyclic() {
            cb = new CyclicBarrier(SIZE, new Runnable() {
                public void run() {
                    System.out.println("CyclicBarrier's parties is: " + cb.getParties());
                }
            });

            // 新建5个任务
            for (int i = 0; i < SIZE; i++)
                new InnerThread().start();
        }

        static class InnerThread extends Thread {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");
                    // 将cb的参与者数量加1
                    cb.await();
                    // cb的参与者数量等于5时，才继续往后执行
                    System.out.println(Thread.currentThread().getName() + " continued.");
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

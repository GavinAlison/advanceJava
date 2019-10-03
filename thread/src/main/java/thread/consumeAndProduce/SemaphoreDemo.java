package thread.consumeAndProduce;

import java.util.concurrent.Semaphore;

/***
 * @Author Alison
 * @Date 2019/5/7
 **/
public class SemaphoreDemo {
    /**
     * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源，
     * 在操作系统中是一个非常重要的问题，可以用来解决哲学家就餐问题。
     * Java中的Semaphore维护了一个许可集，一开始先设定这个许可集的数量，
     * 可以使用acquire()方法获得一个许可，当许可不足时会被阻塞，release()添加一个许可。在下列代码中，还加入了另外一个mutex信号量，
     * 维护生产者消费者之间的同步关系，保证生产者和消费者之间的交替进行
     */
    private static int count = 0;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore notFull = new Semaphore(10);
    private Semaphore notEmpty = new Semaphore(0);
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    mutex.acquire();
                    notFull.acquire();
                    count++;
                    System.out.println(Thread.currentThread().getName() + " produce " + i + "--" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    mutex.acquire();
                    notEmpty.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName() + " consume " + i + "--" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
    public static void main(String[] args) {
        SemaphoreDemo s = new SemaphoreDemo();
        new Thread(s.new Producer()).start();
        new Thread(s.new Consumer()).start();
        new Thread(s.new Producer()).start();
        new Thread(s.new Consumer()).start();
    }
}

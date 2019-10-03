package thread.consumeAndProduce;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * @Author Alison
 * @Date 2019/5/7
 **/
public class BlockingQueueDemo {
    /**
     * BlockingQueue即阻塞队列，从阻塞这个词可以看出，在某些情况下对阻塞队列的访问可能会造成阻塞。被阻塞的情况主要有如下两种:
     * <p>
     * 当队列满了的时候进行入队列操作
     * 当队列空了的时候进行出队列操作
     * 因此，当一个线程对已经满了的阻塞队列进行入队操作时会阻塞，除非有另外一个线程进行了出队操作，
     * 当一个线程对一个空的阻塞队列进行出队操作时也会阻塞，除非有另外一个线程进行了入队操作。
     * 从上可知，阻塞队列是线程安全的。
     * 下面是BlockingQueue接口的一些方法:
     * 操作	抛异常	特定值	阻塞	超时
     * 插入	add(o)	offer(o)	put(o)	offer(o, timeout, timeunit)
     * 移除	remove(o)	poll(o)	take(o)	poll(timeout, timeunit)
     * 检查	element(o)	peek(o)
     * 这四类方法分别对应的是：
     * 1 . ThrowsException：如果操作不能马上进行，则抛出异常
     * 2 . SpecialValue：如果操作不能马上进行，将会返回一个特殊的值，一般是true或者false
     * 3 . Blocks:如果操作不能马上进行，操作会被阻塞
     * 4 . Timesout:如果操作不能马上进行，操作会被阻塞指定的时间，如果指定时间没执行，则返回一个特殊值，一般是true或者false
     * 下面来看由阻塞队列实现的生产者消费者模型,这里我们使用take()和put()方法，这里生产者和生产者，消费者和消费者之间不存在同步，所以会出现连续生成和连续消费的现象
     */
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);
    private static final BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(10);
    private static int count = 0;
    private static class Consumer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    queue1.take();
                    count--;
                    System.out.println(Thread.currentThread().getName() + " consume " + count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static class Producer extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(100);
                    queue1.put(count++);
                    System.out.println(Thread.currentThread().getName() + " Produce " + count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }
}

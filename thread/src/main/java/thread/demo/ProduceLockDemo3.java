package thread.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * @Author Alison
 * @Date 2019/4/29
 * @See ../jucLock02ReentrantLock.md
 * @See ../ProduceLockDemo
 * @See ../ProduceLockDemo2
 **/

public class ProduceLockDemo3 {
    static class Depot {
        private int capacity;
        private int size;
        private Lock lock;
        private Condition fullCondition;
        private Condition emptyCondition;

        public Depot(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.lock = new ReentrantLock();
            this.fullCondition = lock.newCondition(); // 生产条件
            this.emptyCondition = lock.newCondition();  // 消费条件
        }

        public void produce(int val) {
            lock.lock();
            try {
                // left 表示“想要生产的数量”(有可能生产量太多，需在生产)
                int left = val;
                while (left > 0) {
                    while (size >= capacity) {
                        // 库存已满， 等待消费者消费
                        fullCondition.await();
                    }
                    int inc = (size + left) > capacity ? capacity - size : left;
                    size += inc;
                    left -= inc;
                    System.out.printf("%s produce{%3d} --> left=%3d, inc=%3d, size=%3d\n", Thread.currentThread().getName(), val, left, inc, size);
                    // 通知消费者消费
                    emptyCondition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void consume(int val) {
            lock.lock();
            try {
                int left = val;
                while (left > 0) {
                    // 当第一次没消费完时，还需消费，所以用while
                    while (size <= 0) {
                        emptyCondition.await();
                    }
                    int dec = (size < val) ? size : val;
                    size -= dec;
                    left -= dec;
                    System.out.printf("%s consume{%3d}---> left=%3d, dec=%3d, size=%d\n", Thread.currentThread().getName(), val, left, dec, size);
                    fullCondition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        public String toString() {
            return "capacity: " + capacity + ", actual size： " + size;
        }
    }


    static class Producer {
        private Depot depot;

        Producer(Depot depot) {
            this.depot = depot;
        }

        public void producer(final int val) {
            new Thread() {
                @Override
                public void run() {
                    depot.produce(val);
                }
            }.start();
        }
    }

    static class Consumer {
        private Depot depot;

        Consumer(Depot depot) {
            this.depot = depot;
        }

        public void consume(final int val) {
            new Thread() {
                @Override
                public void run() {
                    depot.consume(val);
                }
            }.start();
        }

    }


    public static void main(String[] args) {
        Depot depot = new Depot(100);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);
        producer.producer(60);
        producer.producer(120);
        consumer.consume(90);
        consumer.consume(150);
        producer.producer(130);
    }
}

package thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * @Author Alison
 * @Date 2019/4/29
 * @See    ../jucLock02ReentrantLock.md
 * @See    ../ProduceLockDemo
 * @See    ../ProduceLockDemo3
 **/

public class ProduceLockDemo2 {
    static class Depot {
        private int size;
        private Lock lock;

        public Depot() {
            size = 0;
            lock = new ReentrantLock();
        }

        public void produce(int val) {
//            lock.lock();
//            try {
            size += val;
            System.out.printf("%s produce{%d}---> size=%d\n", Thread.currentThread().getName(), val, size);
//            } finally {
//                lock.unlock();
//            }
        }

        public void consume(int val) {
//            lock.lock();
//            try {
            size -= val;
            System.out.printf("%s consume{%d}--->size=%d\n", Thread.currentThread().getName(), val, size);
//            } finally {
//                lock.unlock();
//            }
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
        Depot depot = new Depot();
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);
        producer.producer(60);
        producer.producer(120);
        consumer.consume(90);
        consumer.consume(150);
        producer.producer(130);
//        Thread-0 produce{60}---> size=60
//        Thread-4 produce{130}---> size=70
//        Thread-3 consume{150}--->size=-60
//        Thread-2 consume{90}--->size=90
//        Thread-1 produce{120}---> size=180
    }
}

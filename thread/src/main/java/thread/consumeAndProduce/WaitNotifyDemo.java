package thread.consumeAndProduce;

/***
 * @Author Alison
 * @Date 2019/5/7
 **/
public class WaitNotifyDemo {
    /**
     * 这是最简单的实现，使用wait()与notifyAll()
     * 缓冲区满和为空时都调用wait()方法等待，
     * 当生产者生产了一个产品或者消费者消费了一个产品之后会唤醒所有线程。
     * 注意： 其中有几个问题，一是，count可能为负数，count可能超过10
     * 下面Demo2 是对其修改
     */
    public static class Demo1 {
        private static volatile int count = 0;
        private static final int FULL = 10;
        Object obj = new Object();
        class Producer implements Runnable {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        synchronized (obj) {
                            Thread.sleep(100);
                            while (count == FULL) {
                                obj.wait();
                            }
                            count++;
                            System.out.println(Thread.currentThread().getName() + " produce, 共有" + count);
                            obj.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * 使用wait()与notifyAll()实现，含有一个容器，该容器内有大小，解决上面Demo1出现负数的情况
         */
        class Consumer implements Runnable {
            @Override
            public void run() {
                try {
                    synchronized (obj) {
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(100);
                            if (count == 0) {
                                obj.wait();
                            }
                            count--;
                            System.out.println(Thread.currentThread().getName() + " consume, 共有 " + count);
                            obj.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void test() {
            new Thread(new Producer()).start();
            new Thread(new Consumer()).start();
            new Thread(new Producer()).start();
            new Thread(new Consumer()).start();
        }
    }
    static class Demo2 {
        class Dept {
            private int size;
            private int capacity;
            private Object obj = new Object();

            Dept(int capacity) {
                this.size = 0;
                this.capacity = capacity;
            }
            public void produce(int val) {
                try {
                    int left = val;
                    while (left > 0) {
                        synchronized (obj) {
                            Thread.sleep(100);
                            while (size >= capacity) {
                                obj.wait();
                            }
                            int inc = (size + left - capacity) > 0 ? capacity - size : left;
                            size += inc;
                            left = val - inc;
                            System.out.println(Thread.currentThread().getName() + " produce " + val + " size=" + size + ", inc=" + inc + ", left=" + left);
                            obj.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            public void consume(int val) {
                try {
                    int left = val;
                    while (left > 0) {
                        synchronized (obj) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            while (size <= 0) {
                                obj.wait();
                            }
                            int dec = (size - left) < 0 ? size : left;
                            size -= dec;
                            left = left - dec;
                            System.out.println(Thread.currentThread().getName() + " consume " + val + " size=" + size + ", dec=" + dec + ", left=" + left);
                            obj.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        class Producer implements Runnable {
            private Dept dept;
            private int val;
            Producer(Dept dept, int val) {
                this.dept = dept;
                this.val = val;
            }
            @Override
            public void run() {
                dept.produce(val);
            }
        }
        class Consumer implements Runnable {
            private Dept dept;
            private int val;

            Consumer(Dept dept, int val) {
                this.dept = dept;
                this.val = val;
            }
            @Override
            public void run() {
                dept.consume(val);
            }
        }
        public void test() {
            Dept dept = new Dept(20);
            new Thread(new Producer(dept, 10)).start();
            new Thread(new Producer(dept, 300)).start();
            new Thread(new Producer(dept, 60)).start();
            new Thread(new Consumer(dept, 20)).start();
            new Thread(new Consumer(dept, 200)).start();
            new Thread(new Consumer(dept, 150)).start();
        }

    }
    public static void main(String[] args) {
        Demo2 d = new Demo2();
//        Demo1 d = new Demo1();
        d.test();
    }
}

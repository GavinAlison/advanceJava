# 生产者与消费者模型

>引用: http://www.importnew.com/27063.html
>引用: https://juejin.im/entry/596343686fb9a06bbd6f888c

实现: 4种写法
1.  wait && notify
2.  使用Lock和Condition的await() / signal()方法
3.  使用BlockingQueue阻塞队列方法
4.  使用信号量机制
5.  使用输入输出管理




## 生产者-消费者模型    

网上有很多生产者-消费者模型的定义和实现。本文研究最常用的有界生产者-消费者模型，简单概括如下：

-   生产者持续生产，直到缓冲区满，阻塞；缓冲区不满后，继续生产
-   消费者持续消费，直到缓冲区空，阻塞；缓冲区不空后，继续消费
-   生产者可以有多个，消费者也可以有多个

可通过如下条件验证模型实现的正确性：

-   同一产品的消费行为一定发生在生产行为之后
-   任意时刻，缓冲区大小不小于0，不大于限制容量

该模型的应用和变种非常多，不赘述。

## wait()和notify()方法的实现

>缓冲区满和为空时都调用wait()方法等待，当生产者生产了一个产品或者消费者消费了一个产品之后会唤醒所有线程。

[猴子007](https://monkeysayhi.github.io/2017/10/08/Java%E5%AE%9E%E7%8E%B0%E7%94%9F%E4%BA%A7%E8%80%85-%E6%B6%88%E8%B4%B9%E8%80%85%E6%A8%A1%E5%9E%8B/
)那种写法，不太赞成，他复杂了，计算机的语言，要求简洁，通用，易懂，并不是一味的抽象就可以了。

```
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
```

## lock 与 condition 实现

>java.util.concurrent.lock 中的 Lock 框架是锁定的一个抽象，通过对lock的lock()方法和unlock()方法
实现了对锁的显示控制，而synchronize()则是对锁的隐性控制。
 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，
 但不受影响，简单来说，该锁维护这一个与获取锁相关的计数器，如果拥有锁的某个线程再次得到锁，
 那么获取计数器就加1，函数调用结束计数器就减1，然后锁需要被释放两次才能获得真正释放。已经获取锁的线程
 进入其他需要相同锁的同步代码块不会被阻塞。
 
 ```
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

```


## 阻塞队列BlockingQueue的实现
> 主要使用BlockingQueue的take()与put()函数, 这两方法底层使用了锁机制，阻塞队列

```
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
     * 4 . TimesOut:如果操作不能马上进行，操作会被阻塞指定的时间，如果指定时间没执行，则返回一个特殊值，一般是true或者false
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
```


## 信号量Semaphore的实现

>Java中的Semaphore维护了一个许可集，一开始先设定这个许可集的数量，
可以使用acquire()方法获得一个许可，当许可不足时会被阻塞，release()添加一个许可。

```java
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
```

## 管道输入输出流PipedInputStream和PipedOutputStream实现

> 先创建一个管道输入流和管道输出流，然后将输入流和输出流进行连接，用生产者线程往管道输出流中写入数据，
消费者在管道输入流中读取数据，这样就可以实现了不同线程间的相互通讯


```java
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/***
 * @Author Alison
 * @Date 2019/5/7
 **/
//管道输入输出流PipedInputStream和PipedOutputStream实现
public class StreamDemo {
    /**
     * 在java的io包下，PipedOutputStream和PipedInputStream分别是管道输出流和管道输入流。
     * 它们的作用是让多线程可以通过管道进行线程间的通讯。在使用管道通信时，必须将PipedOutputStream和PipedInputStream配套使用。
     * 使用方法：先创建一个管道输入流和管道输出流，然后将输入流和输出流进行连接，用生产者线程往管道输出流中写入数据，
     * 消费者在管道输入流中读取数据，这样就可以实现了不同线程间的相互通讯，但是这种方式在生产者和生产者、消费者和消费者之间不能保证同步，
     * 也就是说在一个生产者和一个消费者的情况下是可以生产者和消费者之间交替运行的，多个生成者和多个消费者者之间则不行
     */
    final PipedInputStream pis = new PipedInputStream();
    final PipedOutputStream pos = new PipedOutputStream();
    {
        try {
            pis.connect(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    int num = (int) (Math.random() * 255);
                    System.out.println(Thread.currentThread().getName() + "生产者生产了一个数字，该数字为： " + num);
                    pos.write(num);
                    pos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
                    pis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                    int num = pis.read();
                    System.out.println("消费者消费了一个数字，该数字为：" + num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
                    pis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        StreamDemo s = new StreamDemo();
        new Thread(s.new Producer()).start();
        new Thread(s.new Consumer()).start();
    }
}
```
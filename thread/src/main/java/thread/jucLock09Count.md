# Java多线程系列--“JUC锁”09之 CountDownLatch原理和示例

>## 概要
前面对"独占锁"和"共享锁"有了个大致的了解；本章，我们对CountDownLatch进行学习。和ReadWriteLock.ReadLock一样，CountDownLatch的本质也是一个"共享锁"。本章的内容包括：
-   CountDownLatch简介
-   CountDownLatch数据结构
-   CountDownLatch源码分析(基于JDK1.7.0_40)
-   CountDownLatch示例
-   实例代码CountDownLatchDemo.java

转载请注明出处：http://www.cnblogs.com/skywang12345/p/3533887.html

 

>## CountDownLatch简介
CountDownLatch是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。

 

CountDownLatch和CyclicBarrier的区别   
 01) CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待。
 02) CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因此它被称为是循环的barrier。
关于CyclicBarrier的原理，后面一章再来学习。


**CountDownLatch函数列表**

```
CountDownLatch(int count)
构造一个用给定计数初始化的 CountDownLatch。

// 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。
void await()
// 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断或超出了指定的等待时间。
boolean await(long timeout, TimeUnit unit)
// 递减锁存器的计数，如果计数到达零，则释放所有等待的线程。
void countDown()
// 返回当前计数。
long getCount()
// 返回标识此锁存器及其状态的字符串。
String toString()
```
 

>## CountDownLatch数据结构
CountDownLatch的UML类图如下：
![lock09.png](test/resource/lock09.png)

CountDownLatch的数据结构很简单，它是通过"共享锁"实现的。
它包含了sync对象，sync是Sync类型。Sync是实例类，它继承于AQS。
 

>## CountDownLatch源码分析(基于JDK1.7.0_40)
CountDownLatch完整源码(基于JDK1.7.0_40)

```$xslt
/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;

/**
 * A synchronization aid that allows one or more threads to wait until
 * a set of operations being performed in other threads completes.
 *
 * <p>A {@code CountDownLatch} is initialized with a given <em>count</em>.
 * The {@link #await await} methods block until the current count reaches
 * zero due to invocations of the {@link #countDown} method, after which
 * all waiting threads are released and any subsequent invocations of
 * {@link #await await} return immediately.  This is a one-shot phenomenon
 * -- the count cannot be reset.  If you need a version that resets the
 * count, consider using a {@link CyclicBarrier}.
 *
 * <p>A {@code CountDownLatch} is a versatile synchronization tool
 * and can be used for a number of purposes.  A
 * {@code CountDownLatch} initialized with a count of one serves as a
 * simple on/off latch, or gate: all threads invoking {@link #await await}
 * wait at the gate until it is opened by a thread invoking {@link
 * #countDown}.  A {@code CountDownLatch} initialized to <em>N</em>
 * can be used to make one thread wait until <em>N</em> threads have
 * completed some action, or some action has been completed N times.
 *
 * <p>A useful property of a {@code CountDownLatch} is that it
 * doesn't require that threads calling {@code countDown} wait for
 * the count to reach zero before proceeding, it simply prevents any
 * thread from proceeding past an {@link #await await} until all
 * threads could pass.
 *
 * <p><b>Sample usage:</b> Here is a pair of classes in which a group
 * of worker threads use two countdown latches:
 * <ul>
 * <li>The first is a start signal that prevents any worker from proceeding
 * until the driver is ready for them to proceed;
 * <li>The second is a completion signal that allows the driver to wait
 * until all workers have completed.
 * </ul>
 *
 * <pre>
 * class Driver { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch startSignal = new CountDownLatch(1);
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       new Thread(new Worker(startSignal, doneSignal)).start();
 *
 *     doSomethingElse();            // don't let run yet
 *     startSignal.countDown();      // let all threads proceed
 *     doSomethingElse();
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class Worker implements Runnable {
 *   private final CountDownLatch startSignal;
 *   private final CountDownLatch doneSignal;
 *   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
 *      this.startSignal = startSignal;
 *      this.doneSignal = doneSignal;
 *   }
 *   public void run() {
 *      try {
 *        startSignal.await();
 *        doWork();
 *        doneSignal.countDown();
 *      } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }
 *
 * </pre>
 *
 * <p>Another typical usage would be to divide a problem into N parts,
 * describe each part with a Runnable that executes that portion and
 * counts down on the latch, and queue all the Runnables to an
 * Executor.  When all sub-parts are complete, the coordinating thread
 * will be able to pass through await. (When threads must repeatedly
 * count down in this way, instead use a {@link CyclicBarrier}.)
 *
 * <pre>
 * class Driver2 { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *     Executor e = ...
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       e.execute(new WorkerRunnable(doneSignal, i));
 *
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class WorkerRunnable implements Runnable {
 *   private final CountDownLatch doneSignal;
 *   private final int i;
 *   WorkerRunnable(CountDownLatch doneSignal, int i) {
 *      this.doneSignal = doneSignal;
 *      this.i = i;
 *   }
 *   public void run() {
 *      try {
 *        doWork(i);
 *        doneSignal.countDown();
 *      } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }
 *
 * </pre>
 *
 * <p>Memory consistency effects: Until the count reaches
 * zero, actions in a thread prior to calling
 * {@code countDown()}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions following a successful return from a corresponding
 * {@code await()} in another thread.
 *
 * @since 1.5
 * @author Doug Lea
 */
public class CountDownLatch {
    /**
     * Synchronization control For CountDownLatch.
     * Uses AQS state to represent count.
     */
    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }

    private final Sync sync;

    /**
     * Constructs a {@code CountDownLatch} initialized with the given count.
     *
     * @param count the number of times {@link #countDown} must be invoked
     *        before threads can pass through {@link #await}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public CountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted}.
     *
     * <p>If the current count is zero then this method returns immediately.
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of two things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted},
     * or the specified waiting time elapses.
     *
     * <p>If the current count is zero then this method returns immediately
     * with the value {@code true}.
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of three things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>The specified waiting time elapses.
     * </ul>
     *
     * <p>If the count reaches zero then the method returns with the
     * value {@code true}.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.  If the time is less than or equal to zero, the method
     * will not wait at all.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return {@code true} if the count reached zero and {@code false}
     *         if the waiting time elapsed before the count reached zero
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    public boolean await(long timeout, TimeUnit unit)
        throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    /**
     * Decrements the count of the latch, releasing all waiting threads if
     * the count reaches zero.
     *
     * <p>If the current count is greater than zero then it is decremented.
     * If the new count is zero then all waiting threads are re-enabled for
     * thread scheduling purposes.
     *
     * <p>If the current count equals zero then nothing happens.
     */
    public void countDown() {
        sync.releaseShared(1);
    }

    /**
     * Returns the current count.
     *
     * <p>This method is typically used for debugging and testing purposes.
     *
     * @return the current count
     */
    public long getCount() {
        return sync.getCount();
    }

    /**
     * Returns a string identifying this latch, as well as its state.
     * The state, in brackets, includes the String {@code "Count ="}
     * followed by the current count.
     *
     * @return a string identifying this latch, as well as its state
     */
    public String toString() {
        return super.toString() + "[Count = " + sync.getCount() + "]";
    }
}
```
 
CountDownLatch是通过“共享锁”实现的。
下面，我们分析CountDownLatch中3个核心函数: CountDownLatch(int count), await(), countDown()。

 

**1. CountDownLatch(int count)**
```
public CountDownLatch(int count) {
    if (count < 0) throw new IllegalArgumentException("count < 0");
    this.sync = new Sync(count);
}
```
说明：该函数是创建一个Sync对象，而Sync是继承于AQS类。Sync构造函数如下：
```
Sync(int count) {
    setState(count);
}
```

setState()在AQS中实现，源码如下：
```
protected final void setState(long newState) {
    state = newState;
}
```
说明：在AQS中，state是一个private volatile long类型的对象。
对于CountDownLatch而言，state表示的”锁计数器“。
CountDownLatch中的getCount()最终是调用AQS中的getState()，返回的state对象，即”锁计数器“。

 

**2. await()**
```
public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}
```
说明：该函数实际上是调用的AQS的acquireSharedInterruptibly(1);

AQS中的acquireSharedInterruptibly()的源码如下：

```
public final void acquireSharedInterruptibly(long arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (tryAcquireShared(arg) < 0)
        doAcquireSharedInterruptibly(arg);
}
```
说明：acquireSharedInterruptibly()的作用是获取共享锁。
如果当前线程是中断状态，则抛出异常InterruptedException
否则，调用tryAcquireShared(arg)尝试获取共享锁；尝试成功则返回，否则就调用doAcquireSharedInterruptibly()。
doAcquireSharedInterruptibly()会使当前线程一直等待，直到当前线程获取到共享锁(或被中断)才返回。

tryAcquireShared()在CountDownLatch.java中被重写，它的源码如下：
```
protected int tryAcquireShared(int acquires) {
    return (getState() == 0) ? 1 : -1;
}
```
说明：tryAcquireShared()的作用是尝试获取共享锁。
如果"锁计数器=0"，即锁是可获取状态，则返回1；否则，锁是不可获取状态，则返回-1。

```
private void doAcquireSharedInterruptibly(long arg)
    throws InterruptedException {
    // 创建"当前线程"的Node节点，且Node中记录的锁是"共享锁"类型；并将该节点添加到CLH队列末尾。
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            // 获取上一个节点。
            // 如果上一节点是CLH队列的表头，则"尝试获取共享锁"。
            final Node p = node.predecessor();
            if (p == head) {
                long r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            // (上一节点不是CLH队列的表头) 当前线程一直等待，直到获取到共享锁。
            // 如果线程在等待过程中被中断过，则再次中断该线程(还原之前的中断状态)。
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```
说明：   
 01) addWaiter(Node.SHARED)的作用是，创建”当前线程“的Node节点，且Node中记录的锁的类型是”共享锁“(Node.SHARED)；并将该节点添加到CLH队列末尾。关于Node和CLH在"Java多线程系列--“JUC锁”03之 公平锁(一)"已经详细介绍过，这里就不再重复说明了。
 02) node.predecessor()的作用是，获取上一个节点。如果上一节点是CLH队列的表头，则”尝试获取共享锁“。
 03) shouldParkAfterFailedAcquire()的作用和它的名称一样，如果在尝试获取锁失败之后，线程应该等待，则返回true；否则，返回false。
 04) 当shouldParkAfterFailedAcquire()返回ture时，则调用parkAndCheckInterrupt()，当前线程会进入等待状态，直到获取到共享锁才继续运行。
 
 
doAcquireSharedInterruptibly()中的shouldParkAfterFailedAcquire(), parkAndCheckInterrupt等函数在"Java多线程系列--“JUC锁”03之 公平锁(一)"中介绍过，这里也就不再详细说明了。

 

**3. countDown()**
```
public void countDown() {
    sync.releaseShared(1);
}
```
说明：该函数实际上调用releaseShared(1)释放共享锁。

releaseShared()在AQS中实现，源码如下：

```
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}
```
说明：releaseShared()的目的是让当前线程释放它所持有的共享锁。   
它首先会通过tryReleaseShared()去尝试释放共享锁。
尝试成功，则直接返回；尝试失败，则通过doReleaseShared()去释放共享锁。

tryReleaseShared()在CountDownLatch.java中被重写，源码如下：

```
protected boolean tryReleaseShared(int releases) {
    // Decrement count; signal when transition to zero
    for (;;) {
        // 获取“锁计数器”的状态
        int c = getState();
        if (c == 0)
            return false;
        // “锁计数器”-1
        int nextc = c-1;
        // 通过CAS函数进行赋值。
        if (compareAndSetState(c, nextc))
            return nextc == 0;
    }
}
```
说明：tryReleaseShared()的作用是释放共享锁，将“锁计数器”的值-1。

 
```
总结：CountDownLatch是通过“共享锁”实现的。在创建CountDownLatch中时，
会传递一个int类型参数count，该参数是“锁计数器”的初始状态，
表示该“共享锁”最多能被count个线程同时获取。
当某线程调用该CountDownLatch对象的await()方法时，该线程会等待“共享锁”可用时，
才能获取“共享锁”进而继续运行。而“共享锁”可用的条件，就是“锁计数器”的值为0！
而“锁计数器”的初始值为count，每当一个线程调用该CountDownLatch对象的countDown()方法时，
才将“锁计数器”-1；通过这种方式，必须有count个线程调用countDown()之后，“锁计数器”才为0，
而前面提到的等待线程才能继续运行！

count并不表示可以被获取的最大线程数；count而是表示需要多少个countDown()执行后，
之前所有被阻塞的线程（执行await()的线程）才能继续执行。
当count个countdown()被执行后，state为0，此时所有因doAcquireSharedInterruptibly()
而阻塞的线程都会离开阻塞状态。
```
以上，就是CountDownLatch的实现原理。

 

>## CountDownLatch的使用示例
下面通过CountDownLatch实现："主线程"等待"5个子线程"全部都完成"指定的工作(休眠1000ms)"之后，再继续运行。

```
 import java.util.concurrent.CountDownLatch;
 import java.util.concurrent.CyclicBarrier;
 
 public class CountDownLatchTest1 {
 
     private static int LATCH_SIZE = 5;
     private static CountDownLatch doneSignal;
     public static void main(String[] args) {
 
         try {
             doneSignal = new CountDownLatch(LATCH_SIZE);
 
             // 新建5个任务
             for(int i=0; i<LATCH_SIZE; i++)
                 new InnerThread().start();
 
             System.out.println("main await begin.");
             // "主线程"等待线程池中5个任务的完成
             doneSignal.await();
 
             System.out.println("main await finished.");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 
     static class InnerThread extends Thread{
         public void run() {
             try {
                 Thread.sleep(1000);
                 System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                 // 将CountDownLatch的数值减1
                 doneSignal.countDown();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
 }
```
运行结果：

```
main await begin.
Thread-0 sleep 1000ms.
Thread-2 sleep 1000ms.
Thread-1 sleep 1000ms.
Thread-4 sleep 1000ms.
Thread-3 sleep 1000ms.
main await finished.
```
结果说明：主线程通过doneSignal.await()等待其它线程将doneSignal递减至0。其它的5个InnerThread线程，每一个都通过doneSignal.countDown()将doneSignal的值减1；当doneSignal为0时，main被唤醒后继续执行。

# Java多线程系列--“JUC集合”07之 ArrayBlockingQueue

>## 概要
本章对Java.util.concurrent包中的ArrayBlockingQueue类进行详细的介绍。内容包括：
-   ArrayBlockingQueue介绍
-   ArrayBlockingQueue原理和数据结构
-   ArrayBlockingQueue函数列表
-   ArrayBlockingQueue源码分析(JDK1.7.0_40版本)
-   ArrayBlockingQueue示例

转载请注明出处：http://www.cnblogs.com/skywang12345/p/3498652.html

 

>## ArrayBlockingQueue介绍
ArrayBlockingQueue是数组实现的线程安全的有界的阻塞队列。
线程安全是指，ArrayBlockingQueue内部通过“互斥锁”保护竞争资源，实现了多线程对竞争资源的互斥访问。
而有界，则是指ArrayBlockingQueue对应的数组是有界限的。 
阻塞队列，是指多线程访问竞争资源时，当竞争资源已被某线程获取时，其它要获取该资源的线程需要阻塞等待；
而且，ArrayBlockingQueue是按 FIFO（先进先出）原则对元素进行排序，元素都是从尾部插入到队列，
从头部开始返回。

注意：ArrayBlockingQueue不同于ConcurrentLinkedQueue，ArrayBlockingQueue是数组实现的，并且是有界限的；
而ConcurrentLinkedQueue是链表实现的，是无界限的。

 

>## ArrayBlockingQueue原理和数据结构
ArrayBlockingQueue的数据结构，如下图所示：
![collection11](test/resouce/collection11.png)


说明：
1. ArrayBlockingQueue继承于AbstractQueue，并且它实现了BlockingQueue接口。
2. ArrayBlockingQueue内部是通过Object[]数组保存数据的，也就是说ArrayBlockingQueue本质上是通过数组实现的。ArrayBlockingQueue的大小，即数组的容量是创建ArrayBlockingQueue时指定的。
3. ArrayBlockingQueue与ReentrantLock是组合关系，ArrayBlockingQueue中包含一个ReentrantLock对象(lock)。ReentrantLock是可重入的互斥锁，ArrayBlockingQueue就是根据该互斥锁实现“多线程对竞争资源的互斥访问”。而且，ReentrantLock分为公平锁和非公平锁，关于具体使用公平锁还是非公平锁，在创建ArrayBlockingQueue时可以指定；而且，ArrayBlockingQueue默认会使用非公平锁。
4. ArrayBlockingQueue与Condition是组合关系，ArrayBlockingQueue中包含两个Condition对象(notEmpty和notFull)。而且，Condition又依赖于ArrayBlockingQueue而存在，通过Condition可以实现对ArrayBlockingQueue的更精确的访问 -- (01)若某线程(线程A)要取数据时，数组正好为空，则该线程会执行notEmpty.await()进行等待；当其它某个线程(线程B)向数组中插入了数据之后，会调用notEmpty.signal()唤醒“notEmpty上的等待线程”。此时，线程A会被唤醒从而得以继续运行。(02)若某线程(线程H)要插入数据时，数组已满，则该线程会它执行notFull.await()进行等待；当其它某个线程(线程I)取出数据之后，会调用notFull.signal()唤醒“notFull上的等待线程”。此时，线程H就会被唤醒从而得以继续运行。

>## ArrayBlockingQueue函数列表
```
// 创建一个带有给定的（固定）容量和默认访问策略的 ArrayBlockingQueue。
ArrayBlockingQueue(int capacity)
// 创建一个具有给定的（固定）容量和指定访问策略的 ArrayBlockingQueue。
ArrayBlockingQueue(int capacity, boolean fair)
// 创建一个具有给定的（固定）容量和指定访问策略的 ArrayBlockingQueue，它最初包含给定 collection 的元素，并以 collection 迭代器的遍历顺序添加元素。
ArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c)

// 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），在成功时返回 true，如果此队列已满，则抛出 IllegalStateException。
boolean add(E e)
// 自动移除此队列中的所有元素。
void clear()
// 如果此队列包含指定的元素，则返回 true。
boolean contains(Object o)
// 移除此队列中所有可用的元素，并将它们添加到给定 collection 中。
int drainTo(Collection<? super E> c)
// 最多从此队列中移除给定数量的可用元素，并将这些元素添加到给定 collection 中。
int drainTo(Collection<? super E> c, int maxElements)
// 返回在此队列中的元素上按适当顺序进行迭代的迭代器。
Iterator<E> iterator()
// 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），在成功时返回 true，如果此队列已满，则返回 false。
boolean offer(E e)
// 将指定的元素插入此队列的尾部，如果该队列已满，则在到达指定的等待时间之前等待可用的空间。
boolean offer(E e, long timeout, TimeUnit unit)
// 获取但不移除此队列的头；如果此队列为空，则返回 null。
E peek()
// 获取并移除此队列的头，如果此队列为空，则返回 null。
E poll()
// 获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）。
E poll(long timeout, TimeUnit unit)
// 将指定的元素插入此队列的尾部，如果该队列已满，则等待可用的空间。
void put(E e)
// 返回在无阻塞的理想情况下（不存在内存或资源约束）此队列能接受的其他元素数量。
int remainingCapacity()
// 从此队列中移除指定元素的单个实例（如果存在）。
boolean remove(Object o)
// 返回此队列中元素的数量。
int size()
// 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
E take()
// 返回一个按适当顺序包含此队列中所有元素的数组。
Object[] toArray()
// 返回一个按适当顺序包含此队列中所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
<T> T[] toArray(T[] a)
// 返回此 collection 的字符串表示形式。
String toString()
```
 

>## ArrayBlockingQueue源码分析(JDK1.7.0_40版本)
ArrayBlockingQueue.java的完整源码如下：

``` 
/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * A bounded {@linkplain BlockingQueue blocking queue} backed by an
 * array.  This queue orders elements FIFO (first-in-first-out).  The
 * <em>head</em> of the queue is that element that has been on the
 * queue the longest time.  The <em>tail</em> of the queue is that
 * element that has been on the queue the shortest time. New elements
 * are inserted at the tail of the queue, and the queue retrieval
 * operations obtain elements at the head of the queue.
 *
 * <p>This is a classic &quot;bounded buffer&quot;, in which a
 * fixed-sized array holds elements inserted by producers and
 * extracted by consumers.  Once created, the capacity cannot be
 * changed.  Attempts to {@code put} an element into a full queue
 * will result in the operation blocking; attempts to {@code take} an
 * element from an empty queue will similarly block.
 *
 * <p>This class supports an optional fairness policy for ordering
 * waiting producer and consumer threads.  By default, this ordering
 * is not guaranteed. However, a queue constructed with fairness set
 * to {@code true} grants threads access in FIFO order. Fairness
 * generally decreases throughput but reduces variability and avoids
 * starvation.
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public class ArrayBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {

    /**
     * Serialization ID. This class relies on default serialization
     * even for the items array, which is default-serialized, even if
     * it is empty. Otherwise it could not be declared final, which is
     * necessary here.
     */
    private static final long serialVersionUID = -817911632652898426L;

    /** The queued items */
    final Object[] items;

    /** items index for next take, poll, peek or remove */
    int takeIndex;

    /** items index for next put, offer, or add */
    int putIndex;

    /** Number of elements in the queue */
    int count;

    /*
     * Concurrency control uses the classic two-condition algorithm
     * found in any textbook.
     */

    /** Main lock guarding all access */
    final ReentrantLock lock;
    /** Condition for waiting takes */
    private final Condition notEmpty;
    /** Condition for waiting puts */
    private final Condition notFull;

    // Internal helper methods

    /**
     * Circularly increment i.
     */
    final int inc(int i) {
        return (++i == items.length) ? 0 : i;
    }

    /**
     * Circularly decrement i.
     */
    final int dec(int i) {
        return ((i == 0) ? items.length : i) - 1;
    }

    @SuppressWarnings("unchecked")
    static <E> E cast(Object item) {
        return (E) item;
    }

    /**
     * Returns item at index i.
     */
    final E itemAt(int i) {
        return this.<E>cast(items[i]);
    }

    /**
     * Throws NullPointerException if argument is null.
     *
     * @param v the element
     */
    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }

    /**
     * Inserts element at current put position, advances, and signals.
     * Call only when holding lock.
     */
    private void insert(E x) {
        items[putIndex] = x;
        putIndex = inc(putIndex);
        ++count;
        notEmpty.signal();
    }

    /**
     * Extracts element at current take position, advances, and signals.
     * Call only when holding lock.
     */
    private E extract() {
        final Object[] items = this.items;
        E x = this.<E>cast(items[takeIndex]);
        items[takeIndex] = null;
        takeIndex = inc(takeIndex);
        --count;
        notFull.signal();
        return x;
    }

    /**
     * Deletes item at position i.
     * Utility for remove and iterator.remove.
     * Call only when holding lock.
     */
    void removeAt(int i) {
        final Object[] items = this.items;
        // if removing front item, just advance
        if (i == takeIndex) {
            items[takeIndex] = null;
            takeIndex = inc(takeIndex);
        } else {
            // slide over all others up through putIndex.
            for (;;) {
                int nexti = inc(i);
                if (nexti != putIndex) {
                    items[i] = items[nexti];
                    i = nexti;
                } else {
                    items[i] = null;
                    putIndex = i;
                    break;
                }
            }
        }
        --count;
        notFull.signal();
    }

    /**
     * Creates an {@code ArrayBlockingQueue} with the given (fixed)
     * capacity and default access policy.
     *
     * @param capacity the capacity of this queue
     * @throws IllegalArgumentException if {@code capacity < 1}
     */
    public ArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }

    /**
     * Creates an {@code ArrayBlockingQueue} with the given (fixed)
     * capacity and the specified access policy.
     *
     * @param capacity the capacity of this queue
     * @param fair if {@code true} then queue accesses for threads blocked
     *        on insertion or removal, are processed in FIFO order;
     *        if {@code false} the access order is unspecified.
     * @throws IllegalArgumentException if {@code capacity < 1}
     */
    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }

    /**
     * Creates an {@code ArrayBlockingQueue} with the given (fixed)
     * capacity, the specified access policy and initially containing the
     * elements of the given collection,
     * added in traversal order of the collection's iterator.
     *
     * @param capacity the capacity of this queue
     * @param fair if {@code true} then queue accesses for threads blocked
     *        on insertion or removal, are processed in FIFO order;
     *        if {@code false} the access order is unspecified.
     * @param c the collection of elements to initially contain
     * @throws IllegalArgumentException if {@code capacity} is less than
     *         {@code c.size()}, or less than 1.
     * @throws NullPointerException if the specified collection or any
     *         of its elements are null
     */
    public ArrayBlockingQueue(int capacity, boolean fair,
                              Collection<? extends E> c) {
        this(capacity, fair);

        final ReentrantLock lock = this.lock;
        lock.lock(); // Lock only for visibility, not mutual exclusion
        try {
            int i = 0;
            try {
                for (E e : c) {
                    checkNotNull(e);
                    items[i++] = e;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new IllegalArgumentException();
            }
            count = i;
            putIndex = (i == capacity) ? 0 : i;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts the specified element at the tail of this queue if it is
     * possible to do so immediately without exceeding the queue's capacity,
     * returning {@code true} upon success and throwing an
     * {@code IllegalStateException} if this queue is full.
     *
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IllegalStateException if this queue is full
     * @throws NullPointerException if the specified element is null
     */
    public boolean add(E e) {
        return super.add(e);
    }

    /**
     * Inserts the specified element at the tail of this queue if it is
     * possible to do so immediately without exceeding the queue's capacity,
     * returning {@code true} upon success and {@code false} if this queue
     * is full.  This method is generally preferable to method {@link #add},
     * which can fail to insert an element only by throwing an exception.
     *
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length)
                return false;
            else {
                insert(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting
     * for space to become available if the queue is full.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            insert(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting
     * up to the specified wait time for space to become available if
     * the queue is full.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException {

        checkNotNull(e);
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                if (nanos <= 0)
                    return false;
                nanos = notFull.awaitNanos(nanos);
            }
            insert(e);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : extract();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return extract();
        } finally {
            lock.unlock();
        }
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                if (nanos <= 0)
                    return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            return extract();
        } finally {
            lock.unlock();
        }
    }

    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : itemAt(takeIndex);
        } finally {
            lock.unlock();
        }
    }

    // this doc comment is overridden to remove the reference to collections
    // greater in size than Integer.MAX_VALUE
    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    // this doc comment is a modified copy of the inherited doc comment,
    // without the reference to unlimited queues.
    /**
     * Returns the number of additional elements that this queue can ideally
     * (in the absence of memory or resource constraints) accept without
     * blocking. This is always equal to the initial capacity of this queue
     * less the current {@code size} of this queue.
     *
     * <p>Note that you <em>cannot</em> always tell if an attempt to insert
     * an element will succeed by inspecting {@code remainingCapacity}
     * because it may be the case that another thread is about to
     * insert or remove an element.
     */
    public int remainingCapacity() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.length - count;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements.
     * Returns {@code true} if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     *
     * <p>Removal of interior elements in circular array based queues
     * is an intrinsically slow and disruptive operation, so should
     * be undertaken only in exceptional circumstances, ideally
     * only when the queue is known not to be accessible by other
     * threads.
     *
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     */
    public boolean remove(Object o) {
        if (o == null) return false;
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = takeIndex, k = count; k > 0; i = inc(i), k--) {
                if (o.equals(items[i])) {
                    removeAt(i);
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * @param o object to be checked for containment in this queue
     * @return {@code true} if this queue contains the specified element
     */
    public boolean contains(Object o) {
        if (o == null) return false;
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = takeIndex, k = count; k > 0; i = inc(i), k--)
                if (o.equals(items[i]))
                    return true;
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns an array containing all of the elements in this queue, in
     * proper sequence.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this queue.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this queue
     */
    public Object[] toArray() {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int count = this.count;
            Object[] a = new Object[count];
            for (int i = takeIndex, k = 0; k < count; i = inc(i), k++)
                a[k] = items[i];
            return a;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns an array containing all of the elements in this queue, in
     * proper sequence; the runtime type of the returned array is that of
     * the specified array.  If the queue fits in the specified array, it
     * is returned therein.  Otherwise, a new array is allocated with the
     * runtime type of the specified array and the size of this queue.
     *
     * <p>If this queue fits in the specified array with room to spare
     * (i.e., the array has more elements than this queue), the element in
     * the array immediately following the end of the queue is set to
     * {@code null}.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a queue known to contain only strings.
     * The following code can be used to dump the queue into a newly
     * allocated array of {@code String}:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of the queue are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose
     * @return an array containing all of the elements in this queue
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this queue
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int count = this.count;
            final int len = a.length;
            if (len < count)
                a = (T[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), count);
            for (int i = takeIndex, k = 0; k < count; i = inc(i), k++)
                a[k] = (T) items[i];
            if (len > count)
                a[count] = null;
            return a;
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int k = count;
            if (k == 0)
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = takeIndex; ; i = inc(i)) {
                Object e = items[i];
                sb.append(e == this ? "(this Collection)" : e);
                if (--k == 0)
                    return sb.append(']').toString();
                sb.append(',').append(' ');
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Atomically removes all of the elements from this queue.
     * The queue will be empty after this call returns.
     */
    public void clear() {
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = takeIndex, k = count; k > 0; i = inc(i), k--)
                items[i] = null;
            count = 0;
            putIndex = 0;
            takeIndex = 0;
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public int drainTo(Collection<? super E> c) {
        checkNotNull(c);
        if (c == this)
            throw new IllegalArgumentException();
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = takeIndex;
            int n = 0;
            int max = count;
            while (n < max) {
                c.add(this.<E>cast(items[i]));
                items[i] = null;
                i = inc(i);
                ++n;
            }
            if (n > 0) {
                count = 0;
                putIndex = 0;
                takeIndex = 0;
                notFull.signalAll();
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public int drainTo(Collection<? super E> c, int maxElements) {
        checkNotNull(c);
        if (c == this)
            throw new IllegalArgumentException();
        if (maxElements <= 0)
            return 0;
        final Object[] items = this.items;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = takeIndex;
            int n = 0;
            int max = (maxElements < count) ? maxElements : count;
            while (n < max) {
                c.add(this.<E>cast(items[i]));
                items[i] = null;
                i = inc(i);
                ++n;
            }
            if (n > 0) {
                count -= n;
                takeIndex = i;
                notFull.signalAll();
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     * The elements will be returned in order from first (head) to last (tail).
     *
     * <p>The returned {@code Iterator} is a "weakly consistent" iterator that
     * will never throw {@link java.util.ConcurrentModificationException
     * ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     *
     * @return an iterator over the elements in this queue in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * Iterator for ArrayBlockingQueue. To maintain weak consistency
     * with respect to puts and takes, we (1) read ahead one slot, so
     * as to not report hasNext true but then not have an element to
     * return -- however we later recheck this slot to use the most
     * current value; (2) ensure that each array slot is traversed at
     * most once (by tracking "remaining" elements); (3) skip over
     * null slots, which can occur if takes race ahead of iterators.
     * However, for circular array-based queues, we cannot rely on any
     * well established definition of what it means to be weakly
     * consistent with respect to interior removes since these may
     * require slot overwrites in the process of sliding elements to
     * cover gaps. So we settle for resiliency, operating on
     * established apparent nexts, which may miss some elements that
     * have moved between calls to next.
     */
    private class Itr implements Iterator<E> {
        private int remaining; // Number of elements yet to be returned
        private int nextIndex; // Index of element to be returned by next
        private E nextItem;    // Element to be returned by next call to next
        private E lastItem;    // Element returned by last call to next
        private int lastRet;   // Index of last element returned, or -1 if none

        Itr() {
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                lastRet = -1;
                if ((remaining = count) > 0)
                    nextItem = itemAt(nextIndex = takeIndex);
            } finally {
                lock.unlock();
            }
        }

        public boolean hasNext() {
            return remaining > 0;
        }

        public E next() {
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                if (remaining <= 0)
                    throw new NoSuchElementException();
                lastRet = nextIndex;
                E x = itemAt(nextIndex);  // check for fresher value
                if (x == null) {
                    x = nextItem;         // we are forced to report old value
                    lastItem = null;      // but ensure remove fails
                }
                else
                    lastItem = x;
                while (--remaining > 0 && // skip over nulls
                       (nextItem = itemAt(nextIndex = inc(nextIndex))) == null)
                    ;
                return x;
            } finally {
                lock.unlock();
            }
        }

        public void remove() {
            final ReentrantLock lock = ArrayBlockingQueue.this.lock;
            lock.lock();
            try {
                int i = lastRet;
                if (i == -1)
                    throw new IllegalStateException();
                lastRet = -1;
                E x = lastItem;
                lastItem = null;
                // only remove if item still at index
                if (x != null && x == items[i]) {
                    boolean removingHead = (i == takeIndex);
                    removeAt(i);
                    if (!removingHead)
                        nextIndex = dec(nextIndex);
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
```
 

下面从ArrayBlockingQueue的创建，添加，取出，遍历这几个方面对ArrayBlockingQueue进行分析。

1. 创建

下面以ArrayBlockingQueue(int capacity, boolean fair)来进行说明。

```
public ArrayBlockingQueue(int capacity, boolean fair) {
    if (capacity <= 0)
        throw new IllegalArgumentException();
    this.items = new Object[capacity];
    lock = new ReentrantLock(fair);
    notEmpty = lock.newCondition();
    notFull =  lock.newCondition();
}
```
说明：
 01) items是保存“阻塞队列”数据的数组。它的定义如下：
```
final Object[] items;
```
 02) fair是“可重入的独占锁(ReentrantLock)”的类型。fair为true，表示是公平锁；fair为false，表示是非公平锁。
notEmpty和notFull是锁的两个Condition条件。它们的定义如下：
```
final ReentrantLock lock;
private final Condition notEmpty;
private final Condition notFull;
```
简单对Condition和Lock的用法进行说明，更多内容请参考“Java多线程系列--“JUC锁”06之 Condition条件”。
Lock的作用是提供独占锁机制，来保护竞争资源；而Condition是为了更加精细的对锁进行控制，它依赖于Lock，通过某个条件对多线程进行控制。
notEmpty表示“锁的非空条件”。当某线程想从队列中取数据时，而此时又没有数据，则该线程通过notEmpty.await()进行等待；当其它线程向队列中插入了元素之后，就调用notEmpty.signal()唤醒“之前通过notEmpty.await()进入等待状态的线程”。
同理，notFull表示“锁的满条件”。当某线程想向队列中插入元素，而此时队列已满时，该线程等待；当其它线程从队列中取出元素之后，就唤醒该等待的线程。

 

2. 添加

下面以offer(E e)为例，对ArrayBlockingQueue的添加方法进行说明。

```
public boolean offer(E e) {
    // 创建插入的元素是否为null，是的话抛出NullPointerException异常
    checkNotNull(e);
    // 获取“该阻塞队列的独占锁”
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        // 如果队列已满，则返回false。
        if (count == items.length)
            return false;
        else {
        // 如果队列未满，则插入e，并返回true。
            insert(e);
            return true;
        }
    } finally {
        // 释放锁
        lock.unlock();
    }
}
```
说明：offer(E e)的作用是将e插入阻塞队列的尾部。如果队列已满，则返回false，表示插入失败；
否则，插入元素，并返回true。
 1) count表示”队列中的元素个数“。除此之外，队列中还有另外两个遍历takeIndex和putIndex。
 takeIndex表示下一个被取出元素的索引，putIndex表示下一个被添加元素的索引。它们的定义如下：
```
// 队列中的元素个数
int takeIndex;
// 下一个被取出元素的索引
int putIndex;
// 下一个被添加元素的索引
int count;
```
 2) insert()的源码如下：

```
private void insert(E x) {
    // 将x添加到”队列“中
    items[putIndex] = x;
    // 设置”下一个被取出元素的索引“
    putIndex = inc(putIndex);
    // 将”队列中的元素个数”+1
    ++count;
    // 唤醒notEmpty上的等待线程
    notEmpty.signal();
}
```
insert()在插入元素之后，会唤醒notEmpty上面的等待线程。

inc()的源码如下：
```
final int inc(int i) {
    return (++i == items.length) ? 0 : i;
}
```
若i+1的值等于“队列的长度”，即添加元素之后，队列满；则设置“下一个被添加元素的索引”为0。

 

3. 取出

下面以take()为例，对ArrayBlockingQueue的取出方法进行说明。

```
public E take() throws InterruptedException {
    // 获取“队列的独占锁”
    final ReentrantLock lock = this.lock;
    // 获取“锁”，若当前线程是中断状态，则抛出InterruptedException异常
    lock.lockInterruptibly();
    try {
        // 若“队列为空”，则一直等待。
        while (count == 0)
            notEmpty.await();
        // 取出元素
        return extract();
    } finally {
        // 释放“锁”
        lock.unlock();
    }
}
```
说明：take()的作用是取出并返回队列的头。若队列为空，则一直等待。

extract()的源码如下：

```
private E extract() {
    final Object[] items = this.items;
    // 强制将元素转换为“泛型E”
    E x = this.<E>cast(items[takeIndex]);
    // 将第takeIndex元素设为null，即删除。同时，帮助GC回收。
    items[takeIndex] = null;
    // 设置“下一个被取出元素的索引”
    takeIndex = inc(takeIndex);
    // 将“队列中元素数量”-1
    --count;
    // 唤醒notFull上的等待线程。
    notFull.signal();
    return x;
}
```
说明：extract()在删除元素之后，会唤醒notFull上的等待线程。

 

4. 遍历

下面对ArrayBlockingQueue的遍历方法进行说明。
```
public Iterator<E> iterator() {
    return new Itr();
}
 ```

Itr是实现了Iterator接口的类，它的源码如下：

```
private class Itr implements Iterator<E> {
    // 队列中剩余元素的个数
    private int remaining; // Number of elements yet to be returned
    // 下一次调用next()返回的元素的索引
    private int nextIndex; // Index of element to be returned by next
    // 下一次调用next()返回的元素
    private E nextItem;    // Element to be returned by next call to next
    // 上一次调用next()返回的元素
    private E lastItem;    // Element returned by last call to next
    // 上一次调用next()返回的元素的索引
    private int lastRet;   // Index of last element returned, or -1 if none

    Itr() {
        // 获取“阻塞队列”的锁
        final ReentrantLock lock = ArrayBlockingQueue.this.lock;
        lock.lock();
        try {
            lastRet = -1;
            if ((remaining = count) > 0)
                nextItem = itemAt(nextIndex = takeIndex);
        } finally {
            // 释放“锁”
            lock.unlock();
        }
    }

    public boolean hasNext() {
        return remaining > 0;
    }

    public E next() {
        // 获取“阻塞队列”的锁
        final ReentrantLock lock = ArrayBlockingQueue.this.lock;
        lock.lock();
        try {
            // 若“剩余元素<=0”，则抛出异常。
            if (remaining <= 0)
                throw new NoSuchElementException();
            lastRet = nextIndex;
            // 获取第nextIndex位置的元素
            E x = itemAt(nextIndex);  // check for fresher value
            if (x == null) {
                x = nextItem;         // we are forced to report old value
                lastItem = null;      // but ensure remove fails
            }
            else
                lastItem = x;
            while (--remaining > 0 && // skip over nulls
                   (nextItem = itemAt(nextIndex = inc(nextIndex))) == null)
                ;
            return x;
        } finally {
            lock.unlock();
        }
    }

    public void remove() {
        final ReentrantLock lock = ArrayBlockingQueue.this.lock;
        lock.lock();
        try {
            int i = lastRet;
            if (i == -1)
                throw new IllegalStateException();
            lastRet = -1;
            E x = lastItem;
            lastItem = null;
            // only remove if item still at index
            if (x != null && x == items[i]) {
                boolean removingHead = (i == takeIndex);
                removeAt(i);
                if (!removingHead)
                    nextIndex = dec(nextIndex);
            }
        } finally {
            lock.unlock();
        }
    }
}
```
 

>## ArrayBlockingQueue示例
```
import java.util.*;
import java.util.concurrent.*;

/*
 *   ArrayBlockingQueue是“线程安全”的队列，而LinkedList是非线程安全的。
 *
 *   下面是“多个线程同时操作并且遍历queue”的示例
 *   (01) 当queue是ArrayBlockingQueue对象时，程序能正常运行。
 *   (02) 当queue是LinkedList对象时，程序会产生ConcurrentModificationException异常。
 *
 * @author skywang
 */
public class ArrayBlockingQueueDemo1{

    // TODO: queue是LinkedList对象时，程序会出错。
    //private static Queue<String> queue = new LinkedList<String>();
    private static Queue<String> queue = new ArrayBlockingQueue<String>(20);
    public static void main(String[] args) {
    
        // 同时启动两个线程对queue进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value;
        Iterator iter = queue.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
                int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+i;
                queue.add(val);
                // 通过“Iterator”遍历queue。
                printAll();
            }
        }
    }
}
```
(某一次)运行结果：

```
ta1, ta1, 
tb1, ta1, 
tb1, ta1, ta2, 
tb1, ta1, ta2, tb1, tb2, 
ta2, ta1, tb2, tb1, ta3, 
ta2, ta1, tb2, tb1, ta3, ta2, tb3, 
tb2, ta1, ta3, tb1, tb3, ta2, ta4, 
tb2, ta1, ta3, tb1, tb3, ta2, ta4, tb2, tb4, 
ta3, ta1, tb3, tb1, ta4, ta2, tb4, tb2, ta5, 
ta3, ta1, tb3, tb1, ta4, ta2, tb4, tb2, ta5, ta3, tb5, 
tb3, ta1, ta4, tb1, tb4, ta2, ta5, tb2, tb5, ta3, ta6, 
tb3, ta4, tb4, ta5, tb5, ta6, tb6, 
```
结果说明：    
如果将源码中的queue改成LinkedList对象时，程序会产生ConcurrentModificationException异常。
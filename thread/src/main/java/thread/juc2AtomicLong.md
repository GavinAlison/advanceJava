# Java多线程系列--“JUC原子类”02之 AtomicLong原子类

>## 概要
AtomicInteger, AtomicLong和AtomicBoolean这3个基本类型的原子类的原理和用法相似。本章以AtomicLong对基本类型的原子类进行介绍。内容包括：
-   AtomicLong介绍和函数列表
-   AtomicLong源码分析(基于JDK1.7.0_40)
-   AtomicLong示例

转载请注明出处：http://www.cnblogs.com/skywang12345/p/3514593.html

 

>## AtomicLong介绍和函数列表
AtomicLong是作用是对长整形进行原子操作。    
在32位操作系统中，64位的long 和 double 变量由于会被JVM当作两个分离的32位来进行操作，所以不具有原子性。
而使用AtomicLong能让long的操作保持原子型。

**AtomicLong函数列表**

```
// 构造函数
AtomicLong()
// 创建值为initialValue的AtomicLong对象
AtomicLong(long initialValue)
// 以原子方式设置当前值为newValue。
final void set(long newValue) 
// 获取当前值
final long get() 
// 以原子方式将当前值减 1，并返回减1后的值。等价于“--num”
final long decrementAndGet() 
// 以原子方式将当前值减 1，并返回减1前的值。等价于“num--”
final long getAndDecrement() 
// 以原子方式将当前值加 1，并返回加1后的值。等价于“++num”
final long incrementAndGet() 
// 以原子方式将当前值加 1，并返回加1前的值。等价于“num++”
final long getAndIncrement()    
// 以原子方式将delta与当前值相加，并返回相加后的值。
final long addAndGet(long delta) 
// 以原子方式将delta添加到当前值，并返回相加前的值。
final long getAndAdd(long delta) 
// 如果当前值 == expect，则以原子方式将该值设置为update。成功返回true，否则返回false，并且不修改原值。
final boolean compareAndSet(long expect, long update)
// 以原子方式设置当前值为newValue，并返回旧值。
final long getAndSet(long newValue)
// 返回当前值对应的int值
int intValue() 
// 获取当前值对应的long值
long longValue()    
// 以 float 形式返回当前值
float floatValue()    
// 以 double 形式返回当前值
double doubleValue()    
// 最后设置为给定值。延时设置变量值，这个等价于set()方法，但是由于字段是volatile类型的，因此次字段的修改会比普通字段（非volatile字段）有稍微的性能延时（尽管可以忽略），所以如果不是想立即读取设置的新值，允许在“后台”修改值，那么此方法就很有用。如果还是难以理解，这里就类似于启动一个后台线程如执行修改新值的任务，原线程就不等待修改结果立即返回（这种解释其实是不正确的，但是可以这么理解）。
final void lazySet(long newValue)
// 如果当前值 == 预期值，则以原子方式将该设置为给定的更新值。JSR规范中说：以原子方式读取和有条件地写入变量但不 创建任何 happen-before 排序，因此不提供与除 weakCompareAndSet 目标外任何变量以前或后续读取或写入操作有关的任何保证。大意就是说调用weakCompareAndSet时并不能保证不存在happen-before的发生（也就是可能存在指令重排序导致此操作失败）。但是从Java源码来看，其实此方法并没有实现JSR规范的要求，最后效果和compareAndSet是等效的，都调用了unsafe.compareAndSwapInt()完成操作。
final boolean weakCompareAndSet(long expect, long update)
```
 

AtomicLong源码分析(基于JDK1.7.0_40)
AtomicLong的完整源码

```$xslt
/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package java.util.concurrent.atomic;
import sun.misc.Unsafe;

/**
 * A {@code long} value that may be updated atomically.  See the
 * {@link java.util.concurrent.atomic} package specification for
 * description of the properties of atomic variables. An
 * {@code AtomicLong} is used in applications such as atomically
 * incremented sequence numbers, and cannot be used as a replacement
 * for a {@link java.lang.Long}. However, this class does extend
 * {@code Number} to allow uniform access by tools and utilities that
 * deal with numerically-based classes.
 *
 * @since 1.5
 * @author Doug Lea
 */
public class AtomicLong extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 1927816293512124184L;

    // setup to use Unsafe.compareAndSwapLong for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    /**
     * Records whether the underlying JVM supports lockless
     * compareAndSwap for longs. While the Unsafe.compareAndSwapLong
     * method works in either case, some constructions should be
     * handled at Java level to avoid locking user-visible locks.
     */
    static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();

    /**
     * Returns whether underlying JVM supports lockless CompareAndSet
     * for longs. Called only once and cached in VM_SUPPORTS_LONG_CAS.
     */
    private static native boolean VMSupportsCS8();

    static {
      try {
        valueOffset = unsafe.objectFieldOffset
            (AtomicLong.class.getDeclaredField("value"));
      } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile long value;

    /**
     * Creates a new AtomicLong with the given initial value.
     *
     * @param initialValue the initial value
     */
    public AtomicLong(long initialValue) {
        value = initialValue;
    }

    /**
     * Creates a new AtomicLong with initial value {@code 0}.
     */
    public AtomicLong() {
    }

    /**
     * Gets the current value.
     *
     * @return the current value
     */
    public final long get() {
        return value;
    }

    /**
     * Sets to the given value.
     *
     * @param newValue the new value
     */
    public final void set(long newValue) {
        value = newValue;
    }

    /**
     * Eventually sets to the given value.
     *
     * @param newValue the new value
     * @since 1.6
     */
    public final void lazySet(long newValue) {
        unsafe.putOrderedLong(this, valueOffset, newValue);
    }

    /**
     * Atomically sets to the given value and returns the old value.
     *
     * @param newValue the new value
     * @return the previous value
     */
    public final long getAndSet(long newValue) {
        while (true) {
            long current = get();
            if (compareAndSet(current, newValue))
                return current;
        }
    }

    /**
     * Atomically sets the value to the given updated value
     * if the current value {@code ==} the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful. False return indicates that
     * the actual value was not equal to the expected value.
     */
    public final boolean compareAndSet(long expect, long update) {
        return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    }

    /**
     * Atomically sets the value to the given updated value
     * if the current value {@code ==} the expected value.
     *
     * <p>May <a href="package-summary.html#Spurious">fail spuriously</a>
     * and does not provide ordering guarantees, so is only rarely an
     * appropriate alternative to {@code compareAndSet}.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful.
     */
    public final boolean weakCompareAndSet(long expect, long update) {
        return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    }

    /**
     * Atomically increments by one the current value.
     *
     * @return the previous value
     */
    public final long getAndIncrement() {
        while (true) {
            long current = get();
            long next = current + 1;
            if (compareAndSet(current, next))
                return current;
        }
    }

    /**
     * Atomically decrements by one the current value.
     *
     * @return the previous value
     */
    public final long getAndDecrement() {
        while (true) {
            long current = get();
            long next = current - 1;
            if (compareAndSet(current, next))
                return current;
        }
    }

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the previous value
     */
    public final long getAndAdd(long delta) {
        while (true) {
            long current = get();
            long next = current + delta;
            if (compareAndSet(current, next))
                return current;
        }
    }

    /**
     * Atomically increments by one the current value.
     *
     * @return the updated value
     */
    public final long incrementAndGet() {
        for (;;) {
            long current = get();
            long next = current + 1;
            if (compareAndSet(current, next))
                return next;
        }
    }

    /**
     * Atomically decrements by one the current value.
     *
     * @return the updated value
     */
    public final long decrementAndGet() {
        for (;;) {
            long current = get();
            long next = current - 1;
            if (compareAndSet(current, next))
                return next;
        }
    }

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the updated value
     */
    public final long addAndGet(long delta) {
        for (;;) {
            long current = get();
            long next = current + delta;
            if (compareAndSet(current, next))
                return next;
        }
    }

    /**
     * Returns the String representation of the current value.
     * @return the String representation of the current value.
     */
    public String toString() {
        return Long.toString(get());
    }


    public int intValue() {
        return (int)get();
    }

    public long longValue() {
        return get();
    }

    public float floatValue() {
        return (float)get();
    }

    public double doubleValue() {
        return (double)get();
    }

}

```
 

AtomicLong的代码很简单，下面仅以incrementAndGet()为例，对AtomicLong的原理进行说明。
incrementAndGet()源码如下：

```
public final long incrementAndGet() {
    for (;;) {
        // 获取AtomicLong当前对应的long值
        long current = get();
        // 将current加1
        long next = current + 1;
        // 通过CAS函数，更新current的值
        if (compareAndSet(current, next))
            return next;
    }
}
```
jdk1.8 与jdk1.7 ,  AtomicLong的源码 不同`
```$xslt
/**
 * Atomically increments by one the current value.
 *
 * @return the updated value
 */
public final long incrementAndGet() {
    return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
}
这个是Unsafe类对其提供原子操作
```



说明：
 01) incrementAndGet()首先会根据get()获取AtomicLong对应的long值。该值是volatile类型的变量，get()的源码如下：
```
// value是AtomicLong对应的long值
private volatile long value;
// 返回AtomicLong对应的long值
public final long get() {
    return value;
}
```
 02) incrementAndGet()接着将current加1,然后通过CAS函数，将新的值赋值给value。
compareAndSet()的源码如下：
```
public final boolean compareAndSet(long expect, long update) {
    return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
}
```
compareAndSet()的作用是更新AtomicLong对应的long值。它会比较AtomicLong的原始值是否与expect相等，若相等的话，则设置AtomicLong的值为update。

AtomicLong示例
```
 // LongTest.java的源码
 import java.util.concurrent.atomic.AtomicLong;
 
 public class LongTest {
     
     public static void main(String[] args){
 
         // 新建AtomicLong对象
         AtomicLong mAtoLong = new AtomicLong();
 
         mAtoLong.set(0x0123456789ABCDEFL);
         System.out.printf("%20s : 0x%016X\n", "get()", mAtoLong.get());
         System.out.printf("%20s : 0x%016X\n", "intValue()", mAtoLong.intValue());
         System.out.printf("%20s : 0x%016X\n", "longValue()", mAtoLong.longValue());
         System.out.printf("%20s : %s\n", "doubleValue()", mAtoLong.doubleValue());
         System.out.printf("%20s : %s\n", "floatValue()", mAtoLong.floatValue());
 
         System.out.printf("%20s : 0x%016X\n", "getAndDecrement()", mAtoLong.getAndDecrement());
         System.out.printf("%20s : 0x%016X\n", "decrementAndGet()", mAtoLong.decrementAndGet());
         System.out.printf("%20s : 0x%016X\n", "getAndIncrement()", mAtoLong.getAndIncrement());
         System.out.printf("%20s : 0x%016X\n", "incrementAndGet()", mAtoLong.incrementAndGet());
 
         System.out.printf("%20s : 0x%016X\n", "addAndGet(0x10)", mAtoLong.addAndGet(0x10));
         System.out.printf("%20s : 0x%016X\n", "getAndAdd(0x10)", mAtoLong.getAndAdd(0x10));
 
         System.out.printf("\n%20s : 0x%016X\n", "get()", mAtoLong.get());
 
         System.out.printf("%20s : %s\n", "compareAndSet()", mAtoLong.compareAndSet(0x12345679L, 0xFEDCBA9876543210L));
         System.out.printf("%20s : 0x%016X\n", "get()", mAtoLong.get());
     }
 }
```
运行结果：

```
               get() : 0x0123456789ABCDEF
          intValue() : 0x0000000089ABCDEF
         longValue() : 0x0123456789ABCDEF
       doubleValue() : 8.1985529216486896E16
        floatValue() : 8.1985531E16
   getAndDecrement() : 0x0123456789ABCDEF
   decrementAndGet() : 0x0123456789ABCDED
   getAndIncrement() : 0x0123456789ABCDED
   incrementAndGet() : 0x0123456789ABCDEF
     addAndGet(0x10) : 0x0123456789ABCDFF
     getAndAdd(0x10) : 0x0123456789ABCDFF

               get() : 0x0123456789ABCE0F
     compareAndSet() : false
               get() : 0x0123456789ABCE0F
```
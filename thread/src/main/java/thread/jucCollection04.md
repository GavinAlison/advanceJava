# Java多线程系列--“JUC集合”04之 ConcurrentHashMap

>## 概要
本章是JUC系列的ConcurrentHashMap篇。内容包括：
-   ConcurrentHashMap介绍
-   ConcurrentHashMap原理和数据结构
-   ConcurrentHashMap函数列表
-   ConcurrentHashMap源码分析(JDK1.7.0_40版本)
-   ConcurrentHashMap示例

转载请注明出处：http://www.cnblogs.com/skywang12345/p/3498537.html


>## ConcurrentHashMap介绍
ConcurrentHashMap是线程安全的哈希表。HashMap, Hashtable, ConcurrentHashMap之间的关联如下：
-   HashMap是非线程安全的哈希表，常用于单线程程序中。
-   Hashtable是线程安全的哈希表，它是通过synchronized来保证线程安全的；即，多线程通过同一个“对象的同步锁”来实现并发控制。Hashtable在线程竞争激烈时，效率比较低(此时建议使用ConcurrentHashMap)！因为当一个线程访问Hashtable的同步方法时，其它线程就访问Hashtable的同步方法时，可能会进入阻塞状态。
-   ConcurrentHashMap是线程安全的哈希表，它是通过“锁分段”来保证线程安全的。ConcurrentHashMap将哈希表分成许多片段(Segment)，每一个片段除了保存哈希表之外，本质上也是一个“可重入的互斥锁”(ReentrantLock)。多线程对同一个片段的访问，是互斥的；但是，对于不同片段的访问，却是可以同步进行的。


关于HashMap,Hashtable以及ReentrantLock的更多内容，可以参考：
1. [Java 集合系列10之 HashMap详细介绍(源码解析)和使用示例](http://www.cnblogs.com/skywang12345/p/3310835.html)
2. [Java 集合系列11之 Hashtable详细介绍(源码解析)和使用示例](http://www.cnblogs.com/skywang12345/p/3310887.html)
3. [Java多线程系列--“JUC锁”02之 互斥锁ReentrantLock](http://www.cnblogs.com/skywang12345/p/3496101.html)

>## ConcurrentHashMap原理和数据结构
要想搞清ConcurrentHashMap，必须先弄清楚它的数据结构：

![collection06.png](test/resource/collection06.png)

 01) ConcurrentHashMap继承于AbstractMap抽象类。
 02) Segment是ConcurrentHashMap中的内部类，它就是ConcurrentHashMap中的“锁分段”对应的存储结构。ConcurrentHashMap与Segment是组合关系，1个ConcurrentHashMap对象包含若干个Segment对象。在代码中，这表现为ConcurrentHashMap类中存在“Segment数组”成员。
 03) Segment类继承于ReentrantLock类，所以Segment本质上是一个可重入的互斥锁。
 04) HashEntry也是ConcurrentHashMap的内部类，是单向链表节点，存储着key-value键值对。Segment与HashEntry是组合关系，Segment类中存在“HashEntry数组”成员，“HashEntry数组”中的每个HashEntry就是一个单向链表。

对于多线程访问对一个“哈希表对象”竞争资源，Hashtable是通过一把锁来控制并发；而ConcurrentHashMap则是将哈希表分成许多片段，对于每一个片段分别通过一个互斥锁来控制并发。ConcurrentHashMap对并发的控制更加细腻，它也更加适应于高并发场景！

>## ConcurrentHashMap函数列表
```
// 创建一个带有默认初始容量 (16)、加载因子 (0.75) 和 concurrencyLevel (16) 的新的空映射。
ConcurrentHashMap()
// 创建一个带有指定初始容量、默认加载因子 (0.75) 和 concurrencyLevel (16) 的新的空映射。
ConcurrentHashMap(int initialCapacity)
// 创建一个带有指定初始容量、加载因子和默认 concurrencyLevel (16) 的新的空映射。
ConcurrentHashMap(int initialCapacity, float loadFactor)
// 创建一个带有指定初始容量、加载因子和并发级别的新的空映射。
ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel)
// 构造一个与给定映射具有相同映射关系的新映射。
ConcurrentHashMap(Map<? extends K,? extends V> m)

// 从该映射中移除所有映射关系
void clear()
// 一种遗留方法，测试此表中是否有一些与指定值存在映射关系的键。
boolean contains(Object value)
// 测试指定对象是否为此表中的键。
boolean containsKey(Object key)
// 如果此映射将一个或多个键映射到指定值，则返回 true。
boolean containsValue(Object value)
// 返回此表中值的枚举。
Enumeration<V> elements()
// 返回此映射所包含的映射关系的 Set 视图。
Set<Map.Entry<K,V>> entrySet()
// 返回指定键所映射到的值，如果此映射不包含该键的映射关系，则返回 null。
V get(Object key)
// 如果此映射不包含键-值映射关系，则返回 true。
boolean isEmpty()
// 返回此表中键的枚举。
Enumeration<K> keys()
// 返回此映射中包含的键的 Set 视图。
Set<K> keySet()
// 将指定键映射到此表中的指定值。
V put(K key, V value)
// 将指定映射中所有映射关系复制到此映射中。
void putAll(Map<? extends K,? extends V> m)
// 如果指定键已经不再与某个值相关联，则将它与给定值关联。
V putIfAbsent(K key, V value)
// 从此映射中移除键（及其相应的值）。
V remove(Object key)
// 只有目前将键的条目映射到给定值时，才移除该键的条目。
boolean remove(Object key, Object value)
// 只有目前将键的条目映射到某一值时，才替换该键的条目。
V replace(K key, V value)
// 只有目前将键的条目映射到给定值时，才替换该键的条目。
boolean replace(K key, V oldValue, V newValue)
// 返回此映射中的键-值映射关系数。
int size()
// 返回此映射中包含的值的 Collection 视图。
Collection<V> values()
```
 

>## ConcurrentHashMap源码分析(JDK1.7.0_40版本)
ConcurrentHashMap.java的完整源码如下：

```$xslt
/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent;
import java.util.concurrent.locks.*;
import java.util.*;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

/**
 * A hash table supporting full concurrency of retrievals and
 * adjustable expected concurrency for updates. This class obeys the
 * same functional specification as {@link java.util.Hashtable}, and
 * includes versions of methods corresponding to each method of
 * <tt>Hashtable</tt>. However, even though all operations are
 * thread-safe, retrieval operations do <em>not</em> entail locking,
 * and there is <em>not</em> any support for locking the entire table
 * in a way that prevents all access.  This class is fully
 * interoperable with <tt>Hashtable</tt> in programs that rely on its
 * thread safety but not on its synchronization details.
 *
 * <p> Retrieval operations (including <tt>get</tt>) generally do not
 * block, so may overlap with update operations (including
 * <tt>put</tt> and <tt>remove</tt>). Retrievals reflect the results
 * of the most recently <em>completed</em> update operations holding
 * upon their onset.  For aggregate operations such as <tt>putAll</tt>
 * and <tt>clear</tt>, concurrent retrievals may reflect insertion or
 * removal of only some entries.  Similarly, Iterators and
 * Enumerations return elements reflecting the state of the hash table
 * at some point at or since the creation of the iterator/enumeration.
 * They do <em>not</em> throw {@link ConcurrentModificationException}.
 * However, iterators are designed to be used by only one thread at a time.
 *
 * <p> The allowed concurrency among update operations is guided by
 * the optional <tt>concurrencyLevel</tt> constructor argument
 * (default <tt>16</tt>), which is used as a hint for internal sizing.  The
 * table is internally partitioned to try to permit the indicated
 * number of concurrent updates without contention. Because placement
 * in hash tables is essentially random, the actual concurrency will
 * vary.  Ideally, you should choose a value to accommodate as many
 * threads as will ever concurrently modify the table. Using a
 * significantly higher value than you need can waste space and time,
 * and a significantly lower value can lead to thread contention. But
 * overestimates and underestimates within an order of magnitude do
 * not usually have much noticeable impact. A value of one is
 * appropriate when it is known that only one thread will modify and
 * all others will only read. Also, resizing this or any other kind of
 * hash table is a relatively slow operation, so, when possible, it is
 * a good idea to provide estimates of expected table sizes in
 * constructors.
 *
 * <p>This class and its views and iterators implement all of the
 * <em>optional</em> methods of the {@link Map} and {@link Iterator}
 * interfaces.
 *
 * <p> Like {@link Hashtable} but unlike {@link HashMap}, this class
 * does <em>not</em> allow <tt>null</tt> to be used as a key or value.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.5
 * @author Doug Lea
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V>
        implements ConcurrentMap<K, V>, Serializable {
    private static final long serialVersionUID = 7249069246763182397L;

    /*
     * The basic strategy is to subdivide the table among Segments,
     * each of which itself is a concurrently readable hash table.  To
     * reduce footprint, all but one segments are constructed only
     * when first needed (see ensureSegment). To maintain visibility
     * in the presence of lazy construction, accesses to segments as
     * well as elements of segment's table must use volatile access,
     * which is done via Unsafe within methods segmentAt etc
     * below. These provide the functionality of AtomicReferenceArrays
     * but reduce the levels of indirection. Additionally,
     * volatile-writes of table elements and entry "next" fields
     * within locked operations use the cheaper "lazySet" forms of
     * writes (via putOrderedObject) because these writes are always
     * followed by lock releases that maintain sequential consistency
     * of table updates.
     *
     * Historical note: The previous version of this class relied
     * heavily on "final" fields, which avoided some volatile reads at
     * the expense of a large initial footprint.  Some remnants of
     * that design (including forced construction of segment 0) exist
     * to ensure serialization compatibility.
     */

    /* ---------------- Constants -------------- */

    /**
     * The default initial capacity for this table,
     * used when not otherwise specified in a constructor.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The default load factor for this table, used when not
     * otherwise specified in a constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The default concurrency level for this table, used when not
     * otherwise specified in a constructor.
     */
    static final int DEFAULT_CONCURRENCY_LEVEL = 16;

    /**
     * The maximum capacity, used if a higher value is implicitly
     * specified by either of the constructors with arguments.  MUST
     * be a power of two <= 1<<30 to ensure that entries are indexable
     * using ints.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The minimum capacity for per-segment tables.  Must be a power
     * of two, at least two to avoid immediate resizing on next use
     * after lazy construction.
     */
    static final int MIN_SEGMENT_TABLE_CAPACITY = 2;

    /**
     * The maximum number of segments to allow; used to bound
     * constructor arguments. Must be power of two less than 1 << 24.
     */
    static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

    /**
     * Number of unsynchronized retries in size and containsValue
     * methods before resorting to locking. This is used to avoid
     * unbounded retries if tables undergo continuous modification
     * which would make it impossible to obtain an accurate result.
     */
    static final int RETRIES_BEFORE_LOCK = 2;

    /* ---------------- Fields -------------- */

    /**
     * holds values which can't be initialized until after VM is booted.
     */
    private static class Holder {

        /**
        * Enable alternative hashing of String keys?
        *
        * <p>Unlike the other hash map implementations we do not implement a
        * threshold for regulating whether alternative hashing is used for
        * String keys. Alternative hashing is either enabled for all instances
        * or disabled for all instances.
        */
        static final boolean ALTERNATIVE_HASHING;

        static {
            // Use the "threshold" system property even though our threshold
            // behaviour is "ON" or "OFF".
            String altThreshold = java.security.AccessController.doPrivileged(
                new sun.security.action.GetPropertyAction(
                    "jdk.map.althashing.threshold"));

            int threshold;
            try {
                threshold = (null != altThreshold)
                        ? Integer.parseInt(altThreshold)
                        : Integer.MAX_VALUE;

                // disable alternative hashing if -1
                if (threshold == -1) {
                    threshold = Integer.MAX_VALUE;
                }

                if (threshold < 0) {
                    throw new IllegalArgumentException("value must be positive integer.");
                }
            } catch(IllegalArgumentException failed) {
                throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
            }
            ALTERNATIVE_HASHING = threshold <= MAXIMUM_CAPACITY;
        }
    }

    /**
     * A randomizing value associated with this instance that is applied to
     * hash code of keys to make hash collisions harder to find.
     */
    private transient final int hashSeed = randomHashSeed(this);

    private static int randomHashSeed(ConcurrentHashMap instance) {
        if (sun.misc.VM.isBooted() && Holder.ALTERNATIVE_HASHING) {
            return sun.misc.Hashing.randomHashSeed(instance);
        }

        return 0;
    }

    /**
     * Mask value for indexing into segments. The upper bits of a
     * key's hash code are used to choose the segment.
     */
    final int segmentMask;

    /**
     * Shift value for indexing within segments.
     */
    final int segmentShift;

    /**
     * The segments, each of which is a specialized hash table.
     */
    final Segment<K,V>[] segments;

    transient Set<K> keySet;
    transient Set<Map.Entry<K,V>> entrySet;
    transient Collection<V> values;

    /**
     * ConcurrentHashMap list entry. Note that this is never exported
     * out as a user-visible Map.Entry.
     */
    static final class HashEntry<K,V> {
        final int hash;
        final K key;
        volatile V value;
        volatile HashEntry<K,V> next;

        HashEntry(int hash, K key, V value, HashEntry<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Sets next field with volatile write semantics.  (See above
         * about use of putOrderedObject.)
         */
        final void setNext(HashEntry<K,V> n) {
            UNSAFE.putOrderedObject(this, nextOffset, n);
        }

        // Unsafe mechanics
        static final sun.misc.Unsafe UNSAFE;
        static final long nextOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class k = HashEntry.class;
                nextOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    /**
     * Gets the ith element of given table (if nonnull) with volatile
     * read semantics. Note: This is manually integrated into a few
     * performance-sensitive methods to reduce call overhead.
     */
    @SuppressWarnings("unchecked")
    static final <K,V> HashEntry<K,V> entryAt(HashEntry<K,V>[] tab, int i) {
        return (tab == null) ? null :
            (HashEntry<K,V>) UNSAFE.getObjectVolatile
            (tab, ((long)i << TSHIFT) + TBASE);
    }

    /**
     * Sets the ith element of given table, with volatile write
     * semantics. (See above about use of putOrderedObject.)
     */
    static final <K,V> void setEntryAt(HashEntry<K,V>[] tab, int i,
                                       HashEntry<K,V> e) {
        UNSAFE.putOrderedObject(tab, ((long)i << TSHIFT) + TBASE, e);
    }

    /**
     * Applies a supplemental hash function to a given hashCode, which
     * defends against poor quality hash functions.  This is critical
     * because ConcurrentHashMap uses power-of-two length hash tables,
     * that otherwise encounter collisions for hashCodes that do not
     * differ in lower or upper bits.
     */
    private int hash(Object k) {
        int h = hashSeed;

        if ((0 != h) && (k instanceof String)) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }

    /**
     * Segments are specialized versions of hash tables.  This
     * subclasses from ReentrantLock opportunistically, just to
     * simplify some locking and avoid separate construction.
     */
    static final class Segment<K,V> extends ReentrantLock implements Serializable {
        /*
         * Segments maintain a table of entry lists that are always
         * kept in a consistent state, so can be read (via volatile
         * reads of segments and tables) without locking.  This
         * requires replicating nodes when necessary during table
         * resizing, so the old lists can be traversed by readers
         * still using old version of table.
         *
         * This class defines only mutative methods requiring locking.
         * Except as noted, the methods of this class perform the
         * per-segment versions of ConcurrentHashMap methods.  (Other
         * methods are integrated directly into ConcurrentHashMap
         * methods.) These mutative methods use a form of controlled
         * spinning on contention via methods scanAndLock and
         * scanAndLockForPut. These intersperse tryLocks with
         * traversals to locate nodes.  The main benefit is to absorb
         * cache misses (which are very common for hash tables) while
         * obtaining locks so that traversal is faster once
         * acquired. We do not actually use the found nodes since they
         * must be re-acquired under lock anyway to ensure sequential
         * consistency of updates (and in any case may be undetectably
         * stale), but they will normally be much faster to re-locate.
         * Also, scanAndLockForPut speculatively creates a fresh node
         * to use in put if no node is found.
         */

        private static final long serialVersionUID = 2249069246763182397L;

        /**
         * The maximum number of times to tryLock in a prescan before
         * possibly blocking on acquire in preparation for a locked
         * segment operation. On multiprocessors, using a bounded
         * number of retries maintains cache acquired while locating
         * nodes.
         */
        static final int MAX_SCAN_RETRIES =
            Runtime.getRuntime().availableProcessors() > 1 ? 64 : 1;

        /**
         * The per-segment table. Elements are accessed via
         * entryAt/setEntryAt providing volatile semantics.
         */
        transient volatile HashEntry<K,V>[] table;

        /**
         * The number of elements. Accessed only either within locks
         * or among other volatile reads that maintain visibility.
         */
        transient int count;

        /**
         * The total number of mutative operations in this segment.
         * Even though this may overflows 32 bits, it provides
         * sufficient accuracy for stability checks in CHM isEmpty()
         * and size() methods.  Accessed only either within locks or
         * among other volatile reads that maintain visibility.
         */
        transient int modCount;

        /**
         * The table is rehashed when its size exceeds this threshold.
         * (The value of this field is always <tt>(int)(capacity *
         * loadFactor)</tt>.)
         */
        transient int threshold;

        /**
         * The load factor for the hash table.  Even though this value
         * is same for all segments, it is replicated to avoid needing
         * links to outer object.
         * @serial
         */
        final float loadFactor;

        Segment(float lf, int threshold, HashEntry<K,V>[] tab) {
            this.loadFactor = lf;
            this.threshold = threshold;
            this.table = tab;
        }

        final V put(K key, int hash, V value, boolean onlyIfAbsent) {
            HashEntry<K,V> node = tryLock() ? null :
                scanAndLockForPut(key, hash, value);
            V oldValue;
            try {
                HashEntry<K,V>[] tab = table;
                int index = (tab.length - 1) & hash;
                HashEntry<K,V> first = entryAt(tab, index);
                for (HashEntry<K,V> e = first;;) {
                    if (e != null) {
                        K k;
                        if ((k = e.key) == key ||
                            (e.hash == hash && key.equals(k))) {
                            oldValue = e.value;
                            if (!onlyIfAbsent) {
                                e.value = value;
                                ++modCount;
                            }
                            break;
                        }
                        e = e.next;
                    }
                    else {
                        if (node != null)
                            node.setNext(first);
                        else
                            node = new HashEntry<K,V>(hash, key, value, first);
                        int c = count + 1;
                        if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                            rehash(node);
                        else
                            setEntryAt(tab, index, node);
                        ++modCount;
                        count = c;
                        oldValue = null;
                        break;
                    }
                }
            } finally {
                unlock();
            }
            return oldValue;
        }

        /**
         * Doubles size of table and repacks entries, also adding the
         * given node to new table
         */
        @SuppressWarnings("unchecked")
        private void rehash(HashEntry<K,V> node) {
            /*
             * Reclassify nodes in each list to new table.  Because we
             * are using power-of-two expansion, the elements from
             * each bin must either stay at same index, or move with a
             * power of two offset. We eliminate unnecessary node
             * creation by catching cases where old nodes can be
             * reused because their next fields won't change.
             * Statistically, at the default threshold, only about
             * one-sixth of them need cloning when a table
             * doubles. The nodes they replace will be garbage
             * collectable as soon as they are no longer referenced by
             * any reader thread that may be in the midst of
             * concurrently traversing table. Entry accesses use plain
             * array indexing because they are followed by volatile
             * table write.
             */
            HashEntry<K,V>[] oldTable = table;
            int oldCapacity = oldTable.length;
            int newCapacity = oldCapacity << 1;
            threshold = (int)(newCapacity * loadFactor);
            HashEntry<K,V>[] newTable =
                (HashEntry<K,V>[]) new HashEntry[newCapacity];
            int sizeMask = newCapacity - 1;
            for (int i = 0; i < oldCapacity ; i++) {
                HashEntry<K,V> e = oldTable[i];
                if (e != null) {
                    HashEntry<K,V> next = e.next;
                    int idx = e.hash & sizeMask;
                    if (next == null)   //  Single node on list
                        newTable[idx] = e;
                    else { // Reuse consecutive sequence at same slot
                        HashEntry<K,V> lastRun = e;
                        int lastIdx = idx;
                        for (HashEntry<K,V> last = next;
                             last != null;
                             last = last.next) {
                            int k = last.hash & sizeMask;
                            if (k != lastIdx) {
                                lastIdx = k;
                                lastRun = last;
                            }
                        }
                        newTable[lastIdx] = lastRun;
                        // Clone remaining nodes
                        for (HashEntry<K,V> p = e; p != lastRun; p = p.next) {
                            V v = p.value;
                            int h = p.hash;
                            int k = h & sizeMask;
                            HashEntry<K,V> n = newTable[k];
                            newTable[k] = new HashEntry<K,V>(h, p.key, v, n);
                        }
                    }
                }
            }
            int nodeIndex = node.hash & sizeMask; // add the new node
            node.setNext(newTable[nodeIndex]);
            newTable[nodeIndex] = node;
            table = newTable;
        }

        /**
         * Scans for a node containing given key while trying to
         * acquire lock, creating and returning one if not found. Upon
         * return, guarantees that lock is held. UNlike in most
         * methods, calls to method equals are not screened: Since
         * traversal speed doesn't matter, we might as well help warm
         * up the associated code and accesses as well.
         *
         * @return a new node if key not found, else null
         */
        private HashEntry<K,V> scanAndLockForPut(K key, int hash, V value) {
            HashEntry<K,V> first = entryForHash(this, hash);
            HashEntry<K,V> e = first;
            HashEntry<K,V> node = null;
            int retries = -1; // negative while locating node
            while (!tryLock()) {
                HashEntry<K,V> f; // to recheck first below
                if (retries < 0) {
                    if (e == null) {
                        if (node == null) // speculatively create node
                            node = new HashEntry<K,V>(hash, key, value, null);
                        retries = 0;
                    }
                    else if (key.equals(e.key))
                        retries = 0;
                    else
                        e = e.next;
                }
                else if (++retries > MAX_SCAN_RETRIES) {
                    lock();
                    break;
                }
                else if ((retries & 1) == 0 &&
                         (f = entryForHash(this, hash)) != first) {
                    e = first = f; // re-traverse if entry changed
                    retries = -1;
                }
            }
            return node;
        }

        /**
         * Scans for a node containing the given key while trying to
         * acquire lock for a remove or replace operation. Upon
         * return, guarantees that lock is held.  Note that we must
         * lock even if the key is not found, to ensure sequential
         * consistency of updates.
         */
        private void scanAndLock(Object key, int hash) {
            // similar to but simpler than scanAndLockForPut
            HashEntry<K,V> first = entryForHash(this, hash);
            HashEntry<K,V> e = first;
            int retries = -1;
            while (!tryLock()) {
                HashEntry<K,V> f;
                if (retries < 0) {
                    if (e == null || key.equals(e.key))
                        retries = 0;
                    else
                        e = e.next;
                }
                else if (++retries > MAX_SCAN_RETRIES) {
                    lock();
                    break;
                }
                else if ((retries & 1) == 0 &&
                         (f = entryForHash(this, hash)) != first) {
                    e = first = f;
                    retries = -1;
                }
            }
        }

        /**
         * Remove; match on key only if value null, else match both.
         */
        final V remove(Object key, int hash, Object value) {
            if (!tryLock())
                scanAndLock(key, hash);
            V oldValue = null;
            try {
                HashEntry<K,V>[] tab = table;
                int index = (tab.length - 1) & hash;
                HashEntry<K,V> e = entryAt(tab, index);
                HashEntry<K,V> pred = null;
                while (e != null) {
                    K k;
                    HashEntry<K,V> next = e.next;
                    if ((k = e.key) == key ||
                        (e.hash == hash && key.equals(k))) {
                        V v = e.value;
                        if (value == null || value == v || value.equals(v)) {
                            if (pred == null)
                                setEntryAt(tab, index, next);
                            else
                                pred.setNext(next);
                            ++modCount;
                            --count;
                            oldValue = v;
                        }
                        break;
                    }
                    pred = e;
                    e = next;
                }
            } finally {
                unlock();
            }
            return oldValue;
        }

        final boolean replace(K key, int hash, V oldValue, V newValue) {
            if (!tryLock())
                scanAndLock(key, hash);
            boolean replaced = false;
            try {
                HashEntry<K,V> e;
                for (e = entryForHash(this, hash); e != null; e = e.next) {
                    K k;
                    if ((k = e.key) == key ||
                        (e.hash == hash && key.equals(k))) {
                        if (oldValue.equals(e.value)) {
                            e.value = newValue;
                            ++modCount;
                            replaced = true;
                        }
                        break;
                    }
                }
            } finally {
                unlock();
            }
            return replaced;
        }

        final V replace(K key, int hash, V value) {
            if (!tryLock())
                scanAndLock(key, hash);
            V oldValue = null;
            try {
                HashEntry<K,V> e;
                for (e = entryForHash(this, hash); e != null; e = e.next) {
                    K k;
                    if ((k = e.key) == key ||
                        (e.hash == hash && key.equals(k))) {
                        oldValue = e.value;
                        e.value = value;
                        ++modCount;
                        break;
                    }
                }
            } finally {
                unlock();
            }
            return oldValue;
        }

        final void clear() {
            lock();
            try {
                HashEntry<K,V>[] tab = table;
                for (int i = 0; i < tab.length ; i++)
                    setEntryAt(tab, i, null);
                ++modCount;
                count = 0;
            } finally {
                unlock();
            }
        }
    }

    // Accessing segments

    /**
     * Gets the jth element of given segment array (if nonnull) with
     * volatile element access semantics via Unsafe. (The null check
     * can trigger harmlessly only during deserialization.) Note:
     * because each element of segments array is set only once (using
     * fully ordered writes), some performance-sensitive methods rely
     * on this method only as a recheck upon null reads.
     */
    @SuppressWarnings("unchecked")
    static final <K,V> Segment<K,V> segmentAt(Segment<K,V>[] ss, int j) {
        long u = (j << SSHIFT) + SBASE;
        return ss == null ? null :
            (Segment<K,V>) UNSAFE.getObjectVolatile(ss, u);
    }

    /**
     * Returns the segment for the given index, creating it and
     * recording in segment table (via CAS) if not already present.
     *
     * @param k the index
     * @return the segment
     */
    @SuppressWarnings("unchecked")
    private Segment<K,V> ensureSegment(int k) {
        final Segment<K,V>[] ss = this.segments;
        long u = (k << SSHIFT) + SBASE; // raw offset
        Segment<K,V> seg;
        if ((seg = (Segment<K,V>)UNSAFE.getObjectVolatile(ss, u)) == null) {
            Segment<K,V> proto = ss[0]; // use segment 0 as prototype
            int cap = proto.table.length;
            float lf = proto.loadFactor;
            int threshold = (int)(cap * lf);
            HashEntry<K,V>[] tab = (HashEntry<K,V>[])new HashEntry[cap];
            if ((seg = (Segment<K,V>)UNSAFE.getObjectVolatile(ss, u))
                == null) { // recheck
                Segment<K,V> s = new Segment<K,V>(lf, threshold, tab);
                while ((seg = (Segment<K,V>)UNSAFE.getObjectVolatile(ss, u))
                       == null) {
                    if (UNSAFE.compareAndSwapObject(ss, u, null, seg = s))
                        break;
                }
            }
        }
        return seg;
    }

    // Hash-based segment and entry accesses

    /**
     * Get the segment for the given hash
     */
    @SuppressWarnings("unchecked")
    private Segment<K,V> segmentForHash(int h) {
        long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
        return (Segment<K,V>) UNSAFE.getObjectVolatile(segments, u);
    }

    /**
     * Gets the table entry for the given segment and hash
     */
    @SuppressWarnings("unchecked")
    static final <K,V> HashEntry<K,V> entryForHash(Segment<K,V> seg, int h) {
        HashEntry<K,V>[] tab;
        return (seg == null || (tab = seg.table) == null) ? null :
            (HashEntry<K,V>) UNSAFE.getObjectVolatile
            (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
    }

    /* ---------------- Public operations -------------- */

    /**
     * Creates a new, empty map with the specified initial
     * capacity, load factor and concurrency level.
     *
     * @param initialCapacity the initial capacity. The implementation
     * performs internal sizing to accommodate this many elements.
     * @param loadFactor  the load factor threshold, used to control resizing.
     * Resizing may be performed when the average number of elements per
     * bin exceeds this threshold.
     * @param concurrencyLevel the estimated number of concurrently
     * updating threads. The implementation performs internal sizing
     * to try to accommodate this many threads.
     * @throws IllegalArgumentException if the initial capacity is
     * negative or the load factor or concurrencyLevel are
     * nonpositive.
     */
    @SuppressWarnings("unchecked")
    public ConcurrentHashMap(int initialCapacity,
                             float loadFactor, int concurrencyLevel) {
        if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
            throw new IllegalArgumentException();
        if (concurrencyLevel > MAX_SEGMENTS)
            concurrencyLevel = MAX_SEGMENTS;
        // Find power-of-two sizes best matching arguments
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
        }
        this.segmentShift = 32 - sshift;
        this.segmentMask = ssize - 1;
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        int c = initialCapacity / ssize;
        if (c * ssize < initialCapacity)
            ++c;
        int cap = MIN_SEGMENT_TABLE_CAPACITY;
        while (cap < c)
            cap <<= 1;
        // create segments and segments[0]
        Segment<K,V> s0 =
            new Segment<K,V>(loadFactor, (int)(cap * loadFactor),
                             (HashEntry<K,V>[])new HashEntry[cap]);
        Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
        UNSAFE.putOrderedObject(ss, SBASE, s0); // ordered write of segments[0]
        this.segments = ss;
    }

    /**
     * Creates a new, empty map with the specified initial capacity
     * and load factor and with the default concurrencyLevel (16).
     *
     * @param initialCapacity The implementation performs internal
     * sizing to accommodate this many elements.
     * @param loadFactor  the load factor threshold, used to control resizing.
     * Resizing may be performed when the average number of elements per
     * bin exceeds this threshold.
     * @throws IllegalArgumentException if the initial capacity of
     * elements is negative or the load factor is nonpositive
     *
     * @since 1.6
     */
    public ConcurrentHashMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
    }

    /**
     * Creates a new, empty map with the specified initial capacity,
     * and with default load factor (0.75) and concurrencyLevel (16).
     *
     * @param initialCapacity the initial capacity. The implementation
     * performs internal sizing to accommodate this many elements.
     * @throws IllegalArgumentException if the initial capacity of
     * elements is negative.
     */
    public ConcurrentHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
    }

    /**
     * Creates a new, empty map with a default initial capacity (16),
     * load factor (0.75) and concurrencyLevel (16).
     */
    public ConcurrentHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
    }

    /**
     * Creates a new map with the same mappings as the given map.
     * The map is created with a capacity of 1.5 times the number
     * of mappings in the given map or 16 (whichever is greater),
     * and a default load factor (0.75) and concurrencyLevel (16).
     *
     * @param m the map
     */
    public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                      DEFAULT_INITIAL_CAPACITY),
             DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL);
        putAll(m);
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        /*
         * Sum per-segment modCounts to avoid mis-reporting when
         * elements are concurrently added and removed in one segment
         * while checking another, in which case the table was never
         * actually empty at any point. (The sum ensures accuracy up
         * through at least 1<<31 per-segment modifications before
         * recheck.)  Methods size() and containsValue() use similar
         * constructions for stability checks.
         */
        long sum = 0L;
        final Segment<K,V>[] segments = this.segments;
        for (int j = 0; j < segments.length; ++j) {
            Segment<K,V> seg = segmentAt(segments, j);
            if (seg != null) {
                if (seg.count != 0)
                    return false;
                sum += seg.modCount;
            }
        }
        if (sum != 0L) { // recheck unless no modifications
            for (int j = 0; j < segments.length; ++j) {
                Segment<K,V> seg = segmentAt(segments, j);
                if (seg != null) {
                    if (seg.count != 0)
                        return false;
                    sum -= seg.modCount;
                }
            }
            if (sum != 0L)
                return false;
        }
        return true;
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        // Try a few times to get accurate count. On failure due to
        // continuous async changes in table, resort to locking.
        final Segment<K,V>[] segments = this.segments;
        int size;
        boolean overflow; // true if size overflows 32 bits
        long sum;         // sum of modCounts
        long last = 0L;   // previous sum
        int retries = -1; // first iteration isn't retry
        try {
            for (;;) {
                if (retries++ == RETRIES_BEFORE_LOCK) {
                    for (int j = 0; j < segments.length; ++j)
                        ensureSegment(j).lock(); // force creation
                }
                sum = 0L;
                size = 0;
                overflow = false;
                for (int j = 0; j < segments.length; ++j) {
                    Segment<K,V> seg = segmentAt(segments, j);
                    if (seg != null) {
                        sum += seg.modCount;
                        int c = seg.count;
                        if (c < 0 || (size += c) < 0)
                            overflow = true;
                    }
                }
                if (sum == last)
                    break;
                last = sum;
            }
        } finally {
            if (retries > RETRIES_BEFORE_LOCK) {
                for (int j = 0; j < segments.length; ++j)
                    segmentAt(segments, j).unlock();
            }
        }
        return overflow ? Integer.MAX_VALUE : size;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code key.equals(k)},
     * then this method returns {@code v}; otherwise it returns
     * {@code null}.  (There can be at most one such mapping.)
     *
     * @throws NullPointerException if the specified key is null
     */
    public V get(Object key) {
        Segment<K,V> s; // manually integrate access methods to reduce overhead
        HashEntry<K,V>[] tab;
        int h = hash(key);
        long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
        if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
            (tab = s.table) != null) {
            for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
                     (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
                 e != null; e = e.next) {
                K k;
                if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                    return e.value;
            }
        }
        return null;
    }

    /**
     * Tests if the specified object is a key in this table.
     *
     * @param  key   possible key
     * @return <tt>true</tt> if and only if the specified object
     *         is a key in this table, as determined by the
     *         <tt>equals</tt> method; <tt>false</tt> otherwise.
     * @throws NullPointerException if the specified key is null
     */
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        Segment<K,V> s; // same as get() except no need for volatile value read
        HashEntry<K,V>[] tab;
        int h = hash(key);
        long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
        if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
            (tab = s.table) != null) {
            for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
                     (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
                 e != null; e = e.next) {
                K k;
                if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value. Note: This method requires a full internal
     * traversal of the hash table, and so is much slower than
     * method <tt>containsKey</tt>.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throws NullPointerException if the specified value is null
     */
    public boolean containsValue(Object value) {
        // Same idea as size()
        if (value == null)
            throw new NullPointerException();
        final Segment<K,V>[] segments = this.segments;
        boolean found = false;
        long last = 0;
        int retries = -1;
        try {
            outer: for (;;) {
                if (retries++ == RETRIES_BEFORE_LOCK) {
                    for (int j = 0; j < segments.length; ++j)
                        ensureSegment(j).lock(); // force creation
                }
                long hashSum = 0L;
                int sum = 0;
                for (int j = 0; j < segments.length; ++j) {
                    HashEntry<K,V>[] tab;
                    Segment<K,V> seg = segmentAt(segments, j);
                    if (seg != null && (tab = seg.table) != null) {
                        for (int i = 0 ; i < tab.length; i++) {
                            HashEntry<K,V> e;
                            for (e = entryAt(tab, i); e != null; e = e.next) {
                                V v = e.value;
                                if (v != null && value.equals(v)) {
                                    found = true;
                                    break outer;
                                }
                            }
                        }
                        sum += seg.modCount;
                    }
                }
                if (retries > 0 && sum == last)
                    break;
                last = sum;
            }
        } finally {
            if (retries > RETRIES_BEFORE_LOCK) {
                for (int j = 0; j < segments.length; ++j)
                    segmentAt(segments, j).unlock();
            }
        }
        return found;
    }

    /**
     * Legacy method testing if some key maps into the specified value
     * in this table.  This method is identical in functionality to
     * {@link #containsValue}, and exists solely to ensure
     * full compatibility with class {@link java.util.Hashtable},
     * which supported this method prior to introduction of the
     * Java Collections framework.

     * @param  value a value to search for
     * @return <tt>true</tt> if and only if some key maps to the
     *         <tt>value</tt> argument in this table as
     *         determined by the <tt>equals</tt> method;
     *         <tt>false</tt> otherwise
     * @throws NullPointerException if the specified value is null
     */
    public boolean contains(Object value) {
        return containsValue(value);
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     *
     * <p> The value can be retrieved by calling the <tt>get</tt> method
     * with a key that is equal to the original key.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>
     * @throws NullPointerException if the specified key or value is null
     */
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        Segment<K,V> s;
        if (value == null)
            throw new NullPointerException();
        int hash = hash(key);
        int j = (hash >>> segmentShift) & segmentMask;
        if ((s = (Segment<K,V>)UNSAFE.getObject          // nonvolatile; recheck
             (segments, (j << SSHIFT) + SBASE)) == null) //  in ensureSegment
            s = ensureSegment(j);
        return s.put(key, hash, value, false);
    }

    /**
     * {@inheritDoc}
     *
     * @return the previous value associated with the specified key,
     *         or <tt>null</tt> if there was no mapping for the key
     * @throws NullPointerException if the specified key or value is null
     */
    @SuppressWarnings("unchecked")
    public V putIfAbsent(K key, V value) {
        Segment<K,V> s;
        if (value == null)
            throw new NullPointerException();
        int hash = hash(key);
        int j = (hash >>> segmentShift) & segmentMask;
        if ((s = (Segment<K,V>)UNSAFE.getObject
             (segments, (j << SSHIFT) + SBASE)) == null)
            s = ensureSegment(j);
        return s.put(key, hash, value, true);
    }

    /**
     * Copies all of the mappings from the specified map to this one.
     * These mappings replace any mappings that this map had for any of the
     * keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    /**
     * Removes the key (and its corresponding value) from this map.
     * This method does nothing if the key is not in the map.
     *
     * @param  key the key that needs to be removed
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>
     * @throws NullPointerException if the specified key is null
     */
    public V remove(Object key) {
        int hash = hash(key);
        Segment<K,V> s = segmentForHash(hash);
        return s == null ? null : s.remove(key, hash, null);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if the specified key is null
     */
    public boolean remove(Object key, Object value) {
        int hash = hash(key);
        Segment<K,V> s;
        return value != null && (s = segmentForHash(hash)) != null &&
            s.remove(key, hash, value) != null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if any of the arguments are null
     */
    public boolean replace(K key, V oldValue, V newValue) {
        int hash = hash(key);
        if (oldValue == null || newValue == null)
            throw new NullPointerException();
        Segment<K,V> s = segmentForHash(hash);
        return s != null && s.replace(key, hash, oldValue, newValue);
    }

    /**
     * {@inheritDoc}
     *
     * @return the previous value associated with the specified key,
     *         or <tt>null</tt> if there was no mapping for the key
     * @throws NullPointerException if the specified key or value is null
     */
    public V replace(K key, V value) {
        int hash = hash(key);
        if (value == null)
            throw new NullPointerException();
        Segment<K,V> s = segmentForHash(hash);
        return s == null ? null : s.replace(key, hash, value);
    }

    /**
     * Removes all of the mappings from this map.
     */
    public void clear() {
        final Segment<K,V>[] segments = this.segments;
        for (int j = 0; j < segments.length; ++j) {
            Segment<K,V> s = segmentAt(segments, j);
            if (s != null)
                s.clear();
        }
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from this map,
     * via the <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or
     * <tt>addAll</tt> operations.
     *
     * <p>The view's <tt>iterator</tt> is a "weakly consistent" iterator
     * that will never throw {@link ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        return (ks != null) ? ks : (keySet = new KeySet());
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  The collection
     * supports element removal, which removes the corresponding
     * mapping from this map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt>, and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * <p>The view's <tt>iterator</tt> is a "weakly consistent" iterator
     * that will never throw {@link ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     */
    public Collection<V> values() {
        Collection<V> vs = values;
        return (vs != null) ? vs : (values = new Values());
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from the map,
     * via the <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or
     * <tt>addAll</tt> operations.
     *
     * <p>The view's <tt>iterator</tt> is a "weakly consistent" iterator
     * that will never throw {@link ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     */
    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> es = entrySet;
        return (es != null) ? es : (entrySet = new EntrySet());
    }

    /**
     * Returns an enumeration of the keys in this table.
     *
     * @return an enumeration of the keys in this table
     * @see #keySet()
     */
    public Enumeration<K> keys() {
        return new KeyIterator();
    }

    /**
     * Returns an enumeration of the values in this table.
     *
     * @return an enumeration of the values in this table
     * @see #values()
     */
    public Enumeration<V> elements() {
        return new ValueIterator();
    }

    /* ---------------- Iterator Support -------------- */

    abstract class HashIterator {
        int nextSegmentIndex;
        int nextTableIndex;
        HashEntry<K,V>[] currentTable;
        HashEntry<K, V> nextEntry;
        HashEntry<K, V> lastReturned;

        HashIterator() {
            nextSegmentIndex = segments.length - 1;
            nextTableIndex = -1;
            advance();
        }

        /**
         * Set nextEntry to first node of next non-empty table
         * (in backwards order, to simplify checks).
         */
        final void advance() {
            for (;;) {
                if (nextTableIndex >= 0) {
                    if ((nextEntry = entryAt(currentTable,
                                             nextTableIndex--)) != null)
                        break;
                }
                else if (nextSegmentIndex >= 0) {
                    Segment<K,V> seg = segmentAt(segments, nextSegmentIndex--);
                    if (seg != null && (currentTable = seg.table) != null)
                        nextTableIndex = currentTable.length - 1;
                }
                else
                    break;
            }
        }

        final HashEntry<K,V> nextEntry() {
            HashEntry<K,V> e = nextEntry;
            if (e == null)
                throw new NoSuchElementException();
            lastReturned = e; // cannot assign until after null check
            if ((nextEntry = e.next) == null)
                advance();
            return e;
        }

        public final boolean hasNext() { return nextEntry != null; }
        public final boolean hasMoreElements() { return nextEntry != null; }

        public final void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            ConcurrentHashMap.this.remove(lastReturned.key);
            lastReturned = null;
        }
    }

    final class KeyIterator
        extends HashIterator
        implements Iterator<K>, Enumeration<K>
    {
        public final K next()        { return super.nextEntry().key; }
        public final K nextElement() { return super.nextEntry().key; }
    }

    final class ValueIterator
        extends HashIterator
        implements Iterator<V>, Enumeration<V>
    {
        public final V next()        { return super.nextEntry().value; }
        public final V nextElement() { return super.nextEntry().value; }
    }

    /**
     * Custom Entry class used by EntryIterator.next(), that relays
     * setValue changes to the underlying map.
     */
    final class WriteThroughEntry
        extends AbstractMap.SimpleEntry<K,V>
    {
        WriteThroughEntry(K k, V v) {
            super(k,v);
        }

        /**
         * Set our entry's value and write through to the map. The
         * value to return is somewhat arbitrary here. Since a
         * WriteThroughEntry does not necessarily track asynchronous
         * changes, the most recent "previous" value could be
         * different from what we return (or could even have been
         * removed in which case the put will re-establish). We do not
         * and cannot guarantee more.
         */
        public V setValue(V value) {
            if (value == null) throw new NullPointerException();
            V v = super.setValue(value);
            ConcurrentHashMap.this.put(getKey(), value);
            return v;
        }
    }

    final class EntryIterator
        extends HashIterator
        implements Iterator<Entry<K,V>>
    {
        public Map.Entry<K,V> next() {
            HashEntry<K,V> e = super.nextEntry();
            return new WriteThroughEntry(e.key, e.value);
        }
    }

    final class KeySet extends AbstractSet<K> {
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        public int size() {
            return ConcurrentHashMap.this.size();
        }
        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }
        public boolean contains(Object o) {
            return ConcurrentHashMap.this.containsKey(o);
        }
        public boolean remove(Object o) {
            return ConcurrentHashMap.this.remove(o) != null;
        }
        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    final class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
        public int size() {
            return ConcurrentHashMap.this.size();
        }
        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }
        public boolean contains(Object o) {
            return ConcurrentHashMap.this.containsValue(o);
        }
        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        public Iterator<Map.Entry<K,V>> iterator() {
            return new EntryIterator();
        }
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            V v = ConcurrentHashMap.this.get(e.getKey());
            return v != null && v.equals(e.getValue());
        }
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return ConcurrentHashMap.this.remove(e.getKey(), e.getValue());
        }
        public int size() {
            return ConcurrentHashMap.this.size();
        }
        public boolean isEmpty() {
            return ConcurrentHashMap.this.isEmpty();
        }
        public void clear() {
            ConcurrentHashMap.this.clear();
        }
    }

    /* ---------------- Serialization Support -------------- */

    /**
     * Save the state of the <tt>ConcurrentHashMap</tt> instance to a
     * stream (i.e., serialize it).
     * @param s the stream
     * @serialData
     * the key (Object) and value (Object)
     * for each key-value mapping, followed by a null pair.
     * The key-value mappings are emitted in no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        // force all segments for serialization compatibility
        for (int k = 0; k < segments.length; ++k)
            ensureSegment(k);
        s.defaultWriteObject();

        final Segment<K,V>[] segments = this.segments;
        for (int k = 0; k < segments.length; ++k) {
            Segment<K,V> seg = segmentAt(segments, k);
            seg.lock();
            try {
                HashEntry<K,V>[] tab = seg.table;
                for (int i = 0; i < tab.length; ++i) {
                    HashEntry<K,V> e;
                    for (e = entryAt(tab, i); e != null; e = e.next) {
                        s.writeObject(e.key);
                        s.writeObject(e.value);
                    }
                }
            } finally {
                seg.unlock();
            }
        }
        s.writeObject(null);
        s.writeObject(null);
    }

    /**
     * Reconstitute the <tt>ConcurrentHashMap</tt> instance from a
     * stream (i.e., deserialize it).
     * @param s the stream
     */
    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        // Don't call defaultReadObject()
        ObjectInputStream.GetField oisFields = s.readFields();
        final Segment<K,V>[] oisSegments = (Segment<K,V>[])oisFields.get("segments", null);

        final int ssize = oisSegments.length;
        if (ssize < 1 || ssize > MAX_SEGMENTS
            || (ssize & (ssize-1)) != 0 )  // ssize not power of two
            throw new java.io.InvalidObjectException("Bad number of segments:"
                                                     + ssize);
        int sshift = 0, ssizeTmp = ssize;
        while (ssizeTmp > 1) {
            ++sshift;
            ssizeTmp >>>= 1;
        }
        UNSAFE.putIntVolatile(this, SEGSHIFT_OFFSET, 32 - sshift);
        UNSAFE.putIntVolatile(this, SEGMASK_OFFSET, ssize - 1);
        UNSAFE.putObjectVolatile(this, SEGMENTS_OFFSET, oisSegments);

        // set hashMask
        UNSAFE.putIntVolatile(this, HASHSEED_OFFSET, randomHashSeed(this));

        // Re-initialize segments to be minimally sized, and let grow.
        int cap = MIN_SEGMENT_TABLE_CAPACITY;
        final Segment<K,V>[] segments = this.segments;
        for (int k = 0; k < segments.length; ++k) {
            Segment<K,V> seg = segments[k];
            if (seg != null) {
                seg.threshold = (int)(cap * seg.loadFactor);
                seg.table = (HashEntry<K,V>[]) new HashEntry[cap];
            }
        }

        // Read the keys and values, and put the mappings in the table
        for (;;) {
            K key = (K) s.readObject();
            V value = (V) s.readObject();
            if (key == null)
                break;
            put(key, value);
        }
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long SBASE;
    private static final int SSHIFT;
    private static final long TBASE;
    private static final int TSHIFT;
    private static final long HASHSEED_OFFSET;
    private static final long SEGSHIFT_OFFSET;
    private static final long SEGMASK_OFFSET;
    private static final long SEGMENTS_OFFSET;

    static {
        int ss, ts;
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class tc = HashEntry[].class;
            Class sc = Segment[].class;
            TBASE = UNSAFE.arrayBaseOffset(tc);
            SBASE = UNSAFE.arrayBaseOffset(sc);
            ts = UNSAFE.arrayIndexScale(tc);
            ss = UNSAFE.arrayIndexScale(sc);
            HASHSEED_OFFSET = UNSAFE.objectFieldOffset(
                ConcurrentHashMap.class.getDeclaredField("hashSeed"));
            SEGSHIFT_OFFSET = UNSAFE.objectFieldOffset(
                ConcurrentHashMap.class.getDeclaredField("segmentShift"));
            SEGMASK_OFFSET = UNSAFE.objectFieldOffset(
                ConcurrentHashMap.class.getDeclaredField("segmentMask"));
            SEGMENTS_OFFSET = UNSAFE.objectFieldOffset(
                ConcurrentHashMap.class.getDeclaredField("segments"));
        } catch (Exception e) {
            throw new Error(e);
        }
        if ((ss & (ss-1)) != 0 || (ts & (ts-1)) != 0)
            throw new Error("data type scale not a power of two");
        SSHIFT = 31 - Integer.numberOfLeadingZeros(ss);
        TSHIFT = 31 - Integer.numberOfLeadingZeros(ts);
    }

}
```
 

下面从ConcurrentHashMap的创建，获取，添加，删除这4个方面对ConcurrentHashMap进行分析。

1 创建

下面以ConcurrentHashMap(int initialCapacity,float loadFactor, int concurrencyLevel)来进行说明。

```
@SuppressWarnings("unchecked")
public ConcurrentHashMap(int initialCapacity,
                         float loadFactor, int concurrencyLevel) {
    // 参数有效性判断
    if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
        throw new IllegalArgumentException();
    // concurrencyLevel是“用来计算segments的容量”
    if (concurrencyLevel > MAX_SEGMENTS)
        concurrencyLevel = MAX_SEGMENTS;
    int sshift = 0;
    int ssize = 1;
    // ssize=“大于或等于concurrencyLevel的最小的2的N次方值”
    while (ssize < concurrencyLevel) {
        ++sshift;
        ssize <<= 1;
    }
    // 初始化segmentShift和segmentMask
    this.segmentShift = 32 - sshift;
    this.segmentMask = ssize - 1;
    // 哈希表的初始容量
    // 哈希表的实际容量=“segments的容量” x “segments中数组的长度”
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    // “哈希表的初始容量” / “segments的容量”
    int c = initialCapacity / ssize;
    if (c * ssize < initialCapacity)
        ++c;
    // cap就是“segments中的HashEntry数组的长度”
    int cap = MIN_SEGMENT_TABLE_CAPACITY;
    while (cap < c)
        cap <<= 1;
    // segments
    Segment<K,V> s0 =
        new Segment<K,V>(loadFactor, (int)(cap * loadFactor),
                         (HashEntry<K,V>[])new HashEntry[cap]);
    Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
    UNSAFE.putOrderedObject(ss, SBASE, s0); // ordered write of segments[0]
    this.segments = ss;
}
```
说明：
 01) 前面我们说过，ConcurrentHashMap采用了“锁分段”技术；在代码中，它通过“segments数组”对象来保存各个分段。segments的定义如下：
```
final Segment<K,V>[] segments;
    concurrencyLevel的作用就是用来计算segments数组的容量大小。
    先计算出“大于或等于concurrencyLevel的最小的2的N次方值”，
    然后将其保存为“segments的容量大小(ssize)”。
```
 02) initialCapacity是哈希表的初始容量。需要注意的是，哈希表的实际容量=“segments的容量” x “segments中数组的长度”。
 03) loadFactor是加载因子。它是哈希表在其容量自动增加之前可以达到多满的一种尺度。


ConcurrentHashMap的构造函数中涉及到的非常重要的一个结构体，它就是Segment。下面看看Segment的声明：

```
static final class Segment<K,V> extends ReentrantLock implements Serializable {
    ...

    transient volatile HashEntry<K,V>[] table;
    // threshold阈，是哈希表在其容量自动增加之前可以达到多满的一种尺度。
    transient int threshold;
    // loadFactor是加载因子
    final float loadFactor;

    Segment(float lf, int threshold, HashEntry<K,V>[] tab) {
        this.loadFactor = lf;
        this.threshold = threshold;
        this.table = tab;
    }

    ...
}
```
说明：Segment包含HashEntry数组，HashEntry保存了哈希表中的键值对。
此外，还需要说明的Segment继承于ReentrantLock。这意味着，Segment本质上就是可重入的互斥锁。

HashEntry的源码如下：

```
static final class HashEntry<K,V> {
    final int hash;    // 哈希值
    final K key;       // 键
    volatile V value;  // 值
    volatile HashEntry<K,V> next; // 下一个HashEntry节点

    HashEntry(int hash, K key, V value, HashEntry<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    ...
}
```
说明：和HashMap的节点一样，HashEntry也是链表。这就说明，ConcurrentHashMap是链式哈希表，
它是通过“拉链法”来解决哈希冲突的。

 

2 获取

下面以get(Object key)为例，对ConcurrentHashMap的获取方法进行说明。

```
public V get(Object key) {
    Segment<K,V> s; // manually integrate access methods to reduce overhead
    HashEntry<K,V>[] tab;
    int h = hash(key);
    long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
    // 获取key对应的Segment片段。
    // 如果Segment片段不为null，则在“Segment片段的HashEntry数组中”中找到key所对应的HashEntry列表；
    // 接着遍历该HashEntry链表，找到于key-value键值对对应的HashEntry节点。
    if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
        (tab = s.table) != null) {
        for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
                 (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
             e != null; e = e.next) {
            K k;
            if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                return e.value;
        }
    }
    return null;
}
```
说明：get(Object key)的作用是返回key在ConcurrentHashMap哈希表中对应的值。    
它首先根据key计算出来的哈希值，获取key所对应的Segment片段。    
如果Segment片段不为null，则在“Segment片段的HashEntry数组中”中找到key所对应的HashEntry列表。Segment包含“HashEntry数组”对象，而每一个HashEntry本质上是一个单向链表。    
接着遍历该HashEntry链表，找到于key-value键值对对应的HashEntry节点。    

下面是hash()的源码

```
private int hash(Object k) {
    int h = hashSeed;

    if ((0 != h) && (k instanceof String)) {
        return sun.misc.Hashing.stringHash32((String) k);
    }

    h ^= k.hashCode();

    // Spread bits to regularize both segment and index locations,
    // using variant of single-word Wang/Jenkins hash.
    h += (h <<  15) ^ 0xffffcd7d;
    h ^= (h >>> 10);
    h += (h <<   3);
    h ^= (h >>>  6);
    h += (h <<   2) + (h << 14);
    return h ^ (h >>> 16);
}
```
 

3 增加

下面以put(K key, V value)来对ConcurrentHashMap中增加键值对来进行说明。

```
public V put(K key, V value) {
    Segment<K,V> s;
    if (value == null)
        throw new NullPointerException();
    // 获取key对应的哈希值
    int hash = hash(key);
    int j = (hash >>> segmentShift) & segmentMask;
    // 如果找不到该Segment，则新建一个。
    if ((s = (Segment<K,V>)UNSAFE.getObject          // nonvolatile; recheck
         (segments, (j << SSHIFT) + SBASE)) == null) // in ensureSegment
        s = ensureSegment(j);
    return s.put(key, hash, value, false);
}
```
说明：
 01) put()根据key获取对应的哈希值，再根据哈希值找到对应的Segment片段。如果Segment片段不存在，则新增一个Segment。
 02) 将key-value键值对添加到Segment片段中。

```
final V put(K key, int hash, V value, boolean onlyIfAbsent) {
    // tryLock()获取锁，成功返回true，失败返回false。
    // 获取锁失败的话，则通过scanAndLockForPut()获取锁，并返回”要插入的key-value“对应的”HashEntry链表“。
    HashEntry<K,V> node = tryLock() ? null :
        scanAndLockForPut(key, hash, value);
    V oldValue;
    try {
        // tab代表”当前Segment中的HashEntry数组“
        HashEntry<K,V>[] tab = table;
        //  根据”hash值“获取”HashEntry数组中对应的HashEntry链表“
        int index = (tab.length - 1) & hash;
        HashEntry<K,V> first = entryAt(tab, index);
        for (HashEntry<K,V> e = first;;) {
            // 如果”HashEntry链表中的当前HashEntry节点“不为null，
            if (e != null) {
                K k;
                // 当”要插入的key-value键值对“已经存在于”HashEntry链表中“时，先保存原有的值。
                // 若”onlyIfAbsent“为true，即”要插入的key不存在时才插入”，则直接退出；
                // 否则，用新的value值覆盖原有的原有的值。
                if ((k = e.key) == key ||
                    (e.hash == hash && key.equals(k))) {
                    oldValue = e.value;
                    if (!onlyIfAbsent) {
                        e.value = value;
                        ++modCount;
                    }
                    break;
                }
                e = e.next;
            }
            else {
                // 如果node非空，则将first设置为“node的下一个节点”。
                // 否则，新建HashEntry链表
                if (node != null)
                    node.setNext(first);
                else
                    node = new HashEntry<K,V>(hash, key, value, first);
                int c = count + 1;
                // 如果添加key-value键值对之后，Segment中的元素超过阈值(并且，HashEntry数组的长度没超过限制)，则rehash；
                // 否则，直接添加key-value键值对。
                if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                    rehash(node);
                else
                    setEntryAt(tab, index, node);
                ++modCount;
                count = c;
                oldValue = null;
                break;
            }
        }
    } finally {
        // 释放锁
        unlock();
    }
    return oldValue;
}
```
说明：
put()的作用是将key-value键值对插入到“当前Segment对应的HashEntry中”，在插入前它会获取Segment对应的互斥锁，插入后会释放锁。具体的插入过程如下：
 1) 首先根据“hash值”获取“当前Segment的HashEntry数组对象”中的“HashEntry节点”，每个HashEntry节点都是一个单向链表。
 2) 接着，遍历HashEntry链表。
       若在遍历HashEntry链表时，找到与“要key-value键值对”对应的节点，即“要插入的key-value键值对”的key已经存在于HashEntry链表中。则根据onlyIfAbsent进行判断，若onlyIfAbsent为true，即“当要插入的key不存在时才插入”，则不进行插入，直接返回；否则，用新的value值覆盖原始的value值，然后再返回。
       若在遍历HashEntry链表时，没有找到与“要key-value键值对”对应的节点。当node!=null时，即在scanAndLockForPut()获取锁时，已经新建了key-value对应的HashEntry节点，则”将HashEntry添加到Segment中“；否则，新建key-value对应的HashEntry节点，然后再“将HashEntry添加到Segment中”。 在”将HashEntry添加到Segment中“前，会判断是否需要rehash。如果在添加key-value键值之后，容量会超过阈值，并且HashEntry数组的长度没有超过限制，则进行rehash；否则，直接通过setEntryAt()将key-value键值对添加到Segment中。

在介绍rehash()和setEntryAt()之前，我们先看看自旋函数scanAndLockForPut()。下面是它的源码：

```
private HashEntry<K,V> scanAndLockForPut(K key, int hash, V value) {
    // 第一个HashEntry节点
    HashEntry<K,V> first = entryForHash(this, hash);
    // 当前的HashEntry节点
    HashEntry<K,V> e = first;
    HashEntry<K,V> node = null;
    // 重复计数(自旋计数器)
    int retries = -1; // negative while locating node

    // 查找”key-value键值对“在”HashEntry链表上对应的节点“；
    // 若找到的话，则不断的自旋；在自旋期间，若通过tryLock()获取锁成功则返回；否则自旋MAX_SCAN_RETRIES次数之后，强制获取”锁“并退出。
    // 若没有找到的话，则新建一个HashEntry链表。然后不断的自旋。
    // 此外，若在自旋期间，HashEntry链表的表头发生变化；则重新进行查找和自旋工作！
    while (!tryLock()) {
        HashEntry<K,V> f; // to recheck first below
        // 1. retries<0的处理情况
        if (retries < 0) {
            // 1.1 如果当前的HashEntry节点为空(意味着，在该HashEntry链表上上没有找到”要插入的键值对“对应的节点)，而且node=null；则新建HashEntry链表。
            if (e == null) {
                if (node == null) // speculatively create node
                    node = new HashEntry<K,V>(hash, key, value, null);
                retries = 0;
            }
            // 1.2 如果当前的HashEntry节点是”要插入的键值对在该HashEntry上对应的节点“，则设置retries=0
            else if (key.equals(e.key))
                retries = 0;
            // 1.3 设置为下一个HashEntry。
            else
                e = e.next;
        }
        // 2. 如果自旋次数超过限制，则获取“锁”并退出
        else if (++retries > MAX_SCAN_RETRIES) {
            lock();
            break;
        }
        // 3. 当“尝试了偶数次”时，就获取“当前Segment的第一个HashEntry”，即f。
        // 然后，通过f!=first来判断“当前Segment的第一个HashEntry是否发生了改变”。
        // 若是的话，则重置e，first和retries的值，并重新遍历。
        else if ((retries & 1) == 0 &&
                 (f = entryForHash(this, hash)) != first) {
            e = first = f; // re-traverse if entry changed
            retries = -1;
        }
    }
    return node;
}
```
说明：    
scanAndLockForPut()的目标是获取锁。流程如下：    
    它首先会调用entryForHash()，根据hash值获取”当前Segment中对应的HashEntry节点(first)，即找到对应的HashEntry链表“。
    紧接着进入while循环。在while循环中，它会遍历”HashEntry链表(e)“，查找”要插入的key-value键值对“在”该HashEntry链表上对应的节点“。
         若找到的话，则不断的自旋，即不断的执行while循环。在自旋期间，若通过tryLock()获取锁成功则返回；否则，在自旋MAX_SCAN_RETRIES次数之后，强制获取锁并退出。
         若没有找到的话，则新建一个HashEntry链表，然后不断的自旋。在自旋期间，若通过tryLock()获取锁成功则返回；否则，在自旋MAX_SCAN_RETRIES次数之后，强制获取锁并退出。
     此外，若在自旋期间，HashEntry链表的表头发生变化；则重新进行查找和自旋工作！

理解scanAndLockForPut()时，务必要联系”哈希表“的数据结构。一个Segment本身就是一个哈希表，Segment中包含了”HashEntry数组“对象，而每一个HashEntry对象本身是一个”单向链表“。

 

下面看看rehash()的实现代码。

```
private void rehash(HashEntry<K,V> node) {
    HashEntry<K,V>[] oldTable = table;
    // ”Segment中原始的HashEntry数组的长度“
    int oldCapacity = oldTable.length;
    // ”Segment中新HashEntry数组的长度“
    int newCapacity = oldCapacity << 1;
    // 新的阈值
    threshold = (int)(newCapacity * loadFactor);
    // 新的HashEntry数组
    HashEntry<K,V>[] newTable =
        (HashEntry<K,V>[]) new HashEntry[newCapacity];
    int sizeMask = newCapacity - 1;
    // 遍历”原始的HashEntry数组“，
    // 将”原始的HashEntry数组“中的每个”HashEntry链表“的值，都复制到”新的HashEntry数组的HashEntry元素“中。
    for (int i = 0; i < oldCapacity ; i++) {
        // 获取”原始的HashEntry数组“中的”第i个HashEntry链表“
        HashEntry<K,V> e = oldTable[i];
        if (e != null) {
            HashEntry<K,V> next = e.next;
            int idx = e.hash & sizeMask;
            if (next == null)   //  Single node on list
                newTable[idx] = e;
            else { // Reuse consecutive sequence at same slot
                HashEntry<K,V> lastRun = e;
                int lastIdx = idx;
                for (HashEntry<K,V> last = next;
                     last != null;
                     last = last.next) {
                    int k = last.hash & sizeMask;
                    if (k != lastIdx) {
                        lastIdx = k;
                        lastRun = last;
                    }
                }
                newTable[lastIdx] = lastRun;
                // 将”原始的HashEntry数组“中的”HashEntry链表(e)“的值，都复制到”新的HashEntry数组的HashEntry“中。
                for (HashEntry<K,V> p = e; p != lastRun; p = p.next) {
                    V v = p.value;
                    int h = p.hash;
                    int k = h & sizeMask;
                    HashEntry<K,V> n = newTable[k];
                    newTable[k] = new HashEntry<K,V>(h, p.key, v, n);
                }
            }
        }
    }
    // 将新的node节点添加到“Segment的新HashEntry数组(newTable)“中。
    int nodeIndex = node.hash & sizeMask; // add the new node
    node.setNext(newTable[nodeIndex]);
    newTable[nodeIndex] = node;
    table = newTable;
}
```
说明：rehash()的作用是将”Segment的容量“变为”原始的Segment容量的2倍“。
在将原始的数据拷贝到“新的Segment”中后，会将新增加的key-value键值对添加到“新的Segment”中。

setEntryAt()的源码如下：
```
static final <K,V> void setEntryAt(HashEntry<K,V>[] tab, int i,
                                   HashEntry<K,V> e) {
    UNSAFE.putOrderedObject(tab, ((long)i << TSHIFT) + TBASE, e);
}
```
UNSAFE是Segment类中定义的“静态sun.misc.Unsafe”对象。源码如下：
```
static final sun.misc.Unsafe UNSAFE;
Unsafe.java在openjdk6中的路径是：openjdk6/jdk/src/share/classes/sun/misc/Unsafe.java。其中，putOrderedObject()的源码下：

public native void putOrderedObject(Object o, long offset, Object x);
```
说明：putOrderedObject()是一个本地方法。 
它会设置obj对象中offset偏移地址对应的object型field的值为指定值。它是一个有序或者有延迟的putObjectVolatile()方法，并且不保证值的改变被其他线程立即看到。只有在field被volatile修饰并且期望被意外修改的时候，使用putOrderedObject()才有用。

总之，setEntryAt()的目的是设置tab中第i位置元素的值为e，且该设置会有延迟。

 

4 删除

下面以remove(Object key)来对ConcurrentHashMap中的删除操作来进行说明。
```
public V remove(Object key) {
    int hash = hash(key);
    // 根据hash值，找到key对应的Segment片段。
    Segment<K,V> s = segmentForHash(hash);
    return s == null ? null : s.remove(key, hash, null);
}
```
说明：remove()首先根据“key的计算出来的哈希值”找到对应的Segment片段，然后再从该Segment片段中删除对应的“key-value键值对”。

remove()的方法如下：

```
final V remove(Object key, int hash, Object value) {
    // 尝试获取Segment对应的锁。
    // 尝试失败的话，则通过scanAndLock()来获取锁。
    if (!tryLock())
        scanAndLock(key, hash);
    V oldValue = null;
    try {
        // 根据“hash值”找到“Segment的HashEntry数组”中对应的“HashEntry节点(e)”，该HashEntry节点是一HashEntry个链表。
        HashEntry<K,V>[] tab = table;
        int index = (tab.length - 1) & hash;
        HashEntry<K,V> e = entryAt(tab, index);
        HashEntry<K,V> pred = null;
        // 遍历“HashEntry链表”，删除key-value键值对
        while (e != null) {
            K k;
            HashEntry<K,V> next = e.next;
            if ((k = e.key) == key ||
                (e.hash == hash && key.equals(k))) {
                V v = e.value;
                if (value == null || value == v || value.equals(v)) {
                    if (pred == null)
                        setEntryAt(tab, index, next);
                    else
                        pred.setNext(next);
                    ++modCount;
                    --count;
                    oldValue = v;
                }
                break;
            }
            pred = e;
            e = next;
        }
    } finally {
        // 释放锁
        unlock();
    }
    return oldValue;
}
```
说明：remove()的目的就是删除key-value键值对。在删除之前，它会获取到Segment的互斥锁，在删除之后，再释放锁。
它的删除过程也比较简单，它会先根据hash值，找到“Segment的HashEntry数组”中对应的“HashEntry”节点。根据Segment的数据结构，我们知道Segment中包含一个HashEntry数组对象，而每一个HashEntry本质上是一个单向链表。 在找到“HashEntry”节点之后，就遍历该“HashEntry”节点对应的链表，找到key-value键值对对应的节点，然后删除。

下面对scanAndLock()进行说明。它的源码如下：

```
private void scanAndLock(Object key, int hash) {
    // 第一个HashEntry节点
    HashEntry<K,V> first = entryForHash(this, hash);
    HashEntry<K,V> e = first;
    int retries = -1;

    // 查找”key-value键值对“在”HashEntry链表上对应的节点“；
    // 无论找没找到，最后都会不断的自旋；在自旋期间，若通过tryLock()获取锁成功则返回；否则自旋MAX_SCAN_RETRIES次数之后，强制获取”锁“并退出。
    // 若在自旋期间，HashEntry链表的表头发生变化；则重新进行查找和自旋！
    while (!tryLock()) {
        HashEntry<K,V> f;
        if (retries < 0) {
            // 如果“遍历完该HashEntry链表，仍然没找到”要删除的键值对“对应的节点”
            // 或者“在该HashEntry链表上找到”要删除的键值对“对应的节点”，则设置retries=0
            // 否则，设置e为下一个HashEntry节点。
            if (e == null || key.equals(e.key))
                retries = 0;
            else
                e = e.next;
        }
        // 自旋超过限制次数之后，获取锁并退出。
        else if (++retries > MAX_SCAN_RETRIES) {
            lock();
            break;
        }
        // 当“尝试了偶数次”时，就获取“当前Segment的第一个HashEntry”，即f。
        // 然后，通过f!=first来判断“当前Segment的第一个HashEntry是否发生了改变”。
        // 若是的话，则重置e，first和retries的值，并重新遍历。
        else if ((retries & 1) == 0 &&
                 (f = entryForHash(this, hash)) != first) {
            e = first = f;
            retries = -1;
        }
    }
}
```
说明：scanAndLock()的目标是获取锁。它的实现与scanAndLockForPut()类似，这里就不再过多说明。

 

>##总结：
ConcurrentHashMap是线程安全的哈希表，它是通过“锁分段”来实现的。
ConcurrentHashMap中包括了“Segment(锁分段)数组”，每个Segment就是一个哈希表，
而且也是可重入的互斥锁。第一，Segment是哈希表表现在，Segment包含了“HashEntry数组”，
而“HashEntry数组”中的每一个HashEntry元素是一个单向链表。即Segment是通过链式哈希表。
第二，Segment是可重入的互斥锁表现在，Segment继承于ReentrantLock，而ReentrantLock就是可重入的互斥锁。
对于ConcurrentHashMap的添加，删除操作，在操作开始前，线程都会获取Segment的互斥锁；操作完毕之后，
才会释放。而对于读取操作，它是通过volatile去实现的，HashEntry数组是volatile类型的，
而volatile能保证“即对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入”，
即我们总能读到其它线程写入HashEntry之后的值。 以上这些方式，就是ConcurrentHashMap线程安全的实现原理。

 

ConcurrentHashMap示例
下面，我们通过一个例子去对比HashMap和ConcurrentHashMap。

```
 import java.util.*;
 import java.util.concurrent.*;
 
 /*
  *   ConcurrentHashMap是“线程安全”的哈希表，而HashMap是非线程安全的。
  *
  *   下面是“多个线程同时操作并且遍历map”的示例
  *   (01) 当map是ConcurrentHashMap对象时，程序能正常运行。
  *   (02) 当map是HashMap对象时，程序会产生ConcurrentModificationException异常。
  *
  * @author skywang
  */
 public class ConcurrentHashMapDemo1 {
 
     // TODO: map是HashMap对象时，程序会出错。
     //private static Map<String, String> map = new HashMap<String, String>();
     private static Map<String, String> map = new ConcurrentHashMap<String, String>();
     public static void main(String[] args) {
     
         // 同时启动两个线程对map进行操作！
         new MyThread("ta").start();
         new MyThread("tb").start();
     }
 
     private static void printAll() {
         String key, value;
         Iterator iter = map.entrySet().iterator();
         while(iter.hasNext()) {
             Map.Entry entry = (Map.Entry)iter.next();
             key = (String)entry.getKey();
             value = (String)entry.getValue();
             System.out.print(key+" - "+value+", ");
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
                 map.put(String.valueOf(i), val);
                 // 通过“Iterator”遍历map。
                 printAll();
             }
         }
     }
 }
```
(某一次)运行结果：

```
1 - tb1, 
1 - tb1, 
1 - tb1, 1 - tb1, 2 - tb2, 
2 - tb2, 1 - tb1, 
3 - ta3, 1 - tb1, 2 - tb2, 
3 - tb3, 1 - tb1, 2 - tb2, 
3 - tb3, 1 - tb1, 4 - tb4, 3 - tb3, 2 - tb2, 
4 - tb4, 1 - tb1, 2 - tb2, 
5 - ta5, 1 - tb1, 3 - tb3, 5 - tb5, 4 - tb4, 3 - tb3, 2 - tb2, 
4 - tb4, 1 - tb1, 2 - tb2, 
5 - tb5, 1 - tb1, 6 - tb6, 5 - tb5, 3 - tb3, 6 - tb6, 4 - tb4, 3 - tb3, 2 - tb2, 
4 - tb4, 2 - tb2, 
```
结果说明：如果将源码中的map改成HashMap对象时，程序会产生ConcurrentModificationException异常。

 
# ArrayBlockingQueue
查看一下源码， 比较一下功能，

内部使用数组的形式存储   Object[]

添加
add(E x)
```
insert the specified element at the tail of this queue 
if it is possible to do so immediately without exceeding the queue's capacity,
returning true upon success and throwing an illegalStateException
if this queue is full
往末尾插入一个元素，成功返回true, 失败报错，因为它满了
```

offer(E e)
```
insert the specified element at the tail of this queue 
if it is possible to do so immediately without exceeding the queue's capacity,
returning   true  upon success and   false  if this queue is full.
This method is generally preferable to method add, 
which can fail to insert an element only by throwing an exception.

往末尾插入一个元素，成功返回true, 失败返回false，因为它满了
底层使用了lock锁，保证同一时刻一个线程插入

```
put(E e)
```
inserts the specified element at the tail of this queue,
waiting for space to become available if the queue is full.

往末尾插入一个元素，当元素未满时，直接插入，当元素满了，
等待插入。 底层使用了lock锁， condition.await
```

boolean offer(E e, long timeout, TimeUnit unit)
```
Inserts the specified element at the tail of this queue, waiting up to the specified wait time for space to become available if  the queue is full.
当等待时间为负数，返回false, 否则等待对应的时间，然后返回数值, 这里的等待时间的前提是，当前队列满了
```

E poll()
```
使用lock 锁，取出当前元素，没有就为null， 有就直接取出
```

E take()
```
使用lock 锁，取出当前元素，没有就等待， 有就直接取出
```

E poll(long timeout, TimeUnit unit)
```
使用lock锁，取出当前元素，没有就等待， 有就直接取出
```

E peek()
```
取出当前队列头的元素
```


有阻塞的效果的方法
put(), take()





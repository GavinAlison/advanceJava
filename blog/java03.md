## 一个对象在jvm是如何存在的


1. 类加载， 在方法区中， class类型， 静态变量    所有线程共享
一个对象创建之前，会检查对应的class类型是否已经加载进来，没有，一般经过类的加载机制，
双亲委派模式进行加载，将Class类加载进方法区中，class文件的版本，字段，方法等类描述信息，
还要静态变量，常量。

2. 常量池，  符号引用， 字面量，    所有线程共享
class文件中存在一些符号引用和字面量，这些信息存储到方法区里面的常量池

3. 堆heap, 对象的创建，  所有线程共享
对象的创建，根据class类，利用构造函数进行创建对象，首先在heap堆中分配内存，
这个对象是所有线程共享。 对象所需的内存大小在类加载完成后便可确定，
为对象分配空间的任务等同于把一块确定大小的内存从 Java 堆中划分出来。

3.1.  分配方式有 “指针碰撞” 和 “空闲列表” 两种，选择那种分配方式由 Java 堆是否规整决定，
而Java堆是否规整又由所采用的垃圾收集器是否带有压缩整理功能决定。

    -   指针碰撞
     Java 堆内存规整，GC 收集器的算法采用了复制算法，标记-整理算法
    -   空闲列表
    Java 堆内存不规整，GC 收集器的算法采用了标记-清除算法

    ```
    堆一般是垃圾收集器管理的主要区域， 一般分为 新生代与老年代，perm持久代
    新生代分为Eden区， from survivor（s1）, to survivor(s2),
    一般创建的对象在eden区，当eden的空间满了，发生minor gc，
    就是将eden区存活对象通过标记-整理算法（或者其他算法）移动到s0区，同时记录对象的存活次数。
    然后eden区清空了，没有对象，为下次创建对象做准备。
    然后新的创建的对象又在eden区，eden区又满了，此时会发生minor gc, 然后呢，将eden区存活的对象，
    和s0区存活的对象通过gc算法（复制，标记-整理）移动到s1区，同时记录对象存活的次数。
    
    如此反复，当存活的对象的次数大于系统设置的阈值，此时会在发生minor GC的时候，将对象移动到老年代中。
    当老年代的空间也满了，此时会发生full GC， 会将所有区间无引用的对象清除
    
    持久代在方法区method area, 这里存储了类的描述信息，常量与符号引用
    
    ```

3.2. 为对象的属性初始化零值
内存分配完成后，虚拟机需要将分配到的内存空间都初始化为零值（不包括对象头），这一步操作保证了对象的实例字段在 Java 代码中可以不赋初始值就直接使用，

3.3. 设置对象头
初始化零值完成之后，虚拟机要对对象进行必要的设置，例如这个对象是那个类的实例、
如何才能找到类的元数据信息、对象的哈希吗、对象的 GC 分代年龄等信息。
 这些信息存放在对象头中。
 
3.4. 执行 init 方法
执行对象的init方法，比如{}

4. 创建类型的变量， 栈
在栈里，一个线程创建对应类型的变量，同时会将对象的内存地址指向变量，在线程栈内，对象的其他属性值会从
堆中拷贝一份到线程栈中，线程自己定义的属性会在自己的线程栈中。

5. 调用方法， 栈
通过类型变量（引用）调用方法，此时会在栈中创建本地方法帧，在方法帧中定义的local variable局部变量，在方法帧（栈）
中，通过参数传递进来的，会从堆中拷贝一份到方法帧中，这里涉及到传值还是传址。

6. 调用的过程中，会使用到程序计数器，程序计数器处理记录指令，分支，循环，跳转，异常之类的情况。

7. 方法调用之后，会将对应的数据与引用释放，此时对象的应用释放了。
当遇到minor GC 时， 会将无引用的对象清除。无引用的对象指的是，该对象没有指向，该对象的属性没有其他引用。

8. 该对象如果没有被清除，且该计数值大于阈值，会进入老年代中。
最后的最后， 该对象最终会被清除的。

9. 如果该对象调用了本地方法，native方法， 会在本地方法栈中分配内存进行调用本地方法

10. 当所有对象实例被回收，对应的类加载器classloader被回收， class对象没有对应的反射，
这些条件都满足时，此时会在持久代(method area)进行回收class类信息。


### appendix
1. 什么是符号引用？
符号引用拆开来看，先需要符号，然后查找对应的符号引用关系，在jvm中，class文件会转换成一堆堆
指令，一条指令存在引用其他符号的情况，比如invokevirtual #2, 会到常量池中寻找对应的符号。

2. 直接引用
符号引用在jvm运行时，会转换成直接引用， 直接引用是可以在运行时直接调用，比如方法调用，
运行转换成直接引用，这个方法需要到虚方法表中查找。


### refrence
-   [JVM里的符号引用如何存储？](https://www.zhihu.com/question/30300585/answer/51335493)
-   https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html
-   https://www.zhihu.com/search?type=content&q=java%20%E5%86%85%E5%AD%98
-   https://zhuanlan.zhihu.com/p/25539690
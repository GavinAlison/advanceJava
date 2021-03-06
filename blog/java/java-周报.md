## 关于从5个方面让你真正了解Java内存模型的思考
> 思考文章《 [从5个方面让你真正了解Java内存模型](https://www.jianshu.com/p/201d0c93fc5c?utm_source=tuicool&utm_medium=referral)》

1. L1Cache与L2Cache， L3Cache的区别没有明确出来
2. L1Cache在线程中的情况没有明确
3. 缓存带来的一致性问题，原因在于不同的线程有不同的缓存，各自之间独立，对共享数据操作所带来的数据一致性问题
，这个观点赞成
4. 为什么需要缓存
5. 一级缓存与二级缓存的区别
6. 什么样的数据才可以使用缓存， 什么样的不能
7. 缓存的效果如何，缓存的数据什么时候被清空

## think
#### 1. L1Cache与L2Cache， L3Cache的区别没有明确出来
> 这个题与题5. 一级缓存与二级缓存的区别 一起

这里其实可以解决一个问题，就是为什么会有L1,L2,L3Cache级别缓存， <br>
原因在于L1级缓存可能存储不了那么多数据，需要L2Cache进行扩容，  <br>
实际上也是，如果在L1Cache级缓存中查询不到，就会到L2Cache缓存中查找， <br>
如果在L2Cahce中查找不到，就会到L3Cache中查找，在没有，直接上主内存了。 <br>

L1, L2, L3的区别   <br>
L1, L2 Cache都是CPU专有（私有）， L3Cache是多个CPU共享。<br>
L1, 大小为几十--几百KB, 访问时间5-10ns <br>
L2, 大小为几百KB--几MB, 访问时间40-60ns  <br>
L3, 大小为几百MB--几GB, 访问时间100-150ns  <br>
其他的  <br>
寄存器， 大小为几十--几百B, 访问时间1ns  <br>
内存，   大小为几百GB--几TB, 访问时间3-15ms  <br>

#### 为了L1Cache存储不了这么多数据？
回答这个问题之前，先了解L1Cahe中存储的数据是什么， 一般来说，有两种，指令缓存，数据缓存。
我这里说的是数据缓存，这里数据一般不直接存储数据，而是存储的是内存的地址映射(地址), 
如果存储多了，CPU到L1Cache中查找数据速度就会变得很慢，此时设计L1Cache缓存的目的就不存在了。


#### 4. 为什么需要缓存
在计算机存储体系中，缓存的存在，是为了加快数据的读取与写入（交互）, 毕竟CPU与主内交互时间比较长，
因为他们需要经过系统总线(system bus)， 而在CPU中搭建一个缓存，CPU就可以直接与缓存进行交互，
节省了时间。


#### 5. 一级缓存与二级缓存的区别
这里主要关注Mybatis的一级缓存与二级缓存的实现

mybatis的的一级缓存是SqlSession级别的缓存，一级缓存缓存的是对象，
当SqlSession提交、关闭以及其他的更新数据库的操作发生后，一级缓存就会清空。
二级缓存是SqlSessionFactory级别的缓存，同一个SqlSessionFactory产生的SqlSession
都共享一个二级缓存，二级缓存中存储的是数据，当命中二级缓存时，通过存储的数据构造对象返回。
查询数据的时候，查询的流程是二级缓存>一级缓存>数据库。


#### 6. 什么样的数据才可以使用缓存， 什么样的不能
可缓存数据：经常查询并且不经常改变的，并且的数据的正确与否对最终结果影响不大的
不可缓存数据： 经常改变的数据，数据的正确与否对最终结果影响很大的。


#### 7. 缓存的效果如何，缓存的数据什么时候被清空
缓存的效果显而易见，就是加快访问数据，缓存的数据是存储到缓存器中的，系统断电就会清空。
所以这里对缓存数据库的看法就是，缓存永远就是缓存，如果将其看做数据库使用，一定要考虑数据丢失的情况。


#### 8. 缓存的图形
![缓存](https://pic3.zhimg.com/80/v2-7d6ae0a0629fddd89d947ee5567d075e_hd.jpg)

cache分成三类，
-   1 直接映射高速缓存，这个简单，即每个组只有一个line，选中组之后不需要和组中的每个line比对，因为只有一个line。
-   2 组相联高速缓存，这个就是我们前面介绍的cache。 S个组，每个组E个line。
-   3 全相联高速缓存，这个简单，只有一个组，就是全相联。不用hash来确定组，直接挨个比对高位地址，来确定是否命中。可以想见这种方式不适合大的缓存


Cache 概述
![数据移动到Cache](https://pic2.zhimg.com/80/v2-ac0ecd9b96492e0ece953321ccbe6eb9_hd.jpg)

cache分成多个组，每个组分成多个行，linesize是cache的基本单位，从主存向cache迁移数据都是
按照linesize为单位替换的。比如linesize为32Byte，那么迁移必须一次迁移32Byte到cache。
这个linesize比较容易理解，想想我们前面书的例子，我们从书架往书桌搬书必须以书为单位，
肯定不能把书撕了以页为单位。书就是linesize。当然了现实生活中每本书页数不同，
但是同个cache的linesize总是相同的。

Cache 结构

![Cache 结构](https://pic1.zhimg.com/80/v2-0e1534aa871fbe7124a17e1395825a74_hd.jpg)


> 引用
1. https://zhuanlan.zhihu.com/p/37749443
2. https://www.jianshu.com/p/201d0c93fc5c?utm_source=tuicool&utm_medium=referral
3. https://zhuanlan.zhihu.com/p/80305146

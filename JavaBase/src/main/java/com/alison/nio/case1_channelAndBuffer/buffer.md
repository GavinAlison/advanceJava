## 缓存区
下面是 NIO Buffer 相关的话题列表：

-   Buffer 的基本用法
-   Buffer 的 capacity,position 和 limit
-   Buffer 的类型
-   Buffer 的分配
-   向 Buffer 中写数据
-   flip() 方法
-   从 Buffer 中读取数据
-   clear() 与 compact() 方法
-   mark() 与 reset() 方法
-   equals() 与 compareTo() 方法


Buffer的种类
-   ByteBuffer
-   MappedByteBuffer
-   CharBuffer
-   DoubleBuffer
-   FloatBuffer
-   IntBuffer
-   LongBuffer
-   ShortBuffer

## byteBuffer
-   Capacity
>容量, 缓冲区能够容纳的数据元素的最大数量
-   Limit
>上界, 缓冲区里的数据的总数,代表了当前缓冲区中一共有多少数据
-   Position
>位置, 下一个要被读或写的元素的位置,Position会自动由相应的 get( )和 put( )函数更新
-   Mark
>标记, 一个备忘位置。用于记录上一次读写的位置。表示当前position的位置，可通过reset() 将mark = 0

0 <= mark <= position <= limit <= capacity
 
 
## method
### buffer
-   flip
> 切换成读模式, 将
limit = position ,
position = 0,
mark = -1
-   clear
>  positin =0, limit =capacity, 
mark = -1
-   rewind
>  position = 0,  mark = -1
-   remaining()
> limit - position
-   hasRemaining
> return position < limit
-   isDirect
> 判断是否使用了直接缓存区
-   compact
> 将数据压缩，防止部分写问题
-   mark
> set mark =position
-   reset
> Resets this buffer's position to the previously-marked position

### byteBuffer
-   get(byte[])
> 从buffer中读取数据到byte[]，
-   allocate(capacity)
> Allocates a new byte buffer.
-   wrap(byte[], offset, length)
> 将byte[]数组 复制到buffer中去, 从offset开始，长度为length
-   wrap(byte[])
> 将byte[]数组 复制到buffer中去
-   allocateDirect(capacity)
> 分配capacity空间直接缓存区

### FileChannel
-   map(MapMode, position, size)
> 将channel 转成 MapperByteBuffer, 以后可以用memory map操作文件

### 直接与非直接缓存区
-   直接缓存区
利用内存映射文件，操作内存等于操作文件，省去了从磁盘文件拷贝数据到用户缓存区的过程。
这个缓存区的位置在堆外，不由JVM管理，Java可以直接操作。         
使用方向在于，追求性能的I/O操作 , zore-copy             
实现方式： byteBuffer.allocateDirect()和Channel.map(MapMode, pos, size)                  
注意：All or part of a mapped byte buffer may become
inaccessible at any time, for example if the mapped file is truncated.  An
attempt to access an inaccessible region of a mapped byte buffer will not
change the buffer's content and will cause an unspecified exception to be
thrown either at the time of the access or at some later time.  It is
therefore strongly recommended that appropriate precautions be taken to
avoid the manipulation of a mapped file by this program, or by a
concurrently running program, except to read or write the file's content.       
简单说，如果操作不可访问的区域，试图访问该区域，该区域不会变化，还可能抛出异常。

-   非直接缓存区
需要经过用户空间的缓存区，才可以操作文件。

-   两者区别方法
isDirect()

allocateDirect, 
transferTo, 
map()--->MappedByteBuffer

MappedByteBuffer 使用了内存映射文件进行文件的读写。
内存映射文件就是在内存中找一段空白内存，然后将这部分内存与文件的内容对应起来。
我们对内存的所有操作都会直接反应到文件中去。mmap的主要功能就是建立内存与文件这种对应关系。
所以才被命名为memory map。

一句话： 通过操作内存来操作文件，省了从磁盘文件拷贝数据到内核，再到用户缓冲区的步骤，所以十分高效。

![mmap](https://pic4.zhimg.com/v2-07966aa3187a38a9e7e0def1bf07836f_b.png)





    
    
    
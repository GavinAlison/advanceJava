# Shell常用命令之free

free查看当前系统的内存及交换分区使用情况。

## 语法格式
free [options]

## 选项
```
-b：以Byte为单位显示内存使用情况
-k：以KB为单位显示内存使用情况
-m：以MB为单位显示内存使用情况
-g：以GB为单位显示内存使用情况
-h：以合适的单位显示内存使用情况，最大为三位数，自动计算对应的单位值。单位有：
    B = bytes
    K = kilos
    M = megas
    G = gigas
    T = teras

-o：不显示缓冲区调节列
-s<间隔秒数>：持续观察内存使用状况
-t：显示内存总和列
-V：显示版本信息
```

## 实例
查看当前系统内存使用状况

free -m
```bash
[jrxany@localhost ~]$ free -m 
              total        used        free      shared  buff/cache   available
Mem:           9838        4068         154          94        5614        5344
Swap:          1023           0        1023
[jrxany@localhost ~]$ 



# Mem（第二行）：物理内存描述信息
# Swap（第三行）：交换内存描述信息
# total（第二列）：系统总的可用物理内存大小
# used（第三列）：已被使用的物理内存大小
# free（第四列）：还有多少物理内存可用
# shared（第五列）：多个进程共享的内存总额；如oracle的共享内存
# buff/cache（第六列）：这里是两个内容的描述；buff表示I/O缓存、cache表示高速缓存。
# available（第七列）：还可以被应用程序使用的物理内存大小
```

### free 与 available 的区别

free 是真正尚未被使用的物理内存数量。
available是应用程序认为可用内存数量，available = free + buffer + cache (注：只是大概的计算方法)

Linux 为了提升读写性能，会消耗一部分内存资源缓存磁盘数据，对于内核来说，buffer 和 cache 
其实都属于已经被使用的内存。但当应用程序申请内存时，如果 free 内存不够，
内核就会回收 buffer 和 cache 的内存来满足应用程序的请求。这就是稍后要说明的 buffer 和 cache。

### buffer和cache

-   buff（Buffer Cache）    
是一种I/O缓存，用于内存和硬盘的缓冲，是io设备的读写缓冲区。
根据磁盘的读写设计的，把分散的写操作集中进行，减少磁盘碎片和硬盘的反复寻道，从而提高系统性能。
-   cache（Page Cache）    
是一种高速缓存，用于CPU和内存之间的缓冲 ,是文件系统的cache。把读取过的数据保存起来，重新读取时若命中
（找到需要的数据）就不要去读硬盘了，若没有命中就读硬盘。
其中的数据会根据读取频率进行组织，把最频繁读取的内容放在最容易找到的位置，
把不再读的内容不断往后排，直至从中删除。

## 动态查看内存使用情况

 free -m -s 1

```
[jrxany@localhost ~]$ free -m -s 1
              total        used        free      shared  buff/cache   available
Mem:           9838        4067         155          94        5615        5345
Swap:          1023           0        1023

              total        used        free      shared  buff/cache   available
Mem:           9838        4068         154          94        5615        5344
Swap:          1023           0        1023
```


### 释放内存
```
echo 1 > /proc/sys/vm/drop_caches
echo 2 > /proc/sys/vm/drop_caches
echo 3 > /proc/sys/vm/drop_caches

# 1表示清除page cache数据
# 2表示清除slab分配器中的对象（包括目录项缓存和inode缓存）
# 3表示清除pagecache和slab分配器中的缓存对象
# /proc/sys/vm/drop_caches的默认值是0.
# 清除数据以前记得sync，先同步缓存中的数据至硬盘，以免数据丢失。
```


>link
https://www.cnblogs.com/guge-94/p/12359841.html
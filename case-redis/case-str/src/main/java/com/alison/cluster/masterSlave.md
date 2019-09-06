## 链接
-   [分布式缓存Redis之主从复制与Sentinel哨兵](https://blog.csdn.net/u011489043/article/details/78809446)
-   [redis系列：主从复制](https://juejin.im/post/5b625b9be51d4519956759d0#heading-25)
-   [深入学习Redis（3）：主从复制](https://www.cnblogs.com/kismetv/p/9236731.html)

## 步骤

1.  Slave端在配置文件中添加了slave of指令，于是Slave启动时读取配置文件，初始状态为REDIS_REPL_CONNECT。
2.  Slave端在定时任务serverCron(Redis内部的定时器触发事件)中连接Master，发送sync命令，然后阻塞等待master发送回其内存快照文件**(最新版的Redis已经不需要让Slave阻塞)**。
3.  Master端收到sync命令简单判断是否有正在进行的内存快照子进程，没有则立即开始内存快照，有则等待其结束，当快照完成后会将该文件发送给Slave端。
4.  Slave端接收Master发来的内存快照文件，保存到本地，待接收完成后，清空内存表，重新读取Master发来的内存快照文件，重建整个内存表数据结构，并将最终状态置位为 REDIS_REPL_CONNECTED状态，Slave状态机流转完成。
5.  Master端在发送快照文件过程中，接收的任何会改变数据集的命令都会暂时先保存在Slave网络连接的发送缓存队列里（list数据结构），待快照完成后，依次发给Slave，之后收到的命令相同处理，并将状态置位为 REDIS_REPL_ONLINE。


### Redis主从复制
假设我们有三台Redis服务器，一台主服务器Master，两台从服务器slave，
slave中只能做读操作。Master可做读写操作，每次对Master进行写操作之后，
首先Master会将数据存储在硬盘中，然后通过sync同步命令将每一个slave的数据进行更新，
然后每一个slave都将更新的数据写在自己的硬盘中，这样就保证了数据的一致性。

在研发、测试环境可以考虑bind 0.0.0.0，线上生产环境建议绑定IP地址。

### master/slave步骤
master 启动
slave 启动，
进入slave命令行， 使用命令slaveof masterip masterport ,
注意： masterip 是指真实的ip地址，或者0.0.0.0, 不要设置localhost和127.0.0.1


### master/slave复制原理
1.  设置主服务器的地址和端口
2.  建立套接字连接
3.  发送PING命令
4.  身份验证
    有密码设置，需要验证密码
5.  发送端口信息
    从服务器会向主服务器发送自己的监听端口号。主服务器收到之后会将端口号记录到从服务器对应的状态属性中
6.  同步
    从服务器会向主服务器发送PSYNC命令，执行同步操作， 分为全部同步和部分同步
7.  命令传播
    当完成同步操作之后，主从服务器便会进入命令传播阶段。
    这时候主从服务器的数据是一致的，当主服务器有新的写命令时，会将改命令发送给从服务器，从服务器接收命令并执行便可以保证与主服务器的数据保持一致。
    
#### 问题 
1.  那么Redis是如何保证主从服务器一致处于连接状态以及命令是否丢失？      
        
命令传播阶段，从服务器会利用心跳检测机制定时的向主服务发送消息

**心跳检测机制**
主要作用：
-   检查主从服务器的网络连接状态
-   辅助实现min-slaves选项
-   检测命令丢失， 通过offset偏移量


### 全量同步与部分同步
**部分同步**    
-   复制偏移量
-   复制缓冲区
-   运行ID

由于复制缓冲区的大小是有限制的，所以保存的数据也是有限制的。
如果从服务器与主服务器的复制偏移量相差的数据大于复制缓冲去存储的数据时，同样不会执行部分重同步， 而是执行全量同步。




### 哨(shao)兵(sentinel)模式

对redis系统进行实时的监控

监测主数据库和从数据库是否正常运行。
当我们的主数据库出现故障的时候，可以自动将从数据库转换为主数据库，实现自动的切换。
当被监控的某个Redis出现问题时, 哨兵(sentinel) 可以通过 API 向管理员或者其他应用程序发送通知。


### FAQ
-   master 进行read and write, slave 是什么时候进行触发， 
-   master/slave, sentinel, cluster的区别
-   slaveof 配置主数据库出错，提示condition on socket for SYNC: Connection refused， 
    发现原因： slaveof 不支持localhost, 在配置文件中"bind 0.0.0.0", 不要配置127.0.0.1, 或者配置真实ip地址
-   其他可参考问题--https://www.cnblogs.com/sz-jack/p/5156913.html， https://blog.csdn.net/chwshuang/article/details/54929277
-   
    
















## 拓扑结构

redis-server
```
192.168.56.103:6379 master
192.168.56.103:7001 slave
192.168.56.103:7002 slave02
```
redis-sentinel 
```
192.168.56.103: 26379  
```

master的conf
```
bind 192.168.56.103
port 6379
protected-mode no
daemonize yes
loglevel notice
logfile "/usr/local/redis/master.log"

dir "/usr/local/redis/master"
requirepass redis
masterauth redis
```

slave的conf
```
daemonize yes
bind 192.168.56.103
port 7001
protected-mode no

loglevel notice
logfile /usr/local/redis/redis.log

requirepass redis
masterauth redis

dir /usr/local/redis/7001
pidfile  /usr/local/redis/7001/7001.pid
slaveof 192.168.56.103 6379
#slaveof 0.0.0.0 6379
```

sentinel.conf
```
daemonize yes
port 26379
bind 192.168.56.103
logfile "/usr/local/redis/master/sentinel.log"
pidfile "/use/local/redis/master/sentinel.pid"

dir "/usr/local/redis/master"
###########################
# 注意， 后面的1 表示 一个哨兵, 
sentinel monitor mymaster 192.168.56.103 6379 1
sentinel auth-pass mymaster redis
```

## 操作
启动master 和slave ， 在启动sentinel 服务
关闭master, check logfile， 主要查看master.log， slave.log, sentinel.log ,
主要观察sentinel.log

**master**

redis-server redis.conf

**slave**   

redis-server slave.conf

**sentinel**    

redis-server sentinel.conf --sentinel


##  原理
1. 每个 Sentinel（哨兵）进程以每秒钟一次的频率向整个集群中的 Master 主服务器，
Slave 从服务器以及其他 Sentinel（哨兵）进程发送一个 PING 命令。
（此处我们还没有讲到集群，下一章节就会讲到，这一点并不影响我们模拟哨兵机制）

2. 如果一个实例（instance）距离最后一次有效回复 PING 命令的时间超过 
down-after-milliseconds 选项所指定的值， 则这个实例会被 Sentinel（哨兵）进程标记
为主观下线（SDOWN）。

3. 如果一个 Master 主服务器被标记为主观下线（SDOWN），则正在监视这个 Master 
主服务器的所有 Sentinel（哨兵）进程要以每秒一次的频率确认 Master 主服务器的确进入了
主观下线状态。

4. 当有足够数量的 Sentinel（哨兵）进程（大于等于配置文件指定的值）
在指定的时间范围内确认 Master 主服务器进入了主观下线状态（SDOWN）， 
则 Master 主服务器会被标记为客观下线（ODOWN）。

5. 在一般情况下， 每个 Sentinel（哨兵）进程会以每 10 秒一次的频率向集群中的所有Master 
主服务器、Slave 从服务器发送 INFO 命令。

6. 当 Master 主服务器被 Sentinel（哨兵）进程标记为客观下线（ODOWN）时，
Sentinel（哨兵）进程向下线的 Master 主服务器的所有 Slave 从服务器发送 
INFO 命令的频率会从 10 秒一次改为每秒一次。

7. 若没有足够数量的 Sentinel（哨兵）进程同意 Master 主服务器下线， 
Master 主服务器的客观下线状态就会被移除。若 Master 主服务器重新向 
Sentinel（哨兵）进程发送 PING 命令返回有效回复，Master 主服务器的
主观下线状态就会被移除。


## 验证
./verify.png
```
sdown master mymaster 192.168.56.103 6379 quorun 1
```

## 问题
1. sentinel directive while not in sentinel mode问题解决方案      
注意参数--sentinel , 需要加上
2. sentinel  配置了多个，但是运行 sentinel master mymaster 查看num-slaves和numother-sentinel ，
发现一直为0和1        
原因： 主要是sentinel 配置文件中没有配置 密码， 
`sentinel auth-pass mymaster redis`

> 参考： 
>1.  [配置一主一从一哨兵](https://www.zhihu.com/search?type=content&q=redis%20sentinel%20%20)
>2.  [配置一主二从三哨兵](https://www.cnblogs.com/zhoujinyi/p/5570024.html)
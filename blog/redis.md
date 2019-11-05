## redis的数据类型


## redis的分布式锁
redission设计的分布式锁， 关键点，
1.  设置



## redis的问题



### 如何保证同一连接的所有请求顺序执行？      
使用了单线程+队列模式。        
把请求数据缓冲。然后pipeline发送，返回future，然后channel可读时，
直接在队列中把future取回来，done()就可以了。



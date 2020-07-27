# select、poll、epoll比较
## 区别
![区别](https://pic1.zhimg.com/v2-e6a869884585625dfc7eace1b90c3024_b.png)

select 每次调用进行线性遍历，时间复杂度O(n), 连接数有上线，2048(x64), 1024(x86)
poll 每次调用进行线性遍历，时间复杂度O(n), 连接数无上线
epoll 事件通知机制，好了通过回调就会被调用


> link: https://www.zhihu.com/search?type=content&q=poll

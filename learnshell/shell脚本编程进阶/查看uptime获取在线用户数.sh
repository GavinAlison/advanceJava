#!/usr/bin/env bash

查找当前登录用户数

uptime | sed 's/user.*$//' | gawk '{print $NF}'

sed 's/user.*$//'
替换:  user.*$ 为空

```
[jrxany@localhost ~]$ uptime
22:13:05 up 101 days, 21:44,  2 users,  load average: 0.28, 0.30, 0.21
```

#当前时间 22:13:05
#系统已运行的时间 101 days, 21:44
#当前在线用户 2 users
#平均负载: 0.28, 0.30, 0.21, 最近1分钟、5分钟、15分钟系统的负载






#!/usr/bin/env bash

#上传文件
scp c:\users\hy\Desktop\anyest-engine-admin-3.3.1.jar  jrxany@172.16.102.21:/home/jrxany/20200215_version/admin/anyest-engine-server-3.3.1/lib/

scp root@107.172.27.254:/home/test.txt .   //下载文件

scp test.txt root@107.172.27.254:/home  //上传文件

scp -r root@107.172.27.254:/home/test .  //下载目录

scp -r test root@107.172.27.254:/home   //上传目录

## 解压
tar -zxvf anyest.tar.gz
## 压缩
tar -zcvf all.tar *.txt
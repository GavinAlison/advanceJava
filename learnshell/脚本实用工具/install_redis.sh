#!/bin/bash
# auth:alison
# version:v1.0
# func:redis 5.0.9 安装
cd /opt
mkdir redis
cd redis
wget http://download.redis.io/releases/redis-5.0.9.tar.gz 
tar -zxvf redis-5.0.9.tar.gz
cd /redis-5.0.9
make
cd ..
make install PREFIX=./bin/

## 安装并使用iptables
# 关闭防火墙
systemctl stop firewalld 
# 安装
yum install iptables-services
# 启动
systemctl enable iptables
# 打开
systemctl start iptables
# 暴露端口配置
iptables -I INPUT -p tcp -m tcp --dport 6379 -j ACCEPT
# 保存配置
service iptables save
# 重启 iptables 服务
service iptables restart
# 查看下 iptable配置
iptables -L -n -v

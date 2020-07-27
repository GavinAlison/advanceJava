## Nginx 架构
master-slave 进程模式， 多进程模式    
master 进程主要用来管理 worker 进程

## nginx为什么采用这种进程模式


## Nginx常用功能
Http代理，反向代理
Http代理， nginx 与 多个client 直连
http反向代理, nginx 与多个 server 直连

## 负载均衡
nginx 自动实现的一些负载均衡算法，用于转发请求， 有轮询，加权轮询，Ip hash。

## 反向代理有哪些主要应用
许多大型web网站都用到反向代理。除了可以防止外网对内网服务器的恶性攻击、缓存以减少服务器的压力和访问安全控制之外，还可以进行负载均衡，将用户请求分配给多个服务器。

## 反向代理 简单实现
```
1、模拟n个http服务器作为目标主机

用作测试，简单的使用2个tomcat实例模拟两台http服务器，分别将tomcat的端口改为8081和8082

2、配置IP域名

192.168.56.101 8081.tomcat1
192.168.56.101 8082.tomcat2

3、配置nginx.conf


------
upstream tomcatserver1 {  
    server 192.168.72.49:8081;  
    }  
upstream tomcatserver2 {  
    server 192.168.72.49:8082;  
    }  
server {  
        listen       80;  
        server_name  8081.tomcat1;  
  
        #charset koi8-r;  
  
        #access_log  logs/host.access.log  main;  
  
        location / {  
            proxy_pass   http://tomcatserver1;  
            index  index.html index.htm;  
        }       
    }  
server {  
        listen       80;  
        server_name  8082.tomcat2;  
  
        #charset koi8-r;  
  
        #access_log  logs/host.access.log  main;  
  
        location / {  
            proxy_pass   http://tomcatserver2;  
            index  index.html index.htm;  
        }          
    }  
------
流程：
1）浏览器访问8081.tomcat1，通过本地host文件域名解析，找到192.168.72.49服务器（安装nginx）

2）nginx反向代理接受客户机请求，找到server_name为8081.tomcat1的server节点，根据proxy_pass对应的http路径，将请求转发到upstream tomcatserver1上，即端口号为8081的tomcat服务器。
```


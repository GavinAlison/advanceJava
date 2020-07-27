## 组件
-   ConnectionFactory（连接管理器）：
应用程序与Rabbit之间建立连接的管理器，程序代码中使用；
-   Channel（信道）：消息推送使用的通道；
-   RoutingKey（路由键）：用于把生成者的数据分配到交换器上；
-   Exchange（交换器）：用于接受、分配消息；
-   BindingKey（绑定键）：用于把交换器的消息绑定到队列上；
-   Queue（队列）：用于存储生产者的消息；

## 图
![RabbitMQ](http://icdn.apigo.cn/blog/rabbit-producer.gif)
![Broker](http://icdn.apigo.cn/blog/rabbitmq-flow3.png)

## 内容
1. Hello World入门实战
2. 工作队列轮询分发和公平分发
3. Publish/Subscribe（发布/订阅模式）
4. Routing（路由模式）
5. Topic（主题模式
6. rpc
7. spring boot 集成 rabbitmq

## 链接
>1. https://www.cnblogs.com/vipstone/p/9275256.html



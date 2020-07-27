## 1. ConnectFactory中host,port,virtual,username,password是如何设置的？
从https://spring.io/guides/gs/messaging-rabbitmq/中查看代码，“Spring Boot automatically creates a connection factory and a RabbitTemplate, reducing the amount of code you have to write.”
这句话，spring boot 自动创建工厂

## 2. rabbitadmin与rabbitTemplate的区别，还有AmqpAdmin
rabbitadmin 封装 rabbitTemplate ， 简化操作
rabbitTemplate  是 Spring AMQP 提供简化 RabbitMQ 发送和接收消息操作的工具
AmqpAdmin 跟rabbitTemplate 一样

## 



> 详细链接： https://docs.spring.io/spring-amqp/reference/html
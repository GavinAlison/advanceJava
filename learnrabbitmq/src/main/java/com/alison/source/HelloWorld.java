package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class HelloWorld {

    private static final String QUEUE_NAME = "hello";

    @Test
    public void send() throws IOException, TimeoutException {
        String message = "Hello World!";
        Connection conn = ConnectionUtil.createRabbitConnection();
        // 创建通道
        Channel channel = conn.createChannel();
        // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-routing headers，此属性为MessageProperties.PERSISTENT_TEXT_PLAIN用于设置纯文本消息存储到硬盘；参数四：消息主体】
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("发送信息:'" + message + "'");
        channel.close();
        conn.close();
    }

    @Test
    public void recv() throws Exception {
        Connection conn = ConnectionUtil.createRabbitConnection();
        // 创建通道
        Channel channel = conn.createChannel();
        // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" 等待信息中...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String routingKey = envelope.getRoutingKey(); // 队列名称
                String contentType = properties.getContentType(); // 内容类型
                String content = new String(body, "utf-8"); // 消息正文
                System.out.println("消息正文：" + content);
            }
        };
        // 创建订阅器，并接受消息, 自动ack, 设置消费者
        channel.basicConsume(QUEUE_NAME, true, consumer);
        Thread.sleep(10000);
    }


}

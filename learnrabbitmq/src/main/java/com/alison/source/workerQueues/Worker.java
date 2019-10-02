package com.alison.source.workerQueues;

import com.alison.source.ConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Authtor alsion
 * @DateTime 2019-10-01 22:15
 * @Link https://blog.csdn.net/yhl_jxy/article/details/85322696
 */
public class Worker {
    private Connection connection = ConnectionUtil.getRabbitConnection();


    private final static String WORK_QUEUE_NAME = "work-queue";

    @Test
    public void send() throws IOException, InterruptedException, TimeoutException {
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明消息队列
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
         /*
          每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息。
          限制发送给同一个消费者不得超过1条消息。
        */
        int perfetchCount = 1;
        channel.basicQos(perfetchCount);
        // 消息发送
        for (int i = 0; i < 50; i++) {
            String message = "hello " + i;
            System.out.println("producer：" + message);
            channel.basicPublish("", WORK_QUEUE_NAME, null, message.getBytes());
            // 每次消息发送后休眠下，避免消息发送太快，看不出效果
            Thread.sleep(i + 20);
        }
        // 关闭资源
        channel.close();
    }

    @Test
    public void consumeOne() throws IOException, InterruptedException, TimeoutException {
// 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
        // 采用fair 公平分发
        // 一次只处理一个, 只有在手动应答之后，才接收到下一个消息
        channel.basicQos(1);

        // 定义一个消费者监听消息
        Consumer consumer = new DefaultConsumer(channel) {
            // 一旦有消息，触发该方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者1：" + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者1：" + "Done");
                }
            }
        };

        channel.basicConsume(WORK_QUEUE_NAME, false, consumer);

        // 让程序处于运行状态，让消费者监听消息
        Thread.sleep(1000 * 60);
    }

    @Test
    public void consumerTwo() throws IOException, TimeoutException, InterruptedException {
        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
        // 采用fair 公平分发
        // 一次只处理一个, 只有在手动应答之后，才接收到下一个消息
        channel.basicQos(1);
        // 定义一个消费者监听消息
        Consumer consumer = new DefaultConsumer(channel) {
            // 一旦有消息，触发该方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 处理业务逻辑
                String message = new String(body, "UTF-8");
                System.out.println("消费者2：" + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者2：" + "Done");
                }
            }
        };
        // fair 分发机制，设置不自动应答，需要手动应答
        channel.basicConsume(WORK_QUEUE_NAME, false, consumer);

        // 让程序处于运行状态，让消费者监听消息
        Thread.sleep(1000 * 60);
    }
}

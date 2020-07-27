package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

public class Direct {
    private static final String EXCHANGE_NAME = "direct_logs";


    @Test
    public void send() throws IOException {
        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();
        // default exchange ，默认的exchange
        // 这里declare一下exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//        String routingKey = "info";
//        String routingKey = "warning";
        String routingKey = "error";
//        String message = "info message!";
//        String message = "warning message!";
        String message = "error message!";
//推送direct交换器消息到对于的队列，空字符为默认的direct交换器，用队列名称当做路由键
//        channel.basicPublish("", QueueName, null, message)
//        推送内容【参数说明：参数一：交换机名称；参数二：路由键(队列名称)，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        System.out.println("send message, routing key: " + routingKey + ", message: " + message);
    }

    @Test
    public void receive() throws IOException {
        //String[] argv = new String[]{"info"};
        //String[] argv = new String[]{"error"};
        //String[] argv = new String[]{"warning"};
        String[] argv = new String[]{"warning", "error"};
        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        for (String routingKey : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
            System.out.println(" 等待" + routingKey + "信息中...");
        }

        Consumer consumer = new DefaultConsumer(channel) {
            //
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" 接收到信息:. '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
//        第一个参数是队列的名字；第二个参数表示是否自动确认消息的接收情况，我们使用true，自动确认；第三个参数需要传入一个实现了Consumer接口的对象，
        channel.basicConsume(queueName, true, consumer);
        System.in.read();
    }
}

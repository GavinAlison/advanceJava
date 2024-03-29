package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

public class Fanout {

    private static final String EXCHANGE_NAME = "logs";

    @Test
    public void publish() throws IOException {
        Channel channel = ConnectionUtil.getRabbitConnection().createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String message = "info: Hello World!";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" 发送信息:'" + message + "'");
    }

    @Test
    public void subscribe() throws IOException {
        Channel channel = ConnectionUtil.getRabbitConnection().createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        //让服务器为我们选择一个随机的队列名
        String queueName = channel.queueDeclare().getQueue();
        //队列绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" waiting......");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" receive message:. '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }
}

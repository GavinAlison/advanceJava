package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Headers {

    private static final String EXCHANGE_NAME = "header_exchange";

    @Test
    public void send() throws IOException {
        String message = "message";
        String routingKey = "ourTestRoutingKey";

        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        Map<String, Object> headers = new HashMap<>();
        headers.put("api", "login");
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.headers(headers);
        AMQP.BasicProperties theProps = builder.build();

        channel.basicPublish(EXCHANGE_NAME, "", theProps, message.getBytes("UTF-8"));
        System.out.println(" send:message: '" + message + "'");
    }

    @Test
    public void receiveLogHeader() throws IOException {
        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();

        String routingKey = "ourTestRoutingKey";
        String QUEUE_NAME = "header-queue";

        //声明转发器和类型headers
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);

        Map<String, Object> headers = new Hashtable<String, Object>();
        headers.put("x-match", "any");//all any
        headers.put("api", "login");
        headers.put("version", "1.0");
        // 为转发器指定队列，设置binding 绑定header键值对
        String queuName = channel.queueDeclare().getQueue();
        channel.queueBind(queuName, EXCHANGE_NAME, "", headers);

        System.out.println(" waiting...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" receive message:. '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.in.read();
    }

}

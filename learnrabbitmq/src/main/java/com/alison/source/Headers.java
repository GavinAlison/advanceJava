package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Headers {

    private static final String EXCHANGE_NAME = "header_test";

    @Test
    public void emitLogHeader() throws IOException {

        String message = "message";
        String routingKey = "ourTestRoutingKey";
        Map<String, Object> headers = new HashMap<>();
        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();

        builder.deliveryMode(MessageProperties.PERSISTENT_TEXT_PLAIN.getDeliveryMode());
        builder.priority(MessageProperties.PERSISTENT_TEXT_PLAIN.getPriority());

        builder.headers(headers);

        AMQP.BasicProperties theProps = builder.build();

        channel.basicPublish(EXCHANGE_NAME, routingKey, theProps, message.getBytes("UTF-8"));
        System.out.println(" 发送信息:message: '" + message + "'");
    }

    @Test
    public void receiveLogHeader() throws IOException {
        String[] argv = new String[2];
        argv[0] = "ourTestRoutingKey";

        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        String routingKeyFromUser = "ourTestRoutingKey";

        String queueInputName = argv[0];
        Map<String, Object> headers = new HashMap<String, Object>();
//        headers.put(argv[0], argv[1]);
//        System.out.println("Binding header " + argv[0] + " and value " + argv[1] + " to queue " + queueInputName);

        String queueName = channel.queueDeclare(queueInputName, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKeyFromUser, headers);

        System.out.println(" 等待信息中...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" 接收到信息:. '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

}

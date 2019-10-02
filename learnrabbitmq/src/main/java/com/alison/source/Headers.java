package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @Author: alison
 * @Date2019-10-01 17:48
 * @link   https://blog.csdn.net/vbirdbest/article/details/78638988
 */
public class Headers {

    private static final String EXCHANGE_NAME = "header_exchange";

    @Test
    public void send() throws IOException {
        String message = "message";
        String routingKey = "ourTestRoutingKey";

        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put("api", "login");
        headers.put("token", "123456asdasd");
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.headers(headers);
        AMQP.BasicProperties theProps = builder.build();

        channel.basicPublish(EXCHANGE_NAME, "", theProps, message.getBytes("UTF-8"));
        System.out.println(" send:message: '" + message + "'");
    }

    @Test
    public void receiveLogHeader() throws Exception {
        Connection connection = ConnectionUtil.getRabbitConnection();
        Channel channel = connection.createChannel();

        String routingKey = "ourTestRoutingKey";
        String QUEUE_NAME = "header-queue";

        //声明转发器和类型headers
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        Map<String, Object> headers = new Hashtable<String, Object>();
        headers.put("x-match", "any");//all any
        /**
         * all: 只要在发布消息时携带的有一对键值对headers满足队列定义的多个参数arguments的其中一个就能匹配上，
         * 注意这里是键值对的完全匹配，只匹配到键了，值却不一样是不行的；
         * any: 在发布消息时携带的所有Entry必须和绑定在队列上的所有Entry完全匹配
         */
        headers.put("api", "login");
        headers.put("version", "1.0");
        // 为转发器指定队列，设置binding 绑定header键值对, 获取队列名
        String queuName = channel.queueDeclare().getQueue();
        channel.queueBind(queuName, EXCHANGE_NAME, "", headers);

        System.out.println(" waiting......");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" receive message:. '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queuName, true, consumer);
        Thread.sleep(10000);
    }

}

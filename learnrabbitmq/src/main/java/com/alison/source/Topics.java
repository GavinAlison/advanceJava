package com.alison.source;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 *
 * @Description:
 * 2）使用Topic模式生产者在声明队列时需要制定消息到达队列方式为topic；
 * 3）路由键和某模式匹配，主要有两种模糊匹配：
 *    # 匹配一个或多个，* 匹配一个，一般使用#号匹配多个，*号用的比较少。
 */
public class Topics {

    private static final String EXCHANGE_NAME = "topic_logs";

    @Test
    public void send() throws IOException {
        Channel channel = ConnectionUtil.getRabbitConnection().createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        /**
         * * (星号) 用来表示一个单词.
         * # (井号) 用来表示任意数量（零个或多个）单词。
         */
        String routingKey = "news.china.tourism";//国内的旅游新闻
        String routingKeyEnd = "news.china.tourism.end";//国内的旅游新闻
        //String routingKey = "news.abroad.tourism";//国外的旅游新闻
        //String routingKey = "recommend.*.tourism";//国内外的旅游推荐
        //String routingKey = "news.*.tourism";//国内外的旅游新闻
        //String routingKey = "*.*.tourism";//所有的旅游信息

        String message = "Hello World!";

        channel.basicPublish(EXCHANGE_NAME, routingKeyEnd, null, message.getBytes("UTF-8"));
        System.out.println(" 发送信息:'" + routingKeyEnd + "':'" + message + "'");
    }

    @Test
    public void receive() throws IOException {
        Channel channel = ConnectionUtil.getRabbitConnection().createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //让服务器为我们选择一个随机的队列名
        String queueName = channel.queueDeclare().getQueue();

        String routingKey = "news.china.tourism";//国内的旅游新闻
        String routingKeyAbroad = "news.abroad.tourism";//国外的旅游新闻
        String routingKeyReconmend = "recommend.*.tourism";//国内外的旅游推荐
        String routingKeyT = "news.*.tourism";//国内外的旅游新闻
        String routingKeyTou = "*.*.tourism";//所有的旅游信息
        String routingAny = "news.#";
        channel.queueBind(queueName, EXCHANGE_NAME, routingAny);

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
        System.in.read();
    }
}

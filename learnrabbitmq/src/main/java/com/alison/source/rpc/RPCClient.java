package com.alison.source.rpc;

import com.alison.source.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient {

    private String requestQueueName = "rpc_queue";

    Channel channel = null;

    public RPCClient() throws Exception {
        channel = ConnectionUtil.getRabbitConnection().createChannel();
    }

    public static void main(String[] argv) {
        RPCClient fibonacciRpc = null;
        try {
            fibonacciRpc = new RPCClient();
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fibonacciRpc != null) {
                try {
                    fibonacciRpc.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public String call(String message) throws Exception {
        String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            // corrId 是为了防止幂等操作
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });
        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws Exception {
        channel.close();
    }


}

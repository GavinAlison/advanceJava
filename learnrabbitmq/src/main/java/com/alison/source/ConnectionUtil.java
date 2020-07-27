package com.alison.source;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

    protected static Connection connection = null;

    public static class Constant {
        public final static String HOST = "192.168.56.103";
        public final static int PORT = 5672;
        public final static String VIRTUAL_HOST = "/";
        public final static int CONNECT_TIMEOUT = 10000;
        public final static String USERNAME = "guest";
        public final static String PASSWORD = "guest";
    }

    public static Connection createRabbitConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.HOST);
        factory.setPort(Constant.PORT);
        factory.setVirtualHost(Constant.VIRTUAL_HOST);
        factory.setConnectionTimeout(Constant.CONNECT_TIMEOUT);
        factory.setUsername(Constant.USERNAME);
        factory.setPassword(Constant.PASSWORD);
        try {
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getRabbitConnection() {
        // uri: amqp://username:password@hostname:portnumber/virtualHost
        String uri = String.format("amqp://%s:%s@%s:%d%s", Constant.USERNAME, Constant.PASSWORD, Constant.HOST, Constant.PORT, Constant.VIRTUAL_HOST);
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            factory.setVirtualHost("/");
            factory.setConnectionTimeout(Constant.CONNECT_TIMEOUT);
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}

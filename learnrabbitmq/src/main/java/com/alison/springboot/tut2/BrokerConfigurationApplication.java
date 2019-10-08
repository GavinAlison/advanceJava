//package com.alison.springboot.tut2;
//
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * @Author alison
// * @Date 2019/10/6  22:50
// * @Version 1.0
// * @Description
// */
//
//public class BrokerConfigurationApplication {
//
//    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("rabbitConfiguration.xml");
//        AmqpAdmin amqpAdmin =context.getBean(AmqpAdmin.class);
//        Queue helloWorldQueue = new Queue("hello.world.queue");
//
//        amqpAdmin.declareQueue(helloWorldQueue);
//    }
//
//}

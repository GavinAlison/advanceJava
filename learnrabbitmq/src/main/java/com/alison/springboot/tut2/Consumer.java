//package com.alison.springboot.tut2;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
///**
// * @Author alison
// * @Date 2019/10/6  23:02
// * @Version 1.0
// * @Description
// */
//public class Consumer {
//    public static void main(String[] args) {
////        AnnotationConfigApplicationContext 可以手动加载对应的Configuration class
//        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
//        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
//        System.out.println("Received: " + amqpTemplate.receiveAndConvert());
//    }
//}

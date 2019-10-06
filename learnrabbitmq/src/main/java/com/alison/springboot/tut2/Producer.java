package com.alison.springboot.tut2;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author alison
 * @Date 2019/10/6  23:01
 * @Version 1.0
 * @Description
 */
public class Producer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("Hello World");
        System.out.println("send: Hello World");
    }

}

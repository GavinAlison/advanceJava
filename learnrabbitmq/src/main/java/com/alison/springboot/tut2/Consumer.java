package com.alison.springboot.tut2;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author alison
 * @Date 2019/10/6  23:02
 * @Version 1.0
 * @Description
 */
public class Consumer {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext 可以手动加载对应的Configuration class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("tut2");
        context.register(HelloWorldConfiguration.class);
        context.refresh();
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // receive once
        System.out.println("Received: " + rabbitTemplate.receiveAndConvert());
    }
}

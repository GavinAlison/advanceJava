package com.alison.springboot.tut2;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Author alison
 * @Date 2019/10/6  23:01
 * @Version 1.0
 * @Description
 */
public class Producer {


    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("tut2");
        context.register(HelloWorldConfiguration.class);
        context.refresh();

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        // send once
        rabbitTemplate.convertAndSend("Hello World");
        System.out.println("send: Hello World");
        Thread.sleep(10000);
    }

}

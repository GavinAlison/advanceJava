package com.alison.springboot.tut3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author alison
 * @Date 2019/10/6  23:11
 * @Version 1.0
 * @Description
 */
public class AsyncConsumer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("tut3");
        context.register(ConsumerConfiguration.class);
        context.refresh();
    }
}

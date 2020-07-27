package com.alison.springboot.tut3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author alison
 * @Date 2019/10/6  23:09
 * @Version 1.0
 * @Description
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("tut3");
        context.register(ProducerConfiguration.class);
        context.refresh();
    }

}

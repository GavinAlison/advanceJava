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
        new AnnotationConfigApplicationContext(ProducerConfiguration.class).getEnvironment().setActiveProfiles("tut3");
    }

}

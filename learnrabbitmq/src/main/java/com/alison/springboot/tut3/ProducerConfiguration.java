package com.alison.springboot.tut3;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author alison
 * @Date 2019/10/6  23:09
 * @Version 1.0
 * @Description
 */
@Profile({"tut3", "async-producer"})
@Configuration
public class ProducerConfiguration {

    protected final String helloWorldQueueName = "hello.world.queue";

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.helloWorldQueueName);
        return template;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.56.103");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public ScheduledProducer scheduledProducer() {
        // 构造定时器， 开启定时功能， scheduleProducer
        return new ScheduledProducer();
    }

    @Bean
    public BeanPostProcessor postProcessor() {
        // 构造定时器， 开启定时功能， scheduleAnnotationBeanPostProcessor， 加入到spring 容器中
        return new ScheduledAnnotationBeanPostProcessor();
    }

    static class ScheduledProducer {

        @Autowired
        private volatile RabbitTemplate rabbitTemplate;

        private final AtomicInteger counter = new AtomicInteger();

        @Scheduled(fixedRate = 3000)
        public void sendMessage() {
            rabbitTemplate.convertAndSend("Hello World " + counter.incrementAndGet());
        }
    }

}

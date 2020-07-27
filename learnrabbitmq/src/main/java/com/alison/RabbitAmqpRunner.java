package com.alison;

import com.alison.springboot.tut1.Tut1Config;
import com.alison.springboot.tut1.Tut1Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author alison
 * @Date 2019/10/3  19:50
 * @Version 1.0
 * @Description
 */
@Component
public class RabbitAmqpRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    private final Tut1Receiver receiver;

    public RabbitAmqpRunner(Tut1Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(Tut1Config.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }


}

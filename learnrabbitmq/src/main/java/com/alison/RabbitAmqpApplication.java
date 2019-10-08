package com.alison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitAmqpApplication {
    // cmd: --spring.profiles.active=tut1
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RabbitAmqpApplication.class, args).close();
    }
}
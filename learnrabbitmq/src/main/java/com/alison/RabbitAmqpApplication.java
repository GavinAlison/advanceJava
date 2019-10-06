package com.alison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitAmqpApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RabbitAmqpApplication.class, args).close();
    }
}
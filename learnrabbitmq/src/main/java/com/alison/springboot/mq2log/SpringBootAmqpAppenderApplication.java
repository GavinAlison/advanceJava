package com.alison.springboot.mq2log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringBootAmqpAppenderApplication {

    private static final Log logger = LogFactory.getLog(SpringBootAmqpAppenderApplication.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootAmqpAppenderApplication.class, args);
        System.out.println("Hit 'Enter' to terminate");
        System.in.read();
        ctx.close();
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
    public void generateLog() {
        logger.info("Log via AmqpAppender: " + new Date());
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "log4j2Sample", type = ExchangeTypes.FANOUT),
            value = @org.springframework.amqp.rabbit.annotation.Queue))
    public void echoLogs(String logMessage) {
        System.out.println("Logs over Log4J AmqpAppender: " + logMessage);
    }
}

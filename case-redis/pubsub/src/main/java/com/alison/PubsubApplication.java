package com.alison;

import com.alison.service.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;

import static com.alison.service.Const.PATTERN_TOPIC;

/**
 * @link https://www.tianmaying.com/tutorial/springboot-redis-message
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class PubsubApplication {

    //    使用Spring Boot默认的RedisConnectionFactory,是Jedis Redis库提供的JedisConnectionFactory实现
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
    //        我们将在listenerAdapter方法中定义的Bean注册为一个消息监听者，它将监听chat主题的消息。
        container.addMessageListener(listenerAdapter, new PatternTopic(PATTERN_TOPIC));
        return container;
    }

    //    因为Receiver类是一个POJO，要将它包装在一个消息监听者适配器（实现了MessageListener接口）
    //    这样才能被监听者容器RedisMessageListenerContainer的addMessageListener方法添加到连接工厂中。
    //    有了这个适配器，当一个消息到达时，就会调用receiveMesage()`方法进行响应。
    //    配置好连接工厂和消息监听者容器，你就可以监听消息啦！
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(PubsubApplication.class, args);
        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);

        log.info("Sending message...");
        //  使用StringRedisTemplate来发送键和值均为字符串的消息
        template.convertAndSend("chat", "Hello from Redis!");
        latch.await();
        System.exit(0);
    }
}

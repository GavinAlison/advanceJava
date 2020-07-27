package com.alison.springboot.tut3;

import com.alison.springboot.tut2.HelloWorldConfiguration;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author alison
 * @Date 2019/10/6  23:05
 * @Version 1.0
 * @Description
 */

@Profile({"tut3", "async"})
@Configuration
public class ConsumerConfiguration extends HelloWorldConfiguration {

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        // 设置简单的container，container里面包括一些 connect factory, queue, message listener
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(this.helloWorldQueueName);
        // 不断监听queue，然后处理消息
        container.setMessageListener(new MessageListenerAdapter(new HelloWorldHandler(), "handleMessage"));
        return container;
    }

}
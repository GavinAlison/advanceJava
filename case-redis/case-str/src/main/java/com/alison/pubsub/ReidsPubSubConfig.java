package com.alison.pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Configuration
public class ReidsPubSubConfig {

    /**
     * 消息监听器，使用MessageAdapter可实现自动化解码及方法代理
     */
//    @Bean
//    public MessageListenerAdapter listener(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer, MessageSubscriber subscriber) {
//        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber, "onMessage");
//        adapter.setSerializer(jackson2JsonRedisSerializer);
//        adapter.afterPropertiesSet();
//        return adapter;
//    }

    /**
     * 将订阅器绑定到容器
     *
     * @param connectionFactory
     * @param listener
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, new PatternTopic("/redis/*")); //配置要订阅的订阅项
        return container;
    }
}

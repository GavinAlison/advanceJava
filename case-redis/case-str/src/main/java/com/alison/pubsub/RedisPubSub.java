package com.alison.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class RedisPubSub {

    private RedisTemplate redisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    // 因为其他地方使用了setRedisTemplate , 所以RedisConfig 的配置会被替代
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置key/hashkey序列化
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 设置值序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        this.redisTemplate = template;
    }

    private ChannelTopic topic = new ChannelTopic(Const.PUBSUB);

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    private void schedule() {
        log.info("publish message");
        publish("admin", "hey you must go now!");
    }

    /**
     * 推送消息
     *
     * @param publisher
     * @param content
     */
    public void publish(String publisher, String content) {
        log.info("message send {} by {}", content, publisher);
        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setCreateTime(new Date().getTime());
        pushMsg.setUpdateTime(new Date().getTime());
        pushMsg.setPublisher(publisher);
        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }
}

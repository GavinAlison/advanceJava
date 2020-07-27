package com.alison.pubsub;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSubscriber  extends MessageListenerAdapter {

    public void onMessage(SimpleMessage message, String pattern) {
        log.info("topic {} received {} ", pattern, JSON.toJSON(message));
    }

}
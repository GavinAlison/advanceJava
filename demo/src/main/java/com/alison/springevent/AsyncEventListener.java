package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Classname AsyncEventListener
 * @Date 2021/10/1 0:14
 * @Created by alison
 */
@Slf4j
@Component
public class AsyncEventListener {

    @Async
    @EventListener
    public void onEvent(MyApplicationEvent event) {
        log.info("event=={}", event);
        throw new NullPointerException("AsyncEventListener");
    }
}

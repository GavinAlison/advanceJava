package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Classname EventListenerer
 * @Date 2021/9/30 0:37
 * @Created by alison
 */
@Slf4j
@Component
public class EventListenerer {

    @EventListener(classes = MyApplicationEvent.class)
    public void sendEvent() {
        log.info("处理事件 EventListenerer");
        throw new NullPointerException("exception......");
    }

    @EventListener
    public void sendEvent(MyApplicationEvent event) {
        log.info("处理事件 EventListenerer=={}", event);
    }

    @Order(1)
    @EventListener
    public void sendEventForOrder1(MyApplicationEvent event) {
        log.info("处理事件 sendEventForOrder1=={}", event);
    }

    @Order(2)
    @EventListener
    public void sendEventForOrder2(MyApplicationEvent event) {
        log.info("处理事件 sendEventForOrder2=={}", event);
    }


}

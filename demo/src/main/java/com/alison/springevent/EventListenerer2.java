package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * @Classname EventListenerer
 * @Date 2021/9/30 0:37
 * @Created by alison
 */
@Slf4j
//@Component
public class EventListenerer2 {

    public void sendEvent() {
        log.info("处理事件 EventListenerer");
    }

    @MyEvenListener
    public void sendEvent(MyApplicationEvent event) {
        log.info("处理事件 MyEvenListener=={}", event);
    }

    @Order(1)
    @MyEvenListener
    public void sendEventForOrder1(MyApplicationEvent event) {
        log.info("MyEvenListener处理事件 sendEventForOrder1=={}", event);
    }

    @Order(2)
    @MyEvenListener
    public void sendEventForOrder2(MyApplicationEvent event) {
        log.info("MyEvenListener处理事件 sendEventForOrder2=={}", event);
    }


}

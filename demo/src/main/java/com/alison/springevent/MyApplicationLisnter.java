package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @Classname MyApplicationLisnter
 * @Date 2021/9/30 0:15
 * @Created by alison
 */
@Slf4j
//@Component
public class MyApplicationLisnter implements ApplicationListener<MyApplicationEvent> {
    @Override
    public void onApplicationEvent(MyApplicationEvent myApplicationEvent) {
        log.info("--- MyApplicationListener: {}", myApplicationEvent);
    }
}

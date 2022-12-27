package com.alison.springevent;

import org.springframework.context.ApplicationEvent;

/**
 * @Classname MyApplicationEvent
 * @Date 2021/9/30 0:15
 * @Created by alison
 */
public class MyApplicationEvent extends ApplicationEvent {
    public MyApplicationEvent(Object source) {
        super(source);
    }
}

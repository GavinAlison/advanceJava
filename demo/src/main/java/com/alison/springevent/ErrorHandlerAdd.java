package com.alison.springevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Classname ErrorHandlerAdd
 * @Date 2021/10/1 0:29
 * @Created by alison
 */
@Component
public class ErrorHandlerAdd {

    @Autowired
    private SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;

    @Autowired
    private MyErrorHandler myErrorHandler;

    @PostConstruct
    public void init() {
        simpleApplicationEventMulticaster.setErrorHandler(myErrorHandler);
    }

}

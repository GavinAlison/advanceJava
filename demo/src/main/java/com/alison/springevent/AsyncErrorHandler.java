package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Classname AsyncErrorHandler
 * @Date 2021/10/1 0:33
 * @Created by alison
 */
@Slf4j
@Component
public class AsyncErrorHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error(">>>>>>>>>>>>>>>>>>>handleUncaughtException<<");
        log.error("{}--{}-- {}", throwable, method, objects);
    }
}

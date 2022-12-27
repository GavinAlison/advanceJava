package com.alison.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * @Classname MyErrorHandler
 * @Date 2021/10/1 0:11
 * @Created by alison
 */


@Slf4j
@Component
public class MyErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        log.error("has error === > {}", throwable);
    }
}
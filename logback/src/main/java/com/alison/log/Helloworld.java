package com.alison.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author alison
 * @Date 2020/3/12
 * @Version 1.0
 * @Description
 */
public class Helloworld {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("com.alison");
        logger.debug("hello world!");
        // 打印内部的状态
//        logback-test.xml
//        logback.groovy
//        logback.xml
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
//        打印了 logback 自身的内部状态
        StatusPrinter.print(lc);
    }
}

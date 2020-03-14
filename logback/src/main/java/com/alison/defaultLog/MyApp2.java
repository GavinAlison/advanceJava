package com.alison.defaultLog;

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
public class MyApp2 {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyApp2.class);

    public static void main(String[] args) {
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        LOGGER.info("Entering application.");
        Foo foo = new Foo();
        foo.doIt();
        LOGGER.info("Exiting application.");
    }
}

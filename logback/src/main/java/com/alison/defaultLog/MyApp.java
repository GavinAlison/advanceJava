package com.alison.defaultLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author alison
 * @Date 2020/3/12
 * @Version 1.0
 * @Description
 */
public class MyApp {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {
        LOGGER.info("Entering application.");

        Foo foo = new Foo();
        foo.doIt();
        LOGGER.info("Exiting application.");
    }
}

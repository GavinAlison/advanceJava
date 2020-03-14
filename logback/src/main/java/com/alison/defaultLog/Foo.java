package com.alison.defaultLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author alison
 * @Date 2020/3/12
 * @Version 1.0
 * @Description
 */
public class Foo {

    public static final Logger LOGGER = LoggerFactory.getLogger(Foo.class);

    public void doIt() {
        LOGGER.debug("Did it again!");
    }
}

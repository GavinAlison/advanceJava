package com.alison.log;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author alison
 * @Date 2020/3/12
 * @Version 1.0
 * @Description
 */
public class SelectionRule {
    public static void main(String[] args) {
        // ch.qos.logback.classic.Logger 可以设置日志的级别
        // 获取一个名为 "com.alison" 的 logger 实例
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.alison");
        // 设置 logger 的级别为 INFO
        logger.setLevel(Level.INFO);

        // 这条日志可以打印，因为 WARN >= INFO
        logger.warn("警告信息");
        // 这条日志不会打印，因为 DEBUG < INFO
        logger.debug("调试信息");

        // "com.foo.bar" 会继承 "com.foo" 的有效级别
        Logger barLogger = LoggerFactory.getLogger("com.alison.bar");
        // 这条日志会打印，因为 INFO >= INFO
        barLogger.info("子级信息");
        // 这条日志不会打印，因为 DEBUG < INFO
    }
}

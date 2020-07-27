package com.alison.spring.aop.springaop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author alison
 * @Date 2019/11/14  22:40
 * @Version 1.0
 * @Description
 */
public class Controller {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:aopContainer.xml");
        UserService userService = ctx.getBean("userService", UserServiceImpl.class);
        userService.save();
    }
}

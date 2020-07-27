package com.alison.demo1.controller;

import com.alison.demo1.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

//    @GetMapping("/hello")
//    public String sayHello() {
//        return helloService.sayHello();
//    }

    @GetMapping("/unknown")
    public void unknownError() {
        throw new ArithmeticException("/unknown error");
    }

    @GetMapping("/mysql")
    public Long getCounterFromMysql() {
        return helloService.getCounterFromMysql();
    }

    @GetMapping("/redis")
    public Long getCounterFromRedis() {
        return helloService.getCounterFromRedis();
    }

    @GetMapping("/user")
    public String getCurrentUser(String username) {
        return username;
    }

}

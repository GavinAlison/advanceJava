package com.alison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/index")
    public String index() {
        System.out.println(redisTemplate.getConnectionFactory().getConnection().ping());
        return "hello";
    }

}

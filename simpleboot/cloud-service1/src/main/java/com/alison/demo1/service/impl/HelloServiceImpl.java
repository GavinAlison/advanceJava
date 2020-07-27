package com.alison.demo1.service.impl;

import com.alison.demo1.domain.po.Hello;
import com.alison.demo1.repository.HelloRepository;
import com.alison.demo1.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @Override
//    public String sayHello() {
//        log.info("demo1 sayHello()");
//        Response<String> helloResponse = demo2ClientService.sayHello();
//        ResponseUtils.checkResponse(helloResponse);
//        return helloResponse.getData();
//    }

    @Override
    public Long getCounterFromMysql() {
        Hello hello = new Hello();
        helloRepository.save(hello);
        return helloRepository.count();
    }

    @Override
    public Long getCounterFromRedis() {
        return redisTemplate.opsForValue().increment("DEMO1:COUNTER:KEY", 1L);
    }

}

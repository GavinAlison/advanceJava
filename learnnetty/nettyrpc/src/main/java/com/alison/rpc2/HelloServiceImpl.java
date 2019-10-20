package com.alison.rpc2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @Author alison
 * @Date 2019/10/16  21:57
 * @Version 1.0
 * @Description
 */
//服务端实现
@Profile("server")
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello, " + name;
    }
}

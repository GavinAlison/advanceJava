package com.alison.rpc2;

import com.alison.rpc2.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

/**
 * @Author alison
 * @Date 2019/10/16  21:55
 * @Version 1.0
 * @Description
 */
@SpringBootApplication
@Slf4j
public class ClientApplication {
    public static void main(String[] args) throws Exception {
//        System.setProperty("serlvet.port", "8010");
//        System.setProperty("spring.profiles", "client");
//        SpringApplication.run(ClientApplication.class, args);

        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", 8010);

        new SpringApplicationBuilder(ServerApplication.class)
                .properties(props)
                .profiles("client")
                .build().run(args);
        HelloService helloService = ProxyFactory.create(HelloService.class);
        log.info("响应结果“: {}",helloService.hello("alison"));
    }
}

package com.alison.rpc2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * @Author alison
 * @Date 2019/10/16  22:17
 * @Version 1.0
 * @Description
 */
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) throws Exception {
//        System.setProperty("serlvet.port", "8011");
//        System.setProperty("spring.profiles", "server");
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", 8011);

        new SpringApplicationBuilder(ServerApplication.class)
                .properties(props)
                .profiles("server")
                .build().run(args);

    }
}

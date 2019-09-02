package com.alison.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/hello/1")
    public String error() {
        throw new RuntimeException("默契");
    }
}

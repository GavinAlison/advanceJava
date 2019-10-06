package com.alison.springboot.tut3;

/**
 * @Author alison
 * @Date 2019/10/6  23:07
 * @Version 1.0
 * @Description
 */
public class HelloWorldHandler {
    // handleMessage 是default Method
    public void handleMessage(String text) {
        System.out.println("Received: " + text);
    }
}

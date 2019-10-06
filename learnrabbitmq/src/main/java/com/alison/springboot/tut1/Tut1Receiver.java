package com.alison.springboot.tut1;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/10/3  19:54
 * @Version 1.0
 * @Description
 */
@Component
public class Tut1Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
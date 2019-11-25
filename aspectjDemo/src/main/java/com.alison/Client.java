package com.alison;

import org.springframework.core.annotation.Order;

/**
 * @Author alison
 * @Date 2019/11/17  17:54
 * @Version 1.0
 * @Description
 */
@Order
public class Client {
    Client(){
        System.out.println("new ");
    }
    public static void main(String[] args) {
        Client c = new Client();
    }
}

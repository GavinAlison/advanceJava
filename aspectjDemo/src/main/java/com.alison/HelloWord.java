package com.alison;

/**
 * @Author alison
 * @Date 2019/11/13  14:41
 * @Version 1.0
 * @Description
 */
public class HelloWord {

    public void sayHello() {
        System.out.println("hello world !");
    }

    public static void main(String args[]) {
        HelloWord helloWord = new HelloWord();
        helloWord.sayHello();
    }
}

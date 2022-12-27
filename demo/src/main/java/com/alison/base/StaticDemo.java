package com.alison.base;

/**
 * @Classname StaticDemo
 * @Date 2021/9/20 23:06
 * @Created by alison
 */
public class StaticDemo {

    public static int i = 1;
    public int m = 2;

    void method() {
        m = m + 1;
        i = i + 1;
        staticMethod();
    }

    static void staticMethod() {
//        this(); 错
//        method(); 错
//        m = 3; 错
        i = 10;
        System.out.println("staticMethod");
    }

    public static void main(String[] args) {

    }

}

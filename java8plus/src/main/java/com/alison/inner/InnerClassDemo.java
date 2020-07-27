package com.alison.inner;

/**
 * @Author alison
 * @Date 2019/12/22  21:42
 * @Version 1.0
 * @Description
 */
public class InnerClassDemo {
//    start, run
//    main ---
//    start thread ---
//
//    run
//    main --
//    main --
//    先后
    // thread-0,5, main

    public static void main(String[] args) {
        System.out.println(Thread.currentThread()+"=====1");

        new Thread(new Runnable(){// 接口名
            @Override
            public void run(){// 方法名
                System.out.println(Thread.currentThread()+"=====2");
                System.out.println("Thread run()====3");
            }
        }).start();



    }
}

package com.alison.spring.stopWatch;

import org.springframework.util.StopWatch;

/**
 * @Author alison
 * @Date 2019/10/8  22:28
 * @Version 1.0
 * @Description
 */
public class TestStopWatch {
    private void test() throws InterruptedException {
        StopWatch sw = new StopWatch();

        sw.start("起床");
        Thread.sleep(1000);
        sw.stop();

        sw.start("洗漱");
        Thread.sleep(2000);
        sw.stop();

        sw.start("锁门");
        Thread.sleep(500);
        sw.stop();

        System.out.println(sw.prettyPrint());
        System.out.println(sw.getTotalTimeMillis());
        System.out.println(sw.getLastTaskName());
        System.out.println(sw.getLastTaskInfo());
        System.out.println(sw.getTaskCount());
    }


    public static void main(String []argv) throws InterruptedException {
        TestStopWatch testStopWatch = new TestStopWatch();
        testStopWatch.test();
    }
}

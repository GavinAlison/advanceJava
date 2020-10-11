package com.alison.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);     // 创建一个ScheduledExecutorService实例
        // 定时执行或周期性地执行
        final ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new BeepTask(),
                10, 10, TimeUnit.SECONDS);                   // 每隔10s蜂鸣一次， 10s之后执行

        scheduler.schedule(() -> scheduledFuture.cancel(true)
                , 1, TimeUnit.HOURS);       // 1小时后, 取消蜂鸣任务
    }


    private static class BeepTask implements Runnable {
        @Override
        public void run() {
            System.out.println("beep!");
        }
    }
}

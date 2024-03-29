package com.alison.distributedLocks;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LockTest {

    private String lockName = "lock";

    /**
     * 显示锁的过期时间
     */
    @Before
    public void showLockExpireTime() {
        new Thread(() -> {
            while (true) {
                try {
                    Jedis jedis = new Jedis("192.168.56.103", 6379);
                    jedis.auth("redis");
                    Thread.sleep(5000);
                    System.out.println("锁的过期时间:" + jedis.ttl(lockName) + "秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Test
    public void testLockCase5() {
        //定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10,
                1, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        //添加10个线程获取锁
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    Jedis jedis = new Jedis("192.168.56.103", 6379);
                    jedis.auth("redis");
                    Thread.sleep(5000);
                    LockCase5 lock = new LockCase5(jedis, lockName);
                    lock.lock();
                    // 模拟业务执行15秒
                    try {
                        Thread.sleep(15 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // 当线程池中的线程数为0时，退出
        while (pool.getPoolSize() != 0) {
        }
    }

}

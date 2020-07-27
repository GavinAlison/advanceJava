package com.alison.distributedLocks;

import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class RedisLock implements Lock {
    protected Jedis jedis;
    protected String lockKey;
    protected String lockValue;
    protected volatile boolean isOpenExpirationRenewal = true;

    public RedisLock(Jedis jedis, String lockKey, String lockValue) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.lockValue = lockValue;
    }

    public RedisLock(Jedis jedis, String lockKey) {
        this(jedis, lockKey, UUID.randomUUID().toString() + Thread.currentThread().getId());
    }

    /**
     * 开启定时刷新
     */
    protected void scheduleExpirationRenewal() {
        Thread renewalThread = new Thread(new ExpirationRenewal());
        renewalThread.start();
    }
    @Override
    public void lockInterruptibly(){}

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit){
        return false;
    }

    /**
     * 刷新key的过期时间
     */
    private class ExpirationRenewal implements Runnable {
        @Override
        public void run() {
            while (isOpenExpirationRenewal) {
                System.out.println("执行延迟失效时间中...");
                String checkAndExpireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "return redis.call('expire',KEYS[1],ARGV[2]) " +
                        "else " +
                        "return 0 end";
                // 执行lua脚本
                jedis.eval(checkAndExpireScript, 1, lockKey, lockValue, "2");

                //休眠10秒
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

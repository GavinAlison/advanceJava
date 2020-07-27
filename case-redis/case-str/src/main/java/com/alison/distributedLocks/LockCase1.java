package com.alison.distributedLocks;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;


/**
 * 存在问题：如果获得锁的线程挂了，那么这个锁就永远不会被释放，其他线程获取不到锁。
 */
public class LockCase1 extends RedisLock {

    public LockCase1(Jedis jedis, String name) {
        super(jedis, name);
    }

    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(lockKey, "value", new SetParams().nx());
            if ("OK".equals(result)) {
                System.out.println(Thread.currentThread().getId() + "加锁成功!");
                break;
            }
        }
    }

    @Override
    public void unlock() {
        jedis.del(lockKey);
    }
}
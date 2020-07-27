package com.alison.command;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseConnection {
    protected static Jedis jedis = null;

    protected RedisTemplate redisTemplate;

    protected ValueOperations valueOperations;

    protected HashOperations hashOperations;

    protected ListOperations listOperations;

    protected SetOperations setOperations;

    protected ZSetOperations zSetOperations;

    protected GeoOperations geoOperations;

    protected HyperLogLogOperations hyperLogLogOperations;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.geoOperations = redisTemplate.opsForGeo();
        this.hyperLogLogOperations = redisTemplate.opsForHyperLogLog();

    }

    /**
     * 连接
     */
    @BeforeClass
    public static void init() {
        jedis = new Jedis("192.168.56.103", 6379);
        jedis.auth("redis");
        System.out.println("connected......");
    }

    /**
     * 关闭连接
     */
    @AfterClass
    public static void destroy() {
        jedis.flushDB();
        jedis.close();
        jedis = null;
    }

    @Test
    public void test() {
        System.out.println("hahaha");
    }
}

package com.alison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JedisController {


    @Autowired
    private RedisTemplate redisTemplate;

    private static class JedisBuilder {
        private Jedis jedis;

        public static Jedis builder() {
            Jedis jedis = new Jedis("192.168.56.103", 6379, 3 * 1000);
            jedis.auth("redis");
            return jedis;
        }
    }

    public void testStr() {
        JedisBuilder.builder().set("alison", "gavin");
        System.out.println(JedisBuilder.builder().get("alison"));
    }

    public void testList() {
        //存储数据到列表中
        JedisBuilder.builder().lpush("site-list", "Runoob");
        JedisBuilder.builder().lpush("site-list", "Google");
        JedisBuilder.builder().lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = JedisBuilder.builder().lrange("site-list", 0, 1);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
    }

    public void testKeys() {
        Set<String> keys = JedisBuilder.builder().keys("*");
        keys.stream().forEach(System.out::println);
    }

    public void testHash() {
        JedisBuilder.builder().hset("alisonKey", "name", "alison");
        System.out.println(JedisBuilder.builder().hsetnx("alisonKey:1", "name", "Hello"));
        System.out.println(JedisBuilder.builder().hget("alisonKey:1", "name"));
        System.out.println(JedisBuilder.builder().hgetAll("alisonKey:1"));
        Map<String, String> map = new HashMap<>(3);
        map.put("field1", "value1");
        map.put("field2", "value2");
        map.put("field3", "value3");

        JedisBuilder.builder().hmset("key", map);
        System.out.println(JedisBuilder.builder().hvals("key"));

        //spring redisTemplate
        System.out.println(redisTemplate.opsForHash().values("key"));
    }

    public static void main(String[] args) {
        JedisController jedisController = new JedisController();
//        jedisController.testStr();
//        jedisController.testList();
//        jedisController.testKeys();
        jedisController.testHash();
    }
}

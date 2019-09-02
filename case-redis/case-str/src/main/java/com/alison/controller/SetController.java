package com.alison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@RequestMapping("/set")
public class SetController {
    private static final String A_FRIEND_KEY = "friend:a";

    private static final String B_FRIEND_KEY = "friend:b";

    private RedisTemplate redisTemplate;

    private SetOperations setOperations;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
        this.setOperations = redisTemplate.opsForSet();
    }
    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/getList")
    public Map getList() {
        Map map = new HashMap();
        Set aFriend = setOperations.members(A_FRIEND_KEY);
        Set bFriend = setOperations.members(B_FRIEND_KEY);
        map.put("aFriend", aFriend);
        map.put("bFriend", bFriend);
        return map;
    }

    /**
     * 添加好友
     *
     * @return
     */
    @PostMapping("/addFriend")
    public Long addFriend(String user, String friend) {
        String currentKey = A_FRIEND_KEY;
        if ("B".equals(user)) {
            currentKey = B_FRIEND_KEY;
        }
        //返回添加成功的条数
        return setOperations.add(currentKey, friend);
    }

    /**
     * 删除好友
     *
     * @return
     */
    @DeleteMapping("/delFriend")
    public Long delFriend(String user, String friend) {
        String currentKey = A_FRIEND_KEY;
        if ("B".equals(user)) {
            currentKey = B_FRIEND_KEY;
        }
        //返回删除成功的条数
        return setOperations.remove(currentKey, friend);
    }

    /**
     * 共同好友(交集)
     *
     * @return
     */
    @GetMapping("/intersectFriend")
    public Set intersectFriend() {
        return setOperations.intersect(A_FRIEND_KEY, B_FRIEND_KEY);
    }

    /**
     * 返回用户独有的好友(差集)
     *
     * @return
     */
    @GetMapping("/differenceFriend")
    public Set differenceFriend(String user) {
        return setOperations.difference(A_FRIEND_KEY, B_FRIEND_KEY);
    }

    /**
     * 返回所有的好友(并集)
     *
     * @return
     */
    @GetMapping("/unionFriend")
    public Set unionFriend() {
        return setOperations.union(A_FRIEND_KEY, B_FRIEND_KEY);
    }

}

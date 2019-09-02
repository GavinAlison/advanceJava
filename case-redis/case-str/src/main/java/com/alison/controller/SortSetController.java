package com.alison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/zset")
public class SortSetController {

    private static final String ZSET_KEY = "articleList";

    private RedisTemplate redisTemplate;

    private ZSetOperations zSetOperations;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/getList/{sortType}")
    public Set getList(@PathVariable String sortType) {
        //如果没有数据，则添加10条数据
        if (zSetOperations.size(ZSET_KEY) == 0) {
            IntStream.range(1, 11).forEach(i -> zSetOperations.add(ZSET_KEY, "文章->" + i, (int) (Math.random() * 10 + i)));
        }
        //ASC根据分数从小到大排序,DESC反之
        if ("ASC".equals(sortType)) {
            return zSetOperations.rangeWithScores(ZSET_KEY, 0, -1);
        } else {
            return zSetOperations.reverseRangeWithScores(ZSET_KEY, 0, -1);
        }
    }

    @GetMapping("/getList")
    public Set get() {
        return zSetOperations.rangeWithScores(ZSET_KEY, 0, -1);
    }

    /**
     * 赞或踩
     *
     * @param member
     * @param type
     * @return
     */
    @PostMapping("/star")
    public boolean starOrUnStar(@RequestParam String member, @RequestParam String type) {
        if ("UP".equals(type)) {
            zSetOperations.incrementScore(ZSET_KEY, member, 1);
        } else {
            zSetOperations.incrementScore(ZSET_KEY, member, -1);
        }
        return true;
    }

    /**
     * 获取排名
     *
     * @param member
     * @param type
     * @return
     */
    @GetMapping("/rank/{type}/{member}")
    public Long rank(@PathVariable String member, @PathVariable String type) {
        Long rank = null;
        if ("ASC".equals(type)) {
            rank = zSetOperations.rank(ZSET_KEY, member);
        } else {
            rank = zSetOperations.reverseRank(ZSET_KEY, member);
        }
        return rank;
    }

    @DeleteMapping
    public boolean delete() {
        Set<ZSetOperations.TypedTuple> set = zSetOperations.rangeWithScores(ZSET_KEY, 0, -1);
        set.stream().forEach(it -> System.out.print(String.format("value: %s, score: %5f \n", it.getValue(), it.getScore())));
        set.stream().forEach(it -> zSetOperations.remove(ZSET_KEY, it.getValue()));
        return true;
    }


}

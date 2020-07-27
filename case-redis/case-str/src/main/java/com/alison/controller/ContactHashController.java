package com.alison.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/contacts")
public class ContactHashController {

    private static final String CONTACTS_KEY_PREFIX = "contacts:";

    private static final String CONTACTS_ID_KEY = "contactsID";

    private RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    private ValueOperations valueOperations;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @GetMapping("/getList")
    public List list() {
        List list = new ArrayList();
        Set<String> keys = redisTemplate.keys(CONTACTS_KEY_PREFIX + "*");
        for (String key : keys) {
            Map entries = hashOperations.entries(key);
            list.add(entries);
        }
        return list;
    }

    @GetMapping("/get/{id}")
    public Map get(@PathVariable String id) {
        return hashOperations.entries(CONTACTS_KEY_PREFIX + id);
    }


    @PostMapping("/add")
    public boolean add(@RequestBody JSONObject contacts) {
        // auto increment
        Long contactsId = valueOperations.increment(CONTACTS_ID_KEY, 1);
        contacts.put("id", String.valueOf(contactsId));
        // convert  json to map ,  save redis
        hashOperations.putAll(CONTACTS_KEY_PREFIX + contactsId, contacts.getInnerMap());
        return true;
    }

    @DeleteMapping("/del/{id}")
    public boolean del(@PathVariable String id) {
        return redisTemplate.delete(CONTACTS_KEY_PREFIX + id);
    }

    @PostMapping("/addAttr")
    public boolean addAttr(@RequestBody JSONObject contacts) {
        String id = contacts.getString("id");
        String fieldName = contacts.getString("fieldName");
        String fieldValue = contacts.getString("fieldValue");
        hashOperations.put(CONTACTS_KEY_PREFIX + id, fieldName, fieldValue);
        hashOperations.putIfAbsent(CONTACTS_KEY_PREFIX + id, fieldName, fieldValue);
        return true;
    }

    @PostMapping("/delAttr")
    public boolean delAttr(@RequestBody JSONObject contacts) {
        String id = contacts.getString("id");
        String fieldName = contacts.getString("fieldName");
        hashOperations.delete(CONTACTS_KEY_PREFIX + id, fieldName);
        return true;
    }
}




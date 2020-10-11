package com.alison.LRU;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author alison
 * @Date 2019/8/13  22:08
 * @Version 1.0
 * @Description  LRU缓存LinkedHashMap(inheritance)实现
 */
public class LRUCache2<K, V> extends LinkedHashMap<K, V> {

    private int MAX_CACHE_SIZE;

    public LRUCache2(int cacheSize) {
        // 按照accessOrder存储，最近访问存储到最前，最早读取数据存储到最后
        super((int) Math.ceil(cacheSize / 0.75), 0.75f, true);
        MAX_CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_CACHE_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format("%s:%s", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}

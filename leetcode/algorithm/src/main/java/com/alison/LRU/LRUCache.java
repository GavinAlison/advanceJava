package com.alison.LRU;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Author alison
 * @Date 2019/8/13  22:32
 * @Version 1.0
 * @Description 链表+HashMap实现
 * 主要是控制head和tail两个指针移动，map中size与capacity的大小
 */
public class LRUCache<K, V> {
    private int MAX_CACHE_SIZE;
    private Entry head;
    private Entry tail;

    private HashMap<K, Entry<K, V>> map;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Entry<K, V> {
        private K key;
        private V value;
        private Entry pre;
        private Entry next;
    }

    public LRUCache(int cacheSize) {
        MAX_CACHE_SIZE = cacheSize;
        map = new HashMap<K, Entry<K, V>>();
    }

    public void put(K key, V value) {
        Entry entry = getEntry(key);
        if (entry == null) {
            if (map.size() >= MAX_CACHE_SIZE) {
                map.remove(tail.key);
                removeLast();
            }
            entry = new Entry();
            entry.key = key;
        }
        entry.value = value;
        moveToFirst(entry);
        map.put(key, entry);
    }

    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null)
            return null;
        moveToFirst(entry);
        return entry.value;
    }

    public void remove(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            if (entry == head) {
                head = head.next;
            }
            if (entry == tail) {
                tail = tail.pre;
            }
            if (entry.pre != null) {
                entry.pre.next = entry.next;
            }
            if (entry.next != null) {
                entry.next.pre = entry.pre;
            }
        }
        map.remove(key);
    }


    private void removeLast() {
        if (tail != null) {
            // 向前移动一位
            tail = tail.pre;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
        }

    }

    private void moveToFirst(Entry entry) {
        if (head == null || tail == null) {
            head = tail = entry;
            return;
        }
        if (entry == head) {
            return;
        }
        // 注意双向
        if (entry.pre != null) {
            entry.pre.next = entry.next;
        }
        if (entry.next != null) {
            entry.next.pre = entry.pre;
        }
        if (entry == tail) {
            tail = tail.pre;
        }
        entry.next = head;
        head.pre = entry;
        head = entry;
        entry.pre = null;
    }

    private Entry<K, V> getEntry(K key) {
        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Entry entry = head;
        while (entry != null) {
            sb.append(String.format("%s:%s ", entry.key, entry.value));
            entry = entry.next;
        }
        return sb.toString();
    }

}

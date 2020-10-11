package com.alison;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实现一个基于链表法解决冲突问题的散列表
 */

public class MyHash {

    private static final int DEFAULT_CAPACITY = 10;//默认长度

    private static final float LOAD_FACTOR = 0.75f;//扩容因子

    private Entry[] table = new Entry[DEFAULT_CAPACITY];//初始化

    private int size;//哈希表大小

    private int use;//使用的地址数量

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Entry {
        private int key;
        private int value;
        Entry next;
    }

    public void put(int key, int value) {
        //通过hash方法转换，采用的是直接法
        int index = hash(key);
        //说明位置未被使用
        if (table[index] == null) {
            table[index] = new Entry(-1, -1, null);
        }
        Entry tmp = table[index];
        //说明位置未被使用
        if (tmp.next == null) {
            table[index].next = new Entry(key, value, null);
            size++;
            use++;
            // 判断是否需要扩容
            if (use >= table.length * LOAD_FACTOR) {
                resize();//扩容方法
            }
        } else { //已被使用，则直接扩展链表
            for (tmp = tmp.next; tmp != null; tmp = tmp.next) {
                int k = tmp.key;
                if (k == key) {
                    tmp.value = value;
                    return;
                }
            }
            Entry temp = table[index].next;
            Entry newEntry = new Entry(key, value, temp);
            table[index].next = newEntry;
            size++;
        }
    }

    private void resize() {
        int newLength = table.length * 2;
        Entry[] oldTable = table;
        table = new Entry[newLength];
        use = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && oldTable[i].next != null) {
                Entry e = oldTable[i];
                while (null != e.next) {
                    Entry next = e.next;
                    int index = hash(next.key);
                    if (table[index] == null) {
                        use++;
                        table[index] = new Entry(-1, -1, null);
                    }
                    Entry temp = table[index].next;
                    Entry newEntry = new Entry(next.key, next.value, temp);
                    table[index].next = newEntry;
                    e = next;
                }
            }
        }

    }

    //删除，链表的中间值删除方法
    public void remove(int key) {
        int index = hash(key);
        Entry e = table[index];
        Entry pre = table[index];
        if (e != null && e.next != null) {
            for (e = e.next; e != null; pre = e, e = e.next) {
                int k = e.key;
                if (k == key) {
                    pre.next = e.next;
                    size--;
                    return;
                }
            }
        }
    }

    // 通过key 提取value
    public int get(int key) {
        int index = hash(key);
        Entry e = table[index];
        if (e != null && e.next != null) {
            for (e = e.next; e != null; e = e.next) {
                int k = e.key;
                if (k == key) {
                    return e.value;
                }
            }
        }
        return -1;
    }

    //返回元素个数
    public int size() {
        return size;
    }

    //哈希表大小
    public int getLength() {
        return use;
    }

    private int hash(int key) {
        return key % table.length;
    }
}

package com.alison.heap;

import lombok.Data;

/**
 * @Author alison
 * @Date 2019/8/18  22:12
 * @Version 1.0
 * @Description
 */
@Data
public class Customer implements Comparable<Customer> {

    private int level;
    private String name;
    private String info;
    Customer(int level) {
        this.level = level;
    }
    @Override
    public int compareTo(Customer o) {
        return level > o.level ? -1 : 1;
    }
}

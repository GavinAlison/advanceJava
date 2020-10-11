package com.alison.leetcode;

import org.junit.Test;

import java.util.TreeSet;

public class DDTest {
    @Test
    public void test() {
        TreeSet treeSet = new TreeSet<Integer>((Integer o1, Integer o2) -> {
            System.out.println("==============");
            System.out.println(o1.intValue());
            System.out.println(o2.intValue());
            return o1.intValue() - o2.intValue();
        });
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        System.out.println(treeSet.toString());

    }
}

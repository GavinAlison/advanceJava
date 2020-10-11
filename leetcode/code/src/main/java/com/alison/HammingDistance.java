package com.alison;

import org.junit.Test;

/**
 * @Author alison
 * @Date 2019/7/9  21:47
 * @Version 1.0
 * @Description
 */
public class HammingDistance {


    @Test
    public void test() {
        System.out.println(1 ^ 3);
        System.out.println(hammingDistance(1, 3));
    }

    public int hammingDistance(int x, int y) {
        int n = x ^ y;
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

}

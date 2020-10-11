package com.alison.bitmap;

import java.util.BitSet;

/**
 * @Author alison
 * @Date 2019/8/3  15:23
 * @Version 1.0
 * @Description
 */
public class BitMapDemo {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 44, 33, 22, 0, 6};
        BitSet bitSet = new BitSet(6);
        //将数组内容存储置bitmap
        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i], true);
        }
        System.out.println(bitSet.size());
        System.out.println(bitSet.get(3));
    }
}

package com.alison.Utils;

import lombok.Data;

/**
 * @description: 用于去重
 * @link  https://yao2san.com/article/2053
 */

public class BloomFilterUtils {

    @Data
    public static class BloomFilter {
        private float tolerance;
        private int capacity;
        // 一个字符对应有hashFunNum个位数， hash的次数
        private int hashFunNum;
        // 实际存储的bit位
        private boolean[] bitArray;
        // bit数组的位数
        private int bitArrayLen;

        public void init(float tolerance, int capacity) {
            this.bitArrayLen = bitArrayLen(tolerance, capacity);
            this.hashFunNum = hashFunNum(tolerance);
            this.bitArray = new boolean[bitArrayLen];
        }

        private int bitArrayLen(float tolerance, int capacity) {
            return (int) -(Math.log(tolerance) / (Math.log(2) * Math.log(2))) * capacity;
        }

        private int hashFunNum(float tolerance) {
            return (int) Math.ceil(-Math.log(tolerance) / Math.log(2));
        }

        public boolean isExist(String value) {
            int[] index = index(value);
            boolean res = true;
            for (int idx : index) {
                if (!bitArray[idx]) {
                    res = false;
                    break;
                }
            }
            return res;
        }

        public void put(String value) {
            // 设置对应的位数为1
            int[] index = index(value);
            for (int idx : index) {
                bitArray[idx] = true;
            }
        }
        // 获取value值对应的bit位下标
        private int[] index(String value) {
            int[] res = new int[hashFunNum];
            int h = hash(value);
            int h2 = h >>> 16;
            for (int i = 0; i < hashFunNum; i++) {
                int hash = h + h2 * i;
                hash = hash % bitArrayLen;
                if (hash < 0)
                    hash = -hash;
                res[i] = hash;
            }
            return res;
        }

        private int hash(String value) {
            int h = value.hashCode();
            return h;
        }
    }

    public static void main(String[] args) {
        BloomFilter bf = new BloomFilter();
        final int total = 1000_00;
        bf.init(0.01F, total);
        int errorCount = 0;

        for (int i = 0; i < total; i++) {
            boolean isExist = bf.isExist(i + "bloom filter");
            if (isExist) {
                errorCount++;
            }
            bf.put(i + "bloom filter");
        }
        System.out.println("数据量 " + total + "，bitmap容量 " + bf.getBitArrayLen() + "，hash次数  " + bf.getHashFunNum() + "，判别错误个数 " + errorCount + ",误差 " + (float) errorCount / total);
    }

}


package com.alison.Utils;

/**
 * @description: 用于去重
 * @link  https://yao2san.com/article/2053
 */
public class BloomFilterUtils {

    public static class BloomFilter {
        private float tolerance;
        private int capacity;

        private int hashFunNum;
        private boolean[] bitArray;
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
            int[] index = index(value);
            for (int idx : index) {
                bitArray[idx] = true;
            }
        }

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

        public float getTolerance() {
            return tolerance;
        }

        public void setTolerance(float tolerance) {
            this.tolerance = tolerance;
        }

        public int getHashFunNum() {
            return hashFunNum;
        }

        public void setHashFunNum(int hashFunNum) {
            this.hashFunNum = hashFunNum;
        }

        public boolean[] getBitArray() {
            return bitArray;
        }

        public void setBitArray(boolean[] bitArray) {
            this.bitArray = bitArray;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getBitArrayLen() {
            return bitArrayLen;
        }

        public void setBitArrayLen(int bitArrayLen) {
            this.bitArrayLen = bitArrayLen;
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


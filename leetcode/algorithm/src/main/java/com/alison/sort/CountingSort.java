package com.alison.sort;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/11  20:45
 * @Version 1.0
 * @Description
 */
public class CountingSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        // 获取最大值
        int maxValue = getMaxValue(arr);
        return countingSort(arr, maxValue);
    }

    private int[] countingSort(int[] arr, int maxValue) {
        // 构建最大值+1的数组
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];

        for (int value : arr) {
            bucket[value]++;
        }
        // 遍历bucket数组，输出到arr数组中
        int sortedIndex = 0;
        for (int j = 0; j < bucketLen; j++) {
            while (bucket[j] > 0) {
                arr[sortedIndex++] = j;
                bucket[j]--;
            }
        }
        return arr;
    }

    private int getMaxValue(int[] arr) {
        int maxValue = arr[0];
        for (int value : arr) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}

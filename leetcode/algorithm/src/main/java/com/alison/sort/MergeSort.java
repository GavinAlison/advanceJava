package com.alison.sort;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/11  8:53
 * @Version 1.0
 * @Description
 */
public class MergeSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        if (arr.length < 2) {
            return arr;
        }
        int middle = (int) Math.floor(arr.length / 2);
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);

        return merge(sort(left), sort(right));
    }

    public int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int lIndex = 0, rIndex = 0;
        for (int i = 0; i < result.length; i++) {
            if (lIndex < left.length && rIndex < right.length) {
                if (left[lIndex] < right[rIndex]) {
                    result[i] = left[lIndex++];
                } else {
                    result[i] = right[rIndex++];
                }
            } else if (lIndex < left.length) {
                result[i] = left[lIndex++];
            } else if (rIndex < right.length) {
                result[i] = right[rIndex++];
            }
        }
        return result;

    }
}

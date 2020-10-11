package com.alison.sort;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/11  9:22
 * @Version 1.0
 * @Description
 */
public class QuickSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        return quickSort2(arr, 0, arr.length - 1);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        //如果left等于right，即数组只有一个元素，直接返回
        if (left >= right) {
            return arr;
        }
        //设置最左边的元素为基准值
        int key = arr[left];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i = left;
        int j = right;
        while (i < j) {
            //j向左移，直到遇到比key小的值
            while (arr[j] >= key && i < j) {
                j--;
            }
            //i向右移，直到遇到比key大的值
            while (arr[i] <= key && i < j) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                swap(arr, i, j);
            }
        }
        arr[left] = arr[i];
        arr[i] = key;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
        return arr;
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private int[] quickSort2(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort2(arr, left, partitionIndex - 1);
            quickSort2(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        int key = left;
        // 记录left开始，小于right的数下标
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            // 找到比基数大的数，交换index与i对应的数值
            if (arr[key] > arr[i]) {
                swap(arr, i, index);
                index++;
            }
        }
        // 此时index-1是相对于arr[key]最中间的数
        swap(arr, key, index - 1);
        return index - 1;
    }
}

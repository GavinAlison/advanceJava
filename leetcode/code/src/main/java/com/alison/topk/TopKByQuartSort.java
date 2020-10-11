package com.alison.topk;

import java.util.Arrays;

public class TopKByQuartSort {
    public static void main(String[] args) {
        int[] arr = {8, 3, 9, 1, 5, 10, 4, 11};
        int topN = 3;
        int[] result = quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(result));
        for (int i = 0; i < topN; i++) {
            System.out.println(result[i]);
        }
    }

    public static int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int index = partition(arr, left, right);
            quickSort(arr, left, index - 1);
            quickSort(arr, index + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] > arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, left, index - 1);
        return index - 1;
    }

    public static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
}

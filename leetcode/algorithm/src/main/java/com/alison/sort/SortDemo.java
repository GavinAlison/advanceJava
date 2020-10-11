package com.alison.sort;

import java.util.PriorityQueue;

/**
 * @Author alison
 * @Date 2019/8/10  18:43
 * @Version 1.0
 * @Description
 */
public class SortDemo {

    public static void main(String[] args) {
//        IArraySort arraySort = new BubbleSort();
//        int[] arr = {3, 7, 4, 9, 5, 22, 91};
//        System.out.println(Arrays.toString(arr));
//        arraySort = new SelectionSort();
//        arraySort = new InsertSort();
//        arraySort = new ShellSort();
//        arraySort = new MergeSort();
//        arraySort = new QuickSort();
//        arraySort = new HeapSort();
//        arraySort = new CountingSort();
//        arraySort = new BucketSort();
//        arraySort = new CountingSort();
//        arraySort = new RadixSort();
//        arr = arraySort.sort(arr);
//        System.out.println(Arrays.toString(arr));
        SortDemo sortDemo = new SortDemo();
        int[] arr = {1, 4, 6, 8, 20, 22, 24};
//        int res = sortDemo.binarySearch2(arr, 5);
//        System.out.println(res);
//        System.out.println(sortDemo.mySqrt(5));
        System.out.println(sortDemo.findKthLargest(arr, 3));
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.poll();
    }

    // 二分查找，不断递归，注意递归会增加内存的使用，方法的使用层级， 最好用循坏
    public int binarySearch(int[] arr, int start, int end, int target) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (target < arr[mid])
            return binarySearch(arr, start, mid - 1, target);
        if (target > arr[mid])
            return binarySearch(arr, mid + 1, end, target);
        return mid;
    }

    public int binarySearch2(int[] arr, int target) {
        if (arr.length == 0) {
            return -1;
        }
        if (target > arr[arr.length - 1]) {
            return arr.length - 1;
        }
        if (target < arr[0]) {
            return 0;
        }
        int start = 0;
        int end = arr.length - 2;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (target > arr[mid] && target <= arr[mid + 1]) {
                return mid + 1;
            } else if (target > arr[mid + 1]) {
                start = mid + 1;
            } else if (target <= arr[mid]) {
                end = mid;
            }
        }
        return -1;
    }

    // 这种解法针对于整数的算数平方根
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 1;
        int right = x;
        int result = 1;
        // 小于等于， 注意等于号
        while (left <= right) {
            int m = (left + right) / 2;
            // x/m 可以避免x*x造成内存溢出
            if (m == x / m)
                return m;
            if (m > x / m) {
                right = m - 1;
            } else {
                left = m + 1;
                result = m;
            }
        }
        return result;
    }

    // 牛顿迭代法
    public int mySqrt2(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("x's value error");
        }
        if (x == 0 || x == 1) {
            return x;
        }
        double k = 1;
        while (Math.abs(x - k * k) >= 0.5) {
            k = (k + x / k) / 2;
        }
        return (int) k;
    }

    public int mySqrt3(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        long k = x;
        while (k * k > x) {
            k = (k + x / k) / 2;
        }
        return (int) k;
    }


}

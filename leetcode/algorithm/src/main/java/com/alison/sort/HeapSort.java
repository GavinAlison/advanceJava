package com.alison.sort;

import java.util.Arrays;


/**
 * @Author alison
 * @Date 2019/8/11  13:08
 * @Version 1.0
 * @Description
 */
public class HeapSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        //   buildMaxHeap(arr);
        int len = arr.length;
        buildMaxHeap2(arr, len);

        // 每次建堆就可以排除一个元素
        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            len--;
            heapify(arr, 0, len);
        }
        return arr;
    }

    private void buildMaxHeap(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int child = i;
            int parent = (i - 1) / 2;
            int tmp = arr[i];
            while (parent >= 0 && child != 0 && arr[parent] < tmp) {
                arr[child] = arr[parent];
                child = parent;
                parent = (parent - 1) / 2;
            }
            arr[child] = tmp;
        }
    }


    public void buildMaxHeap2(int[] arr, int size) {
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, size);
        }
    }

    private void heapify(int[] arr, int i, int size) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        //把当前父节点位置看成是最大的
        int largest = i;
        if (left < size && arr[largest] < arr[left]) {
            //如果左子树对应值比当前根元素要大，记录它的位置
            largest = left;
        }
        if (right < size && arr[largest] < arr[right]) {
            //如果右子树对应值比当前根元素要大，记录它的位置
            largest = right;
        }
        //如果最大的不是根元素位置，那么就交换
        if (largest != i) {
            swap(arr, i, largest);
            //继续比较，直到完成一次建堆
            heapify(arr, largest, size);
        }
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

package com.alison;

/**
 * @Author alison
 * @Date 2019/8/1  21:54
 * @Version 1.0
 * @Description
 */
public class TopN {

    // 构建堆
    private int[] buildHeap(int n, int[] data) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = data[i];
        }
        for (int i = 1; i < n; i++) {
            int child = i;
            int parent = (i - 1) / 2;
            // 调整堆
            while (child != 0 && result[parent] > result[child]) {
                int temp = result[child];
                result[child] = result[parent];
                result[parent] = temp;
                child = parent;
            }
        }
        return result;
    }

    // 21,12,3,58,6,45,10,8,59,2
    // 58 21 3 12
    // 调整data[i]
    private void adjust(int value, int[] result) {
        if (value <= result[0]) {
            return;
        }
        // 置换堆顶
        int temp = value;
        value = result[0];
        value = temp;
        // 调整堆顶
        int n = result.length;
        int t = 0;
        int left = 2 * t + 1;
        int right = 2 * t + 2;
        while ((left < n && result[t] > result[left])
                || (right < n && result[t] > result[right])) {
            if (right < n && result[right] < result[left]) {
                // 右孩子更小，置换右孩子
                temp = result[t];
                result[t] = result[right];
                result[right] = temp;
                t = right;
            } else {
                // 否则置换左孩子
                temp = result[t];
                result[t] = result[left];
                result[left] = temp;
                t = left;
            }
        }
    }

    // 寻找topN，该方法改变data，将topN排到最前面
    public void findTopN(int n, int[] data) {
        // 先构建n个数的小顶堆
        int[] result = buildHeap(n, data);
        // n往后的数进行调整
        for (int i = n; i < data.length; i++) {
            adjust(data[i], result);
        }
    }

    // 打印数组
    public void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

}

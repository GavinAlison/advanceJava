package com.alison.heap;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author alison
 * @Date 2019/8/18  16:28
 * @Version 1.0
 * @Description
 */
public class HeapDemo {
    private static class Element {
        public int row, col, val;

        public Element(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    private Comparator<Element> MyCmp = (Element o1, Element o2) -> o1.val - o2.val;

    public int[] mergeKSortedArrays(int[][] arr) {
        if (arr == null) {
            return new int[0];
        }
        int sum = 0;
        Queue<Element> q = new PriorityQueue<Element>(arr.length, MyCmp);

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                Element e = new Element(i, 0, arr[i][0]);
                q.add(e);
                sum += arr[i].length; //记录结果数组总长度
            }
        }

        int[] res = new int[sum];
        int idx = 0;
        while (!q.isEmpty()) {
            Element e = q.poll(); //弹出堆顶最小值
            res[idx++] = e.val;
            // 当前结点被 PriorityQueue 抛出来后，当前行的第二个结点加入 PriorityQueue
            if (e.col + 1 < arr[e.row].length) {     //将当前最小所在数组的下一个元素存入pq
                q.add(new Element(e.row, e.col + 1, arr[e.row][e.col + 1]));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        HeapDemo m = new HeapDemo();
        int[][] arr = {{1, 3, 5, 7}, {2, 4, 6}, {0, 8, 9, 10, 11}};
        int[] res = m.mergeKSortedArrays(arr);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + " ");
        }
    }
}

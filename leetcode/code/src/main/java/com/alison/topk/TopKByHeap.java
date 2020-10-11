package com.alison.topk;

import java.util.PriorityQueue;

public class TopKByHeap {

    PriorityQueue<Integer> queue;
    int maxHeapSize;

    public void topk(int k, int[] nums) {
        queue = new PriorityQueue<>(k);
        maxHeapSize = k;
        if (k < nums.length) {
            for (int i = 0; i < k; i++) {
                queue.add(nums[i]);
            }
            for (int j = k; j < nums.length; j++) {
                if (nums[j] > queue.peek()) {
                    queue.poll();
                    queue.offer(nums[j]);
                }
            }
        } else {
            for (Integer integer : nums) {
                queue.add(integer);
            }
        }
    }
}
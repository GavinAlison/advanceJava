package com.alison;

import java.util.ArrayDeque;
import java.util.LinkedList;

public class QueueDemo {

    public class MyCircularDeque {

        private int[] elementData;
        private int size;
        private int capacity;
        private int head;
        private int tail;

        private int DEFAULT_CAPACITY = 10;

        /**
         * Initialize your data structure here. Set the size of the deque to be k.
         */
        public MyCircularDeque(int k) {
            if (k < 0) {
                this.capacity = DEFAULT_CAPACITY;
            }
            capacity = k;
            elementData = new int[capacity];
            head = 0;
            tail = 0;
        }

        /**
         * Adds an item at the front of Deque. Return true if the operation is successful.
         */
        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            }
            if (head - 1 < 0) {
                head = capacity - 1;
            } else {
                head = head - 1;
            }
            elementData[head] = value;
            size++;
            return true;
        }

        /**
         * Adds an item at the rear of Deque. Return true if the operation is successful.
         */
        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            }

            elementData[tail] = value;
            size++;
            return true;
        }

        /**
         * Deletes an item from the front of Deque. Return true if the operation is successful.
         */
        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            head = (head + 1) % capacity;
            size--;
            return true;
        }

        /**
         * Deletes an item from the rear of Deque. Return true if the operation is successful.
         */
        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            if (tail == 0) {
                tail = capacity - 1;
            } else {
                tail = tail - 1;
            }
            size--;
            return true;
        }

        /**
         * Get the front item from the deque.
         */
        public int getFront() {
            return elementData[head];
        }

        /**
         * Get the last item from the deque.
         */
        public int getRear() {
            return elementData[tail];
        }

        /**
         * Checks whether the circular deque is empty or not.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks whether the circular deque is full or not.
         */
        public boolean isFull() {
            return size == capacity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (head < tail) {
                for (int i = head; i <= tail; i++) {
                    sb.append(elementData[i] + " ");
                }
            } else {
                for (int i = head; i < capacity; i++) {
                    sb.append(elementData[i] + " ");
                }
                for (int i = 0; i < tail; i++) {
                    sb.append(elementData[i] + " ");
                }
            }
            return sb.toString();
        }
    }


    public int[] maxSlibingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0)
            return new int[0];
        int[] output = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[i]);
            }
            output[i] = max;
        }
        return output;
    }

    ArrayDeque<Integer> deq = new ArrayDeque<Integer>();
    int[] nums;

    public void clean_deque(int i, int k) {
        // remove indexes of elements not from sliding window
        if (!deq.isEmpty() && deq.getFirst() == i - k)
            deq.removeFirst();

        // remove from deq indexes of all elements
        // which are smaller than current element nums[i]
        while (!deq.isEmpty() && nums[i] > nums[deq.getLast()]) {
            deq.removeLast();
        }
    }

    /*
    遍历数组，将数存放在双向队列中，并用L,R来标记窗口的左边界和右边界。队列中保存的并不是真的数，
    而是该数值对应的数组下标位置，并且数组中的数要从大到小排序。如果当前遍历的数比队尾的值大，
    则需要弹出队尾值，直到队列重新满足从大到小的要求。刚开始遍历时，L和R都为0，有一个形成窗口的过程，
    此过程没有最大值，L不动，R向右移。当窗口大小形成时，L和R一起向右移，每次移动时，
    判断队首的值的数组下标是否在[L,R]中，如果不在则需要弹出队首的值，当前窗口的最大值即为队首的数。
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0)
            return new int[0];
        if (k == 1)
            return nums;

        // init deque and output
        this.nums = nums;
        int max_idx = 0;
        for (int i = 0; i < k; i++) {
            clean_deque(i, k);
            deq.addLast(i);
            // compute max in nums[:k]
            if (nums[i] > nums[max_idx])
                max_idx = i;
        }
        int[] output = new int[n - k + 1];
        output[0] = nums[max_idx];

        // build output
        for (int i = k; i < n; i++) {
            clean_deque(i, k);
            deq.addLast(i);
            output[i - k + 1] = nums[deq.getFirst()];
        }
        return output;
    }

    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length < 2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length - k + 1];
        // 遍历nums数组
        for (int i = 0; i < nums.length; i++) {
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if (i + 1 >= k) {
                result[i + 1 - k] = nums[queue.peek()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        QueueDemo queueDemo = new QueueDemo();
//        queueDemo.maxSlibingWindow(nums, 3);
        MyCircularDeque myCircularDeque = queueDemo.new MyCircularDeque(10);
        myCircularDeque.insertFront(1);
        myCircularDeque.insertFront(2);
        myCircularDeque.insertFront(3);
        myCircularDeque.insertFront(4);
        myCircularDeque.insertFront(5);
        System.out.println(myCircularDeque.toString());
        myCircularDeque.insertLast(66);
        myCircularDeque.deleteFront();
        System.out.println(myCircularDeque.toString());
        myCircularDeque.deleteLast();
        System.out.println(myCircularDeque.toString());
        System.out.println(myCircularDeque.getFront());
        System.out.println(myCircularDeque.getRear());

    }

}

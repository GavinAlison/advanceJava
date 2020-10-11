package com.alison.heap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author alison
 * @Date 2019/8/18  22:21
 * @Version 1.0
 * @Description
 */
public class MyPriorityQueue<T> {
    private List<T> queue;
    private int size;

    public MyPriorityQueue() {
        queue = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            queue.add(null);
        }
    }

    public boolean offer(T t) {
        int k = size;
        if (size == 0)
            queue.set(0, t);
        size++;
        shiftUp(k, t);
        return true;
    }

    // 从堆底往上浮动
    public void shiftUp(int k, T t) {
        Comparable<? super T> key = (Comparable<? super T>) t;
        while (k > 0) {
            int parent = (k - 1) >> 1;
            Object e = queue.get(parent);
            if (key.compareTo((T) e) > 0)
                break;
            queue.set(k, (T) e);
            k = parent;
        }
        queue.set(k, (T) key);
    }

    public T poll() {
        if (size == 0)
            return null;
        int s = --size;
        T result = (T) queue.get(0);
        T end = (T) queue.get(s);
        queue.set(s, null);
        if (s != 0)
            shiftDown(0, end);
        return result;
    }

    public void shiftDown(int k, T end) {
        Comparable<? super T> key = (Comparable<? super T>) end;
        int half = size / 2;
        while (k < half) {
            int left = (k << 1) + 1;
            int right = (k << 1) + 2;
            T c = queue.get(left);
            if (right < size && ((Comparable<? super T>) c).compareTo((T) queue.get(right)) > 0) {
                c = queue.get(right);
            }
            if (key.compareTo((T) c) <= 0)
                break;
            queue.set(k, c);
            k = left;
        }
        queue.set(k, (T) key);
    }

    public static void main(String[] args) {
        MyPriorityQueue<Integer> myPriorityQueue = new MyPriorityQueue();
        myPriorityQueue.offer(1);
        myPriorityQueue.offer(5);
        myPriorityQueue.offer(4);
        myPriorityQueue.offer(3);
        myPriorityQueue.offer(9);
        myPriorityQueue.queue.stream().filter((item) -> !Objects.isNull(item)).forEach(item -> System.out.print(item + " "));
        System.out.println("\n" + myPriorityQueue.poll());
    }

}

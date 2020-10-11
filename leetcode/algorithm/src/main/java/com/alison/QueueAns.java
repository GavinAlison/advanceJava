package com.alison;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/7  21:10
 * @Version 1.0
 * @Description
 */
public class QueueAns<E> {

    // 算法思想： 需要数组存储元素，size表示实际大小，DEFAULT_CAPACITY： 默认的容量
    //  CAPACITY： 用于初始化数组，  head: 队头， tail:  队尾
    //  initialize 初始化数组，
    //  动态的顺序队列
    public class ArrayQueue<E> {
        private int DEFAULT_SIZE = 10;

        private int capacity;

        private Object[] elementData;

        private int head = 0;

        private int tail = 0;

        private int size = 0;

        public ArrayQueue() {
            capacity = DEFAULT_SIZE;
            elementData = new Object[capacity];
        }

        public ArrayQueue(int initialze) {
            elementData = new Object[initialze];
            capacity = initialze;
        }

        public int size() {
            return size;
        }

        public void offer(E element) {
            ensureCapacity(tail + 1);
            elementData[tail++] = element;
        }

        private void ensureCapacity(int minCapacity) {
            int oldCapacity = elementData.length;
            if (minCapacity > oldCapacity) {
                int newCapacity = oldCapacity * 3 / 2 + 1;
                if (newCapacity < minCapacity)
                    newCapacity = minCapacity;
                elementData = Arrays.copyOf(elementData, newCapacity);
            }
        }

        public E poll() {
            if (isEmpty())
                throw new RuntimeException("空队列");
            E val = (E) elementData[head];
            elementData[head++] = null;
            return val;
        }

        public E peek() {
            if (isEmpty())
                throw new RuntimeException("空队列");
            return (E) elementData[head];

        }

        private boolean isEmpty() {
            return size == 0;
        }

        @Override
        public String toString() {
            if (isEmpty()) {
                return "[]";
            } else {
                StringBuilder sb = new StringBuilder("[");
                for (int i = head; i < tail; i++) {
                    sb.append(elementData[i].toString() + ", ");
                }
                int len = sb.length();
                return sb.delete(len - 2, len).append("]").toString();
            }
        }
    }

    public class LinkQueue<E> {
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        private class Node {
            private E data;
            private Node next;
        }

        private Node head;
        private Node tail;
        private int size;

        public int size() {
            return size;
        }

        public void offer(E element) {
            Node newNode = new Node(element, null);
            if (head == null) {
                head = newNode;
                tail = head;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        public E poll() {
            Node oldNode = head;
            E val = head.data;
            head = head.next;
            oldNode = null;
            return val;
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] arr2 = new int[arr1.length];
        System.arraycopy(arr1, 0, arr2, 2, 2);
//        Arrays.stream(arr2).forEach((item) -> System.out.print(item + " "));
        Arrays.stream(arr2).forEach(System.out::print);
    }


}

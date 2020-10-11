package com.alison;

/**
 * 循环队列
 *
 * @param <E>
 */
public class LoopQueue<E> {
    private Object[] elementData;
    private int head;
    private int tail;
    private int capacity;
    private int size;

    private int DEFAULT_SIZE = 10;

    public LoopQueue() {
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    public LoopQueue(E e) {
        this();
        elementData[0] = e;
        tail++;
        size++;
    }

    public LoopQueue(int capacity) {
        this.capacity = capacity;
        elementData = new Object[capacity];
    }

    public void enqueue(E e) {
        if (head == tail && elementData[head] != null) {
            throw new IndexOutOfBoundsException("队列已满");
        }
        elementData[tail] = e;
        tail = tail == capacity ? 0 : tail + 1;
        size++;
    }

    public E dequeue() {
        if (isEmpty())
            throw new RuntimeException("队列为空");
        E value = (E) elementData[head];
        // 释放elementData[head]
        elementData[head++] = null;
        // head 已经向后移动一位
        head = head == capacity ? 0 : head;
        size--;
        return value;
    }

    private boolean isEmpty() {
        return head == tail && elementData[head] == null;
    }

    public E peek() {
        return (E) elementData[head];
    }

    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder("[");
            if (head < tail) {
                for (int i = head; i < tail; i++) {
                    sb.append(elementData[i] + ", ");
                }
                sb.delete(sb.length() - 2, sb.length()).append("]");
            } else {
                for (int i = head; i < capacity; i++) {
                    sb.append(elementData[i] + ", ");
                }
                for (int i = 0; i < tail; i++) {
                    sb.append(elementData[i] + ", ");
                }
                sb.delete(sb.length() - 2, sb.length()).append("]");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        LoopQueue loopQueue = new LoopQueue(10);
        loopQueue.enqueue("a");
        loopQueue.enqueue("b");
        loopQueue.enqueue("c");
        loopQueue.enqueue("d");
        loopQueue.enqueue("e");
        System.out.println(loopQueue);
        System.out.println(loopQueue.dequeue());
        System.out.println(loopQueue.peek());
        System.out.println(loopQueue);
    }

}

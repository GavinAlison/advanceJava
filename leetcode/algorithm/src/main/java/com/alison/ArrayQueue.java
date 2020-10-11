package com.alison;

/**
 * @Author alison
 * @Date 2019/8/6  22:52
 * @Version 1.0
 * @Description
 */
public class ArrayQueue<E> {
    private Object[] elementData;
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private int capacity;


    public ArrayQueue(int initialize) {
        elementData = new Object[initialize];
        this.capacity = initialize;
    }

    public boolean enqueue(E e) {
        if (isFull())
            return false;
//        if (tail < capacity - 1) {
//            elementData[tail++] = e;
//            size++;
//            return true;
//        }
//        // adjust
//        if (tail == capacity - 1 && head != 0) {
//            for (int i = head; i <= tail; i++) {
//                elementData[i - head] = elementData[i];
//            }
//            head = 0;
//            tail = tail - head;
//            elementData[tail++] = e;
//            size++;
//        }
//        return true;
        // adjust
        if (tail == capacity && head != 0) {
            for (int i = head; i < tail; i++) {
                elementData[i - head] = elementData[i];
            }
            tail = tail - head;
            head = 0;
        }
        elementData[tail++] = e;
        size++;
        return true;
    }

    private boolean isFull() {
        return size == capacity;
    }


    public E dequeue() {
        if (isEmpty())
            return null;
        E result = (E) elementData[head];
        head++;
        size--;
        for (int i = head; i < tail; i++) {
            elementData[i - head] = elementData[i];
        }
        tail = tail - head;
        head = 0;
        return result;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public void display() {
        for (int i = head; i < tail; i++) {
            System.out.print(elementData[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(8);
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        arrayQueue.enqueue(10);
        arrayQueue.display();
        System.out.println(arrayQueue.dequeue());
        System.out.println(arrayQueue.dequeue());
        System.out.println(arrayQueue.dequeue());
        arrayQueue.display();
        arrayQueue.enqueue(10);
        arrayQueue.enqueue(11);
        arrayQueue.display();
    }

}

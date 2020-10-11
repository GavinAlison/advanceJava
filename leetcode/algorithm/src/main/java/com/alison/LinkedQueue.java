package com.alison;

/**
 * @Author alison
 * @Date 2019/8/6  23:38
 * @Version 1.0
 * @Description
 */
public class LinkedQueue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    // 队尾入队
    public void enqueue(E e) {
        Node<E> newNode = new Node(e, null);
        if (isEmpty()) {
            tail = newNode;
            head = tail;
        } else {
            tail.setNext(newNode);
            tail = tail.getNext();
        }
        size++;
    }

    // 对头出队
    public E dequeue() {
        if (isEmpty())
            throw new RuntimeException("当前队列为空");
        E result = head.getData();
        head = head.getNext();
        return result;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public void display() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.getData() + " ");
            curr = curr.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedQueue linkedQueue = new LinkedQueue();
//        linkedQueue.dequeue();
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4);
        linkedQueue.enqueue(5);
        linkedQueue.display();
        System.out.println(linkedQueue.dequeue());
        linkedQueue.display();
    }
}

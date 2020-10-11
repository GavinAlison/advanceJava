package com.alison;

import lombok.NoArgsConstructor;

/**
 * @Author alison
 * @Date 2019/8/4  9:07
 * @Version 1.0
 * @Description
 */
@NoArgsConstructor
public class CircleLinkList<E> {

    private Node<E> elementData;

    private int size;

    private static class Node<E> {
        private E value;
        private Node next;

        public Node(E e, Node next) {
            this.value = e;
            this.next = next;
        }

        public Node() {
        }
    }

    // get
    public E get(int index) {
        checkElementIndex(index);
        Node<E> head = elementData;
        int i = 0;
        while (i != index) {
            head = head.next;
            i++;
        }
        return (E) head.value;
    }

    // add
//    public boolean add(int index, E e) {
//        checkElementIndex(index);
//        size++;
//        if (size == 0) {
//            Node<E> newNode = new Node<>(e, null);
//            newNode.next = newNode;
//            elementData = newNode;
//            return true;
//        }
//        Node<E> head = elementData;
//        for (int i = 0; i < size; i++) {
//            Node<E> newNode = new Node<>();
//            newNode.next = head.next;
//            head.next = newNode;
//            head = head.next;
//            elementData = head;
//        }
//        return true;
//    }

    public boolean add(E e) {
        Node head = elementData;
        size++;
        Node newNode = new Node(e, null);
        if (head == null) {
            newNode.next = newNode;
            elementData = newNode;
            return true;
        }
        int i = 1;
        while (i != size - 1) {
            head = head.next;
            i++;
        }
        newNode.next = head.next;
        head.next = newNode;
        return true;
    }

    private void checkElementIndex(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(OutOfBoundMsg(index));
    }

    private String OutOfBoundMsg(int index) {
        return "Index: " + index + ", size:" + size;
    }

    // del
    public boolean remove(int index) {
        checkElementIndex(index);
        Node<E> head = elementData;
        if (index == 0) {
            for (int i = 0; i < size - 1; i++) {
                head = head.next;
            }
            head.next = head.next.next;
            elementData = head.next;
            size--;
            return true;
        }
        for (int i = 0; i < index - 1; i++) {
            head = head.next;
        }
        head.next = head.next.next;
        size--;
        return true;
    }

    // size
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        Node head = elementData;
        for (int i = 0; i < size; i++) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println("\n----");
        return "CircleLinkList{" +
                "elementData=" + elementData +
                ", size=" + size +
                '}';
    }

    public static void main(String[] args) {
        CircleLinkList<Integer> circleLinkList = new CircleLinkList();
        circleLinkList.add(2);
        circleLinkList.add(3);
        circleLinkList.add(4);
        circleLinkList.add(5);
        System.out.println(circleLinkList);
        int val = circleLinkList.get(1);
        System.out.println(val);
//        circleLinkList.remove(10);
        circleLinkList.remove(1);
        System.out.println(circleLinkList);
    }
}

package com.alison;

import lombok.NoArgsConstructor;

/**
 * @Author alison
 * @Date 2019/8/3  23:56
 * @Version 1.0
 * @Description
 */
@NoArgsConstructor
public class SinglyLinkList<E> {

    private Node<E> elementData;

    private int size;

    private static class Node<E> {
        private Object data;
        private Node next;

        public Node(Object obj, Node next) {
            data = obj;
            this.next = next;
        }

        public Node() {
        }
    }

    // add
    public boolean add(int index, E e) {
        checkElementIndex(index);
        size++;
        Node tail = new Node<>();
        if (elementData == null) {
            elementData = new Node<>(e, tail);
            return true;
        }
        int i = 0;
        Node head = elementData;
        while (i != index) {
            head = head.next;
            i++;
        }
        Node newNode = new Node(e, head.next);
        head.next = newNode;
        return true;
    }

    public boolean add(E e) {
        size++;
        Node tail = new Node<>();
        if (elementData == null) {
            elementData = new Node<>(e, tail);
            return true;
        }
        Node head = elementData;
        while (head.next != null) {
            head = head.next;
        }
        head.data = e;
        head.next = tail;
        return true;
    }

    // get
    public E get(int index) {
        checkElementIndex(index);
        Node head = elementData;
        int i = 0;
        while (i != index) {
            head = head.next;
            i++;
        }
        return (E) head.data;
    }

    private void checkElementIndex(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(OutOfBoundsMsg(index));
    }

    private String OutOfBoundsMsg(int index) {
        return "Index: " + index + ", size: " + size;
    }

    // del
    public boolean delete(int index) {
        checkElementIndex(index);
        size--;
        Node head = elementData;
        int i = 0;
        while (i != index - 1) {
            head = head.next;
            i++;
        }
        head.next = head.next.next;
        return true;
    }

    // size
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        Node head = this.elementData;
        while (head.next != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println("----");
        return "SingleLinkList{" +
                "elementData=" + elementData +
                ", size=" + size +
                '}';
    }

    public static void main(String[] args) {
        SinglyLinkList singlyLinkList = new SinglyLinkList();
        singlyLinkList.add(1);
        singlyLinkList.add(2);
        singlyLinkList.add(0, 99);
        System.out.println(singlyLinkList);
        singlyLinkList.delete(1);
        System.out.println(singlyLinkList);
        System.out.println(singlyLinkList.get(1));

    }
}

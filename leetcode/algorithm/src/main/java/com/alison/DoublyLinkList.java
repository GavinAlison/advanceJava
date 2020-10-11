package com.alison;

/**
 * @Author alison
 * @Date 2019/8/4  9:37
 * @Version 1.0
 * @Description
 */
public class DoublyLinkList<E> {

    private int size;

    private Node<E> head;

    private static class Node<E> {
        private Node<E> pre;
        private Node<E> next;
        private E value;

        public Node(Node<E> pre, E value, Node<E> next) {
            this.pre = pre;
            this.value = value;
            this.next = next;
        }

        public Node(E value) {
            this.value = value;
        }

        public Node() {
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    // add
    public void addFirst(E e) {
        Node newNode = new Node(e);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head.pre = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node newNode = new Node(e);
        if (head == null) {
            head = newNode;
        } else {
            Node<E> cursor = head;
            int i = 0;
            while (i < size - 1) {
                cursor = cursor.next;
                i++;
            }
            cursor.next = newNode;
            newNode.next = null;
            newNode.pre = cursor;
        }
        size++;
    }

    public void deleteHead() {
        checkLink();
        head = head.next;
        head.pre = null;
        size--;
    }


    private void checkLink() {
        if (head == null)
            throw new RuntimeException("链表为null");
    }

    public void deleteTail() {
        checkLink();
        int i = 1;
        Node<E> cursor = head;
        while (i < size - 1) {
            cursor = cursor.next;
            i++;
        }
        cursor.next = null;
        size--;
    }

    public void insertlist(int index, E e) {
        checkElementIndex(index);
        if (head == null) {
            addFirst(e);
        } else {
            Node preNode = head;
            int i = 0;
            while (i < index - 1) {
                preNode = preNode.next;
                i++;
            }
            Node currNode = preNode.next;
            Node newNode = new Node(e);
            newNode.next = currNode;
            newNode.pre = preNode;
            preNode.next = newNode;
            if (currNode != null)
                currNode.pre = newNode;
            size++;
        }
    }

    public void deletelist(int index) {
        checkElementIndex(index);
        if (index == 0) {
            deleteHead();
        } else if (index == size - 1) {
            deleteTail();
        } else {
            Node cursor = head;
            int i = 0;
            while (i != index - 1) {
                cursor = cursor.next;
                i++;
            }
            cursor.next = cursor.next.next;
            if (cursor.next.next != null)
                cursor.next.next.pre = cursor;
            size--;
        }
    }

    private void checkElementIndex(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(OutOfBoundsMsg(index));
    }

    private String OutOfBoundsMsg(int index) {
        return "Index: " + index + ", size: " + size;
    }

    // size
    public int size() {
        return this.size;
    }

    public E get(int index) {
        checkElementIndex(index);
        int i = 0;
        Node cursor = head;
        while (i < index) {
            cursor = cursor.next;
            i++;
        }
        return (E) cursor.value;
    }

    @Override
    public String toString() {
        Node cursor = head;
        for (int i = 0; i < size; i++) {
            System.out.print(cursor.value + " ");
            cursor = cursor.next;
        }
        System.out.println();
        return "TwoWayLinkList{" +
                "size=" + size +
                '}';
    }

    public static void main(String[] args) {
        DoublyLinkList<Integer> doublyLinkList = new DoublyLinkList();
        doublyLinkList.addFirst(1);
        doublyLinkList.addLast(5);
        doublyLinkList.addLast(2);
        doublyLinkList.addFirst(4);
        doublyLinkList.insertlist(1, 14);
        System.out.println(doublyLinkList);
        doublyLinkList.deleteHead();
        doublyLinkList.deleteTail();
        System.out.println(doublyLinkList);
        System.out.println(doublyLinkList.get(0));
        doublyLinkList.deletelist(1);
        System.out.println(doublyLinkList);
    }
}

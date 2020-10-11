package com.alison;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LinkedStack<E> {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Node<E> {
        private E data;
        private Node<E> next;
    }

    private Node top;

    private int size;

    public void push(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (top != null) {
            Node curr = top;
            newNode.next = curr;
        }
        top = newNode;
        size++;
    }

    public E pop() {
        if (size == 0)
            throw new IllegalArgumentException("当前栈为空");
        size--;
        E value = (E) top.data;
        top = top.next;
        return value;
    }

    public E peek() {
        if (size == 0)
            throw new IllegalArgumentException("当前栈为空");
        return (E) top.data;
    }

    public void display() {
        Node curr = top;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedStack<Integer> linkedStack = new LinkedStack<>();
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.push(3);
        linkedStack.push(4);
        linkedStack.display();
        System.out.println(linkedStack.pop());
        System.out.println(linkedStack.peek());
        linkedStack.display();
    }
}

package com.alison;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author alison
 * @Date 2019/8/5  22:52
 * @Version 1.0
 * @Description
 */
public class Browser<E> {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Node<E> {
        private E data;
        private Node next;
    }

    private static class LinkedStack<E> {
        private Node top;
        private int size;

        public void push(E e) {
            Node newNode = new Node(e, null);
            if (size != 0) {
                newNode.next = top;
            }
            top = newNode;
            size++;
        }

        public E pop() {
            if (size == 0) {
                throw new RuntimeException("当前栈元素为空！");
            }
            E result = (E) top.data;
            top = top.next;
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    private LinkedStack<E> history = new LinkedStack<>();

    private LinkedStack<E> forward = new LinkedStack<>();

    public void go() {
        if (history.isEmpty())
            throw new RuntimeException("没有前进记录");
        E val = forward.pop();
        System.out.println("前进" + val);
        history.push(val);
    }

    public void back() {
        if (history.isEmpty())
            throw new RuntimeException("没有历史记录");
        E val = history.pop();
        System.out.println(val + "后退");
        forward.push(val);
    }

    public void push(E e) {
        history.push(e);
    }

    public static void main(String[] args) {
        Browser<Integer> browser = new Browser();
        browser.push(1);
        browser.push(2);
        browser.push(3);
//        browser.go();
        browser.back();
        browser.go();
        browser.back();
        browser.back();
        browser.back();
    }

}

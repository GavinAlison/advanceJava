package com.alison;

import java.util.LinkedList;

/**
 * @Author alison
 * @Date 2019/8/6  23:24
 * @Version 1.0
 * @Description
 */
public class ArrayQueueT<E> {
    private LinkedList<E> vet = new LinkedList<>();

    public void enqueue(E e) {
        this.vet.addFirst(e);
    }

    public E dequeue() {
        return (E) this.vet.removeFirst();
    }

    public boolean isEmpty(){
        return this.vet.isEmpty();
    }

}

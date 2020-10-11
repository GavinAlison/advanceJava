package com.alison;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/3  21:58
 * @Version 1.0
 * @Description
 */
public class DynamicArray<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static Object[] EMPTY_ELEMENTDATA = {};

    private transient Object[] elementData;

    private int size = 0;

    public DynamicArray(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity" + initialCapacity);
        }
    }

    public DynamicArray() {
        this.elementData = EMPTY_ELEMENTDATA;
    }

    // add
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size] = e;

        size++;
        return true;

    }

    // delete
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    public E remove(int index) {
        rangCheck(index);
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    private void rangCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", size: " + size;
    }

    private E elementData(int index) {
        return (E) elementData[index];
    }

    // get
    public E get(int index) {
        rangCheck(index);
        return elementData(index);
    }


    // size
    public int size() {
        return this.size;
    }


    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public String toString() {
        return "DynamicArray{" +
                "elementData=" + Arrays.toString(elementData) +
                ", size=" + size +
                '}';
    }

    public static void main(String[] args) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(2);
        dynamicArray.add(21);
        dynamicArray.add(22);
        dynamicArray.add(23);
        System.out.println(dynamicArray);
        int num = dynamicArray.get(0);
        System.out.println(num);
        dynamicArray.remove(2);
        System.out.println(dynamicArray);
    }


}

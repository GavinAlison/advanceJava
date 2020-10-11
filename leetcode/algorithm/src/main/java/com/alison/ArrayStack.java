package com.alison;

/**
 * @Author alison
 * @Date 2019/8/5  23:06
 * @Version 1.0
 * @Description 顺序栈
 * 实现进栈、出栈、获取栈顶元素、判空、栈容量、获取下标对应元素、遍历
 */
public class ArrayStack<E> {

    private Object[] elementData; // 栈元素

    private int capacity; // 栈大小

    private int top; // 指向栈顶元素

    // 初始化栈
    public ArrayStack(int initialSize) {
        valid(initialSize);
        elementData = new Object[initialSize];
        this.capacity = initialSize;
        top = -1; // 开始栈顶top=-1
    }

    private void valid(int initialSize) {
        if (initialSize < 0)
            throw new IllegalArgumentException("长度小于0");
    }

    // 实现进栈
    public void push(E val) {
        if (!isFull())
            elementData[++top] = val;
    }

    // 出栈
    public E pop() {
        if (!isEmpty()) {
            capacity--;
            return (E) elementData[top--];
        }
        throw new RuntimeException("栈为空");
    }

    // 获取栈顶元素
    public E peek() {
        if (!isEmpty())
            return (E) elementData[top];
        throw new RuntimeException("栈为空");
    }

    // 判空
    private boolean isFull() {
        return (top + 1 == capacity) ? true : false;
    }

    private boolean isEmpty() {
        return (top == -1) ? true : false;
    }

    // 栈容量
    public int size() {
        return capacity;
    }

    // 获取下标对应元素
    public E getPeekN(int n) {
        checkElementIndex(n);
        return (E) elementData[n];
    }

    private void checkElementIndex(int n) {
        if (n < 0 || n > top)
            throw new IndexOutOfBoundsException("下标越界, index: " + n);
    }

    // 遍历
    public void display() {
        int head = top;
        while (head != -1)
            System.out.print(elementData[head--] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(4);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.display();
        System.out.println(arrayStack.size());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.peek());
        arrayStack.display();
    }
}

package com.alison.heap;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Author alison
 * @Date 2019/8/18  22:17
 * @Version 1.0
 * @Description
 */
public class PriorityQueueDemo {
    private static Random random = new Random();

    public static Customer[] factory(int i) {
        Customer[] customers = new Customer[i];
        for (int t = 0; t < i; t++) {
            customers[t] = new Customer(random.nextInt(10));
        }
        return customers;
    }

    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue();
        Customer[] customers = factory(10);
        for (int i = 0; i < customers.length; i++)
            priorityQueue.add(customers[i]);
        Customer customer;
        for (int j = 0; j < customers.length; j++) {
            customer = (Customer) priorityQueue.remove();
            System.out.println(customer.getLevel());
        }
    }
}

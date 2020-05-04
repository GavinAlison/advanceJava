package com.alison.recursion;

/**
 * @Author alison
 * @Date 2020/5/4
 * @Version 1.0
 * @Description
 */
public class RecursionUtil {

    static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n-1);
    }
    static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

}

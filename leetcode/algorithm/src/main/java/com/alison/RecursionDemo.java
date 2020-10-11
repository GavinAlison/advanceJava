package com.alison;

import java.util.Arrays;

/**
 * @Author alison
 * @Date 2019/8/7  21:50
 * @Version 1.0
 * @Description
 */
public class RecursionDemo {

    public int fibonaci(int n) {
        if (n == 1 || n == 0)
            return 1;
        return fibonaci(n - 1) + fibonaci(n - 2);
    }

    public int factorial(int n) {
        if (n == 1)
            return 1;
        return factorial(n - 1) * n;
    }

    public void perm(int[] arr, int begin, int end) {
        if (begin == end) {
            System.out.print(Arrays.toString(arr));
            return;
        }
        for (int i = begin; i <= end; i++) {
            swap(arr, begin, i);
            perm(arr, begin + 1, end);
            swap(arr, i, begin);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 递归
    public int climbStairs(int n) {
        if (n == 1 || n == 0)
            return 1;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    // 动态规划
    public int climStairs2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        RecursionDemo recursion = new RecursionDemo();
        System.out.println(recursion.climbStairs(4));
    }


}

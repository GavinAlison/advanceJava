package com.alison.recursion;

/**
 * @Author alison
 * @Date 2020/4/28
 * @Version 1.0
 * @Description
 */
public class FactorialTailRecursion {
    /**
     * 阶乘计算 -- 尾递归解决, java8不支持尾递归优化
     *
     * @param factorial 上一轮递归保存的值
     * @param number    当前阶乘需要计算的数值
     * @return number!
     */
    public static int factorialTailRecursion_only(final int factorial, final int number) {
        if (number == 1) {
            return factorial;
        }
        return factorialTailRecursion_only(factorial * number, number - 1);
    }

    /**
     * 阶乘计算 -- 递归解决
     *
     * @param number 当前阶乘需要计算的数值
     * @return number!
     */
    public static int factorialRecursion(final int number) {
        if (number == 1) {
            return number;
        }
        return number * factorialRecursion(number - 1);
    }


    /**
     * 阶乘计算 -- 使用尾递归接口完成
     *
     * @param factorial 当前递归栈的结果值
     * @param number    下一个递归需要计算的值
     * @return 尾递归接口, 调用invoke启动及早求值获得结果
     */
    public static TailRecursion<Integer> factorialTailRecursion(final int factorial, final int number) {
        if (number == 1) {
            return TailInvoke.done(factorial);
        }
        return TailInvoke.call(() -> FactorialTailRecursion.factorialTailRecursion(factorial + number, number - 1));
    }
}

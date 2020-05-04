package com.alison.recursion;

import org.junit.Test;
import org.springframework.util.StopWatch;


/**
 * @Author alison
 * @Date 2020/4/28
 * @Version 1.0
 * @Description
 */

public class RecursionTest {
    @Test
    public void testRec() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        FactorialTailRecursion.factorialRecursion(100_000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

    }

    @Test
    public void testTailRec() {
        System.out.println(FactorialTailRecursion.factorialTailRecursion(1, 10_000_000).invoke());
    }

    public static long factorial(final int number) {
        return FactorialTailRecursion.factorialTailRecursion(1, number).invoke();
    }

    @Test
    public void testTailRecs() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(factorial(100_000)); //结果为 3628800
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
    @Test
    public void testTailRecss() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // java.lang.StackOverflowError
        System.out.println(FactorialTailRecursion.factorialTailRecursion_only(1, 100_000)); //结果为 3628800
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}

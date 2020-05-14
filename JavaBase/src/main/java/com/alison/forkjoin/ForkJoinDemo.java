package com.alison.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;
import static com.alison.testMsecs.MeasureSumPerf.measureSumPerf;

/**
 * @Author alison
 * @Date 2020/4/23
 * @Version 1.0
 * @Description
 */
public class ForkJoinDemo {

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinDemo().new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    @Test
    public void test() {
        System.out.println("ForkJoin sum done in: " + measureSumPerf(
                ForkJoinDemo::forkJoinSum, 10_000_000) + " msecs");
    }
    @Test
    public void test1() {
        System.out.println("ForkJoin sum done in: " + measureSumPerf(
                n->ForkJoinDemo.forkJoinSum(n), 10_000_000) + " msecs");
    }

    public class ForkJoinSumCalculator
            extends java.util.concurrent.RecursiveTask<Long> {
        private final long[] numbers;
        private final int start;
        private final int end;
        public static final long THRESHOLD = 10_000;

        public ForkJoinSumCalculator(long[] numbers) {
            this(numbers, 0, numbers.length);
        }

        private ForkJoinSumCalculator(long[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                return computeSequentially();
            }
            ForkJoinSumCalculator leftTask =
                    new ForkJoinSumCalculator(numbers, start, start + length / 2);
            leftTask.fork();
            ForkJoinSumCalculator rightTask =
                    new ForkJoinSumCalculator(numbers, start + length / 2, end);
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();
            return leftResult + rightResult;
        }

        private long computeSequentially() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
    }


}

package com.alison.testMsecs;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author alison
 * @Date 2020/4/21
 * @Version 1.0
 * @Description
 */
public class MeasureSumPerf {

    public static long measureSumPerf(Function<Long, Long> fun, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = fun.apply(n);
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
//            System.out.println(String.format("cost time : %d ms", costTime));
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    @Test
    public void stdout() {
        System.out.println(System.nanoTime());
//        LocalDateTime.ofInstant(Instant.)
    }

    @Test
    public void testCost() {
        long start = System.nanoTime();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long period = System.nanoTime() - start;
        System.out.println(String.format(
                "period(nanoseconds): %d, period(seconds): %d", new Object[]{
                        period, TimeUnit.NANOSECONDS.toSeconds(period)}));
    }

    @Test
    public void test() {
        long start = System.nanoTime();
        long result = 0L;
        for (long i = 1L; i <= 100_000_000; i++) {
            result += i;
        }
        System.out.println(String.format("cost time : %d ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)));
    }

    @Test
    public void measureTest() {
        System.out.println("Sequential sum done in:" +
                measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs");
        System.out.println("Sequential sum done in:" +
                measureSumPerf(ParallelStreams::iterativeSum, 10_000_000L) + " msecs");
    }

    @Test
    public void measureStream() throws InterruptedException {
        System.out.println("Sequential sum done in:" +
                measureSumPerf(ParallelStreams::streamMap, 10) + " msecs");
        System.out.println("Sequential sum done in:" +
                measureSumPerf(ParallelStreams::iterativeSum, 100_000_000L) + " msecs");
        TimeUnit.HOURS.sleep(1);
    }

    static class ParallelStreams {
        public static long sequentialSum(long n) {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .reduce(0L, Long::sum);
        }

        public static long iterativeSum(long n) {
            long result = 0;
            for (long i = 1L; i <= n; i++) {
                result += i;
            }
            return result;
        }

        static List<Integer> list = Lists.newArrayList(1, 3, 2, 4, 5, 5, 6, 7, 8, 10);

        public static long streamMap(long n) {
            return Stream.iterate(1L, i -> i + 1)
                    .limit(n)
                    .mapToLong(x -> x)
                    .map(x -> ++x)
                    .boxed()
                    .reduce(0L, Long::sum);
        }

        public static void iteratorMap() {
            List<Integer> result = new ArrayList<>();
            for (Integer e : list) {
                result.add(++e);
            }
        }

        public static void paralleStreamMap() {
            List<Integer> result = list.parallelStream()
                    .mapToInt(x -> x)
                    .map(x -> ++x)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }
}

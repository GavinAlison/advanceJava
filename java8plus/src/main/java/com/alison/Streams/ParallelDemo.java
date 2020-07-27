package com.alison.Streams;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelDemo {

    public static void main(String[] args) {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());    // 7
        // 可通过jvm 设置: -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
//        filter:  b1 [main]
//        filter:  a2 [ForkJoinPool.commonPool-worker-1]
//        map:     a2 [ForkJoinPool.commonPool-worker-1]
//        filter:  c2 [ForkJoinPool.commonPool-worker-3]
//        map:     c2 [ForkJoinPool.commonPool-worker-3]
//        filter:  c1 [ForkJoinPool.commonPool-worker-2]
//        map:     c1 [ForkJoinPool.commonPool-worker-2]
//        forEach: C2 [ForkJoinPool.commonPool-worker-3]
//        forEach: A2 [ForkJoinPool.commonPool-worker-1]
//        map:     b1 [main]
//        forEach: B1 [main]
//        filter:  a1 [ForkJoinPool.commonPool-worker-3]
//        map:     a1 [ForkJoinPool.commonPool-worker-3]
//        forEach: A1 [ForkJoinPool.commonPool-worker-3]
//        forEach: C1 [ForkJoinPool.commonPool-worker-2]
    }

    private void sort(){
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
//filter:  c2 [ForkJoinPool.commonPool-worker-3]
//filter:  c1 [ForkJoinPool.commonPool-worker-2]
//map:     c1 [ForkJoinPool.commonPool-worker-2]
//filter:  a2 [ForkJoinPool.commonPool-worker-1]
//map:     a2 [ForkJoinPool.commonPool-worker-1]
//filter:  b1 [main]
//map:     b1 [main]
//filter:  a1 [ForkJoinPool.commonPool-worker-2]
//map:     a1 [ForkJoinPool.commonPool-worker-2]
//map:     c2 [ForkJoinPool.commonPool-worker-3]
//sort:    A2 <> A1 [main]
//sort:    B1 <> A2 [main]
//sort:    C2 <> B1 [main]
//sort:    C1 <> C2 [main]
//sort:    C1 <> B1 [main]
//sort:    C1 <> C2 [main]
//forEach: A1 [ForkJoinPool.commonPool-worker-1]
//forEach: C2 [ForkJoinPool.commonPool-worker-3]
//forEach: B1 [main]
//forEach: A2 [ForkJoinPool.commonPool-worker-2]
//forEach: C1 [ForkJoinPool.commonPool-worker-1]

    }
}

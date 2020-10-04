package com.alison.forkjoin;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

/**
 * @author peidong.meng
 * @date 2019/11/23
 */
@Slf4j
public class ForkJoinPoolRun {

    /**
     * 并行处理列表方法
     * @param task  任务
     * @param <V>   参数对象类型
     * @param <R>   返回对象类型
     * @return
     */
    public static <V, R> List<R> run(ListForkJoinExecution<V, R> task){
        return run(8, task);
    }

    public static <V, R> List<R> run(int poolSize, ListForkJoinExecution<V, R> task){
        ForkJoinPool pool = new ForkJoinPool(poolSize);

        List<R> result = Lists.newArrayList();
        ConcurrentLinkedQueue<ListForkJoinExecution<V, R>> resultQueue = new ConcurrentLinkedQueue<>();
        try {

            task.setResult(resultQueue);
            // 执行
            R r = pool.submit(task).get();
            // 没有结算结果的不追加到结果集中
            if (r != null) {
                result.add(r);
            }

            while (resultQueue.iterator().hasNext()) {
                ListForkJoinExecution<V, R> poll = resultQueue.poll();
                if (poll != null) {
                    R join = poll.join();
                    // 没有结算结果的不追加到结果集中
                    if (join != null) {
                        result.add(join);
                    }
                }

            }

            pool.shutdown();

//            log.info("----- 遍历处理任务结果数量: {}", result.size());

            return result;
        } catch (Exception e) {
            log.error("遍历处理任务异常！", e);
        }

        return result;
    }

    /**
     * 并执行无返回方法
     * @param task  任务
     * @param <R>   返回对象类型
     * @return
     */
    public static <R> void run(VoidForkJoinExecution<R> task){
        run(8, task);
    }

    public static <R> void run(int poolSize, VoidForkJoinExecution<R> task){
        ForkJoinPool pool = new ForkJoinPool(poolSize);

        try {
            // 执行
            pool.submit(task);

            while (!pool.isTerminated()){
                pool.shutdown();

//                log.info("----- 遍历处理任务结果数量: {}", atomicInteger.get());
            }
        } catch (Exception e) {
            log.error("遍历处理任务异常！", e);
        }
    }
}

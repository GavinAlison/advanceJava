package com.alison.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author peidong.meng
 * @date 2019/11/13
 */
@Slf4j
public class VoidForkJoinExecution<V> extends RecursiveAction {

    private AtomicInteger atomicInteger;
    /**
     * 待处理数据
     */
    private transient List<V> values;

    /**
     * 单元逻辑执行函数
     */
    private transient Consumer<V> consumer;

    public VoidForkJoinExecution(List<V> values, Consumer<V> consumer) {
        this.values = values;
        this.consumer = consumer;
        atomicInteger = new AtomicInteger(values.size());
    }

    @Override
    protected void compute() {

        int len = values.size();

        try {
            if (len >= 3) {
                int min = len / 2;

                // 拆分前一半
                List<V> headValues = values.subList(0, min);
                VoidForkJoinExecution<V> a = new VoidForkJoinExecution(headValues, consumer);
                a.fork();

                // 拆分后一半
                List<V> endValues = values.subList(min + 1, len);
                VoidForkJoinExecution<V> b = new VoidForkJoinExecution(endValues, consumer);
                b.fork();

                // 本次任务处理一个
                try {
                    consumer.accept(values.get(min));
                } catch (Exception e) {
                    throw e;
                } finally {
                    atomicInteger.decrementAndGet();
                }
            } else if (len == 2) {

                List<V> headValues = values.subList(0, 1);
                VoidForkJoinExecution<V> a = new VoidForkJoinExecution(headValues, consumer);
                a.fork();

                // 拆分后一半
                List<V> endValues = values.subList(1, 2);
                VoidForkJoinExecution<V> b = new VoidForkJoinExecution(endValues, consumer);
                b.fork();
            } else if (len == 1) {

                try {
                    consumer.accept(values.get(0));
                } catch (Exception e) {
                    throw e;
                } finally {
                    atomicInteger.decrementAndGet();
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 运行方法
     * @return
     */
    public void run(){
        run(8);
    }

    public void run(int poolSize){

    }
}

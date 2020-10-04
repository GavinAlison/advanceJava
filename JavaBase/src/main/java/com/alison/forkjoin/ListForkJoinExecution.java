package com.alison.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

/**
 * @author peidong.meng
 * @date 2019/11/13
 */
@Slf4j
public class ListForkJoinExecution<V, R> extends RecursiveTask<R> {

    /**
     * 待处理数据
     */
    private transient List<V> values;

    /**
     * 单元逻辑执行函数
     */
    private transient Function<V, R> function;

    /**
     * 结果队列
     */
    private transient ConcurrentLinkedQueue<ListForkJoinExecution<V, R>> resultQueue;

    public ListForkJoinExecution(List<V> values, Function<V, R> function){
        this.values = values;
        this.function = function;
    }

    public void setResult(ConcurrentLinkedQueue<ListForkJoinExecution<V, R>> resultQueue) {
        this.resultQueue = resultQueue;
    }

    @Override
    protected R compute() {

//        List<R> result = Lists.newArrayList();
        int len = values.size();

        try {
            if(len >= 3){
                int min = len / 2;

                // 拆分前一半
                List<V> headValues = values.subList(0 , min);
                ListForkJoinExecution<V,R> a = new ListForkJoinExecution(headValues, function);
                a.setResult(resultQueue);
                a.fork();
                resultQueue.offer(a);

                // 拆分后一半
                List<V> endValues = values.subList(min + 1 , len);
                ListForkJoinExecution<V,R> b = new ListForkJoinExecution(endValues, function);
                b.setResult(resultQueue);
                b.fork();
                resultQueue.offer(b);

                // 本次任务处理一个
                R r = function.apply(values.get(min));
                if (r != null) {
                    return r;
                }
//
//                List<R> aR = a.join();
//                if (!CollectionUtils.isEmpty(aR)) {
//                    result.addAll(aR);
//                }
//                List<R> bR = b.join();
//                if (!CollectionUtils.isEmpty(bR)) {
//                    result.addAll(b.join());
//                }
            } else if (len == 2){

                List<V> headValues = values.subList(0 , 1);
                ListForkJoinExecution<V,R> a = new ListForkJoinExecution(headValues, function);
                a.setResult(resultQueue);
                a.fork();
                resultQueue.offer(a);

                // 拆分后一半
                List<V> endValues = values.subList(1 , 2);
                ListForkJoinExecution<V,R> b = new ListForkJoinExecution(endValues, function);
                b.setResult(resultQueue);
                b.fork();
                resultQueue.offer(b);

//                List<R> aR = a.join();
//                if (!CollectionUtils.isEmpty(aR)) {
//                    result.addAll(aR);
//                }
//                List<R> bR = b.join();
//                if (!CollectionUtils.isEmpty(bR)) {
//                    result.addAll(b.join());
//                }
            } else if(len == 1){

                R r = function.apply(values.get(0));
                if (r != null) {
                    return r;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return null;
    }
}

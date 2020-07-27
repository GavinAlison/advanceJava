//package com.alison.async;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.task.AsyncTaskExecutor;
//
//import java.util.concurrent.Callable;
//import java.util.concurrent.Future;
//
///**
// * @Author alison
// * @Date 2019/12/11  21:08
// * @Version 1.0
// * @Description
// */
//@Slf4j
//public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor {
//
//    private AsyncTaskExecutor executor;
//
//    public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
//        this.executor = executor;
//    }
//
//
//    @Override
//    public void execute(Runnable task, long startTimeout) {
//        executor.execute(createWrapper(task), startTimeout);
//    }
//
//    @Override
//    public Future<?> submit(Runnable runnable) {
//        return executor.submit(createWrapper(runnable));
//    }
//
//    @Override
//    public <T> Future<T> submit(Callable<T> callable) {
//        return executor.submit(createCallable(callable));
//    }
//
//    private <T> Callable createCallable(final Callable<T> task) {
//        return () -> {
//            try {
//                return task.call();
//            } catch (Exception e) {
//                handle(e);
//                throw e;
//            }
//        };
////        return new Callable() {
////            @Override
////            public T call() throws Exception {
////                try {
////                    return task.call();
////                } catch (Exception e) {
////                    handle(e);
////                    throw e;
////                }
////            }
////        };
//    }
//
//    // 用独立的线程来包装，@Async其本质就是如此
//    @Override
//    public void execute(Runnable task) {
//        // 注意： 异常处理在wrapper类中处理
//        executor.execute(createWrapper(task));
//    }
//
//    private Runnable createWrapper(Runnable task) {
//        return () -> {
//            try {
//                task.run();
//            } catch (Exception e) {
//                // 这里是处理异常的
//                handle(e);
//            }
//        };
//    }
//
//    private void handle(Exception e) {
//        // 具体的异常处理在这里
//        log.error("error info, {}", e.getMessage());
//    }
//
//
//}

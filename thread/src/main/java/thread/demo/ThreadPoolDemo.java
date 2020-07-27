package thread.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * @Author Alison
 * @Date 2019/5/6
 **/

public class ThreadPoolDemo {

    private static final int THREAD_SIZE = 1;
    private static final int CAPACITY = 1;
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));

    public static void main(String[] args) {
//        setRejectedExecutionHandlerAbort();
        setRejectedExecutionHandlerDiscard();
//        setRejectedExecutionHandlerDiscardOld();
//        setRejectedExecutionHandlerCaller();
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-" + i);
            pool.execute(myrun);
        }
        pool.shutdown();
    }

    static class MyRunnable implements Runnable {
        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.name + " is running");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void setRejectedExecutionHandlerAbort() {
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    }

    private static void setRejectedExecutionHandlerDiscard() {
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    }

    private static void setRejectedExecutionHandlerDiscardOld() {
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    private static void setRejectedExecutionHandlerCaller() {
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

}

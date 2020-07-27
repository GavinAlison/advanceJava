import java.util.concurrent.*;

/**
 * @Author alison
 * @Date 2019/10/19  21:32
 * @Version 1.0
 * @Description
 * @link  https://github.com/vpinfra/sourecode/blob/master/src/main/java/com/vpinfra/juc/ThreadPoolExcutorTest.java
 */
public class ThreadPoolDemo {

    static class Task implements Runnable{
        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName()+ " is running.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void validate(ThreadPoolExecutor executor, String caseDesc) throws InterruptedException {
        System.out.println(String.format("==========准备开始验证案例：%s==========", caseDesc));
        System.out.println("线程池数" + executor.getPoolSize());

        Task task = new Task();
        System.out.println("---先执行两个任务---");
        executor.execute(task);
        executor.execute(task);
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());

        System.out.println("---再执行两个任务---");
        executor.execute(task);
        executor.execute(task);
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());

        Thread.sleep(5000);
        System.out.println("----5秒之后----");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
    }

    /**
     * SynchronousQueue 没有数量限制。并且不保持这些任务，而是直接交给线程池去执行。当任务数量超过最大线程数时会直接抛异常。
     *
     * @throws InterruptedException
     */
    private static void validateSynchronousQueue() throws InterruptedException {
        ThreadPoolExecutor executor = null;
        //线程数量>核心线程数，并且>最大线程数，会因为线程池拒绝添加任务而抛出异常。
        try{
            executor = new ThreadPoolExecutor(1,2,3,TimeUnit.SECONDS,new SynchronousQueue<>());
            validate(executor, "1个核心线程数，最大线程数为2，验证 SynchronousQueue 队列");
        }catch (RejectedExecutionException e) {
            System.out.println("线程池执行任务异常，并处于阻塞状态");
            executor.shutdown();
        }

        //线程数量>核心线程数，但<=最大线程数，线程池会创建新线程执行任务，这些任务也不会被放在任务队列中。这些线程属于非核心线程，在任务完成后，闲置时间达到了超时时间就会被清除。
        executor = new ThreadPoolExecutor(1,4,3,TimeUnit.SECONDS,new SynchronousQueue<>());
        validate(executor, "1个核心线程数，最大线程数为4，验证 SynchronousQueue 队列");
        executor.shutdown();

        //线程数量<=核心线程数量，那么直接启动一个核心线程来执行任务，不会放入队列中。
        executor = new ThreadPoolExecutor(5,6,3,TimeUnit.SECONDS,new SynchronousQueue<>());
        validate(executor, "5个核心线程数，最大线程数为6，验证 SynchronousQueue 队列");
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        Executors.newFixedThreadPool(10);
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(10);
        Executors.newWorkStealingPool(10);

        validateSynchronousQueue();
//        validateLinkedBlockingDeque();
//        validateArrayBlockingQueue();
//        validateAbortPolicy();
//        validateCallerRunsPolicy();
//        validateDiscardOldestPolicy();
//        validateDiscardPolicy();
//        validateThreadFactory();
    }
}

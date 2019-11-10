package thread.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author alison
 * @Date 2019/5/7  22:52
 * @Version 1.0
 */
public class ForkJoinTaskTaskTest extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;// 阈值
    private int start;
    private int end;

    public ForkJoinTaskTaskTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start < THRESHOLD) {
            System.out.println(Thread.currentThread().getName() + ":" + start + "-" + end);
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 任务大于阈值，拆分子任务
            int middle = start + (end - start) >> 1;
            ForkJoinTaskTaskTest task1 = new ForkJoinTaskTaskTest(start, middle);
            ForkJoinTaskTaskTest task2 = new ForkJoinTaskTaskTest(middle + 1, end);
            //  // 执行子任务
            task1.fork();
            task2.fork();
            // 等待子任务执行完成，并得到其结果
            int result1 = task1.join();
            int result2 = task2.join();
            sum = result1 + result2;
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        int start = 1;
        int end = 10;
        ForkJoinTaskTaskTest f = new ForkJoinTaskTaskTest(start, end);
        ForkJoinPool pool = new ForkJoinPool();  // 用来执行ForkJoinTask
        ForkJoinTask<Integer> result = pool.submit(f);
//        ForkJoinTask  implments Future
        System.out.println("最后的结果为：" + result.get());
    }


}

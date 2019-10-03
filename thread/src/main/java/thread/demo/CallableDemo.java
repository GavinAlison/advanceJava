package thread.demo;

import java.util.concurrent.*;

/***
 * @Author Alison
 * @Date 2019/5/6
 **/

public class CallableDemo {


    static class MyCallable implements Callable {
        @Override
        public Object call() throws Exception {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            System.out.println("llllll");
            return Integer.valueOf(sum);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Callable cl = new MyCallable();
        Future f = pool.submit(cl);
        Object obj = f.get();
        System.out.println(obj);
        pool.shutdown();

        Callable c2 = new MyCallable();
        FutureTask ft = new FutureTask(c2);
        ft.run();
    }
}

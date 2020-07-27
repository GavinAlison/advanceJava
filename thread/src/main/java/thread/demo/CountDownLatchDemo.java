package thread.demo;

import java.util.concurrent.CountDownLatch;

/***
 * @Author Alison
 * @Date 2019/5/5
 **/

public class CountDownLatchDemo {

    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;

    public static void main(String[] args) throws InterruptedException {
        doneSignal = new CountDownLatch(LATCH_SIZE);
        for(int i =0 ;i<5; i++){
            new InnerThread().start();
        }
        System.out.println("main await begin");
        doneSignal.await();
        System.out.println("main await finished");
    }


    static class InnerThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms");
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

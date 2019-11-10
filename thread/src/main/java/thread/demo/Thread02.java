package thread.demo;


import org.junit.Test;

import java.util.concurrent.*;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class Thread02 {
    class MyThread extends Thread {
        MyThread() {
        }
        MyThread(String name) {
            super(name);
        }
        private int ticket = 10;
        public void run() {
            for (int i = 0; i < 20; i++) {
                if (this.ticket > 0) {
                    System.out.println(this.getName() + " sole: ticket=" + this.ticket--);
                }
            }
        }
    }
    class Runner implements Runnable {
        //  将ticket修改成volatile。这样保证了，每个线程修改了ticket之后，其他线程都能读取到最新的ticket值。
        private volatile int ticket = 1000;
        //  给run()方法添加synchronized同步关键字。这保证了多个线程对是同一个Thread对象的run()是互斥操作的。
//        在主线程中创建了t1,t2和t3共三个线程，它们共用一个MyThread任务对象。thread-0启动之后，
//        一直占着"MyThread同步锁"，而一个对象有且只有一个同步锁；因此，只有thread-0在工作。
        public synchronized void run() {
            for (int i = 0; i < 20; i++) {
                if (this.ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + " sole: ticket=" + this.ticket--);
                }
            }
        }
    }


    class MyRunnable implements Runnable{
        private volatile int ticket = 20;
        @Override
        public void run(){
           while(true){
               // synchronized保证多线程对MyRunner对象的互斥性
                synchronized(this){
                    if(ticket <= 0){
                        System.out.println("Thread:" + Thread.currentThread().getName() + " exit for loop,not enough tickets.");
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " sales the " + ticket + " ticket.");
                    ticket--;
                }
                try{
                    Thread.sleep(100);
                }catch(InterruptedException ie){
                    System.out.println("Thread:" + Thread.currentThread().getName() + " catch exception.");
                }
            }
        }
    }


    class CallableImpl  implements Callable{
        @Override
        public Object call() throws Exception {
            System.out.println(Thread.currentThread().getName()+"---CallableImpl");
            return "CallableImpl";
        }
    }


    @Test
    public void CallTest(){
        CallableImpl c= new CallableImpl();
        Thread t1= new Thread(new FutureTask(c));
        t1.start();
        System.out.println("-----------------------");
        CallableImpl c1= new CallableImpl();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future result =  executor.submit(c1);
        try{
            Object obj = (Object)result.get();
            System.out.println("obj===="+obj);
        }catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void ThreadTest() {
        // 启动3个线程t1,t2,t3；每个线程各卖10张票！
        MyThread m1 = new MyThread("m1");
        MyThread m2 = new MyThread("m2");
        MyThread m3 = new MyThread("m3");
        m1.start();
        m2.start();
        m3.start();
    }
    @Test
    public void RunnableTest() throws  Exception{
        // 启动3个线程t1,t2,t3(它们共用一个Runnable对象)，这3个线程一共卖10张票！ ×
        // 在多线程对ticket这个竞争资源的互斥访问
        Runner r = new Runner();
        Thread m1 = new Thread(r);
        Thread m2 = new Thread(r);
        Thread m3 = new Thread(r);
        m1.start();
        m1.sleep(4000);
        m2.start();
        m3.start();
    }
}

package thread.demo;


import org.junit.Test;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class Thread3 {

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running");
        }
    }

    ;

    @Test
    public void StartTest() {
        Thread mythread = new MyThread("mythread");
        System.out.println(Thread.currentThread().getName() + " call mythread.run()");
        mythread.run();
        System.out.println(Thread.currentThread().getName() + " call mythread.start()");
        mythread.start();
    }
}

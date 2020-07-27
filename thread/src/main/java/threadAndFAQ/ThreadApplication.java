package threadAndFAQ;

import org.junit.Test;

public class ThreadApplication {

    class Mythread extends Thread {
        @Override
        public void run() {
            System.out.println("this :" + this);
            System.out.println("Thread.currentThread:" + Thread.currentThread());
        }
    }

    @Test
    public void practice2() {
        Mythread mythread = new Mythread();
        mythread.start();
    }

    class RunnableTest implements Runnable {
        private int count = 100;

        public RunnableTest() {
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        Thread.sleep(400); //为了方便查看，这里给线程睡眠一下，不然时间太短一下就执行完了看不了效果
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    count -= 10;
                    if (count < 0) break;
                    else {
                        System.out.println(Thread.currentThread().getName() + "还剩次数为：" + count);
                    }
                }
            }
        }
    }

    @Test
    public void practice1() throws Exception {
        RunnableTest test = new RunnableTest();
        Thread t1 = new Thread(test, "线程1");
        t1.start();
        Thread t2 = new Thread(test, "线程2");
        t2.start();
    }

}

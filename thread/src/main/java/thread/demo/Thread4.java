package thread.demo;


/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class Thread4 {
    class MyRunable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100); // 休眠100ms
                        System.out.println(Thread.currentThread().getName() + " loop " + i);
                    }
                } catch (InterruptedException e) {
                }
            }
        }
    }

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100); // 休眠100ms
                        System.out.println(Thread.currentThread().getName() + " loop " + i);
                    }
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    class Count {
        // 含有synchronized同步块的方法
        public void synMethod() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100); // 休眠100ms
                        System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                    }
                } catch (InterruptedException ie) {
                }
            }
        }

        // 非同步的方法
        public void nonSynMethod() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " nonSynMethod loop " + i);
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    public void synchronizedTest2() {
        Thread t1 = new MyThread("t1");  // 新建“线程t1”
        Thread t2 = new MyThread("t2");  // 新建“线程t2”
        t1.start();                          // 启动“线程t1”
        t2.start();                          // 启动“线程t2”
    }

    public void synchronizedTest() {
        Runnable demo = new MyRunable();     // 新建“Runnable对象”
        Thread t1 = new Thread(demo, "t1");  // 新建“线程t1”, t1是基于demo这个Runnable对象
        Thread t2 = new Thread(demo, "t2");  // 新建“线程t2”, t2是基于demo这个Runnable对象
        t1.start();                          // 启动“线程t1”
        t2.start();                          // 启动“线程t2”
    }

    public void synchronizedTest3() {
        final Count count = new Count();
        // 新建t1, t1会调用“count对象”的synMethod()方法
        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count.synMethod();
                    }
                }, "t1");
        // 新建t2, t2会调用“count对象”的nonSynMethod()方法
        Thread t2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count.nonSynMethod();
                    }
                }, "t2");
        t1.start();  // 启动t1
        t2.start();  // 启动t2
    }

    public void synchronizedTest4() {
        final Count count = new Count();
        // 新建t1, t1会调用“count对象”的synMethod()方法
        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count.synMethod();
                    }
                }, "t1");

        // 新建t2, t2会调用“count对象”的nonSynMethod()方法
        Thread t2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count.nonSynMethod();
                    }
                }, "t2");
        t1.start();  // 启动t1
        t2.start();  // 启动t2
    }


    class Count2 {
        // 含有synchronized同步块的方法
        public void synMethod() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100); // 休眠100ms
                        System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                    }
                } catch (InterruptedException ie) {
                }
            }
        }

        // 也包含synchronized同步块的方法
        public void nonSynMethod() {
            synchronized (this) {
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + " nonSynMethod loop " + i);
                    }
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    class Demo4 {

        public synchronized void synMethod() {
            for (int i = 0; i < 1000000; i++)
                ;
        }

        public void synBlock() {
            synchronized (this) {
                for (int i = 0; i < 1000000; i++)
                    ;
            }
        }
    }

    public void synchronizedTest5() {
        Demo4 demo = new Demo4();
        long start, diff;
        start = System.currentTimeMillis();                // 获取当前时间(millis)
        demo.synMethod();                                // 调用“synchronized方法”
        diff = System.currentTimeMillis() - start;        // 获取“时间差值”
        System.out.println("synMethod() : " + diff);

        start = System.currentTimeMillis();                // 获取当前时间(millis)
        demo.synBlock();                                // 调用“synchronized方法块”
        diff = System.currentTimeMillis() - start;        // 获取“时间差值”
        System.out.println("synBlock()  : " + diff);
    }


    public static void main(String[] args) {
        Thread4 t = new Thread4();
//        t.synchronizedTest();
        System.out.println("-------------------");
//        t.synchronizedTest2();
//        t.synchronizedTest3();
//        t.synchronizedTest4();
        t.synchronizedTest5();
    }
}

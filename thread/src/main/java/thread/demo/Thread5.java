package thread.demo;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class Thread5 {
    class ThreadA extends  Thread{
        public ThreadA(String name){
            super(name);
        }
        public void run(){
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+" call notify");
                notify();
            }
        }
    }


    public void waitTest(){
        ThreadA t1 = new ThreadA("t1");
        synchronized (t1){
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start t1");
                t1.start();
                // 主线程等待,t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" continue");
                t1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

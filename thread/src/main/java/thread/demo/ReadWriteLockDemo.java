package thread.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * @Author Alison
 * @Date 2019/5/5
 **/

public class ReadWriteLockDemo {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class User {
        private String name;
        private MyCount myCount;
        private ReadWriteLock readWriteLock;


        public void getCash() {
            new Thread() {
                @Override
                public void run() {
                    readWriteLock.readLock().lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + " getCash  start ");
                        int cash = myCount.getCash();
                        System.out.println(Thread.currentThread().getName() + " getCash cash = " + cash);
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + " getCash end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readWriteLock.readLock().unlock();
                    }
                }
            }.start();
        }

        public void setCash(final int cash) {
            new Thread() {
                @Override
                public void run() {
                    readWriteLock.writeLock().lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + " setCash start");
                        myCount.setCash(cash);
                        System.out.println(Thread.currentThread().getName() + " setCash , cash =" + cash);
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + " setCash end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readWriteLock.writeLock().unlock();
                    }
                }
            }.start();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static  class MyCount {
        private String id;
        private int cash;
    }


    public static void main(String[] args) {
        MyCount myCount = new MyCount("123456", 10000);
        User user = User.builder().name("alison").myCount(myCount).readWriteLock(new ReentrantReadWriteLock()).build();
        for (int i = 0; i < 3; i++) {
            user.getCash();
            user.setCash((i + 1) * 1000);
        }
    }

}

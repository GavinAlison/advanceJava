package com.alison.thread;

public class PrintNumber extends Thread {
    private static int cnt = 0;
    private int id;  // 线程编号
    public PrintNumber(int id) {
        this.id = id;
    }

//    @Override
//    public void run() {
//        while (cnt <= 100) {
//            if (cnt%2 == id) {
//                synchronized (PrintNumber.class) {
//                    cnt++;
//                    System.out.println("thread_" + id + " num:" + cnt);
//                }
//            }
//        }
//    }

    @Override
    public void run() {
        while (cnt <= 100) {
            synchronized (PrintNumber.class) {
                cnt++;
                System.out.println("thread_" + id + " num:" + cnt);
                PrintNumber.class.notify();
                try {
                    PrintNumber.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Thread thread0 = new PrintNumber(0);
        Thread thread1 = new PrintNumber(1);
        thread0.start();
        thread1.start();
    }
}


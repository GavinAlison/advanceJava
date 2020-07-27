package thread.demo;

import java.util.*;
import java.util.concurrent.*;

/***
 * @Author Alison
 * @Date 2019/5/6
 **/

public class JucListDemo {

    public static void main(String[] args) {
//        CopyOnWriteArrayList1.test();
//        CopyOnWriteArraySet1.test();
//        ConcurrentHashMap1.test();
//        ArrayBlockingQueue1.test();
        LinkedBlockingQueue1.test();
    }

    static class CopyOnWriteArrayList1 {
        private static List<String> list = new CopyOnWriteArrayList<String>();

        public static void test() {
            new MyThread("t1").start();
            new MyThread("t2").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 6) {
                    String val = Thread.currentThread().getName() + "--" + i;
                    list.add(val);
                    printAll(list);
                }
            }
        }
    }

    static class CopyOnWriteArraySet1 {
        private static Set<String> set = new CopyOnWriteArraySet<String>();

        public static void test() {
            new MyThread("t1").start();
            new MyThread("t2").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + "--" + (i % 6);
                    set.add(val);
                    printAll(set);
                }
            }
        }
    }

    static class ConcurrentHashMap1 {
        private static Map<String, String> map = new ConcurrentHashMap<String, String>();

        public static void test() {
            new MyThread("ta").start();
            new MyThread("tb").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + i;
                    map.put(String.valueOf(i), val);
                    printAll(map);
                }
            }
        }
    }

    static class ArrayBlockingQueue1 {
        private static Queue<String> queue = new ArrayBlockingQueue<String>(20);

        public static void test() {
            new MyThread("ta").start();
            new MyThread("tb").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + i;
                    queue.offer(val);
                    printAll(queue);
                }
            }
        }
    }

    static class LinkedBlockingQueue1 {
        private static Queue<String> queue = new LinkedBlockingQueue<String>();

        public static void test() {
            new MyThread("ta").start();
            new MyThread("tb").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + i;
                    queue.offer(val);
                    printAll(queue);
                }
            }
        }
    }
    static class LinkedBlockingDeque1 {
        private static Queue<String> queue = new LinkedBlockingDeque<String>();

        public static void test() {
            new MyThread("ta").start();
            new MyThread("tb").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + i;
                    queue.offer(val);
                    printAll(queue);
                }
            }
        }
    }
    static class ConcurrentLinkedQueue1 {
        private static Queue<String> queue = new ConcurrentLinkedQueue<String>();

        public static void test() {
            new MyThread("ta").start();
            new MyThread("tb").start();
        }

        private static class MyThread extends Thread {
            MyThread(String name) {
                super(name);
            }

            @Override
            public void run() {
                int i = 0;
                while (i++ < 10) {
                    String val = Thread.currentThread().getName() + i;
                    queue.offer(val);
                    printAll(queue);
                }
            }
        }
    }

    public static void printAll(Object obj) {
        if (obj instanceof List) {
            String value = null;
            Iterator<String> it = ((List<String>) obj).iterator();
            while (it.hasNext()) {
                value = it.next();
                System.out.print(value + ", ");
            }
            System.out.println();
        }
        if (obj instanceof Set) {
            String value = null;
            Iterator<String> it = ((Set<String>) obj).iterator();
            while (it.hasNext()) {
                value = it.next();
                System.out.print(value + ", ");
            }
            System.out.println();
        }
        if (obj instanceof Map) {
            String key, value;
            Iterator it = ((Map<String, String>) obj).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                key = (String) entry.getKey();
                value = (String) entry.getValue();
                System.out.print(key + "-" + value + ", ");
            }
            System.out.println();
        }
        if (obj instanceof Queue) {
            String value;
            Iterator it = ((Queue<String>) obj).iterator();
            while (it.hasNext()) {
                value = (String) it.next();
                System.out.print(value + ", ");
            }
            System.out.println();
        }
    }


}

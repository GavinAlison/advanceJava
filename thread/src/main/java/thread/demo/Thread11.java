package thread.demo;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class Thread11 {
    // 仓库
    static class Depot {
        private int capacity;
        private int size;

        public Depot(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int val) {
            try {
                // left 表示“想要生产的数量”(有可能生产量太多，需多此生产)
                int left = val;
                while (left > 0) {
                    // 库存已满时，等待“消费者”消费产品。
                    while (size >= capacity) {
                        this.wait();
                    }
                    // 获取“实际生产的数量”(即库存中新增的数量)
                    // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                    // 否则“实际增量”=“想要生产的数量”
                    int inc = (size + left) > capacity ? capacity - size : left;
                    size += inc;
                    left -= inc;
                    System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                            Thread.currentThread().getName(), val, left, inc, size);
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void consume(int val) {
            try {
                int left = val;
                while (left > 0) {
                    while (size <= 0) {
                        wait();
                    }
                    int dec = size < left ? size : left;
                    size -= dec;
                    left -= dec;
                    System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n",
                            Thread.currentThread().getName(), val, left, dec, size);
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return "capacity:" + capacity + ", actual size:" + size;
        }
    }

    // 生产者
    static class Producer {
        private Depot depot;

        public Producer(Depot depot) {
            this.depot = depot;
        }

        // 消费产品：新建一个线程向仓库中生产产品。
        public void produce(final int val) {
            new Thread() {
                public void run() {
                    depot.produce(val);
                }
            }.start();
        }
    }

    // 消费者
    static class Customer {
        private Depot depot;

        public Customer(Depot depot) {
            this.depot = depot;
        }

        // 消费产品：新建一个线程从仓库中消费产品。
        public void consume(final int val) {
            new Thread() {
                public void run() {
                    depot.consume(val);
                }
            }.start();
        }
    }

    public static void main(String[] args) {
        Depot mDepot = new Depot(100);
        Producer mPro = new Producer(mDepot);
        Customer mCus = new Customer(mDepot);

        mPro.produce(60);
        mPro.produce(120);
        mCus.consume(90);
        mCus.consume(150);
        mPro.produce(110);
    }

}

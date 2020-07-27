package thread.demo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class ProduCusto {
    // 队列容器
    public Queue queue = new ConcurrentLinkedDeque();

    static class Producer extends Thread {
        private  Queue queue = new ConcurrentLinkedDeque();

        @Override
        public void run() {
            queue.add(1);
        }
    }






}

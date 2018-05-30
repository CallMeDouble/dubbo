package com.doubledragon.lock.ReenternLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhushuanglong on 2017/10/12.
 *
 */

public class LockDemo{

    static class Depot {
        private int size;
        private Lock lock;//独占锁

        public Depot(int size) {
            this.size = size;
            this.lock = lock = new ReentrantLock();
            System.out.println("init size:" + size);
        }

        public void producer(int count){
            lock.lock();

            try {
                size = size + count;

                System.out.printf("%s produce(%d) --> size=%d\n",
                        Thread.currentThread().getName(), count, size);
            } finally {
                lock.unlock();
            }
        }

        public void consumer(int count){
            lock.lock();

            size = size - count;

            try {
                System.out.printf("%s consumer(%d) --> size=%d\n",
                        Thread.currentThread().getName(), count, size);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer{
        private Depot depot;

        public Producer(Depot depot) {
            this.depot = depot;
        }

        public void producer(int count){
            new Thread(() -> depot.producer(count)).start();
        }
    }

    static class Consumer{
        private Depot depot;

        public Consumer(Depot depot) {
            this.depot = depot;
        }

        public void consumer(int size){
            new Thread(() -> depot.consumer(size)).start();
        }
    }

    /**
     *
     结果分析：
     (01) Depot 是个仓库。通过produce()能往仓库中生产货物，通过consume()能消费仓库中的货物。通过独占锁lock实现对仓库的互斥访问：在操作(生产/消费)仓库中货品前，会先通过lock()锁住仓库，操作完之后再通过unlock()解锁。
     (02) Producer是生产者类。调用Producer中的produce()函数可以新建一个线程往仓库中生产产品。
     (03) Customer是消费者类。调用Customer中的consume()函数可以新建一个线程消费仓库中的产品。
     (04) 在主线程main中，我们会新建1个生产者mPro，同时新建1个消费者mCus。它们分别向仓库中生产/消费产品。
     运行结果是符合我们预期的！

     这个模型存在两个问题：
     (01) 现实中，仓库的容量不可能为负数。容量确实没有限制的！
     这两个问题，我们稍微会讲到如何解决。现在，先看个简单的示例2；通过对比“示例1”和“示例2”,我们能更清晰的认识lock(),unlock()的用途。
     * @param args但是，此模型中的仓库容量可以为负数，这与现实相矛盾！
    (02) 现实中，仓库的容量是有限制的。但是，此模型中的
     */
    public static void main(String[] args) {
        Depot depot = new Depot(100);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        producer.producer(50);
        producer.producer(20);
        consumer.consumer(20);
        consumer.consumer(50);
        producer.producer(10);
    }


}

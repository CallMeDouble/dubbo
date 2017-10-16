package com.doubledragon.lock.ReenternLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhushuanglong on 2017/10/12.
 */
public class LockConditionDemo {
    static class Depot {
        private int capacity;//仓库容量
        private int size;//产品在仓库中的实际数量
        private Lock lock;
        private Condition fullCondition;
        private Condition emptyCondition;

        public Depot() {
            this.capacity = 100;
            this.size = 0;
            this.lock = new ReentrantLock();
            this.fullCondition = lock.newCondition();
            this.emptyCondition = lock.newCondition();
        }

        public void producer(int count){
            lock.lock();

            try {
                int left = count;
                while(left > 0){
                    while (size > capacity) {
                        fullCondition.await();
                    }
                    // 获取“实际生产的数量”(即库存中新增的数量)
                    // 如果“库存”+“想要生产的数量”>“总的容量”，则“实际增量”=“总的容量”-“当前容量”。(此时填满仓库)
                    // 否则“实际增量”=“想要生产的数量”
                    int inc = (size+left)>capacity ? (capacity-size) : left;
                    size += inc;
                    left -= inc;
                    System.out.printf("%s produce(%3d) --> inc=%3d, size=%3d\n",
                            Thread.currentThread().getName(), count,  inc, size);
                    // 通知“消费者”可以消费了
                    emptyCondition.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void consumer(int val){
            lock.lock();

            try {
                int left = val;
                while (left > 0){
                    while (size <= 0){
                        emptyCondition.await();
                    }

                    // 获取“实际消费的数量”(即库存中实际减少的数量)
                    // 如果“库存”<“客户要消费的数量”，则“实际消费量”=“库存”；
                    // 否则，“实际消费量”=“客户要消费的数量”。
                    int dec = (size<left) ? size : left;
                    size -= dec;
                    left -= dec;
                    System.out.printf("%s consume(%3d) <--  dec=%3d, size=%3d\n",
                            Thread.currentThread().getName(), val,  dec, size);

                    fullCondition.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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


    public static void main(String[] args) {
//        Depot depot = new Depot();
//        Producer producer = new Producer(depot);
//        Consumer consumer = new Consumer(depot);
//
//        producer.producer(50);
//        producer.producer(20);
//        consumer.consumer(50);
//        consumer.consumer(50);
//        producer.producer(10);

            MyThread myThread = new MyThread();
            myThread.start();
            myThread.interrupt();
            System.out.println("第一次调用myThread.interrupted(),返回值："+myThread.interrupted());
            System.out.println("第二次调用myThread.interrupted(),返回值："+myThread.interrupted());
            System.out.println("============end===================");
        System.out.println("?????????");
        Thread.currentThread().interrupt();
        System.out.println("第一次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第二次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("=================end===============================");
    }

}

package com.dragon.threadTest.ProduceAndConsume;

/**
 * 跟demo1一毛一样 Created by zhushuanglong on 2017/7/28.
 */
public class Demo2 {
    public static void main(String[] args) {
        Depot2 depot2 = new Depot2(100);
        Producer2 producer2 = new Producer2(depot2);
        Consumer2 consumer2 = new Consumer2(depot2);
        producer2.produce(60);
        consumer2.consumer(90);
        producer2.produce(120);
        consumer2.consumer(150);
        producer2.produce(110);
    }
}

class Depot2 {

    private int capacity;
    private int remain;

    public Depot2(int capacity) {
        this.capacity = capacity;
        this.remain = 0;
    }

    public synchronized void produce(int val) {
        int left = val;

        while (left > 0) {
            while (remain >= capacity) {
                try {
                    System.out.println("产品多余仓库容量,等待消费者消费.");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int inc = (left + remain) > capacity ? capacity - remain : left;
            left = left - inc;
            remain = remain + inc;
            System.out.printf("生产记录：%s produce(%3d) --> left=%3d, inc=%3d, remain=%3d\n",
                    Thread.currentThread().getName(), val, left, inc, remain);
            notifyAll();
        }
    }

    public synchronized void consume(int val) {
        int left = val;
        while (left > 0) {
            while (remain <= 0) {
                try {
                    System.out.println("没有产品了，等待生产者生产.");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int desc = (left > remain) ? remain : left;
            left = left - desc;
            remain = remain - desc;
            System.out.printf("消费记录： %s produce(%3d) --> left=%3d, inc=%3d, remain=%3d\n",
                    Thread.currentThread().getName(), val, left, desc, remain);
            notifyAll();
        }
    }
}

class Producer2 {
    private Depot2 depot2;

    public Producer2(Depot2 depot2) {
        this.depot2 = depot2;
    }

    public void produce(final int val) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                depot2.produce(val);
            }
        }).start();
    }
}

class Consumer2 {
    private Depot2 depot2;

    public Consumer2(Depot2 depot2) {
        this.depot2 = depot2;
    }

    public void consumer(final int val) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                depot2.consume(val);
            }
        }).start();
    }
}
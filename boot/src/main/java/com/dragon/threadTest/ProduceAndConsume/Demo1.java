package com.dragon.threadTest.ProduceAndConsume;

/**
 * Created by zhushuanglong on 2017/7/27.
 */
public class Demo1 {
    public static void main(String[] args) {
        Depot depot = new Depot(100);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);
        producer.Produce(60);
        consumer.consume(90);
        producer.Produce(120);
        consumer.consume(150);
        producer.Produce(110);
    }
}

//仓库
class Depot{
    private int capacity;//仓库容量
    private int remain;//仓库实际产品数量

    public Depot(int capacity) {
        this.capacity = capacity;
        this.remain = 0;
    }

    /**
     * 生产方
     * @param totalWant 总共需要生产的数量
     */
    public synchronized void produce(int totalWant){
        int left = totalWant;//剩余需要生产的数量
        while(left > 0){
            while(remain >= capacity){//产品多余仓库容量
                try {
                    System.out.println("产品多余仓库容量,等待消费者消费");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            int inc = (left + remain) > capacity ? capacity - remain : left; //实际一次需要生产的数量
            left = left - inc;
            remain = remain + inc;
            System.out.printf("生产记录：%s produce(%3d) --> left=%3d, inc=%3d, remain=%3d\n",
                    Thread.currentThread().getName(), totalWant, left, inc, remain);
            notifyAll();
        }
    }

    /**
     * 消费方
     * @param val 总共需要产品的数量
     */
    public synchronized void consume(int val){
        int left = val;//还需要多少数量
        while(left > 0){
            while(remain <= 0){
                try {
                    System.out.println("没有产品了，等待生产者生产");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int inc = (left > remain)? remain : left;//实际消费的数量
            left = left - inc;
            remain = remain - inc;
            System.out.printf("消费记录： %s produce(%3d) --> left=%3d, inc=%3d, remain=%3d\n",
                    Thread.currentThread().getName(), val, left, inc, remain);
            notifyAll();
        }
    }
}

class Producer{
    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void Produce(final int val){
        new Thread(() -> depot.produce(val)).start();
    }
}

class Consumer{
    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(final int val){

        new Thread(){
            public void run(){
                depot.consume(val);
            }
        }.start();
    }
}


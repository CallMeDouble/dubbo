package com.doubledragon.lock.ReenternReadWriteLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhushuanglong on 2017/10/16.
 */
public class User {

    private String name;
    private Count count;
    private ReadWriteLock lock;
    private AtomicInteger threadname = new AtomicInteger();

    public User(String name, Count count) {
        this.name = name;
        this.count = count;
        this.lock = new ReentrantReadWriteLock(false);
    }

    public void getCash(){
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread()+" 准备获取读锁");
            lock.readLock().lock();
            System.out.println(Thread.currentThread()+ " 获取了读锁");
            try {
//                System.out.println(Thread.currentThread()+ " 睡眠30秒");
//                Thread.sleep(1000*10);
//                System.out.println(Thread.currentThread()+ " 30秒睡完");

                System.out.println(Thread.currentThread() + " getCash :" + count.getCash());
                System.out.println(Thread.currentThread() + " 获取读锁完成 end");

                System.out.println(Thread.currentThread() + " 准备获取写锁");
                lock.writeLock().lock();
                System.out.println(Thread.currentThread() + " 获取写锁");
                count.setCash(100);
                lock.writeLock().unlock();
                System.out.println(Thread.currentThread() + " 释放写锁");
                System.out.println(Thread.currentThread() + " getCash :" + count.getCash());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
                System.out.println(Thread.currentThread()+ " 释放读锁完成");
            }
        });

        thread.setName("thread" + threadname.incrementAndGet());
        thread.start();
    }

    public void addCash(int cash){
        Thread thread = new Thread(() -> {
            lock.writeLock().lock();
            try {
                lock.readLock().lock();
                System.out.println(Thread.currentThread() + " setCash start");
                count.setCash(cash);
                Thread.sleep(1000);
                System.out.println(Thread.currentThread() + " setCash :" + count.getCash());
                System.out.println(Thread.currentThread() + " setCash end");
                lock.readLock().unlock();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        });
        thread.setName("thread" + threadname.incrementAndGet());
        thread.start();
    }
}

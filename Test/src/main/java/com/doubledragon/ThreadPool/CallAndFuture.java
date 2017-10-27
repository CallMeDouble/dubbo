package com.doubledragon.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhushuanglong on 2017/10/25.
 */
public class CallAndFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> submit = executorService.submit(new MyThread());
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    static class MyThread implements Callable<Integer>{

        @Override
        public Integer call() throws InterruptedException {
            int sum = 0;
            for (int i=0;i<500;i++){
                sum += i;
            }
            Thread.sleep(5000);
            return sum;
        }
    }
}

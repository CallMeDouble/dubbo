package com.dragon.lock.CopyOnWriteList;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhushuanglong on 2017/10/23.
 */
public class Test {
   private static CopyOnWriteArrayList<String> cowal = new CopyOnWriteArrayList();
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cowal.forEach(e -> System.out.print(e+ "  "));
    }

    static class MyThread implements Runnable{

        private void printall(){
            cowal.forEach(e -> System.out.print(e+ "  "));
            System.out.println("");
        }
        @Override
        public void run() {
            int i =0;
            while(i<6){
                String v = Thread.currentThread().getName()+":" + i;
                cowal.add(v);
                printall();
                i++;
            }
        }
    }
}

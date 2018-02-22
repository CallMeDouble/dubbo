package com.dragon.lock.MyReentrantLock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by zhushuanglong on 2017/10/27.
 */
public class TwinsLock {

    private final Sync sync = new Sync(2);

    static class Sync extends AbstractQueuedSynchronizer{
        Sync(int args){
            setState(args);
        }

        @Override
        public int tryAcquireShared(int args){
            for(;;){
                int current = getState();
                int expect = current - args;
                if(expect < 0 || compareAndSetState(current, expect)) {
                    return expect;
                }
            }
        }


        @Override
        public boolean tryReleaseShared(int args){
            for (;;){
                int current = getState();
                int expect = current + args;
                if(compareAndSetState(current, expect)){
                    return true;
                }
            }

        }
    }

    public void lock(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }

}

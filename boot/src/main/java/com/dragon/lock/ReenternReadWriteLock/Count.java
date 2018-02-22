package com.dragon.lock.ReenternReadWriteLock;

/**
 * Created by zhushuanglong on 2017/10/16.
 */
public class Count {
    private Integer id;
    private int cash;

    public Count(Integer id, int cash) {
        this.id = id;
        this.cash = cash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = this.cash +cash;
    }
}

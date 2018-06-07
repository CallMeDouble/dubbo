package com.dragon.CallBack;

/**
 * Created by dragon
 */
public class Test {
    public static void main(String[] args) {
        Process process = new Process();

        Call call = new Call(process);
        call.question();
    }
}

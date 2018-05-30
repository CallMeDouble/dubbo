package com.dragon.CallBack;

/**
 * Created by dragon
 */
public class Call implements CallBack {

    private Process process;

    public Call(Process process) {
        this.process = process;
    }

    public void question(){
        String question = "1+1 = ?";
        System.out.println("提出问题："+question);

        new Thread(() -> {
            process.process(this, question);
        }).start();

        System.out.println("先去忙了，等待答案");
    }

    @Override
    public void callBack(Object result) {
        System.out.println("回调函数被触发，拿到了result：" + result);
    }
}

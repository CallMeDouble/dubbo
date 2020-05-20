package com.demo.netflix.histrix.helloWorld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;

/**
 * @Author: shuanglong.zhu@ele.me
 * @Date: 2019/1/28 下午5:02
 */
public class HystrixCommandHelloworld extends HystrixCommand<String> {
    private final String name;

    public HystrixCommandHelloworld(String name){
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "hello " + name + "！";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String execute = new HystrixCommandHelloworld("zsl").execute();  //同步执行
        System.out.println(execute);

        String zsl2 = new HystrixCommandHelloworld("zsl2").queue().get(); //异步执行
        System.out.println(zsl2);
    }
}
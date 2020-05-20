package com.demo.netflix.histrix.helloWorld;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @Author: shuanglong.zhu@ele.me
 * @Date: 2019/1/29 下午2:49
 */
public class HystrixObservableCommandHelloWord extends HystrixObservableCommand<String> {

    private final String name;

    public HystrixObservableCommandHelloWord(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext("hello ");
                        subscriber.onNext("" + name + "!");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void main(String[] args) {


    }
}

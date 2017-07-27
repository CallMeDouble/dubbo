package demo.dubbo.provider.impl;

import demo.dubbo.service.AnotherService;

/**
 * Created by allan on 16-11-21.
 */
public class AnotherServiceImpl implements AnotherService {
    public String sayHi(String name) {
//        try {
//            Thread.sleep(100000000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "Hi";
    }
}

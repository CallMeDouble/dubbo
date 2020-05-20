package com.demo.netflix.histrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: shuanglong.zhu@ele.me
 * @Date: 2019/1/28 下午6:50
 */
@SpringBootApplication
//@EnableMetrics
//@EnableHystrix
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}

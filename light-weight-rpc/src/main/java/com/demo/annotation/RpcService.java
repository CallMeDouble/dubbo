package com.demo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dragon
 *
 * 用于接口实现类指明实现的哪个接口
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component //可以被spring扫描到
public @interface RpcService {
    //接口类型
    Class<?> value();
}

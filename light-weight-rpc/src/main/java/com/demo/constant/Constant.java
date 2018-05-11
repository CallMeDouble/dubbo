package com.demo.constant;

/**
 * Created by dragon
 */
public interface Constant {
    int  ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/registry";

    //注册中心数据路径
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/service/";
}

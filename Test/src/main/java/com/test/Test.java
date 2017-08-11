package com.test;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;

/**
 * Created by zhushuanglong on 2017/8/10.
 */
public class Test {
    public static void main(String[] args) {
        String a= "{\"dt_order\":\"20170810142655\",\"info_order\":\"路畅卡>车运费\",\"money_order\":\"1.0\",\"no_order\":\"20170808114905eEAxh1502347010684\",\"oid_partner\":\"201704281001687508\",\"oid_paybill\":\"2017081022465497\",\"result_pay\":\"SUCCESS\",\"settle_date\":\"20170810\",\"sign\":\"J1Og4Dq5+K5smCdUDn7OKbvCrwiOYwx/m6QERooV7vzXw/uW995wPCzr0E0Cnqig04tDqmqfkeQ/eZdQjxJJZwPbKjW/MBDXDRLliPqPQ0LMTgHIsep2wi1oAzh5k9onpH6xucATGURZR9a/zro6JwXD9BHvd7BsBxKt1tsCYtI=\",\"sign_type\":\"RSA\"}";
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(a);
        Object info_order = jsonObject.get("info_order");
        System.out.println((String) info_order);
    }
}

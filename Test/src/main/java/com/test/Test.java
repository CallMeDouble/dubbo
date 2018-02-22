package com.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by zhushuanglong on 2017/8/10.
 */
public class Test {

    private static String string = null;

    private static void A() throws Exception{
        //System.out.println("a方法");
            B();
            System.out.println("A抛异常了");
    }

    private static void B() throws Exception{
        //System.out.println("b方法");
            C();
            System.out.println("B抛异常了");
    }

    private static void C(){
        //System.out.println("c方法");
        System.out.println("C抛异常了");
        string.getBytes();
    }

    public static void main(String[] args) throws Exception{
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime
                .parse("20190911030404", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());
        System.out.println(date);
        System.out.println(localDateTime);
    }
}

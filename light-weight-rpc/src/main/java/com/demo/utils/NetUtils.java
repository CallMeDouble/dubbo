package com.demo.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dragon
 *
 * ip和端口的获取
 */
public class NetUtils {

    public static String getLocalIp() throws UnknownHostException {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw e;
        }
    }
}

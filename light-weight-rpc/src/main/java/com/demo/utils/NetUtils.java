package com.demo.utils;

import com.demo.codec.SerializerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dragon
 *
 * ip和端口的获取
 */
public class NetUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetUtils.class);

    public static String getLocalIp() throws UnknownHostException {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            LOGGER.info("ip："+hostAddress);
            return hostAddress;
        } catch (UnknownHostException e) {
            throw e;
        }
    }
}

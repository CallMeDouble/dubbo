package com.demo.core.client.netty;

import com.demo.core.server.Response;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dragon
 */
public class ResponseMapHelper {
    public static Map<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();
}

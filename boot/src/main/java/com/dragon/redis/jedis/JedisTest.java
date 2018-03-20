package com.dragon.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zhushuanglong on 2018/3/20.
 */
public class JedisTest {
    public static void main(String[] args) {
        JedisPool jedisPoolInstance = JedisPoolUtil.getJedisPoolInstance();
        Jedis resource = jedisPoolInstance.getResource();
        Object eval = resource.eval("return redis.call('get', 'foo')");
        System.out.println(eval);
    }
}

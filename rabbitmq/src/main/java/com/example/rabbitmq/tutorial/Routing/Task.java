package com.example.rabbitmq.tutorial.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by dragon
 */
public class Task {
    //设置队列名
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("zsl");
        connectionFactory.setPassword("zsl");
        //创建一个连接，Connection是用来open Channels的
        Connection connection = connectionFactory.newConnection();
        //创建一个channel，AMQP协议层面的操作通过Channel接口实现。
        //显示的关闭channel是一个很好的习惯，但这不是必须的，在基本的connection关闭的时候channel也会自动的关闭。
        Channel channel = connection.createChannel();

        //预取数量，指明消费者一次从mq队列中获取消息的数量
        int prefechCount = 1;
        channel.basicQos(prefechCount);

        Map<String, String> arguments = new HashMap<String, String>();
        //队列容量
        arguments.put("x-max-length", "10");
        //队列溢出策略
        arguments.put("x-overflow", "reject-publish");

        //声明一个fanout类型的exchange
        channel.exchangeDeclare("logExchange2", "direct");

        String message = "H.e.l.l.o....W.o.r.l.d.!.";
        //然后将消息发送到exchange中
        //消息内容是一个字节数组，所以你可以对它进行任意的编码
        channel.basicPublish("logExchange2", "info", MessageProperties.PERSISTENT_TEXT_PLAIN, (message+"info").getBytes());
        channel.basicPublish("logExchange2", "error", MessageProperties.PERSISTENT_TEXT_PLAIN, (message+"error").getBytes());
        channel.basicPublish("logExchange2", "error", MessageProperties.PERSISTENT_TEXT_PLAIN, (message+"error").getBytes());
        channel.basicPublish("logExchange2", "debug", MessageProperties.PERSISTENT_TEXT_PLAIN, (message+"debug").getBytes());

        System.out.println("send message:" + message);

        //最后关闭channel和connection
        channel.close();
        connection.close();



    }


    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}

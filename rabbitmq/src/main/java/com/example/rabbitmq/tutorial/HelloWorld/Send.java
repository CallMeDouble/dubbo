package com.example.rabbitmq.tutorial.HelloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by dragon
 */
public class Send {
    //设置队列名
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.64.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("double");
        connectionFactory.setPassword("double");
        //创建一个连接，Connection是用来open Channels的
        Connection connection = connectionFactory.newConnection();
        //创建一个channel，AMQP协议层面的操作通过Channel接口实现。
        //显示的关闭channel是一个很好的习惯，但这不是必须的，在基本的connection关闭的时候channel也会自动的关闭。
        Channel channel = connection.createChannel();

        //声明一个队列来让我们发送消息
        //声明队列是幂等的，只有在这个队列不存在的时候才会创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello world";
        //然后将消息发送到队列中
        //消息内容是一个字节数组，所以你可以对它进行任意的编码
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("send message:" + message);

        //最后关闭channel和connection
        channel.close();
        connection.close();



    }
}

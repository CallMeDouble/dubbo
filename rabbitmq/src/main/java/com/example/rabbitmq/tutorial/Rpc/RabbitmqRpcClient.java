package com.example.rabbitmq.tutorial.Rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by dragon
 */
public class RabbitmqRpcClient {
    //设置队列名
    private final static String REQUEST_QUEUE = "request_queue";
    private String replayQueue;
    private Channel channel;
    private Connection connection;


    public RabbitmqRpcClient() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("zsl");
        connectionFactory.setPassword("zsl");
        //创建一个连接，Connection是用来open Channels的
        connection = connectionFactory.newConnection();
        //创建一个channel，AMQP协议层面的操作通过Channel接口实现。
        //显示的关闭channel是一个很好的习惯，但这不是必须的，在基本的connection关闭的时候channel也会自动的关闭。
        channel = connection.createChannel();

        replayQueue = channel.queueDeclare().getQueue();
    }

    public String call(String message) throws IOException, InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().correlationId(uuid).replyTo(replayQueue).build();

        System.out.println("request:"+message);
        channel.basicPublish("", REQUEST_QUEUE, basicProperties, message.getBytes());
        System.out.println("message have send");

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(1);

        channel.basicConsume(replayQueue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if(properties.getCorrelationId().equals(uuid)){

                    blockingQueue.offer(new String(body, "utf-8"));
                }
            }
        });
        return blockingQueue.take();
    }


    public void close() throws IOException {
        connection.close();

    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitmqRpcClient rabbitmqRpcClient = new RabbitmqRpcClient();
        String call = rabbitmqRpcClient.call("3");
        System.out.println("response:"+call);
        rabbitmqRpcClient.close();
    }
}

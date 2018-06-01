package com.example.rabbitmq.tutorial.Rpc;

import com.rabbitmq.client.*;
import com.sun.org.apache.regexp.internal.RE;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by dragon
 */
public class RabbitmqRpcServer {

    private final static String REQUEST_QUEUE = "request_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //消费者和生产者一样，新建连接，创建通道，声明队列
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("zsl");
        connectionFactory.setPassword("zsl");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(REQUEST_QUEUE, false, false, false, null);

        channel.basicQos(1);

        System.out.println("waiting for message");

        //一个提供回调接口的对象，当有消息被推送时，回调该接口
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                AMQP.BasicProperties replayProperties = new AMQP.BasicProperties.Builder().
                        correlationId(properties.getCorrelationId()).build();


                String message = new String(body, "utf-8");
                System.out.println("receive message:"+message);

                Integer response = new Integer("4");


                channel.basicPublish("", properties.getReplyTo(), replayProperties, response.toString().getBytes());
            }
        };

        channel.basicConsume(REQUEST_QUEUE,true, defaultConsumer);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

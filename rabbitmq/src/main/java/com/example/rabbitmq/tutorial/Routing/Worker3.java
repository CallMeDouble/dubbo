package com.example.rabbitmq.tutorial.Routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by dragon
 */
public class Worker3 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //消费者和生产者一样，新建连接，创建通道，声明队列
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("zsl");
        connectionFactory.setPassword("zsl");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明一个接收的交换机
        channel.exchangeDeclare("logExchange2", "direct");
        //声明一个新的队列
        String queueName = channel.queueDeclare().getQueue();
        //将新队列绑定到交换机上
        channel.queueBind(queueName, "logExchange2", "error");
        channel.queueBind(queueName, "logExchange2", "debug");

        System.out.println("waiting for message");

        //一个提供回调接口的对象，当有消息被推送时，回调该接口
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("delivery message:"+message);

                try {
                    doWork(message);

                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }
        };
        boolean autoAck = false; // acknowledgment is covered below
        //设置消费的队列，是否ack，回调对象
        channel.basicConsume(queueName, autoAck, defaultConsumer);

    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}

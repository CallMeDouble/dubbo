package com.dragon.netty.netty.firstNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by zhushuanglong on 2018/3/28.
 */
public class NettyTelnetServer {

    private static final int port=8888;

    //ServerBootstrap是Socket服务端启动类,通过这个类的实例，用户可以创建对应的服务端程序。
    private ServerBootstrap serverBootstrap;

    //NioEventLoopGroup 对应一个被封装好的NIO线程池
    //bossGroup负责收集客户端连接，使用了1个线程
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //workGroup负责处理每个连接的IO读写
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        //指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)  //指定是一个NIO连接通道
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        //绑定对应的端口号，并启动时开始监听端口上的通道连接, 当有连接进来时，会监听到
        Channel channel = serverBootstrap.bind(port).sync().channel();

        //等待关闭，同步端口？？？？？？
        channel.closeFuture().sync();
    }

    public void close(){
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }




    //启动测试服务
    public static void main(String[] args) {
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

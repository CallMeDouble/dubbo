package com.dragon.netty.netty.inAction;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by double on 18-3-25.
 */
//连接服务器
//发送信息
//发送的每个信息，等待和接收从服务器返回的同样的信息
//关闭连接
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //1.创建 Bootstra 被创建来初始化客户端
            Bootstrap bootstrap = new Bootstrap();
            //2.指定 EventLoopGroup 来处理客户端事件。
            //  由于我们使用 NIO 传输，所以用到了 NioEventLoopGroup 的实现
            // 一个 NioEventLoopGroup 实例被分配给处理该事件的处理，这包括创建新的连接和处理入站和出站数据
            bootstrap.group(eventExecutors)
                    //3.使用的 channel 类型是一个用于 NIO 传输
                    .channel(NioSocketChannel.class)
                    //4.设置服务器的 InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //5.当建立一个连接和一个新的通道时，创建添加到 EchoClientHandler 实例 到 channel pipeline
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //6.Bootstrap.connect（）被调用，用于连接到远程的 - 本例就是 echo(回声)服务器。
            //  等待连接完成
            bootstrap.connect();
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //7.阻塞直到 Channel 关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            //8.调用 shutdownGracefully() 来关闭线程池和释放所有资源
            eventExecutors.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception{
        EchoClient echoClient = new EchoClient("127.0.0.1", 8888);
        while (true){
            echoClient.start();
        }
    }
}

package com.dragon.netty.netty.inAction;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by double on 18-3-25.
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        //1.设置端口值
        EchoServer echoServer = new EchoServer(8888);
        //2.呼叫服务器的 start() 方法
        echoServer.start();
    }

    public void start()  throws Exception{
        //3.创建 EventLoopGroup
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //4.创建 ServerBootstrap实例来引导服务器并随后绑定
            //ServerBootstrap是Socket服务端启动类,通过这个类的实例，用户可以创建对应的服务端程序。！！！
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //分配一个 NioEventLoopGroup 实例来处理事件的处理，如接受新的连接和读/写数据。
            serverBootstrap.group(eventExecutors)
                    //5.指定使用 NIO 的传输 Channel, 指定信道类型,可以是别的不同的传输类型
                    .channel(NioServerSocketChannel.class)
                    //6.设置 socket 地址使用所选的端口
                    .localAddress(new InetSocketAddress(port))
                    //当一个新的连接被接受，一个新的子 Channel 将被创建，
                    // ChannelInitializer 会添加我们EchoServerHandler 的实例到 Channel 的 ChannelPipeline。
                    // 正如我们如前所述，这个处理器将被通知如果有入站信息。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //7.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                            //  通过 EchoServerHandler 实例给每一个新的 Channel 初始化
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            //8.最后调用 ServerBootstrap.bind() 绑定服务器;
            // 调用 sync() 的原因是当前线程阻塞？？？？？？？？？
            // sync 等待服务器关闭
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println(EchoServer.class.getName()+"start and listen on "+channelFuture.channel().localAddress());
            //9.关闭 channel 和 块，直到它被关闭 ，因为我们 在 Channel 的 CloseFuture 上调用 sync()
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //10.关机的 EventLoopGroup，释放所有资源。
            eventExecutors.shutdownGracefully().sync();
        }

    }
}

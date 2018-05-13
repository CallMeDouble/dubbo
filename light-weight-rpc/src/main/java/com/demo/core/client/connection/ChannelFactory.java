package com.demo.core.client.connection;

import com.demo.core.server.Response;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.codec.RpcDecoder;
import com.demo.codec.RpcEncoder;
import com.demo.core.client.Request;
import com.demo.core.client.netty.RpcClientResponseHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by dragon
 *
 * channel池
 */
public class ChannelFactory extends BasePooledObjectFactory<Channel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelFactory.class);

    private String ip;
    private int port;

    public ChannelFactory(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    //与服务器建立通道!!!
    private Channel createNewChannel(){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup(1))
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new RpcDecoder(10 * 1024 * 1024, Response.class))
                                .addLast(new RpcEncoder())
                                .addLast(new RpcClientResponseHandler());
                    }
                });

        try {
            LOGGER.info("与服务器建立通道");
            //通道被打开的future
            ChannelFuture openChannelFuture = bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .option(ChannelOption.TCP_NODELAY, true).connect(ip, port).sync();
            openChannelFuture.addListener((ChannelFutureListener) future -> {
                if(future.isSuccess()){
                    LOGGER.info("与服务器建立通道成功", future);
                }
            });
            //通道对象
            Channel channel = openChannelFuture.channel();
            //通道被关闭的future
            ChannelFuture closeChannelFuture = channel.closeFuture();
            closeChannelFuture.addListener((ChannelFutureListener) future -> {
                LOGGER.info("close channel ip:port = {}:{}", ip, port);
            });
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Channel create() throws Exception {
        return createNewChannel();
    }

    @Override
    public PooledObject<Channel> wrap(Channel obj) {
        //排查出错，之前直接返回个null，未对方法进行重写，导致出错，拿不出对象
        return new DefaultPooledObject<>(obj);
    }

    //销毁对象时调用
    @Override
    public void destroyObject(PooledObject<Channel> p) throws Exception {
        p.getObject().close().addListener((ChannelFutureListener) future -> LOGGER.info("Close Finish"));
    }

    //判断对象是否存活
    @Override
    public boolean validateObject(PooledObject<Channel> p) {
        Channel object = p.getObject();
        return object.isActive();
    }
}

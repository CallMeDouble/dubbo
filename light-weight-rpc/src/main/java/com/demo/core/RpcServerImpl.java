package com.demo.core;

import com.demo.codec.RpcDecoder;
import com.demo.codec.RpcEncoder;
import com.demo.constant.Constant;
import com.demo.utils.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Created by double
 *
 * 服务端, 利用netty接收请求，并处理调用请求的实现
 */
public class RpcServerImpl implements RpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerImpl.class);

    private String localIp;
    private int port;
    private boolean started = false;

    private Object serviceImpl;
    private String serviceName;

    private Channel channel;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private String zkConn;
    private String serviceRegistryPath;
    private CuratorFramework curatorFramework;

    public RpcServerImpl(int port, Object serviceImpl, String serviceName){
        this.port = port;
        this.serviceImpl = serviceImpl;
        this.serviceName = serviceName;
    }

    public RpcServerImpl(int port, Object serviceImpl, String serviceName, String zkConn) {
        this.port = port;
        this.serviceImpl = serviceImpl;
        this.serviceName = serviceName;
        this.zkConn = zkConn;
    }


    @Override
    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new RpcDecoder(10*1024*1024))
                                .addLast(new RpcEncoder())
                                .addLast(new RpcServerHandler(serviceImpl));
                    }
                });

        try {
            //绑定端口来等待客户端链接
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //接着注册本服务
            registryServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registryServer() throws UnknownHostException {
        zkConn = getZkConn();
        localIp = NetUtils.getLocalIp();

        //相当于标示一个集群节点
        String serviceBasePath = Constant.ZK_DATA_PATH + serviceName;
        //本节点在某个集群节点下
        String serviceIp = localIp + "+" + port;

        //链接zookeeper
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkConn,
                new ExponentialBackoffRetry(1000, 3));
        //连接上zookeeper后开始注册服务节点
        try {
            curatorFramework.create().creatingParentsIfNeeded().forPath(serviceBasePath);
        } catch (Exception e) {
            if (e.getMessage().contains("NodeExist")) {
                LOGGER.info("This Path Service has already Exist");
            } else {
                LOGGER.error("Create Path Error ", e);
                throw new RuntimeException("Register error");
            }
        }
        boolean registerSuccess = false;
        //如果集群节点添加成功, 则在集群节点下添加本节点
        while(!registerSuccess){
            try {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(serviceIp);
                registerSuccess = true;
            } catch (Exception e) {
                //出错重新注册(要先删除下节点再重新注册)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LOGGER.info("Retry Register ZK, {}", e.getMessage());
                try {
                    curatorFramework.delete().forPath(serviceBasePath + "/" + serviceIp);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }

    }

    @Override
    public void shutdown() {

    }

    public String getZkConn() {
        return zkConn;
    }

    public void setZkConn(String zkConn) {
        this.zkConn = zkConn;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

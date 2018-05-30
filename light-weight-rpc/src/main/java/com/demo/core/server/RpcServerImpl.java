package com.demo.core.server;

import com.demo.codec.RpcDecoder;
import com.demo.codec.RpcEncoder;
import com.demo.constant.Constant;
import com.demo.core.client.Request;
import com.demo.core.server.netty.RpcServerRequestHandler;
import com.demo.utils.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
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
 * 服务器端server的实现类, 利用netty接收请求，并处理调用请求的实现
 */
public class RpcServerImpl implements RpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerImpl.class);

    //指定zookeeper服务地址
    private String zkConn;

    //本地ip丶port 和 serviceName 均用于向zookeeper注册服务
    private String localIp;
    private int port;
    private String serviceName;

    //当接收请求后，真正执行方法的类。
    private Object serviceImpl;


    //netty
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

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
                                .addLast(new RpcDecoder(10*1024*1024, Request.class))
                                .addLast(new RpcEncoder())
                                .addLast(new RpcServerRequestHandler(serviceImpl));
                    }
                });

        try {
            //绑定端口来等待客户端链接
            ChannelFuture future = serverBootstrap.bind(port).sync();
            LOGGER.info("绑定端口来等待客户端链接, 接着向zookeeper注册本服务");
            //接着向zookeeper注册本服务
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
        String connStr = localIp + ":" + port;

        //链接zookeeper
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkConn,
                new ExponentialBackoffRetry(1000, 3));
        //连接上zookeeper后开始注册服务节点
        curatorFramework.start();
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
        String serviceRegisterPath = serviceBasePath + "/" + connStr;
        while(!registerSuccess){
            try {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(serviceRegisterPath);
                registerSuccess = true;
                LOGGER.info("向zookeeper注册本服务成功");
            } catch (Exception e) {
                //出错重新注册(要先删除下节点再重新注册)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LOGGER.info("Retry Register ZK, {}", e.getMessage());
                try {
                    curatorFramework.delete().forPath(serviceRegisterPath);
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

}

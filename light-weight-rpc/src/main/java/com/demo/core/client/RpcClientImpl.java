package com.demo.core.client;

import com.demo.constant.Constant;
import com.demo.core.client.connection.ChannelConfig;
import com.demo.core.client.netty.ResponseMapHelper;
import com.demo.core.client.proxy.CglibRpcProxy;
import com.demo.core.client.proxy.RpcProxy;
import com.demo.core.server.Response;
import com.google.common.base.Splitter;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dragon
 *
 * 客户端的实现
 * 一个客户端对应一个服务，但是一个服务会有多个连接（集群）
 * 客户端具体实现，发送请求，接受响应。。。
 */
public class RpcClientImpl implements RpcClient{
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientImpl.class);

    private static AtomicLong atomicLong = new AtomicLong();

    //服务的名字，就是调用的接口的名字
    private String serviceName;
    //zookeeper监控框架
    private CuratorFramework curatorFramework;
    //zookeeper连接地址
    private String zkConn;
    //客户端调用服务的生成代理类的类, 可以指定动态代理的方式，本项目只用了cglib
    private Class<? extends RpcProxy> clientProxyClass;

    private long requestTimeOut = 10 * 1000;

    //一个服务会有多个连接，因此是个list中，一个ChannelConfig对应一个服务
    //存放channelConfig到一个CopyOnWriteArrayList中, 作为本地缓存
    //这本就是读多写少的场景，因此很适用（服务注册后很少会发生状态的改变）
    public static CopyOnWriteArrayList<ChannelConfig> channelConfigWrappers = new CopyOnWriteArrayList<>();

    public RpcClientImpl(String serviceName, String zkConn, Class<? extends RpcProxy> clientProxyClass, long requestTimeOut) {
        this.serviceName = serviceName;
        this.zkConn = zkConn;
        this.clientProxyClass = clientProxyClass;
        this.requestTimeOut = requestTimeOut;

        this.initBootStrapAndChannelConfigByZookeeper();
    }

    //主要逻辑是从注册中心中获取服务的节点信息。
    //保存在本地并利用zookeeper保证一致性
    public void initBootStrapAndChannelConfigByZookeeper(){
        //与注册中心建立连接
        curatorFramework = CuratorFrameworkFactory.newClient(getZkConn(), new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();

        try {
            final GetChildrenBuilder children = curatorFramework.getChildren();
            final String serviceZkPath = Constant.ZK_DATA_PATH + serviceName;

            List<String> connStrList = children.forPath(serviceZkPath);
            if(CollectionUtils.isEmpty(connStrList)){
                throw new RuntimeException("no service avaliable for serviceName:" + serviceName);
            }

            LOGGER.info("Found Server {} List {}", serviceName, connStrList);
            //为每个服务提供的节点建立一个通道channel
            for (String connStr : connStrList) {
                addNewChannel(connStr);
            }

            // 整个节点 = Constant.ZK_DATA_PATH + serviceName + IP:PORT
            // serviceZkPath的子节点就是：IP:PORT

            // 通过curator的api：PathChildrenCache来监控一个ZNode的子节点
            // PathChildrenCache包含最新的子节点，子节点的数据和状态, 是用来监控path的
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, serviceZkPath, true);
            pathChildrenCache.start();

            //监听服务是否改变，如果改变则需要改变本地缓存
            pathChildrenCache.getListenable().addListener((client, event) -> {
                LOGGER.info("Listen Event {}", event);
                List<String> newServiceData = connStrList;
                LOGGER.info("Server {} list change {}", serviceName, newServiceData);

                //关闭删除本地缓存中多出的channel
                Iterator<ChannelConfig> iterator = channelConfigWrappers.iterator();
                while (iterator.hasNext()){
                    ChannelConfig channelConfig = iterator.next();
                    String connStr = channelConfig.getConnStr();
                    if(!newServiceData.contains(channelConfig.getConnStr())){
                        channelConfig.close();
                        LOGGER.info("Remove channel {}", connStr);
                        channelConfigWrappers.remove(channelConfig);
                    }
                }

                // 增加本地缓存中不存在的连接地址
                for (String connStr : newServiceData) {
                    boolean containThis = false;
                    for (ChannelConfig cw : channelConfigWrappers) {
                        if (connStr != null && connStr.equals(cw.getConnStr())) {
                            containThis = true;
                        }
                    }
                    if (!containThis) {
                        addNewChannel(connStr);
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向服务发送请求
    @Override
    public Response sendMessage(Class<?> serviceInterface, Method method, Object[] args) {
        //选择一个通道配置
        ChannelConfig channelConfig = selectChannel();

        //如果通道配置为空，则直接返回异常
        if(channelConfig == null){
            return getExceptionResponse("service is not avaliable, beacuse channel not active now");
        }

        Channel channel = null;
        try {
            channel = channelConfig.getChannel();
        } catch (Exception e) {
            return getExceptionResponse("channel not active now");
        }

        if(channel == null){
            return getExceptionResponse("channel not active now");
        }


        //封装请求
        Request request = new Request();
        request.setRequestId(atomicLong.incrementAndGet());
        request.setServiceInterface(serviceInterface);
        request.setMethod(method.getName());
        request.setParameters(args);
        request.setParameterType(method.getParameterTypes());
        request.setRequestTime(System.currentTimeMillis());

        //利用channel去发送请求，这个方法会被这个BootStrap建立的channelHandler所处理
        channel.writeAndFlush(request);

        //从阻塞队列中获取响应
        BlockingQueue<Response> responseBlockingQueue = new ArrayBlockingQueue<>(1);
        ResponseMapHelper.responseMap.put(request.getRequestId(), responseBlockingQueue);

        try {
            //如果规定时间内，阻塞队列中没有获取到，证明服务器端没有响应，则返回异常response
            return responseBlockingQueue.poll(requestTimeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return getExceptionResponse("service:" + serviceName + ", method:" + method.getName()+ " time out");
        }finally {
            //放回channel
            try {
                channelConfig.getChannel();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResponseMapHelper.responseMap.remove(request.getRequestId());
        }
    }

    private Response getExceptionResponse(String message) {
        Response response = new Response();
        RuntimeException runtimeException = new RuntimeException(message);
        response.setThrowable(runtimeException);
        return response;
    }

    //随机选择一个节点进行调用
    public ChannelConfig selectChannel(){
        Random random = new Random();
        int size = channelConfigWrappers.size();
        if(size <= 0){
            return null;
        }
        int nextInt = random.nextInt(size);
        return channelConfigWrappers.get(nextInt);
    }


    //todo ??????????????
    // 代理的原因：
    // 客户端调用的是一个接口的方法（service.A()），
    // 所以用代理的方式对本接口进行增强，
    // 增强的逻辑（拦截方法）就是将request利用this(RpcClientImpl)的sendMessage方法远程调用服务器端方法, 将request发送出去并接收response
    // 这就是rpc ！
    @Override
    public <T> T proxyInterface(Class<T> serviceInterface) throws IllegalAccessException, InstantiationException {
        if(clientProxyClass == null){
            clientProxyClass = CglibRpcProxy.class;
        }
        RpcProxy rpcProxy = clientProxyClass.newInstance();
        return rpcProxy.proxyInterface(this, serviceInterface);
    }

    //关闭客户端
    @Override
    public void close() {
        if(curatorFramework != null){
            curatorFramework.close();
        }

        EventLoop eventLoop = null;
        try {
            eventLoop = channelConfigWrappers.size() != 0 ? channelConfigWrappers.get(0).getChannel().eventLoop() : null;
            //关闭所有channel
            for (ChannelConfig channelConfig : channelConfigWrappers) {
                channelConfig.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(eventLoop != null){
                eventLoop.shutdownGracefully();
            }
        }

    }


    private void addNewChannel(String connStr) {
        try {
            List<String> strings = Splitter.on(":").splitToList(connStr);
            if (strings.size() != 2) {
                throw new RuntimeException("Error connection str " + connStr);
            }
            String host = strings.get(0);
            int port = Integer.parseInt(strings.get(1));
            ChannelConfig channelWrapper = new ChannelConfig(host, port);
            channelConfigWrappers.add(channelWrapper);
            LOGGER.info("建立一个新通道： Channel {}, {}", connStr, channelWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getZkConn() {
        return zkConn;
    }

    public void setZkConn(String zkConn) {
        this.zkConn = zkConn;
    }


}

package com.demo.core.client.connection;

import io.netty.channel.Channel;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by dragon
 *
 * 本地客户端对某个服务(比如订单的服务)的channel配置，他被封装到一个对象池中，可以利用对象池获取channel这个连接对象(channelObjectPool.borrowObject())
 * 比如一个服务挂了，或者改变了服务的ip等。此时客户端肯定是需要做出相应的改变的，否则还是调用的老的ip的服务器，就不能用了。
 * 这里面的一致性是靠zookeeper来做的
 *
 * 一个服务对应一个ChannelConfig
 */
public class ChannelConfig {
    private String connStr;
    private String ip;
    private int port;
    private Channel channel;
    private ObjectPool<Channel> channelObjectPool;

    public ChannelConfig(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.connStr = ip + ":" + port;
        channelObjectPool = new GenericObjectPool<>(new ChannelFactory(ip, port));
    }

    public Channel getChannel() throws Exception {
        this.channel = this.channelObjectPool.borrowObject();
        return this.channel;
    }

    public void returnChannel() throws Exception {
        this.channelObjectPool.returnObject(this.channel);
    }

    public void close() {
        channelObjectPool.close();
    }




    public String getConnStr() {
        return connStr;
    }

    public void setConnStr(String connStr) {
        this.connStr = connStr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("连接信息：ChannelConf{");
        sb.append("connStr='").append(connStr).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", port=").append(port);
        sb.append(", channel=").append(channel);
        sb.append(", channelObjectPool=").append(channelObjectPool);
        sb.append('}');
        return sb.toString();
    }


}

package com.dragon.netty.netty.firstNetty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by zhushuanglong on 2018/3/29.
 * 业务处理类，也就是说，
 * 它会拿到数据，之后进行一系列操作，再返回数据
 * 也可以操作异常
 */
public class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 通道被激活时触发的事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当通道被激活时，发送消息
        ctx.write("welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("it's " +new Date() + " now \r\n");
        ctx.flush();
    }

    /**
     * 读取消息，并响应
     * @param channelHandlerContext
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String request) throws Exception {
        String response;
        boolean close = false;
        if(request.isEmpty()){
            response = "please type something. \r\n";
        }else if("bye".equals(request.toLowerCase())){
            response = "have a good day \r\n";
            close = true;
        }else {
            response = "did you say " +request + "? \r\n";
        }
        //future
        ChannelFuture future = channelHandlerContext.write(response);
        //刷新缓存
        channelHandlerContext.flush();
        //关闭通道
        if(close){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

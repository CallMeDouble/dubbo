package com.dragon.netty.netty.firstNetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by zhushuanglong on 2018/3/29.
 * 业务处理类，也就是说，
 * 它会拿到数据，之后进行一系列操作，再返回数据
 * 也可以操作异常
 */
// ChannelHandler 可以属于多个 ChannelPipeline ,它可以绑定多个 ChannelHandlerContext 实例。
// 然而,ChannelHandler 用于这种共享的用法必须添加 @Sharable 注解。否则,试图将它添加到多个 ChannelPipeline 将引发一个异常。
// 所以,使用它时，ChannelHandler 必须既是线程安全的又能安全地使用多个同时的通道(比如,连接)。
// 为什么共享 ChannelHandler?
// 常见原因是要在多个 ChannelPipelines 上安装一个 ChannelHandler 以此来实现跨多个渠道收集统计数据的目的。
@ChannelHandler.Sharable
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
        //如果需要激活pipeline中的下一个handler，就要调用ChannelHandlerContext的fireChannelActive方法
        ctx.fireChannelActive();

        ChannelPipeline pipeline = ctx.pipeline();
        Channel channel = ctx.channel();

        pipeline.fireChannelActive();
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
        ChannelFuture channelFuture = channelHandlerContext.write(response);
        //刷新缓存
        channelHandlerContext.flush();

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("write sucess");
                }else{
                    System.out.println("write false");
                }
            }
        });

        //关闭通道
        if(close){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

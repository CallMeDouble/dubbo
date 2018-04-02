package com.dragon.netty.netty.inAction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by double on 18-3-25.
 */
//1.@Sharable标记这个类的实例可以在 channel 里共享
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    //通道连接被建立后，会调用此方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //2.当被通知该 channel 是活动的时候就向channel发送信息到服务器
        ctx.writeAndFlush(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
    }

    //在接收到消息时被调用
    //注意，
    //①由服务器所发送的消息可以以块的形式被接收。即，当服务器发送 5 个字节是不是保证所有的 5 个字节会立刻收到
    //  - 即使是只有 5 个字节，channelRead0() 方法可被调用两次，第一次用一个ByteBuf（Netty的字节容器）装载3个字节和
    //  第二次一个 ByteBuf 装载 2 个字节。唯一要保证的是，该字节将按照它们发送的顺序分别被接收。
    //  （注意，这是真实的，只有面向流的协议如TCP）。
    //②当 channelRead0() 完成，我们已经拿到的入站的信息byteBuf
    //  当方法返回，SimpleChannelInboundHandler 会小心的释放对 ByteBuf（保存信息） 的引用
    //  所以在客户端使用了SimpleChannelInboundHandler，而在服务端使用了ChannelInboundHandlerAdapter的channelReadComplete来写入和刷新缓存！！！
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
            throws Exception {
        //3.记录接收到的消息
        System.out.println("client reveived:"+ byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //4.记录日志错误并关闭 channel
        cause.printStackTrace();
        ctx.close();

    }
}

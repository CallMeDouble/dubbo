package com.dragon.netty.netty.inAction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by double on 18-3-25.
 * Handler组件实现了服务器的业务逻辑！！！决定了连接创建后和接收到信息后该如何处理和返回消息.
 * ChannelInboundHandlerAdapter是 ChannelInboundHandler的一个实现
 * 关键点要牢记：
        ChannelHandler 是给不同类型的事件调用
        应用程序实现或扩展 ChannelHandler 挂接到事件生命周期和 提供自定义应用逻辑。
 */
//1.@Sharable 标识这类的实例之间可以在 channel 里面共享 ？？？？？？？
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每条信息进入通道时会调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        //2.日志消息输出到控制台
        System.out.println("EchoServerHandler receiver:" + in.toString(CharsetUtil.UTF_8));
        //3.将所接收的消息返回给发送者。注意，这还没有冲刷数据
        //因为write() 是异步的在 channelRead()返回时，可能还没有完成
        //最后在 channelReadComplete() 我们调用 ctxWriteAndFlush() 来释放信息。
        ctx.write(in);
        System.out.println("ChannelHandlerContext write");

    }

    /**
     * 最后一条信息处理完毕时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //4.冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        System.out.println("ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);");
    }

    /**
     * 发生异常时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //5.打印异常 堆栈跟踪
        cause.printStackTrace();
        //6.关闭通道
        ctx.close();
        System.out.println("ctx.close();");
    }
}

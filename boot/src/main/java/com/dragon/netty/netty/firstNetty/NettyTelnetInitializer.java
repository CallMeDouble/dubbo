package com.dragon.netty.netty.firstNetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * Created by zhushuanglong on 2018/3/29.
 * 初始化通道的配置类
 */
public class NettyTelnetInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // Add the text line codec combination first,
        // 添加文本行解码器？？？
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        //添加编码和解码的类
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        //添加处理业务的类
        pipeline.addLast(new NettyTelnetHandler());


    }
}

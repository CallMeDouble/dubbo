package com.demo.codec;

import com.demo.core.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by dragon
 */
public class RpcDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcDecoder.class);

    public RpcDecoder(int maxFrameLength){
        super(maxFrameLength, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if(decode != null){
            int length = decode.readableBytes();
            byte[] bytes = new byte[length];
            decode.readBytes(bytes);
            Object object = SerializerUtil.deserialize(bytes, Request.class);
            LOGGER.info("入站："+object);
        }
        return super.decode(ctx, in);
    }
}

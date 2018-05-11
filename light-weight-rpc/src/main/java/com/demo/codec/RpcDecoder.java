package com.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dragon
 */
public class RpcDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcDecoder.class);

    private Class<?> genericClass;


    public RpcDecoder(int maxFrameLength, Class<?> genericClass){
        super(maxFrameLength, 0, 4, 0, 4);
        this.genericClass = genericClass;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if(decode != null){
            int length = decode.readableBytes();
            byte[] bytes = new byte[length];
            decode.readBytes(bytes);
            Object object = SerializerUtil.deserialize(bytes, genericClass);
            LOGGER.info("入站："+object);
        }
        return super.decode(ctx, in);
    }
}

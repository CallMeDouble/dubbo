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

    private Class genericClass;


    public RpcDecoder(int maxFrameLength, Class genericClass){
        super(maxFrameLength, 0, 4, 0, 4);
        this.genericClass = genericClass;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
//        if(decode != null){
//            int length = in.readByte();
//            byte[] bytes = new byte[length];
//            in.readBytes(bytes);
        int length = in.readInt();
        ByteBuf buf = in.readBytes(length);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        Object object = SerializerUtil.deserialize(bytes, genericClass);
            LOGGER.info("入站："+object);
//        }
        return object;
    }
}

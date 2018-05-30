package com.demo.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by dragon
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        LOGGER.info("出站："+o);
        byte[] bytes = SerializerUtil.serialize(o);
        int length = bytes.length;
        byteBuf.writeByte(length);
        byteBuf.writeBytes(bytes);
    }
}

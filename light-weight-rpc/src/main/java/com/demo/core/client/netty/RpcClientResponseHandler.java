package com.demo.core.client.netty;

import com.demo.core.server.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * Created by dragon
 *
 * 客户端发送请求是直接用channel write request 的.
 * 这里是:客户端拿到response后的处理逻辑
 */
public class RpcClientResponseHandler extends SimpleChannelInboundHandler<Response> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientResponseHandler.class);

    //将response返回保存到一个BlockingQueue中
    //客户端在发送完请求以后，会立马到这个阻塞队列里面获取response，直到timeOut
    //如果timeOut，证明规定时间内服务器端没有返回响应
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response)
            throws Exception {
        BlockingQueue<Response> responses = ResponseMapHelper.responseMap.get(response.getRequestId());
        if(responses == null){
            responses.put(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("Exception caught on {}, ", ctx.channel(), cause);
        ctx.channel().close();
    }
}

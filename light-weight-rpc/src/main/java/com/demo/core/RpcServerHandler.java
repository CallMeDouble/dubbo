package com.demo.core;

import com.demo.utils.NetUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dragon
 *
 * 服务端的netty接收到请求后，调用实现类
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<Request>{

    private static final Logger LOGGER = LoggerFactory.getLogger(NetUtils.class);

    private Object service;

    //此处应该传入服务的实现方
    public RpcServerHandler(Object service){
        this.service = service;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request)
            throws Exception {
        String methodName = request.getMethod();
        Class<?>[] parameterType = request.getParameterType();
        Method method = service.getClass().getMethod(methodName, parameterType);
        Object[] parameters = request.getParameters();

        Response response = new Response();
        response.setRequestId(request.getRequestId());
        Object result = null;
        try {
            result = method.invoke(service, parameters);
            response.setResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.setThrowable(e);
        }
        LOGGER.info("服务处理结果："  +result);

        channelHandlerContext.pipeline().writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("Exception caught on {}, ", ctx.channel(), cause);
        ctx.channel().close();
    }
}

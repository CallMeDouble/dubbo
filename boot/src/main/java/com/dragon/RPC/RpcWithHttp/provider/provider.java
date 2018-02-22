package com.dragon.RPC.RpcWithHttp.provider;

import com.dragon.RPC.RpcWithHttp.util.Encode;
import com.dragon.RPC.RpcWithHttp.util.ProtocolUtil;
import com.dragon.RPC.RpcWithHttp.util.Request;
import com.dragon.RPC.RpcWithHttp.util.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class provider {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(4567);
        while(true){
            Socket socket = serverSocket.accept();

            //读取响应数据
            InputStream input = socket.getInputStream();
            System.out.println("请求进来了");
            Request request = ProtocolUtil.readRequest(input);

            OutputStream output = socket.getOutputStream();

            //组装响应数据
            Response response = new Response();
            response.setEncode(Encode.UTF8);
            if(request.getCommand().equals("HELLO")){
                response.setResponse("hello");
            }else{
                response.setResponse("bye");
            }
            response.setResponseLength(response.getResponse().length());

            //发送响应数据
            System.out.println("发送响应数据");
            ProtocolUtil.writeResponse(output, response);
        }
    }
}

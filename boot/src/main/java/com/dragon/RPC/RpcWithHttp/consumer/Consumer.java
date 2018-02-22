package com.dragon.RPC.RpcWithHttp.consumer;

import com.dragon.RPC.RpcWithHttp.util.Encode;
import com.dragon.RPC.RpcWithHttp.util.ProtocolUtil;
import com.dragon.RPC.RpcWithHttp.util.Request;
import com.dragon.RPC.RpcWithHttp.util.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class Consumer {

    public static void main(String[] args) throws Exception{
        //封装请求
        Request request = new Request();
        request.setCommand("HELLO");
        request.setCommandLength(request.getCommand().length());
        request.setEncode(Encode.UTF8);

        Socket client = new Socket("127.0.0.1", 4567);
        OutputStream outputStream = client.getOutputStream();

        ////发送请求
        ProtocolUtil.writeRequest(outputStream, request);

        InputStream input = client.getInputStream();
        Response response = ProtocolUtil.readResponse(input);
        System.out.println("response:" + response.getResponse());
    }
}

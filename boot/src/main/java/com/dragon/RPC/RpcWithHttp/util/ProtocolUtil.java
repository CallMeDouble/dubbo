package com.dragon.RPC.RpcWithHttp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class ProtocolUtil {
    /**
     * 写出请求消息
     * @param outputStream
     * @param request
     */
    public static void writeRequest(OutputStream outputStream, Request request) throws IOException {
        outputStream.write(request.getEncode());
        //outputstream 中直接写入一个int类型，会截取其低8位，丢弃其高24位,因此，需要将基本类型先转换为字节流
        // java采用的是big endian字节序（参考维基百科），所有的网络协议也采用big endian字节序进行传输
        outputStream.write(ByteUtil.int2ByteArray(request.getCommandLength()));
        if(Encode.GBK==request.getEncode()){
            outputStream.write(request.getCommand().getBytes("GBK"));
        }else if(Encode.UTF8==request.getEncode()){
            outputStream.write(request.getCommand().getBytes("UTF-8"));
        }
        outputStream.flush();
    }

    /**
     * 读取请求消息
     * @param inputStream
     * @return
     */
    public static Request readRequest(InputStream inputStream) throws IOException, ClassNotFoundException {
        byte[] encodeByte=new byte[1];
        inputStream.read(encodeByte);
        byte encode=encodeByte[0];

        //读取命令长度
        byte[] commandLengthBytes=new byte[4];
        inputStream.read(commandLengthBytes);
        int commandLength=ByteUtil.bytes2Int(commandLengthBytes);

        //读取命令
        byte[] commandBytes=new byte[commandLength];
        inputStream.read(commandBytes);
        String command="";
        if(Encode.GBK==encode){
            command=new String(commandBytes,"GBK");
        } else {
            command=new String(commandBytes,"UTF-8");
        }
        Request request=new Request();
        request.setEncode(encode);
        request.setCommandLength(commandLength);
        request.setCommand(command);
        return request;
    }

    /**
     * 写出响应信息
     * @param outputStream
     * @param response
     */
    public static void writeResponse(OutputStream outputStream, Response response) throws IOException {
        outputStream.write(response.getEncode());
        outputStream.write(ByteUtil.int2ByteArray(response.getResponseLength()));
        if(Encode.GBK==response.getEncode()){
            outputStream.write(response.getResponse().getBytes("GBK"));
        } else if (Encode.UTF8==response.getEncode()){
            outputStream.write(response.getResponse().getBytes("UTF-8"));
        }
        outputStream.flush();
    }

    /**
     * 读取响应信息
     * @param inputStream
     * @return
     */
    public static Response readResponse(InputStream inputStream) throws IOException {
        //读取编码
        byte[] encodeByte = new byte[1];
        inputStream.read(encodeByte);
        byte encode = encodeByte[0];

        //读取长度
        byte[] commandLengthBytes=new byte[4];
        inputStream.read(commandLengthBytes);
        int commandLength=ByteUtil.bytes2Int(commandLengthBytes);
        //读取内容
        byte[] commandBytes=new byte[commandLength];
        inputStream.read(commandBytes);
        String command="";
        if(Encode.GBK==encode){
            command=new String(commandBytes,"GBK");
        } else if(Encode.UTF8==encode){
            command=new String(commandBytes,"UTF-8");
        }
        Response response = new Response();
        response.setEncode(encode);
        response.setResponseLength(commandLength);
        response.setResponse(command);
        return response;
    }
}

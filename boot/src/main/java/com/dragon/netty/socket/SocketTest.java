package com.dragon.netty.socket;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class SocketTest {
    private static byte[] bytes = new byte[1024];
    public static void main(String[] args) throws Exception{
        Socket client = new Socket("127.0.0.1", 8888);
        OutputStream outputStream = client.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        InputStream inputStream = client.getInputStream();
//        DataInputStream dataInputStream = new DataInputStream(inputStream);
//        int index = 0;
//        while(true){
//            byte b = dataInputStream.readByte();
//            bytes[index] = b;
//            if(b == -1){
//                break;
//            }
//        }
    }
}

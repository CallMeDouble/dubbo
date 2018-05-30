package com.dragon.netty.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class ServerSocketTest {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8008);
        serverSocket.setSoTimeout(10000);
        while (true){
            Socket server = serverSocket.accept();
            InputStream inputStream = server.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String s = dataInputStream.readUTF();
            System.out.println(s);
            DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
            dataOutputStream.writeUTF("哦哦");
            server.close();
        }

    }
}

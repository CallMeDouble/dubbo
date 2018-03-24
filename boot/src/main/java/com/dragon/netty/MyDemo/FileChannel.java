package com.dragon.netty.MyDemo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by double on 18-3-21.
 */
public class FileChannel {
    public static void main(String[] args) throws Exception {
        //写入文件
//        FileOutputStream fileInputStream = new FileOutputStream(new File("//home//double//test.txt"));
//        java.nio.channels.FileChannel channel = fileInputStream.getChannel();
//
//        ByteBuffer allocate = ByteBuffer.allocate(1024);
//        allocate.put(new String("aaaaaaaaaaaaaaaaa").getBytes());
//        allocate.flip();
//
//        channel.write(allocate);


        //读取文件
        FileInputStream fileInputStream1 = new FileInputStream(new File("//home//double//test.txt"));
        java.nio.channels.FileChannel fileInputStream1Channel = fileInputStream1.getChannel();

        //写入文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File("//home//double//test2.txt"));
        java.nio.channels.FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(5);
        while (true){
            buffer.clear();
            int read = fileInputStream1Channel.read(buffer);
            if(read != -1){
                buffer.flip();
                fileOutputStreamChannel.write(buffer);

            }else{
                break;
            }

        }

    }
}

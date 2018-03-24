package com.dragon.netty.MyDemo;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhushuanglong on 2018/3/22.
 */
public class SelectorTest {
    public static void main(String[] args) throws Exception{
        //缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

        //新建一个Selector，它是一个可以让通道注册进来，并监听通道channel上的各种事件的组件
        Selector selector = Selector.open();

        //设置监听的端口号。其实可以监听多个的
        int port = 8888;
        //ServerSocketChannel是一个可以监听新进来的TCP连接的通道, 相当于传统io的ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //指定非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //绑定监听的端口
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        //将channel注册到selector上，并指定监听的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("开始监听8888端口上的事件");
        while (true){
            //select()方法会阻塞至到至少有一个IO事件发生，当一个或多个事件发生时，他返回事件的数量
            int readyChannelNumbers = selector.select();
            if(readyChannelNumbers == 0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //判断是不是通道中已经准备就绪的事件是不是 接收事件
                if((selectionKey.readyOps()& SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    //已经判断了是一个接收事件了，所以不用担心这里会阻塞
                    // 这个SocktChannel就相当于传统IO的套接字socket，所以在此接收这个套接字
                    SocketChannel socket = server.accept();
                    //设置这个新连接为非阻塞连接
                    socket.configureBlocking(false);
                    //由于设置了非阻塞，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。 因此，需要检查返回的SocketChannel是否是null
                    if(socket != null){
                        //而且由于接收这个连接的目的是为了读取来自套接字的数据，所以我们还必须将 SocketChannel 注册到 Selector上，并监听读事件
                        SelectionKey newKey = socket.register(selector, SelectionKey.OP_READ);
                        System.out.println("准备接收数据");

                        //我们还必须将处理过的 SelectionKey 从选定的键集合中删除。
                        //如果我们没有删除处理过的键，那么它仍然会在主集合中以一个激活的键出现，这会导致我们尝试再次处理它。
                        // 我们调用迭代器的 remove() 方法来删除处理过的 SelectionKey：
                        iterator.remove();
                    }
                }else if((selectionKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                    //已经判断了是一个 读事件
                    SocketChannel socket = (SocketChannel) selectionKey.channel();
                    //读取数据
                    System.out.println("开始接收数据");
                    int byteCount = 0;
                    while(true){
                        //清空缓存
                        byteBuffer.clear();
                        int read = socket.read(byteBuffer);

                        //在非阻塞模式下，非阻塞模式下,read()方法在尚未读取到任何数据时可能就返回了。所以需要关注它的int返回值，它会告诉你读取了多少字节。
                        //read()方法返回的int值表示读了多少字节进Buffer里。如果返回的是-1，表示已经读到了流的末尾（连接关闭了）。
                        if (read <= 0 ){
                            break;
                        }
                        //把数据写回
                        byteBuffer.flip();
                        //非阻塞模式下，write()方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用write()
                        while(byteBuffer.hasRemaining()){
                            socket.write(byteBuffer);
                        }
                        byteCount += read;
                    }
                    System.out.println("SocketChannel 接收到了" + byteCount +" 字节");

                    //和上面一样，为了避免重复处理这个键，要从集合键中删除
                    iterator.remove();
                }
            }
        }


    }
}

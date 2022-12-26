package com.msb.net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @author: msb
 * @date: 2022/12/20 - 12 - 20 - 15:49
 * @description: com.msb.net.io.nio
 * @version: 1.0
 */
/*
 * nio里最难用的地方在ByteBuffer  缓冲区里只用一个指针  读和写 都只能用这个指针 数据很容易卵
 * 而net里面 ByteBuf 缓冲区里就比较好了  缓冲区里有两个指针 读和写分开来用
 * */
public class Server {
    public static void main(String[] args) throws Exception{
        /*阻塞式的用 ServerSocket  非阻塞式的ServerSocketChannel*/
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定网络地址
        ssc.socket().bind(new InetSocketAddress("localhost",8888));
        ssc.configureBlocking(false);//通道不阻塞

        System.out.println("server started listening on :" + ssc.getLocalAddress());
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();//没有客户端时阻塞
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }
        }
    }

    private static void handle(SelectionKey key) {
        if (key.isAcceptable()){
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //new Client
                sc.register(key.selector(),SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }else if(key.isReadable()){//flip
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.clear();

                int len = sc.read(buffer);

                if (len != -1){
                    System.out.println(new String(buffer.array(),0, len));
                }
                ByteBuffer bufferToWriter = ByteBuffer.wrap("HelloClient".getBytes());
                sc.write(bufferToWriter);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sc != null){
                    try {
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

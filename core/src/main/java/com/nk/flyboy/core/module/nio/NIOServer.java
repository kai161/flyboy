package com.nk.flyboy.core.module.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/8/11.
 */
public class NIOServer {

    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void Server() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress(9092));

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            int num = selector.select();

            //System.out.println(num);

            Set skset = selector.selectedKeys();
            Iterator it = skset.iterator();
            while (it.hasNext()) {

                SelectionKey key = (SelectionKey) it.next();
                it.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    int i = atomicInteger.getAndIncrement();
                    socketChannel.register(selector, SelectionKey.OP_READ, i);

                    String info = "accept " + i;

                    writeInfo(socketChannel, info);

                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int i = (int) key.attachment();

                    socketChannel.read(byteBuffer);

                    byteBuffer.flip();

                    //if(byteBuffer.hasRemaining()){
                    byte[] bytes = new byte[byteBuffer.limit()];
                    byteBuffer.get(bytes, 0, byteBuffer.limit());

                    String info = new String(bytes) + " read you info " + i;

                    //System.out.println(info);

                    writeInfo(socketChannel, info);
                    //}


                    byteBuffer.clear();

                    //将SelectionKey注销，
                    //否则 client段socket关闭时，selector会一直select到这个key，并执行最后的操作，到时对通道读写就会报异常了。
                    key.cancel();

                    //关闭socket 否则会出现 Too many open files in system 的异常
                    socketChannel.close();
                }

            }
        }
    }

    private static void writeInfo(SocketChannel socketChannel, String info) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(info.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        System.out.println(info);
    }

    public static void main(String[] args) throws IOException {
        NIOServer.Server();
    }

}

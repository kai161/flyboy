package com.nk.flyboy.core.module.nio;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created on 2017/8/11.
 */
public class NIOClient {

    static boolean flag=true;
    public static void client() {
        ExecutorService service=Executors.newFixedThreadPool(100);

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            flag=false;
            service.shutdown();
        }));
        int i=0;

        long start=System.currentTimeMillis();
        while (i<10000){
            final int d=i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket=new Socket();

                        socket.connect(new InetSocketAddress(9092));

                        InputStream inputStream=socket.getInputStream();
                        OutputStream outputStream=socket.getOutputStream();

                        String client="client "+d+" link ";

                        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStream);
                        bufferedOutputStream.write(client.getBytes());
                        bufferedOutputStream.flush();


                        boolean s=true;
                        while (s){

                            BufferedInputStream byteInputStream=new BufferedInputStream(inputStream);

                            if(byteInputStream.available()>0){
                                byte[] input=new byte[byteInputStream.available()];
                                byteInputStream.read(input,0,byteInputStream.available());
                                String info=new String(input);
                                System.out.println(info);
                                if(info.contains("client")){
                                    s=false;
                                }
                            }
                        }

                        socket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            i++;
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    public static void main(String[] args) {
        NIOClient.client();
    }
}

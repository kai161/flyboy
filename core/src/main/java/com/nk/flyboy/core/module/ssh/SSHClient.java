package com.nk.flyboy.core.module.ssh;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;

/**
 * Created on 2017/9/27.
 */
public class SSHClient {

    public static void main(String[] args) throws JSchException, IOException {
        String username="kai1";
        String ip="172.16.184.128";
        Session session=SSHHelper.connect(ip,22,username,"1");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("disconnect");
            session.disconnect();
        }));

        String result= SSHHelper.exec(session,"ls");

        System.out.println(result);


        String result1= SSHHelper.exec(session,"pwd");

        System.out.println(result1);




    }
}

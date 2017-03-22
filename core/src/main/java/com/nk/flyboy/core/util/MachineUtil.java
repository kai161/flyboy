package com.nk.flyboy.core.util;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 2017/3/2.
 */
public class MachineUtil {


    public static String getRealIP(){
        String localIP=null;
        String netIP=null;
        try {
            Enumeration<NetworkInterface> networkInterfaces=NetworkInterface.getNetworkInterfaces();
            InetAddress ip=null;
            boolean finded=false;
            while (networkInterfaces.hasMoreElements()&&!finded){
                NetworkInterface ni=networkInterfaces.nextElement();
                Enumeration<InetAddress> address=ni.getInetAddresses();
                while (address.hasMoreElements()){
                    ip=address.nextElement();
                    if(!ip.isSiteLocalAddress()&&!ip.isLoopbackAddress()&&ip.getHostAddress().indexOf(":")==-1){
                        netIP=ip.getHostAddress();
                        finded=true;
                        break;
                    }else if(ip.isSiteLocalAddress()&&!ip.isLoopbackAddress()&&ip.getHostAddress().indexOf(":")==-1){
                        localIP=ip.getHostAddress();
                    }
                }
            }
            if(!StringUtils.isEmpty(netIP)){
                return netIP;
            }
            return localIP;

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Integer> getProtByCmd(String[] cmd){
        Runtime runtime=Runtime.getRuntime();
        BufferedReader br=null;
        Process p=null;
        List<Integer> ports=new LinkedList<>();

        try{
            p=runtime.exec(cmd);
            br=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str=null;
            while ((str=br.readLine())!=null){
                str=str.trim();
                String str0=str;
                String str1=str;
                try {
                    str0=str0.split(":::")[1].trim();
                    ports.add(Integer.parseInt(str0));
                }catch (Exception ex){
                    str1=str1.split(":")[1].split(" ")[0].trim();
                    ports.add(Integer.parseInt(str1));
                }
            }
            Collections.sort(ports);
            p.waitFor();
        }catch (Exception ex){

        }finally {
            if(br!=null){
                try {
                    br.close();
                }catch (Exception ex){

                }
            }
            if(p!=null){
                p.destroy();
            }
        }

        return ports;
    }

    public static void main(String[] args) {

        String[] cmds=new String[] { "/bin/bash", "-c", "netstat -i|grep localhost"  };
        //getProtByCmd(cmds);
        getRealIP();

    }
}

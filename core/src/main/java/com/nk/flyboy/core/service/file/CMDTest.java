package com.nk.flyboy.core.service.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 2017/5/2.
 */
public class CMDTest {
    public static void main(String[] args) {
        try {
            String[] cmd={"/bin/sh","/Users/kai/work/code/my/flyboy/dao/test.sh"};

            Process process=Runtime.getRuntime().exec(cmd);

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream())) ;

            String str=null;
            while ((str=bufferedReader.readLine())!=null){
                System.out.println(str);
            }

            process.waitFor();

            System.out.println("cmd finish!");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

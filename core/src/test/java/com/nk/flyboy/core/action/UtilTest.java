package com.nk.flyboy.core.action;

import com.nk.flyboy.core.util.HttpUtil;
import com.nk.flyboy.core.util.JVMUtil;
import com.nk.flyboy.core.util.LockUtil;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kai on 2017/1/18.
 */
public class UtilTest {

    private String publisInfo="hello world";
    @Test
    public void getMemoryMXBean() throws Exception {

        JVMUtil.getLocalMemoryMXBean();

        JVMUtil.getLocalOperatingSystemMXBean();

        JVMUtil.getLocalRuntimeMXBean();

        JVMUtil.getLocalThreadMXBean();
    }

    @Test
    public void getHtml() {
        try {
            String s1 = HttpUtil.get("https://my.oschina.net/lonelydawn/blog/827615");
            Document s = HttpUtil.htmlParser("https://www.souyidai.com/myaccount/capital");
            URL netUrl = new URL("https://www.souyidai.com/myaccount/capital");
            String demoinURL = netUrl.getHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void synchronizUtil() {

        String info = "this is a info you can use";

        String result = new LockUtil(() -> {

            System.out.println(publisInfo);
            useMethod();
            System.out.println(info);
            System.out.println("this is something that i want to running");
        }).start();

        System.out.println(result);
    }

    private void  useMethod(){
        System.out.println("use this method");
    }

}

package com.nk.flyboy.core.action;

import com.nk.flyboy.core.util.HttpUtil;
import com.nk.flyboy.core.util.JVMUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kai on 2017/1/18.
 */
public class UtilTest {

    @Test
    public void getMemoryMXBean() throws Exception {

        JVMUtil.getLocalMemoryMXBean();

        JVMUtil.getLocalOperatingSystemMXBean();

        JVMUtil.getLocalRuntimeMXBean();

        JVMUtil.getLocalThreadMXBean();
    }

    @Test
    public void getHtml(){
        try {
            String s1=HttpUtil.get("https://my.oschina.net/lonelydawn/blog/827615");
            Document s= HttpUtil.htmlParser("https://my.oschina.net/lonelydawn/blog/827615");
            URL netUrl=new URL("https://my.oschina.net/lonelydawn/blog/827615");
            String demoinURL=netUrl.getHost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

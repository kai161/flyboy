package com.nk.flyboy.core.action;

import com.nk.flyboy.core.util.HttpUtil;
import com.nk.flyboy.core.util.JVMUtil;
import org.junit.Test;

import java.io.IOException;

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
            String s1=HttpUtil.get("https://item.jd.com/3597673.html");
            String s= HttpUtil.htmlParser("https://item.jd.com/3597673.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

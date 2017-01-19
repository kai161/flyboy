package com.nk.flyboy.core.action;

import com.nk.flyboy.core.util.JVMUtil;
import org.junit.Test;

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
}

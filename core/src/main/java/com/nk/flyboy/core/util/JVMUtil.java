package com.nk.flyboy.core.util;

import org.springframework.util.StringUtils;

import javax.management.MBeanServerConnection;
import javax.management.MXBean;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kai on 2017/1/18.
 */
public class JVMUtil {


    /**
     * 获取本地机器的JVM内存信息
     */
    public static MemoryMXBean getLocalMemoryMXBean() throws Exception{

        MemoryMXBean memoryMXBean= ManagementFactory.getMemoryMXBean();

        return memoryMXBean;

    }

    public static OperatingSystemMXBean getLocalOperatingSystemMXBean(){
        return ManagementFactory.getOperatingSystemMXBean();
    }

    public static ThreadMXBean getLocalThreadMXBean(){
        return ManagementFactory.getThreadMXBean();
    }

    public static RuntimeMXBean getLocalRuntimeMXBean(){
        return ManagementFactory.getRuntimeMXBean();
    }

    public class RemoteJVM{

        private MBeanServerConnection mbsc;
        private String monitorURL="service:jmx:rmi:///jndi/rmi://%s:$s/jmxrmi";
        private JMXConnector connector;

        public RemoteJVM(String host,String port,String username,String password){
            init(host,port,username,password);
        }

        public void  init(String host,String port,String username,String password){
            try {
                monitorURL=String.format(monitorURL,host,port);
                JMXServiceURL serviceURL=new JMXServiceURL(monitorURL);
                Map<String,String[]> params=new HashMap<String, String[]>();
                if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(password)){
                    String[] credentials=new String[]{username,password};
                    params.put("jmx.remote.credentials",credentials);
                }

                connector= JMXConnectorFactory.connect(serviceURL,params);
                mbsc=connector.getMBeanServerConnection();
            }catch (Exception ex){

            }
        }

        public void close() throws IOException {
            connector.close();
        }

        public MemoryMXBean getMemoryMXBean() throws IOException {
            return ManagementFactory.newPlatformMXBeanProxy(mbsc,ManagementFactory.MEMORY_MXBEAN_NAME,MemoryMXBean.class);
        }

        public OperatingSystemMXBean getOperatingSystemMXBean() throws IOException {
            return ManagementFactory.newPlatformMXBeanProxy(mbsc,ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,OperatingSystemMXBean.class);
        }

    }




}

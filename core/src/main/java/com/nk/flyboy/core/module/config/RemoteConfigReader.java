package com.nk.flyboy.core.module.config;

import com.nk.flyboy.core.module.zookeeper.ZookeeperUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * Created on 2017/7/3.
 */
public class RemoteConfigReader extends PropertyPlaceholderConfigurer {

    private String zookeeperUrl;

    private String confNode="/config/test2";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            Properties mergedProps = getPropertiesFromRemote();

            // Convert the merged properties, if necessary.
            convertProperties(mergedProps);

            // Let the subclass process the properties.
            processProperties(beanFactory, mergedProps);
        }
        catch (Exception ex) {
            throw new BeanInitializationException("Could not load properties", ex);
        }
    }

    /**
     * 从远程服务器获取配置信息
     * @return
     */
    public Properties getPropertiesFromRemote(){
        Properties properties=new Properties();
        try {
            Properties localProperties=mergeProperties();
            if(localProperties.size()>0){
                properties.putAll(localProperties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(zookeeperUrl!=null){
            byte[] bytes= ZookeeperUtils.getNodeData(zookeeperUrl,confNode);
            if(bytes!=null){
                String props=new String(bytes);
                String[] propsList=props.split(";");
                if(propsList.length>0){
                    for (String prop:propsList){
                        String[] keyValues=prop.split("=");
                        properties.put(keyValues[0],keyValues[1]);
                    }
                }
            }
        }
        return properties;
    }

    public String getZookeeperUrl() {
        return zookeeperUrl;
    }

    public void setZookeeperUrl(String zookeeperUrl) {
        this.zookeeperUrl = zookeeperUrl;
    }

    public String getConfNode() {
        return confNode;
    }

    public void setConfNode(String confNode) {
        this.confNode = confNode;
    }

}

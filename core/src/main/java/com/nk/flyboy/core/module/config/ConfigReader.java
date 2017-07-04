package com.nk.flyboy.core.module.config;

import com.nk.flyboy.core.module.zookeeper.ZookeeperUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created on 2017/7/3.
 */
public class ConfigReader extends PropertyPlaceholderConfigurer {

    private String remoteUrl;

    private Resource[] locationResource;

    private String confNode="/config/test2";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            Properties mergedProps = getProperties();

            // Convert the merged properties, if necessary.
            convertProperties(mergedProps);

            // Let the subclass process the properties.
            processProperties(beanFactory, mergedProps);
        }
        catch (Exception ex) {
            throw new BeanInitializationException("Could not load properties", ex);
        }
    }

    public Properties getProperties(){
        Properties properties=new Properties();

        Properties locationProp=getPropertiesFromLocaltion();
        if(locationProp.size()>0){
            properties.putAll(locationProp);
        }

        Properties remoteProp=getPropertiesFromRemote();
        if(remoteProp.size()>0){
            properties.putAll(remoteProp);
        }
        return properties;
    }

    /**
     * 获取本地配置信息
     * @return
     */
    public Properties getPropertiesFromLocaltion(){
        Properties properties=new Properties();
        try {
            if(locationResource!=null&&locationResource.length>0){
                setLocations(locationResource);
            }

            Properties localProperties=mergeProperties();
            if(localProperties.size()>0){
                properties.putAll(localProperties);

                for (Resource resource:locationResource){
                    new Thread(()->{
                        new WatchLocalConfig(resource).watch();
                    }).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * 从远程服务器获取配置信息
     * @return
     */
    public Properties getPropertiesFromRemote(){
        Properties properties=new Properties();
        if(remoteUrl!=null){
            byte[] bytes= ZookeeperUtils.getNodeData(remoteUrl,confNode);
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

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public Resource[] getLocationResource() {
        return locationResource;
    }

    public void setLocationResource(Resource[] locationResource) {
        this.locationResource = locationResource;
    }

    public String getConfNode() {
        return confNode;
    }

    public void setConfNode(String confNode) {
        this.confNode = confNode;
    }

}

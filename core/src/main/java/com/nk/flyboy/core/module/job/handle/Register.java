package com.nk.flyboy.core.module.job.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.flyboy.core.module.job.annotation.JobDesc;
import com.nk.flyboy.core.module.zookeeper.ZookeeperUtils;
import com.nk.flyboy.core.util.MachineUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.io.Serializable;

/**
 * Created on 2017/7/10.
 */
public class Register implements BeanFactoryPostProcessor {

    private String zookeeper;

    private String registerPath="/schedul";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String ip= MachineUtil.getRealIP();
        String[] beanList=beanFactory.getBeanNamesForAnnotation(JobDesc.class);
        if(beanList!=null&&beanList.length>0){
            String path= registerPath +"/"+ip;
            StringBuilder stringBuilder=new StringBuilder();
            ObjectMapper objectMapper=new ObjectMapper();
            for(String beanName:beanList){
                JobDesc jobDesc=beanFactory.findAnnotationOnBean(beanName,JobDesc.class);
                JobDescription jobDescription=new JobDescription();
                jobDescription.setProject(jobDesc.project());
                jobDescription.setName(jobDesc.name());
                jobDescription.setDesc(jobDesc.desc());
                try {
                    String json=objectMapper.writeValueAsString(jobDescription);
                    stringBuilder.append(json).append(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(zookeeper!=null&&stringBuilder.length()>0){
                try {
                    ZookeeperUtils.createNode(zookeeper,path,stringBuilder.toString(), CreateMode.EPHEMERAL);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public String getRegisterPath() {
        return registerPath;
    }

    public void setRegisterPath(String registerPath) {
        this.registerPath = registerPath;
    }

    public class JobDescription implements Serializable{
        String project;
        String name;
        String desc;

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

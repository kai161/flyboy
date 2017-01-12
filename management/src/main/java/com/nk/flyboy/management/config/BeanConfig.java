package com.nk.flyboy.management.config;

import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kai on 2017/1/11.
 */
@Configuration
@EnableAutoConfiguration()
public class BeanConfig {


    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public org.apache.shiro.mgt.SecurityManager securityManager(DataSource dataSource){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        JdbcRealm realm=new JdbcRealm();
        realm.setDataSource(dataSource);
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
//    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
//        MethodInvokingFactoryBean methodInvokingFactoryBean=new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//        methodInvokingFactoryBean.setArguments(new Object[]{securityManager()});
//        return methodInvokingFactoryBean;
//    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DataSource dataSource){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager(dataSource));
        shiroFilterFactoryBean.setLoginUrl("/auth.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");

//        Map<String,String> map=new LinkedHashMap<String, String>();
//        map.put("/*","admin");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DataSource dataSource){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor= new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager(dataSource));
        return authorizationAttributeSourceAdvisor;
    }

}

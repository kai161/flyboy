package com.nk.flyboy.management;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kai on 2017/1/11.
 */
@SpringBootApplication
@ComponentScan("com.nk.flyboy.management")
public class Application {

    public static void main(String[] args){

        SpringApplication springApplication=new SpringApplication(Application.class);
        springApplication.setWebEnvironment(true);
        springApplication.run(args);

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        Map<String,String> map=new LinkedHashMap<String, String>();
        map.put("targetFilterLifecycle","true");
        filterRegistrationBean.setInitParameters(map);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}

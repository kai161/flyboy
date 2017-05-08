package com.nk.flyboy.dba.config;

import com.nk.flyboy.dba.interceptor.AuthInterceptor;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;


/**
 * Created by kai on 2017/1/5.
 * 基于java的配置，方法名将作为bean的ID
 */
@Configuration
@EnableAutoConfiguration()
public class BeanConfig {

    Logger logger= LoggerFactory.getLogger(BeanConfig.class);

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        logger.info("InternalResourceViewResolver init");
        InternalResourceViewResolver internalResourceViewResolver=new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/view/");
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer=new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/*").excludePathPatterns("/index.html");
            }
        };
        return webMvcConfigurer;
    }

//    @Bean
//    public DriverManagerDataSource dataSource(){
//        DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("");
//        driverManagerDataSource.setUrl("");
//        driverManagerDataSource.setUsername("");
//        driverManagerDataSource.setPassword("");
//        return driverManagerDataSource;
//    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource ){
        SpringLiquibase liquibase=new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setShouldRun(true);
        return liquibase;
    }


}

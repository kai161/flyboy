package com.nk.flyboy.core.module.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created on 2017/8/15.
 */
@Configuration
public class JavaConfig {

    @Bean
    public DataSource db2source(DataSource dataSource){
        DataSource dataSource1=dataSource;
        return dataSource1;
    }
}

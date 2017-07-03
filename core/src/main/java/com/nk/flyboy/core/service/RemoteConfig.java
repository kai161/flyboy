package com.nk.flyboy.core.service;

import org.springframework.stereotype.Service;

/**
 * Created on 2017/7/3.
 */
public class RemoteConfig {
    private String dataSource;

    private String username;

    private String passsword;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    @Override
    public String toString(){
        return "RemoteConfig: dataSource:"+dataSource+
                ",username:"+username+
                ",password:"+passsword+
                ",";
    }
}

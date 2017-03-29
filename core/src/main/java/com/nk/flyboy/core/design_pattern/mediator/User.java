package com.nk.flyboy.core.design_pattern.mediator;

/**
 * Created on 2017/3/29.
 */
public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name){
        this.name=name;
    }

    public void sendMessage(String message){
        ChatRoom.showMessage(this,message);
    }
}

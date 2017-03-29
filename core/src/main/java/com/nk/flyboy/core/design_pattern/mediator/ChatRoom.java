package com.nk.flyboy.core.design_pattern.mediator;

/**
 * Created on 2017/3/29.
 */
public class ChatRoom {

    public static void showMessage(User user,String message){
        System.out.println("user name: "+user.getName()+", say"+message);
    }
}

package com.nk.flyboy.core.activemq;

import javax.jms.JMSException;

/**
 * Created by cheris on 2016/9/13.
 */
public class TestMain {

    public static void main(String[] arg) throws JMSException {

        Product product=new Product();

        Consume consume=new Consume();

        consume.consume();

        product.product("RabbitMQ test");



    }
}

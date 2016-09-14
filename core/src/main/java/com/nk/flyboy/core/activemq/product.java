package com.nk.flyboy.core.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


/**
 * Created by cheris on 2016/9/13.
 */
public class Product {

    public void product(String message) throws JMSException {

        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://192.168.177.130:61616");

        Connection connection=connectionFactory.createConnection();

        connection.start();

        Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);

        Destination destination=session.createQueue("testQueue");

        MessageProducer messageProducer=session.createProducer(destination);

        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i=0;i<100;i++){
            TextMessage textMessage=session.createTextMessage(message+i);

            messageProducer.send(textMessage);
        }

        session.commit();
        connection.close();

    }

}

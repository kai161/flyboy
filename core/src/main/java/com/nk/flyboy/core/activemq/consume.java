package com.nk.flyboy.core.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by cheris on 2016/9/13.
 */
public class Consume {

    public void consume() throws JMSException {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://192.168.177.130:61616");

        Connection connection=connectionFactory.createConnection();
        connection.start();

        Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);

        Destination destination=session.createQueue("testQueue");

        MessageConsumer messageConsumer=session.createConsumer(destination);

        for(;;){
            TextMessage textMessage=(TextMessage)messageConsumer.receive(10000);
            if(textMessage!=null){

                System.out.println(textMessage.getText());
            }else {
                break;
            }
        }

        session.close();
        connection.close();


    }
}

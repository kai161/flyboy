package com.nk.flyboy.core.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Created by cheris on 2016/9/14.
 */
public class Publish {

    public static void main(String[] arg ){
        ConnectionFactory connectionFactory=null;
        Connection connection=null;
        Session session=null;
        MessageProducer messageProducer=null;
        try {
            connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://192.168.177.131:61616");
            connection=connectionFactory.createConnection();
            connection.start();
            session=connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            Topic topic=session.createTopic("testTopic");

            messageProducer=session.createProducer(topic);

            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for(int i=0;i<10;i++){
                TextMessage textMessage=session.createTextMessage("this is topic test "+ i);

                messageProducer.send(textMessage);

                session.commit();

                Thread.sleep(3000);
            }



        }catch (Exception ex){

        }finally {
            try {
                connection.close();
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}

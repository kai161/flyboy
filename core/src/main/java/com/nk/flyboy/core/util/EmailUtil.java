package com.nk.flyboy.core.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created on 2017/10/20.
 */
public class EmailUtil {

    public static boolean sendEmail(String subject, String text,String toEmail) throws MessagingException {

        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost("");


        MimeMessage MimeMessage= javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(MimeMessage,true);

        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);

        javaMailSender.send(MimeMessage);

        return true;
    }


    public static void main(String[] args) throws MessagingException {
        sendEmail("text","this is a test mail","kai161@126.com");
    }
}

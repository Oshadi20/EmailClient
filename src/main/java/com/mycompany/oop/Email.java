/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.oop;

/**
 *
 * @author OshadiPC
 */
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.Serializable;

public class Email implements Serializable {
    private String emailAddressTo = new String();
    private String subject = new String();
    private String content = new String();
    private String date;

    final String USER_NAME = "pererasaumya909@gmail.com";   //User name of the Goole(gmail) account
    final String PASSSWORD = "ywdckdpwcjfzduqp";  //App password of the Goole(gmail) account
    final String FROM_ADDRESS = "pererasaumya909@gmail.com";  //From addresss
 
    public Email() {
    }
    public void createAndSendEmail(String emailAddressTo, String subject, String content) {
        this.emailAddressTo = emailAddressTo;
        this.subject = subject;
        this.content = content;
        sendEmailMessage();
    }
 
    private void sendEmailMessage() {
        //Create email sending properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
  
        Session session = Session.getInstance(props, 
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pererasaumya909@gmail.com", "ywdckdpwcjfzduqp");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pererasaumya909@gmail.com")); //Set from address of the email
            message.setContent(content,"text/html"); //set content type of the email
        
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailAddressTo)); //Set email recipient
    
            message.setSubject(subject); //Set email message subject
            Transport.send(message); //Send email message

            System.out.println("sent email successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEmailAddressTo(String emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessageText(String content) {
        this.content = content;
    }
 
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return date;
    }
    
    public String getEmailAddressTo(){
        return emailAddressTo;
    }
    
    public String getSubject(){
        return subject;
    }
       
}

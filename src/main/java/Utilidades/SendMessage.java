/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author marti
 */
public class SendMessage {
    public static void send(String to) {
    try{
        InputStream input = new FileInputStream("config.properties");
            Properties properties = System.getProperties();  
            properties.load(input);
            
        String from = properties.getProperty("emailFrom");
        String pass =  properties.getProperty("emailPass");
            
            
            
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from,
                    pass);
        }
    });  

        try{  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(from, "The Bull"));  
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
            message.setSubject("Gracias por registrarte");  
            message.setContent(Html.html, "text/html");  

            // Send message  
            Transport.send(message);
            
        }catch (MessagingException mex) {mex.printStackTrace();}
    }catch(IOException e){
          e.printStackTrace();
      }
    }
    
    
    public static void sendFile(String to, File attachementPath){
    try{
        InputStream input = new FileInputStream("config.properties");
            Properties properties = System.getProperties();  
            properties.load(input);
            
        String from = properties.getProperty("emailFrom");
        String pass =  properties.getProperty("emailPass");
            
            
            
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from,
                    pass);
        }
    });  

        try{  
            MimeMessage msg = new MimeMessage(session);  

                            msg.setFrom(new InternetAddress("thebullapp@outlook.es", "The Bull"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject("JSON USERS");

        Multipart multipart = new MimeMultipart();

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("JSON USERS");

        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(attachementPath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName("UsersJson.json");

        multipart.addBodyPart(textBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        msg.setContent(multipart);


        Transport.send(msg);
            
        }catch (MessagingException mex) {mex.printStackTrace();}
    }catch(IOException e){
          e.printStackTrace();
      }    
    }
}

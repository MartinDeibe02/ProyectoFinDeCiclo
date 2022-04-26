/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author marti
 */
public class SendMessage {
    public static void send(String to) {
    try{
        InputStream input = new FileInputStream("C:\\Users\\marti\\Documents\\NetBeansProjects\\ProyectoFinCiclo\\src\\main\\java\\Utilidades\\config.properties");
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
            message.setFrom(new InternetAddress(from));  
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.table;

import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import java.io.IOException;
import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Markyuu
 */
public class ControladorTabPane {
    @FXML
    TextArea recibidos;

@FXML
private TextField txtChatSend;

String texto;
ControladorLogin controlLogin = new ControladorLogin();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  


    public void append(String text){
        recibidos.appendText(text + "\n");
    }
    
        public void send(){
        
        texto =dtf.format(now) + " " + controlLogin.nombre + " >> " + txtChatSend.getText();

        try {
            DatagramPacket paquete = new DatagramPacket(texto.getBytes(), texto.length(), App.grupo, App.port);
            App.ms.send(paquete);
        } catch (IOException ex) {
        }
    }

}

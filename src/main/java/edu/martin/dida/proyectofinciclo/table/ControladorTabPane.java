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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Markyuu
 */
public class ControladorTabPane {
    // <editor-fold defaultstate="collapsed" desc="Chat">

    @FXML
    TextArea recibidos;

    @FXML
    private TextField txtChatSend;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Jugadores">

    @FXML
    private Rectangle prueba;
    // </editor-fold>

    String texto;
    
    ControladorLogin controlLogin = new ControladorLogin();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  

    // <editor-fold defaultstate="collapsed" desc="Mandar mensaje chat">
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
    // </editor-fold>
    
    //Bordes de imagen + shadow
    private void setimg(Rectangle rec1, Image img1) {
        rec1.setArcWidth(30.0);   // Corner radius
        rec1.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(img1);

        rec1.setFill(pattern);
        rec1.setEffect(new DropShadow(20, Color.BLACK));    
    }
    
    public void photo(){
        Image img = new Image("https://bolavip.com/__export/1646958112918/sites/bolavip/img/2022/03/10/lebron_james_nba_la_lakers.jpg_1890075089.jpg");
        setimg(prueba, img);
 
    }

    
    public void salir(){
        System.exit(0);
    }

}

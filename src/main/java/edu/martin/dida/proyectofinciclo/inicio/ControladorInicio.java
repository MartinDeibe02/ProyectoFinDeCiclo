/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.inicio;

import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Markyuu
 */
public class ControladorInicio implements Initializable{
    
    // <editor-fold defaultstate="collapsed" desc="Imagenes">

    
    @FXML
    private Rectangle rec1;
    Image img = new Image("/recursos/zach.jpg");
    
    @FXML
    private Rectangle rec2;
    Image img2 = new Image("/recursos/lebron.jpg");

    @FXML
    private Rectangle rec3;
    Image img3 = new Image("/recursos/durant.jpg");

    @FXML
    private Rectangle rec4;
    Image img4 = new Image("/recursos/curry.jpg");

        @FXML
    private Rectangle news;
        Image noticia = new Image("/recursos/warriors.png");
        
    // </editor-fold>

    ControladorLogin contLog = new ControladorLogin();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setimg(rec1, img);
        setimg(rec2, img2);
        setimg(rec3, img3);
        setimg(rec4, img4);
        setimg(news, noticia);
    }

    public void goManager(ActionEvent event){
        Scene scene = App.scene1;

        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
        
        contLog.moverEscena(scene, stage);
        stage.centerOnScreen();

        stage.show();
    }
    
    public void lebron(){
        System.out.println("hola");
    }
    
    public void goHome(ActionEvent event){
        Scene scene = App.pantallas.get("inicio");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
    }

    
    private void setimg(Rectangle rec1, Image img1) {
        rec1.setArcWidth(30.0);   // Corner radius
        rec1.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(img1);

        rec1.setFill(pattern);
        rec1.setEffect(new DropShadow(20, Color.BLACK));    
    }
}

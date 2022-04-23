/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorLogin;

import edu.martin.dida.proyectofinciclo.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author marti
 */
public class ControladorLogin implements Initializable{
        @FXML
    private Button btnExit;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void signUp(ActionEvent event) throws IOException{

        
        
        
        Stage stage = new Stage();
        Scene scene = App.pantallas.get("Register");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.initStyle(StageStyle.UNDECORATED);
        moverEscena(scene, stage);

        stage.centerOnScreen();

        stage.show();
    }
    
    
    public void minimizeApp(ActionEvent event){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
        
    public void exitApp(){
        System.exit(0);
    }

    private void moverEscena(Scene escena, Stage stage) {
        escena.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneX();
            }
            
        });
        
        escena.setOnMousePressed(pressEvent -> {
            escena.setOnMouseDragged(drag -> {
                stage.setX(drag.getScreenX() - pressEvent.getSceneX());
                stage.setY(drag.getScreenY() - pressEvent.getSceneY());
            });
        });    }
}

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
}

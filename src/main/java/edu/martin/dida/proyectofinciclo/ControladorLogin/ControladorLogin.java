/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorLogin;

import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.admin.ControladorAdmin;
import edu.martin.dida.proyectofinciclo.table.ControladorTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author marti
 */
public class ControladorLogin {

    @FXML
    private TextField txtUsername;
    
    @FXML
    private Button btnMinimize;

    @FXML
    private PasswordField txtPassword;

    UsuariosDAO usersDAO;
    public static String nombre;
    private double xOffset = 0;
    private double yOffset = 0;
    

    
    public void signUp(ActionEvent event) throws IOException{

        Scene scene = App.pantallas.get("Register");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);
        moverEscena(scene, stage);
        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
    }
    
    public void acceder( ActionEvent event) throws IOException{
        usersDAO = new UsuariosDAO();
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "The fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
        
        int validate = usersDAO.validateLogin(txtUsername.getText(), txtPassword.getText());
        
        
        
        if(validate==1){
            nombre = txtUsername.getText();
                Scene scene = App.pantallas.get("inicio");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene.setFill(Color.TRANSPARENT);
            moverEscena(scene, stage);

            stage.setScene(scene);
            stage.centerOnScreen();

            stage.centerOnScreen();

            stage.show();
            
        }else if (validate==2){
            
            Scene scene = App.scene2;
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene.setFill(Color.TRANSPARENT);
            moverEscena(scene, stage);

            stage.setScene(scene);
            stage.centerOnScreen();

            stage.centerOnScreen();

            stage.show();        
            
            App.controller1.cargarUser();
            App.controller1.cargarAdmin();
            App.controller1.numberUser();
        }else if(validate==3){
            JOptionPane.showMessageDialog(new JFrame(), "This user does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }
    
    
    public void minimizeApp(ActionEvent event){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
        
    public void exitApp(){
        System.exit(0);
    }

    public void moverEscena(Scene escena, Stage stage) {
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
        });    
    }
    
    
    public void hrefFacebook() throws IOException{
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.facebook.com/"));
    }

    public void hrefInstagram() throws IOException{
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.instagram.com/"));
    }
        
    public void hrefTwitter() throws IOException{
        java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.twitter.com/"));
    }  
}

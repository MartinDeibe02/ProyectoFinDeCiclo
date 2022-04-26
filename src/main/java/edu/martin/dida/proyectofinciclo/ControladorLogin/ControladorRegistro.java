/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorLogin;

import Utilidades.Logger;
import Utilidades.SendMessage;
import edu.martin.dida.proyectofinciclo.App;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author marti
 */
public class ControladorRegistro {
        
    @FXML
    private Button cancel;
    
    @FXML
    private Button btnMinimize;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword1;

    @FXML
    private TextField txtUsername;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    public void exitRegister(ActionEvent event){
        Scene scene = App.pantallas.get("login");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
        

        moverEscena(scene, stage);

        stage.centerOnScreen();

        stage.show();
    }
    
    public void register() throws IOException{
        if(!(txtUsername.getText().equals("") || txtEmail.getText().equals("") || txtDate.getValue().equals("") || txtPassword.getText().equals("")|| txtPassword1.getText().equals(""))){
            if(checkmail(txtEmail.getText())==true){
                if(txtPassword.getText().equals(txtPassword1.getText())){
                    Logger.logear().logInfo("Registrado Satisfactoriamente", 1);
                    SendMessage.send(txtEmail.getText());
                    Logger.logear().logInfo("Correo mandado a " + txtEmail.getText(), 2);
                    txtEmail.clear();
                }else{
                              JOptionPane.showMessageDialog(new JFrame(), "Las contraseñas no coinciden", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "The email is incorrect", "Error",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Todos los campos deben estar cubiertos", "Error",JOptionPane.ERROR_MESSAGE);
        }
        


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
    
    public void minimizeApp(ActionEvent event){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
        
    public void exitApp(){
        System.exit(0);
    }

    private boolean checkmail(String mail){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(mail);
        boolean match = matcher.matches();
        
        return match;
        
    }
}

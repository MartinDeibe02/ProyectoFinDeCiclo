/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorLogin;

import POJO.Usuario;
import Utilidades.Logger;
import Utilidades.SendMessage;
import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author marti
 */
public class ControladorRegistro implements Initializable{
        
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
    
    public static UsuariosDAO userDAO;
    SendMessage sendMessage;
    private int id;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userDAO = new UsuariosDAO();
            sendMessage = new SendMessage();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ControladorRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
                    
                    LocalDate date = txtDate.getValue();
                    String dateConverted = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    
                    Usuario user = new Usuario(id,txtUsername.getText(), txtEmail.getText(), dateConverted, txtPassword.getText());
                    
                    ExecutorService service = Executors.newFixedThreadPool(4);
                        service.submit(new Runnable() {
                            public void run() {
                                sendMessage.send(txtEmail.getText());
                            }
                        });
                    
                    userDAO.insertLogin(user);
                    
                    Logger.logear().logInfo("Email sent to " + txtEmail.getText(), 1);
                    clearFields();
                    JOptionPane.showMessageDialog(new JFrame(), "Register succesfully", "Error",JOptionPane.INFORMATION_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "Password must match", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "The email is incorrect", "Error",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "All fields must be covered", "Error",JOptionPane.ERROR_MESSAGE);
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

    private void clearFields() {
        txtEmail.clear();    
        txtPassword.clear();
        txtDate.setValue(null);
        txtUsername.clear();
        txtPassword1.clear();
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

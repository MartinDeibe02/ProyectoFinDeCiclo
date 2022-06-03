/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.admin;

import POJO.Usuario;
import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorRegistro;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Markyuu
 */
public class ControladorAdmin {
    
    
    @FXML
    private Button btnMinimize;
    
    @FXML
    private TableView tableuser;
    
    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUser;
    
    @FXML
    private CheckBox checkAdmin;

    @FXML
    private Text lblCountUser;

    @FXML
    private TableView tableadmin;
    
    
    
    
    boolean decryptBool = true;
    int id;
    


    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    
    
    
    public void cargarUser() throws IOException {
        ObservableList<Usuario> users = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarUser();
        users.addAll(user);
        tableuser.setItems(users);
    }
        
    public void cargarAdmin() throws IOException {
        ObservableList<Usuario> users = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarAdmin();
        users.addAll(user);
        tableadmin.setItems(users);
    }
    

    
    
    public void editUser() throws IOException{
        Usuario user = (Usuario) tableuser.getSelectionModel().getSelectedItem();
        Usuario admin = (Usuario) tableadmin.getSelectionModel().getSelectedItem();

        if(!(user == null)){
            txtUser.setText(user.getUser());
            txtEmail.setText(user.getEmail());
            txtPassword.setText(encrypt(user.getPassword()));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date = user.getDateBirth();
            LocalDate localDate = LocalDate.parse(date, formatter);
            txtDate.setValue(localDate);
            
            if(user.getPermisos().equals("user")){
                checkAdmin.setSelected(false);
            }
            id = user.getId();
        
         }else if(user==null && admin!=null){
            txtUser.setText(admin.getUser());
            txtEmail.setText(admin.getEmail());
            txtPassword.setText(encrypt(admin.getPassword()));
            
            
            
            if(admin.getPermisos().equals("admin")){
                checkAdmin.setSelected(true);
            }
            id = admin.getId();
            
            
            
        }else if (admin == null && user == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a user", "Error", JOptionPane.ERROR_MESSAGE);        
        }
            
        decryptBool = true;
      }
    
    public void insertUser() throws IOException{
        Usuario user = new Usuario();
        
        if(!(txtUser.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDate.getValue() == null || txtPassword.getText().isEmpty())){
            if(checkmail(txtEmail.getText())){
                user.setId(id);
                user.setUser(txtUser.getText());
                user.setEmail(txtEmail.getText());

                LocalDate date = txtDate.getValue() ;
                String convertirFecha = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                user.setDateBirth(convertirFecha); 

                user.setPassword(txtPassword.getText());

                if(checkAdmin.isSelected()){
                    user.setPermisos("admin");
                }else{
                    user.setPermisos("user");
                }


                ControladorRegistro.userDAO.saveEdit(user);

                cargarAdmin();
                cargarUser();
                clearFieldsUser();
                id=0;
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "Email not valid", "Error", JOptionPane.ERROR_MESSAGE);        
            }
            
        
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "All fields must be covered", "Error", JOptionPane.ERROR_MESSAGE);        
        }
        
    }

    public void deleteUser() throws IOException{
        Usuario user = (Usuario) tableuser.getSelectionModel().getSelectedItem();
        Usuario admin = (Usuario) tableadmin.getSelectionModel().getSelectedItem();

        if(!(user == null)){
            ControladorRegistro.userDAO.deleteUser(user);
            cargarUser();
        }else if(user==null && admin!=null){
            ControladorRegistro.userDAO.deleteUser(user);
            cargarAdmin();
        }else if(admin == null && user == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a user", "Error", JOptionPane.ERROR_MESSAGE);        
        }

    }
    
    public void numberUser() throws IOException{
        lblCountUser.setText(String.valueOf(ControladorRegistro.userDAO.countUsers()));
    }
    
    
    public void desencriptar(){
        if(decryptBool){
                if(!txtPassword.getText().isEmpty()){
                    txtPassword.setText(decrypt(txtPassword.getText()));
                }
                decryptBool=false;
        }else{
            if(!txtPassword.getText().isEmpty()){
                    txtPassword.setText(encrypt(txtPassword.getText()));
                }
            decryptBool = true;
        }

    }
    
    private static String decrypt(String pass){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(pass);
                 
        return new String(bytes);
    }
    
    private String encrypt(String pass){
        Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(pass.getBytes());
 
            return encodedString;
        }
    
    private boolean checkmail(String mail){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(mail);
        boolean match = matcher.matches();
        
        return match;
        
    }
    
    private void clearFieldsUser(){
        txtUser.clear();
        txtEmail.clear();
        txtDate.setValue(null);
        txtPassword.clear();
    }
}

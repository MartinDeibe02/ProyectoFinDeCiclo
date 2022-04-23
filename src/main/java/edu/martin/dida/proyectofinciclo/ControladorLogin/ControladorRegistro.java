/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorLogin;

import Utilidades.Logger;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author marti
 */
public class ControladorRegistro {
        
    @FXML
    private Button cancel;
    
    public void exitRegister(){
           Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close(); 
    }
    
    public void register() throws IOException{
        Logger.logear().logInfo("Registrado Satisfactoriamente");
    }
}

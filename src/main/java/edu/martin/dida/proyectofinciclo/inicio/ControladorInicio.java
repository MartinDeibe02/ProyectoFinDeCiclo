/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.inicio;

import edu.martin.dida.proyecto.conexion.PlayersDAO;
import edu.martin.dida.proyecto.conexion.TeamsDAO;
import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import edu.martin.dida.proyectofinciclo.table.ControladorTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
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

        
     @FXML
     MenuButton salir;
        
    ControladorLogin contLog = new ControladorLogin();
    ControladorTabPane contMan = new ControladorTabPane();
    public static TeamsDAO teamdao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setimg(rec1, img);
        setimg(rec2, img2);
        setimg(rec3, img3);
        setimg(rec4, img4);
        setimg(news, noticia);

    }

    public void goManager(ActionEvent event) throws IOException{
        Scene scene = App.scene3;

        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
        
        contLog.moverEscena(scene, stage);
        stage.centerOnScreen();
        
        stage.show();
        
    teamdao = new TeamsDAO();
        PlayersDAO dao = new PlayersDAO();


    App.controller.setLblNbaAge();
    App.controller.setLblNbaCount();
    teamdao.insertFreeAgent();
    App.controller.showPie();
    App.controller.cargarPlayer();
    App.controller.loadFilter(); 
    App.controller.cargarTeams();
    App.controller.comboTeam.setItems(contMan.chargeTeamsCombo(contLog.nombre));
    App.controller.combodash.setItems(contMan.chargeTeamsCombo(contLog.nombre));

    }
    
    
    public void logOut(ActionEvent event) throws IOException{
        UsuariosDAO.updateStatusOffline(ControladorLogin.nombre);
        
         Scene scene = App.pantallas.get("login");
            Stage stage = (Stage) salir.getScene().getWindow();
                    scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.centerOnScreen();

            stage.centerOnScreen();

            stage.show();
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
    
    public void goNews(ActionEvent event){
        Scene scene = App.pantallas.get("news");
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
    
    public void exit() throws IOException{
        UsuariosDAO.updateStatusOffline(ControladorLogin.nombre);

        System.exit(0);
    }
}

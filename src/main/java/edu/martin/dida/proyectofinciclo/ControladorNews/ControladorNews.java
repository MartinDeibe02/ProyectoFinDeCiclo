/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.ControladorNews;

import edu.martin.dida.proyecto.conexion.TeamsDAO;
import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import static edu.martin.dida.proyectofinciclo.inicio.ControladorInicio.teamdao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Markyuu
 */
public class ControladorNews implements Initializable{
    @FXML
    private Rectangle rec1;

    @FXML
    private Rectangle rec2;

    @FXML
    private Rectangle rec3;

    @FXML
    MenuButton salir;
    
    Image img1 = new Image("/recursos/new1.png");
    Image img2 = new Image("/recursos/new2.jpg");
    Image img3 = new Image("/recursos/new3.jpg");

    
    
    
    
    private void setimg(Rectangle rec1, Image img1) {
        rec1.setArcWidth(30.0);   // Corner radius
        rec1.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(img1);

        rec1.setFill(pattern);
        rec1.setEffect(new DropShadow(20, Color.BLACK));    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setimg(rec1, img1);
        setimg(rec2, img2);
        setimg(rec3, img3);
    }
    
    public void goManager(ActionEvent event){
        Scene scene = App.pantallas.get("manager");
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
        
        
    public void goHome(ActionEvent event){
        Scene scene = App.pantallas.get("inicio");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
    }
    
    public void exit() throws IOException{
        UsuariosDAO.updateStatusOffline(ControladorLogin.nombre);

        System.exit(0);
    }
    
    public void openNew1(ActionEvent event){
        Scene scene = App.scene1;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
        
        App.controllerNew.setimg(App.controllerNew.recnew, img1);
        App.controllerNew.txtnew.setText("La NBA festeja su 75 aniversario con un curso que celebra el regreso a la viaja normalidad después de ir dejando atrás las restricciones y modificaciones a las que obligó la pandemia. Ricky Rubio (Cavaliers), Willy Hernangómez (Pelicans), Juancho Hernangómez (Celtics), Serge Ibaka (Clippers) y los debutantes Usman Garuba (Rockets) y Santi Aldama (Grizzlies) parten con distintos y apasionantes objetivos.");

    }    
    
    public void openNew2(ActionEvent event){
        Scene scene = App.scene1;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
        
        App.controllerNew.setimg(App.controllerNew.recnew, img2);
        App.controllerNew.txtnew.setText("Según la revista Forbes, James se convirtió en el tercer jugador de la NBA que llega a los 1,000 millones de dólares en ingresos y el primero en hacerlo en activo, ya que Michael Jordan y Kobe Bryant lo consiguieron tras dejar el baloncesto.");

    }
    
    public void openNew3(ActionEvent event){
        Scene scene = App.scene1;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
        
        App.controllerNew.setimg(App.controllerNew.recnew, img3);
        App.controllerNew.txtnew.setText("Como era de esperar, Scottie Barnes (Toronto Raptors), Cade Cunningham (Detroit Pistons) y Evan Mobley (Clevelad Cavaliers) han sido los más votados, logrando los 100 votos necesarios para hacer pleno. Barnes se llevó sorpresivamente el premio al Rookie del Año por delante de sus dos rivales y figura, igual que ellos, en el Mejor Quinteto de Rookies. Acompañan a los tres mosqueteros Jalen Green (Houston Rockets), número 1 del pasado draft (los 4 primeros han sido seleccionados en este quinteto) y Franz Wagner, que ha cuajado un gran curso en los Magic.");

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
}

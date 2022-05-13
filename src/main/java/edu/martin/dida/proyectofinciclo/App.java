package edu.martin.dida.proyectofinciclo;

import com.sun.javafx.util.Utils;
import edu.martin.dida.proyectofinciclo.table.ControladorTabPane;
import edu.martin.dida.proyectofinciclo.table.ThreadRun;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene1;
    public static HashMap<String, Scene> pantallas = new HashMap<String, Scene>();
    
    private double xOffset = 0;
    private double yOffset = 0;
        public static MulticastSocket ms = null;
    public static InetAddress grupo = null;
    public static byte[] buf = new byte[1000];
    public static int port = 12345;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        ms = new MulticastSocket(port);
            grupo = InetAddress.getByName("239.192.0.1");
            ms.joinGroup(grupo);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("table/EquipoTabla.fxml"));
            Parent root = loader.load();
            ControladorTabPane controller = (ControladorTabPane) loader.getController();   
            scene1 = new Scene(root);

            ThreadRun a = new ThreadRun(controller);
            Thread t = new Thread(a, "hola");

            t.start();
            
        
        Parent login = FXMLLoader.load(getClass().getResource("login/LogIn.fxml"));
        Parent register = FXMLLoader.load(getClass().getResource("login/Register.fxml"));
        Parent inicio = FXMLLoader.load(getClass().getResource("inicio/inicio.fxml"));
        Parent manager = FXMLLoader.load(getClass().getResource("table/EquipoTabla.fxml"));

        pantallas.put("inicio", new Scene(inicio,1100,750));
        pantallas.put("login", new Scene(login,1050,850));
        pantallas.put("Register", new Scene(register,1050,850));
        pantallas.put("manager", new Scene(manager,1100,750));

        Scene escena = pantallas.get("login");
        
        primaryStage.initStyle(StageStyle.UNDECORATED);
        escena.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(escena);
        
        
        
        
        escena.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneX();
            }
            
        });
        
        escena.setOnMousePressed(pressEvent -> {
            escena.setOnMouseDragged(drag -> {
                primaryStage.setX(drag.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(drag.getScreenY() - pressEvent.getSceneY());
            });
        });
        
        primaryStage.show();
    }
    



    public static void main(String[] args) {
        launch();
    }

}
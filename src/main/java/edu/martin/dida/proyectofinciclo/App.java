package edu.martin.dida.proyectofinciclo;

import com.sun.javafx.util.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static HashMap<String, Scene> pantallas = new HashMap<String, Scene>();
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login/LogIn.fxml"));
        Parent register = FXMLLoader.load(getClass().getResource("login/Register.fxml"));

        pantallas.put("login", new Scene(login,1050,850));
        pantallas.put("Register", new Scene(register,1050,850));
        
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
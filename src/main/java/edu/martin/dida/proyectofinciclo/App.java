package edu.martin.dida.proyectofinciclo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static HashMap<String, Scene> pantallas = new HashMap<String, Scene>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login/LogIn.fxml"));
        pantallas.put("login", new Scene(login,1050,600));

        
        Scene escena = pantallas.get("login");
        
        primaryStage.setScene(escena);
        
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch();
    }

}

package edu.martin.dida.proyectofinciclo;

import edu.martin.dida.proyectofinciclo.ControladorNews.OpenNew;
import edu.martin.dida.proyectofinciclo.admin.ControladorAdmin;
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
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene1;
    public static Scene scene2;
    public static Scene scene3;
    public static HashMap<String, Scene> pantallas = new HashMap<String, Scene>();
    
    private double xOffset = 0;
    private double yOffset = 0;
    
        public static MulticastSocket ms = null;
    public static InetAddress grupo = null;
    public static byte[] buf = new byte[1000];
    public static int port = 12345;
    

    public static ControladorTabPane controller;
    public static ControladorAdmin controller1;
    public static OpenNew controllerNew;
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        ms = new MulticastSocket(port);
            grupo = InetAddress.getByName("239.192.0.1");
            ms.joinGroup(grupo);
            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("table/EquipoTabla.fxml"));
            Parent root = loader.load();
            controller = (ControladorTabPane) loader.getController();
            scene3 = new Scene(root);            
            
            FXMLLoader loaderNew = new FXMLLoader(getClass().getResource("news/OpenNew.fxml"));
            Parent rootNew = loaderNew.load();
            controllerNew = (OpenNew) loaderNew.getController();
            scene1 = new Scene(rootNew);
            
            
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("admin/Admin.fxml"));
            Parent root1 = loader1.load();
            controller1 = (ControladorAdmin) loader1.getController();
            scene2 = new Scene(root1);

            ThreadRun a = new ThreadRun(controller);
            Thread t = new Thread(a, "hola");
            t.start();
            

            
            
            

            
        
        Parent login = FXMLLoader.load(getClass().getResource("login/LogIn.fxml"));
        Parent register = FXMLLoader.load(getClass().getResource("login/Register.fxml"));
        Parent inicio = FXMLLoader.load(getClass().getResource("inicio/inicio.fxml"));
        Parent manager = FXMLLoader.load(getClass().getResource("table/EquipoTabla.fxml"));
        Parent admin = FXMLLoader.load(getClass().getResource("admin/Admin.fxml"));
        Parent news = FXMLLoader.load(getClass().getResource("news/news.fxml"));
        Parent openNew = FXMLLoader.load(getClass().getResource("news/OpenNew.fxml"));
        
        pantallas.put("inicio", new Scene(inicio,1300,900));
        pantallas.put("login", new Scene(login,1050,850));
        pantallas.put("Register", new Scene(register,1050,850));
        pantallas.put("manager", new Scene(manager,1300,900));
        pantallas.put("admin", new Scene(admin,1050,850));
        pantallas.put("news", new Scene(news,1300,900));
        pantallas.put("OpenNew", new Scene(openNew,1300,900));

        
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